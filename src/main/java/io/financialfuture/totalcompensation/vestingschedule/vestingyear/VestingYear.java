package io.financialfuture.totalcompensation.vestingschedule.vestingyear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
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
@JsonIgnoreProperties({"vestingSchedule"})
public class VestingYear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "year")
  private Integer year;


  @ManyToOne
  @JoinColumn(name = "vesting_schedule_id")
  private VestingSchedule vestingSchedule;

  @Column(name = "\"percent\"")
  private Integer percent;

  public VestingYear(Integer year, Integer percent) {
    this.year = year;
    this.percent = percent;
  }

  public VestingYear(VestingYearDetail vestingYearDetail, VestingSchedule vestingSchedule) {
    this.year = vestingYearDetail.year;
    this.percent = vestingYearDetail.percent;
    this.vestingSchedule = vestingSchedule;
  }
}
