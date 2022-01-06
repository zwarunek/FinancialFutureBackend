package com.zacharywarunek.financialfuture.passwordreset.token;

import com.zacharywarunek.financialfuture.account.Account;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordResetTokenService {

  private final PasswordResetTokenRepo passwordResetTokenRepo;

  public void savePasswordResetToken(PasswordResetToken token) {
    passwordResetTokenRepo.save(token);
  }

  public Optional<PasswordResetToken> getToken(String token) {
    return passwordResetTokenRepo.findByToken(token);
  }

  public void setUsedAt(String token) {
    passwordResetTokenRepo.updateUsedAt(token, LocalDateTime.now());
  }

  public void deleteAllAtAccount(Account account) {
    passwordResetTokenRepo.deleteAllByAccountId(account);
  }
}
