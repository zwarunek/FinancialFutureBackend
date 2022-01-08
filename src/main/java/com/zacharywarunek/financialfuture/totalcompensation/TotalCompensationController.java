package com.zacharywarunek.financialfuture.totalcompensation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/total-compensation")
public class TotalCompensationController {
  private final TotalCompensationService totalCompensationService;


}
