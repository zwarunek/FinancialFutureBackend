package io.financialfuture.totalcompensation.vestingschedule;

import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYear;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vesting_schedule")
public class VestingSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;


  @Column(name = "rate", length = 1)
  private String rate;

  @OneToMany(mappedBy = "vestingSchedule")
  private Set<VestingYear> vestingYears;

  @Column(name = "comp")
  private Integer comp;

  @Column(name = "total_compensation_id")
  private Integer totalCompensationId;


  public VestingSchedule(VestingScheduleDetail vestingScheduleDetail) {
    this.comp = vestingScheduleDetail.comp;
    this.rate = vestingScheduleDetail.rate;
    this.vestingYears = vestingScheduleDetail.vestingYears;
  }
}