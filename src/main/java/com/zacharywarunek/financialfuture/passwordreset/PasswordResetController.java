package com.zacharywarunek.financialfuture.passwordreset;

import com.zacharywarunek.financialfuture.exceptions.BadRequestException;
import com.zacharywarunek.financialfuture.exceptions.EntityNotFoundException;
import com.zacharywarunek.financialfuture.exceptions.ExpiredTokenException;
import com.zacharywarunek.financialfuture.exceptions.InvalidTokenException;
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
  public ResponseEntity<Object> passwordReset(@RequestBody Map<String, String> data) {
    try {
      passwordResetService.passwordReset(data.get("username"));
      return ResponseEntity.ok().build();
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
  @PostMapping(path = "reset")
  public ResponseEntity<Object> changeUserPassword(@RequestParam("token") String token, @RequestBody Map<String, String> data) {
    try {
      passwordResetService.changeUserPassword(token, data.get("password"));
      return ResponseEntity.ok().build();
    } catch (InvalidTokenException | ExpiredTokenException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping()
  public ResponseEntity<Object> getToken(@RequestParam("token") String token) {

    try {
      return ResponseEntity.ok(passwordResetService.getAccountDetailsFromToken(token));
    } catch (InvalidTokenException | ExpiredTokenException e) {
      return ResponseEntity.ok().build();
    }
  }

  @GetMapping(path = "confirm")
  public ResponseEntity<Object> confirm(@RequestParam("token") String token) {

    try {
      return ResponseEntity.ok(passwordResetService.useToken(token));
    } catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (InvalidTokenException | ExpiredTokenException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
