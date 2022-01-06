package com.zacharywarunek.financialfuture.totalcompensation.vestingschedule.vestingyear;

import com.zacharywarunek.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "vesting_year")
public class VestingYear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "year")
  private Integer year;

  @Column(name = "percent")
  private Integer percent;

  @ManyToOne
  @JoinColumn(name = "vesting_schedule_id")
  private VestingSchedule vestingSchedule;

  public VestingYear(Integer year, Integer percent) {
    this.year = year;
    this.percent = percent;
  }

  public VestingYear(VestingYearDetail vestingYearDetail) {
    this.year = vestingYearDetail.year;
    this.percent = vestingYearDetail.percent;
  }
}
