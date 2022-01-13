package io.financialfuture.totalcompensation;

import io.financialfuture.totalcompensation.bonus.BonusDetail;
import io.financialfuture.totalcompensation.vestingschedule.VestingScheduleDetail;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TotalCompensationDetail {

  private Integer salary;
  private String company;
  private VestingScheduleDetail vestingSchedule;
  private Set<BonusDetail> bonuses;
  private Integer _401kMatch;
  private Integer _401kMatchEnds;
  private String title;
}
