package com.zacharywarunek.financialfuture.registration.token;

import com.zacharywarunek.financialfuture.account.Account;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

  private final ConfirmationTokenRepo confirmationTokenRepo;

  public void saveConfirmationToken(ConfirmationToken token) {
    confirmationTokenRepo.save(token);
  }

  public Optional<ConfirmationToken> getToken(String token) {
    return confirmationTokenRepo.findByToken(token);
  }

  public void setConfirmedAt(String token) {
    confirmationTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
  }

  public void deleteAllAtAccount(Account account) {
    confirmationTokenRepo.deleteAllByAccountId(account);
  }
}
