package io.financialfuture.totalcompensation;

import io.financialfuture.util.JPA.IJPABaseRepo;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface TotalCompensationRepo extends IJPABaseRepo<TotalCompensation> {

  @Query("select a FROM TotalCompensation a where a.company = :company")
  Optional<TotalCompensation> findAccountByUsername(String company);
}
