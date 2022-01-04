package com.zacharywarunek.financialfuture;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.account.AccountService;
import com.zacharywarunek.financialfuture.exceptions.BadRequestException;
import com.zacharywarunek.financialfuture.exceptions.EntityNotFoundException;
import com.zacharywarunek.financialfuture.exceptions.UnauthorizedException;
import com.zacharywarunek.financialfuture.util.AuthRequest;
import com.zacharywarunek.financialfuture.util.recaptcha.ReCaptchaService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class BaseController {

  protected final Log logger = LogFactory.getLog(getClass());
  private final AccountService accountService;
  private final ReCaptchaService reCaptchaService;

  @GetMapping(value = "/apiTest")
  public ResponseEntity<String> apiTest() {
    return ResponseEntity.ok()
        .body("API is functioning normally for environment: " + System.getenv("ENV"));
  }

  @GetMapping(value = "/apiTestAuth")
  public ResponseEntity<Object> apiTestAuth() {
    return ResponseEntity.ok()
        .body("API Auth is functioning normally for environment: " + System.getenv("ENV"));
  }

  @PostMapping(path = "/authenticate")
  public ResponseEntity<Account> authenticate(@RequestBody AuthRequest authRequest) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", accountService.authenticate(authRequest));
      logger.info(authRequest.getUsername() + " Authorized");
      return new ResponseEntity<>(accountService.findByUsername(authRequest.getUsername()), headers, HttpStatus.OK);
    } catch (UnauthorizedException | EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (BadRequestException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping(path = "/recaptcha-validate")
  public ResponseEntity<String> reCaptchaValidation(@RequestBody Map<String, String> data, HttpServletRequest request) {
    try{
      return ResponseEntity.ok(reCaptchaService.validate(data.get("response"), request.getRemoteAddr()).toString());
    } catch (IOException e) {
      return ResponseEntity.status(500).build();
    }
  }
}
