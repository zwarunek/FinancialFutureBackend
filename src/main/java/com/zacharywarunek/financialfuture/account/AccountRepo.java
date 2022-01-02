package com.zacharywarunek.financialfuture.account;

import com.zacharywarunek.financialfuture.util.JPA.IJPABaseRepo;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepo extends IJPABaseRepo<Account> {

  @Query("select a FROM Account a where a.username = :username")
  Optional<Account> findAccountByUsername(String username);


  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE Account a " + "SET a.enabled = TRUE WHERE a.username = ?1")
  void enableAccount(String username);
}
