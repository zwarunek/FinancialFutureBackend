package io.financialfuture.registration;

import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import io.financialfuture.exceptions.UsernameTakenException;
import java.util.HashMap;
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
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping
  public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
    Map<String, String> map = new HashMap<>();
    try {
      registrationService.register(request);
      map.put("data", "Successful");
    } catch (UsernameTakenException e) {
      map.put("data", "Username Taken");
    }
    catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    return ResponseEntity.ok(map);
  }

  @GetMapping(path = "confirm")
  public ResponseEntity<Object> confirm(@RequestParam("token") String token) {

    try {
      return ResponseEntity.ok(registrationService.confirmToken(token));
    } catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping(path = "sendmail")
  public ResponseEntity<Object> sendEmail(@RequestBody Map<String, String> data) {
    try {
      registrationService.sendEmail(data.get("username"));
    } catch (EntityNotFoundException ignored) {
    }
    return ResponseEntity.ok().build();
  }
}
