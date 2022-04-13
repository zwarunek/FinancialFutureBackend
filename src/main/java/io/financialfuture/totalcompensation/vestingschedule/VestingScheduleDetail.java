package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYearDetail;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VestingScheduleDetail {

  String rate;
  Set<VestingYearDetail> vestingYears;
  Integer comp;
}
