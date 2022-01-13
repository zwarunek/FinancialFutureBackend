package io.financialfuture.totalcompensation.vestingschedule.vestingyear;

import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VestingYearService {

  private VestingYearRepo vestingYearRepo;

  public void createVestingSchedule(List<VestingYear> vestingYear) {
    vestingYearRepo.saveAll(vestingYear);
  }

  public void deleteAllByVestingSchedule(VestingSchedule vestingSchedule) {
    vestingYearRepo.deleteAllByVestingSchedule(vestingSchedule);
  }
}
