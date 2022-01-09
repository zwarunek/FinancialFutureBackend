package com.zacharywarunek.financialfuture.passwordreset.token;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.totalcompensation.util.JPA.IJPABaseRepo;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PasswordResetTokenRepo extends IJPABaseRepo<PasswordResetToken> {

  Optional<PasswordResetToken> findByToken(String token);

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE PasswordResetToken c " + "SET c.usedAt = :usedAt " + "WHERE c.token = :token")
  void updateUsedAt(String token, LocalDateTime usedAt);

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM PasswordResetToken WHERE account = ?1")
  int deleteAllByAccountId(Account account);
}
