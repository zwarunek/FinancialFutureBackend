package io.financialfuture.totalcompensation;

import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/total-compensation")
public class TotalCompensationController {

  private final TotalCompensationService totalCompensationService;


  @PostMapping
  public ResponseEntity<Object> creatTotalCompensation(@RequestBody TotalCompensationDetail tc) {
    Map<String, String> map = new HashMap<>();
    try {
      totalCompensationService.create(tc);
      map.put("data", "Successful");
    } catch (BadRequestException e) {
      map.put("data", "Bad Request");
    } catch (EntityNotFoundException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok(map);
  }

  @DeleteMapping(path = "{total_compensation_id}")
  public ResponseEntity<String> delete(
      @PathVariable("total_compensation_id") Integer totalCompensationId) {
    try {
      totalCompensationService.delete(totalCompensationId);
      return ResponseEntity.ok("Deleted Total Compensation");
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
