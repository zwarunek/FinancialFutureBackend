package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.TotalCompensation;
import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYearService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VestingScheduleService {

  private VestingScheduleRepo vestingScheduleRepo;
  private VestingYearService vestingYearService;


  public void createVestingSchedule(VestingSchedule vestingSchedule) {
    vestingScheduleRepo.save(vestingSchedule);
    vestingYearService.createVestingSchedule(vestingSchedule.getVestingYears());

  }

  public void deleteAllAtTotalCompensation(TotalCompensation totalCompensation) {
    vestingYearService.deleteAllByVestingSchedule(totalCompensation.getVestingSchedule());
    vestingScheduleRepo.deleteAllByTotalCompensation(totalCompensation);
  }
}
