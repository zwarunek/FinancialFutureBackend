package com.zacharywarunek.financialfuture.company;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/company")
public class CompanyController {

  private CompanyService companyService;
}
