package io.financialfuture.company;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService {

  private CompanyRepo companyRepo;
  public List<Company> findAll() {
    return companyRepo.findAll();
  }
}
