package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYear;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VestingScheduleDetail {
  String rate;
  Integer comp;
  Set<VestingYear> vestingYears;
}
