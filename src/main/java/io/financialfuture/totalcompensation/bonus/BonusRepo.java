package io.financialfuture.totalcompensation.bonus;

import io.financialfuture.totalcompensation.TotalCompensation;
import io.financialfuture.util.JPA.IJPABaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BonusRepo extends IJPABaseRepo<Bonus> {

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM Bonus WHERE totalCompensation = ?1")
  void deleteAllByTotalCompensation(TotalCompensation totalCompensation);
}
