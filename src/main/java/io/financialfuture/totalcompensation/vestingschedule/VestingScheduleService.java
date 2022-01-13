package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.TotalCompensation;
import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYear;
import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYearService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public void update(VestingSchedule vestingSchedule, VestingScheduleDetail vestingScheduleDetail) {
    if (vestingScheduleDetail.getComp() != null) {
      vestingSchedule.setComp(vestingScheduleDetail.getComp());
    }
    if (vestingScheduleDetail.getRate() != null) {
      vestingSchedule.setRate(vestingScheduleDetail.getRate());
    }
    vestingYearService.deleteAllByVestingSchedule(vestingSchedule);
    vestingYearService.createVestingSchedule(vestingScheduleDetail.getVestingYears().stream()
        .map(e -> new VestingYear(e, vestingSchedule)).collect(Collectors.toList()));
  }
}
