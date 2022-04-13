package io.financialfuture.passwordreset;

import io.financialfuture.account.Account;
import io.financialfuture.account.AccountInfo;
import io.financialfuture.account.AccountService;
import io.financialfuture.email.EmailService;
import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import io.financialfuture.passwordreset.token.PasswordResetToken;
import io.financialfuture.passwordreset.token.PasswordResetTokenService;
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
public class PasswordResetService {
  private final AccountService accountService;
  private final PasswordResetTokenService passwordResetTokenService;
  private final EmailService emailService;

  public Map<String, Object> getAccountDetailsFromToken(String token) {
    Map<String, Object> map = new HashMap<>();
    Optional<PasswordResetToken> passwordResetTokenOptional =
        passwordResetTokenService.getToken(token);
    if (passwordResetTokenOptional.isEmpty()) {
      map.put("data", "Invalid Token");
    } else {
      PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
      if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
        map.put("data", "Expired Token");
      else {
        map.put("data", "Successful");
      }
      map.put(
          "account",
          new AccountInfo(
              passwordResetToken.getAccount().getFirstName(),
              passwordResetToken.getAccount().getLastName(),
              passwordResetToken.getAccount().getUsername()));
    }
    return map;
  }

  public void sendEmail(String username) throws EntityNotFoundException {
    Account account = accountService.findByUsername(username);
    String token = UUID.randomUUID().toString();
    PasswordResetToken passwordResetToken =
        new PasswordResetToken(
            token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), account);

    passwordResetTokenService.savePasswordResetToken(passwordResetToken);

    String link = System.getenv("FRONT_END_URL") + "/passwordreset?token=" + token;
    try {
      emailService.send(username, buildEmail(account.getFirstName(), link), "Password reset requested");
    }
    catch (FileNotFoundException ignored){}
  }

  public Map<String, Object> changeUserPassword(String token, String password)
      throws BadRequestException {
    Map<String, Object> map = new HashMap<>();
    Optional<PasswordResetToken> passwordResetTokenOptional =
        passwordResetTokenService.getToken(token);
    if (passwordResetTokenOptional.isEmpty()) {
      map.put("data", "Invalid Token");
    } else {
      PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();
      if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
        map.put("data", "Expired Token");
      else {
        map.put("data", "Successful");
        accountService.changePassword(passwordResetToken.getAccount(), password);
        passwordResetTokenService.setUsedAt(token);
      }
    }
    return map;
  }

  private String buildEmail(String name, String link) throws FileNotFoundException {
    StringBuilder contentBuilder = new StringBuilder();
    try {
      BufferedReader in = new BufferedReader(new FileReader("src/main/resources/html/ForgotPassword.html"));
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
