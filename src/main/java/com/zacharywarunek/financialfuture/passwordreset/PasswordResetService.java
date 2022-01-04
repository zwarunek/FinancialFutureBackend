package com.zacharywarunek.financialfuture.passwordreset;

import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.EMAIL_ALREADY_CONFIRMED;
import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.EXPIRED_TOKEN;
import static com.zacharywarunek.financialfuture.exceptions.ExceptionResponses.INVALID_TOKEN;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.account.AccountService;
import com.zacharywarunek.financialfuture.email.EmailService;
import com.zacharywarunek.financialfuture.exceptions.BadRequestException;
import com.zacharywarunek.financialfuture.exceptions.EntityNotFoundException;
import com.zacharywarunek.financialfuture.exceptions.ExpiredTokenException;
import com.zacharywarunek.financialfuture.exceptions.InvalidTokenException;
import com.zacharywarunek.financialfuture.passwordreset.token.PasswordResetToken;
import com.zacharywarunek.financialfuture.passwordreset.token.PasswordResetTokenService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PasswordResetService {
  private final AccountService accountService;
  private final PasswordResetTokenService passwordResetTokenService;
  private final EmailService emailService;

  public Map<String, String> getAccountDetailsFromToken(String token)
      throws InvalidTokenException, ExpiredTokenException {
    PasswordResetToken passwordResetToken =
        passwordResetTokenService
            .getToken(token)
            .orElseThrow(() -> new InvalidTokenException(INVALID_TOKEN.label));
    if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
      throw new ExpiredTokenException(EXPIRED_TOKEN.label);
    Map<String, String> map = new HashMap<>();
    map.put("firstName", passwordResetToken.getAccount().getFirstName());
    map.put("lastName", passwordResetToken.getAccount().getLastName());
    map.put("username", passwordResetToken.getAccount().getUsername());
    return map;
  }

  public void passwordReset(String username) throws EntityNotFoundException {

    Account account = accountService.findByUsername(username);

    String token = UUID.randomUUID().toString();
    PasswordResetToken passwordResetToken =
        new PasswordResetToken(
            token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), account);

    passwordResetTokenService.savePasswordResetToken(passwordResetToken);

    String link = System.getenv("FRONT_END_URL") + "/passwordreset?token=" + token;
    emailService.send(username, buildEmail(account.getFirstName(), link));
  }

  @Transactional
  public PasswordResetToken useToken(String token)
      throws BadRequestException, EntityNotFoundException, InvalidTokenException,
          ExpiredTokenException {
    PasswordResetToken passwordResetToken =
        passwordResetTokenService
            .getToken(token)
            .orElseThrow(() -> new InvalidTokenException(INVALID_TOKEN.label));
    if (passwordResetToken.getCreatedAt().isAfter(LocalDateTime.now()))
      throw new InvalidTokenException(INVALID_TOKEN.label);
    accountService.findByUsername(passwordResetToken.getAccount().getUsername());
    if (passwordResetToken.getUsedAt() != null)
      throw new BadRequestException(EMAIL_ALREADY_CONFIRMED.label);
    if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
      throw new ExpiredTokenException(EXPIRED_TOKEN.label);
    return passwordResetToken;
  }

  public void changeUserPassword(String token, String password)
      throws InvalidTokenException, ExpiredTokenException, BadRequestException {
    PasswordResetToken passwordResetToken =
        passwordResetTokenService
            .getToken(token)
            .orElseThrow(() -> new InvalidTokenException(INVALID_TOKEN.label));
    if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
      throw new ExpiredTokenException(EXPIRED_TOKEN.label);
    accountService.changePassword(passwordResetToken.getAccount(), password);
    passwordResetTokenService.setUsedAt(token);
  }

  private String buildEmail(String name, String link) {
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
        + "\n"
        + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n"
        + "\n"
        + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
        + "    <tbody><tr>\n"
        + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n"
        + "        \n"
        + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
        + "          <tbody><tr>\n"
        + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
        + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        + "                  <tbody><tr>\n"
        + "                    <td style=\"padding-left:10px\">\n"
        + "                  \n"
        + "                    </td>\n"
        + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
        + "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n"
        + "                    </td>\n"
        + "                  </tr>\n"
        + "                </tbody></table>\n"
        + "              </a>\n"
        + "            </td>\n"
        + "          </tr>\n"
        + "        </tbody></table>\n"
        + "        \n"
        + "      </td>\n"
        + "    </tr>\n"
        + "  </tbody></table>\n"
        + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        + "    <tbody><tr>\n"
        + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
        + "      <td>\n"
        + "        \n"
        + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        + "                  <tbody><tr>\n"
        + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
        + "                  </tr>\n"
        + "                </tbody></table>\n"
        + "        \n"
        + "      </td>\n"
        + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n"
        + "    </tr>\n"
        + "  </tbody></table>\n"
        + "\n"
        + "\n"
        + "\n"
        + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        + "    <tbody><tr>\n"
        + "      <td height=\"30\"><br></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
        + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
        + "        \n"
        + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
        + name
        + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
        + link
        + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
        + "        \n"
        + "      </td>\n"
        + "      <td width=\"10\" valign=\"middle\"><br></td>\n"
        + "    </tr>\n"
        + "    <tr>\n"
        + "      <td height=\"30\"><br></td>\n"
        + "    </tr>\n"
        + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n"
        + "\n"
        + "</div></div>";
  }

}
