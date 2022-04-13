package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.TotalCompensation;
import io.financialfuture.util.JPA.IJPABaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VestingScheduleRepo extends IJPABaseRepo<VestingSchedule> {

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM VestingSchedule WHERE totalCompensation = ?1")
  void deleteAllByTotalCompensation(TotalCompensation totalCompensation);
}
