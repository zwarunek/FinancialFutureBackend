package com.zacharywarunek.financialfuture.company;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/companies")
public class CompanyController {

  private CompanyService companyService;

  @GetMapping
  public ResponseEntity<List<Company>> findAll() {
    return ResponseEntity.ok(companyService.findAll());
  }
}
