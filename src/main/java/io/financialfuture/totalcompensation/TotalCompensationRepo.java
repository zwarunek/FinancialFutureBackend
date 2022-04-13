package io.financialfuture.totalcompensation;

import io.financialfuture.account.Account;
import io.financialfuture.util.JPA.IJPABaseRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface TotalCompensationRepo extends IJPABaseRepo<TotalCompensation> {

  @Query("select a FROM TotalCompensation a where a.company = :company")
  Optional<TotalCompensation> findAccountByUsername(String company);

  //  @Query("select a FROM TotalCompensation a where a.account = :account")
  List<TotalCompensation> findAllByAccount(Account account);

  //  @Query("select a FROM TotalCompensation a where a.account = :account")
  List<TotalCompensation> findAllByAccountAndCompany(Account account, String company);

  List<TotalCompensation> findAllByAccountAndCompanyIn(Account account, List<String> company);
}
