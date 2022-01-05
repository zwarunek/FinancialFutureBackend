package com.zacharywarunek.financialfuture.account;

import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.ACCOUNT_NOT_FOUND;
import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.NULL_VALUES;
import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.USERNAME_NOT_FOUND;
import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.USERNAME_TAKEN;

import com.zacharywarunek.financialfuture.config.JwtUtil;
import com.zacharywarunek.financialfuture.exceptions.BadRequestException;
import com.zacharywarunek.financialfuture.exceptions.EntityNotFoundException;
import com.zacharywarunek.financialfuture.exceptions.ExceptionResponses;
import com.zacharywarunek.financialfuture.exceptions.UnauthorizedException;
import com.zacharywarunek.financialfuture.exceptions.UsernameTakenException;
import com.zacharywarunek.financialfuture.passwordreset.token.PasswordResetTokenService;
import com.zacharywarunek.financialfuture.registration.token.ConfirmationToken;
import com.zacharywarunek.financialfuture.registration.token.ConfirmationTokenService;
import com.zacharywarunek.financialfuture.util.AuthRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

  protected final Log logger = LogFactory.getLog(getClass());
  private final PasswordEncoder passwordEncoder;
  private final ConfirmationTokenService confirmationTokenService;
  private final PasswordResetTokenService passwordResetTokenService;
  AccountRepo accountRepo;
  JwtUtil jwtUtil;

  public Account findById(Long account_id) throws EntityNotFoundException {
    return accountRepo
        .findById(account_id)
        .orElseThrow(
            () -> new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND.label, account_id)));
  }

  public Account findByUsername(String username) throws EntityNotFoundException {
    return accountRepo
        .findAccountByUsername(username)
        .orElseThrow(
            () -> new EntityNotFoundException(String.format(ACCOUNT_NOT_FOUND.label, username)));
  }

  public List<Account> getAll() {
    return accountRepo.findAll();
  }

  public Account create(Account account) throws BadRequestException, UsernameTakenException {
    if (account.getPassword() == null
        || account.getUsername() == null
        || account.getLastName() == null
        || account.getFirstName() == null)
      throw new BadRequestException(ExceptionResponses.NULL_VALUES.label);
    if (accountRepo.findAccountByUsername(account.getUsername()).isPresent())
      throw new UsernameTakenException(
          String.format(ExceptionResponses.USERNAME_TAKEN.label, account.getUsername()));

    account.setPassword(passwordEncoder.encode(account.getPassword()));
    return accountRepo.save(account);
  }

  public String authenticate(AuthRequest authRequest)
      throws BadRequestException, UnauthorizedException {
    if (authRequest.getUsername() == null || authRequest.getPassword() == null)
      throw new BadRequestException(NULL_VALUES.label);
    Optional<Account> accountOptional =
        accountRepo.findAccountByUsername(authRequest.getUsername());
    if (accountOptional.isEmpty()
        || !passwordEncoder.matches(authRequest.getPassword(), accountOptional.get().getPassword()))
      throw new UnauthorizedException("Unauthorized");
    return jwtUtil.generateToken(accountOptional.get());
  }

  @Transactional
  public Account update(Long account_id, AccountDetails accountDetails)
      throws EntityNotFoundException, UsernameTakenException {
    Account account = findById(account_id);
    if (accountDetails.getUsername() != null
        && !accountDetails.getUsername().equals(account.getUsername()))
      if (accountRepo.findAccountByUsername(accountDetails.getUsername()).isPresent())
        throw new UsernameTakenException(
            String.format(USERNAME_TAKEN.label, accountDetails.getUsername()));
      else account.setUsername(accountDetails.getUsername());
    if (accountDetails.getPassword() != null)
      account.setPassword(passwordEncoder.encode(accountDetails.getPassword()));
    if (accountDetails.getFirstName() != null
        && !accountDetails.getFirstName().equals(account.getFirstName()))
      account.setFirstName(accountDetails.getFirstName());
    if (accountDetails.getFirstName() != null
        && !accountDetails.getLastName().equals(account.getLastName()))
      account.setLastName(accountDetails.getLastName());
    return account;
  }

  @Transactional
  public Account changePassword(Account account, String password) throws BadRequestException {
    if (password != null)
      account.setPassword(passwordEncoder.encode(password));
    else
      throw new BadRequestException(ExceptionResponses.NULL_VALUES.label);
    return account;
  }

  public void delete(Long account_id) throws EntityNotFoundException {
    Account account = findById(account_id);
    confirmationTokenService.deleteAllAtAccount(account);
    passwordResetTokenService.deleteAllAtAccount(account);
    accountRepo.deleteById(account.getId());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account =
        accountRepo
            .findAccountByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format(USERNAME_NOT_FOUND.name(), username)));
    return new User(account.getUsername(), account.getPassword(), account.getAuthorities());
  }

  public void enable(String username) {
    accountRepo.enableAccount(username);
  }
}
