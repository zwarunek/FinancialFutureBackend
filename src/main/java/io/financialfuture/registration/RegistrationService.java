package io.financialfuture.registration;

import io.financialfuture.account.Account;
import io.financialfuture.account.AccountInfo;
import io.financialfuture.account.AccountRepo;
import io.financialfuture.account.AccountRole;
import io.financialfuture.account.AccountService;
import io.financialfuture.email.EmailService;
import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import io.financialfuture.exceptions.UsernameTakenException;
import io.financialfuture.registration.token.ConfirmationToken;
import io.financialfuture.registration.token.ConfirmationTokenService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final AccountService accountService;
  private final AccountRepo accountRepo;
  private final ConfirmationTokenService confirmationTokenService;
  private final EmailService emailService;

  public void register(RegistrationRequest request)
      throws UsernameTakenException, BadRequestException, EntityNotFoundException {
    accountService.create(
        new Account(
            request.getFirstName(),
            request.getLastName(),
            request.getUsername(),
            request.getPassword(),
            AccountRole.ROLE_USER));
    sendEmail(request.getUsername());
  }

  public void sendEmail(String username) throws EntityNotFoundException {

    Account account = accountService.findByUsername(username);
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken =
        new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            account);
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    String link = System.getenv("FRONT_END_URL") + "/register/confirm?token=" + token;
    try {
      emailService.send(username, buildEmail(account.getFirstName(), link), "Confirm your email");
    }
    catch (FileNotFoundException ignored){}
  }

  public Map<String, Object> confirmToken(String token) throws BadRequestException {
    Map<String, Object> map = new HashMap<>();
    Optional<ConfirmationToken> confirmationTokenOptional =
        confirmationTokenService.getToken(token);
    if (confirmationTokenOptional.isEmpty()) {
      map.put("data", "Invalid Token");
    } else {
      ConfirmationToken confirmationToken = confirmationTokenOptional.get();
      if (confirmationToken.getExpires_at().isBefore(LocalDateTime.now()))
        map.put("data", "Expired Token");
      else {
        map.put("data", "Successful");
        confirmationTokenService.setConfirmedAt(token);
        accountService.enable(confirmationToken.getAccount().getUsername());
      }
      map.put(
          "account",
          new AccountInfo(
              confirmationToken.getAccount().getFirstName(),
              confirmationToken.getAccount().getLastName(),
              confirmationToken.getAccount().getUsername()));
    }
    return map;
  }

  private String buildEmail(String name, String link) throws FileNotFoundException {
    StringBuilder contentBuilder = new StringBuilder();
    try {
      BufferedReader in = new BufferedReader(new FileReader("src/main/resources/html/Register.html"));
      String str;
      while ((str = in.readLine()) != null) {
        contentBuilder.append(str);
      }
      in.close();
    } catch (IOException e) {
    }
    String content = contentBuilder.toString();
    content = content.replace("${name}", name);
    content = content.replace("${link}", link);
    System.out.println(content);
    return content;
  }
}
