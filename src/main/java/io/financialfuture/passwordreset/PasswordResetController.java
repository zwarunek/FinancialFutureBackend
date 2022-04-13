package io.financialfuture.passwordreset;

import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/passwordreset")
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  @PostMapping
  public ResponseEntity<Object> sendEmail(@RequestBody Map<String, String> data) {
    try {
      passwordResetService.sendEmail(data.get("username"));
    } catch (EntityNotFoundException ignored) {
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "reset")
  public ResponseEntity<Object> changeUserPassword(
      @RequestParam("token") String token, @RequestBody Map<String, String> data) {
    try {
      return ResponseEntity.ok(
          passwordResetService.changeUserPassword(token, data.get("password")));
    } catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping()
  public ResponseEntity<Object> getToken(@RequestParam("token") String token) {
    return ResponseEntity.ok(passwordResetService.getAccountDetailsFromToken(token));
  }
}
