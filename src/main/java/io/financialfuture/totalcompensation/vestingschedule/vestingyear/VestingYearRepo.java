package io.financialfuture.totalcompensation.vestingschedule.vestingyear;

import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import io.financialfuture.util.JPA.IJPABaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VestingYearRepo extends IJPABaseRepo<VestingYear> {

  @Transactional
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM VestingYear WHERE vestingSchedule = ?1")
  void deleteAllByVestingSchedule(VestingSchedule vestingSchedule);
}
