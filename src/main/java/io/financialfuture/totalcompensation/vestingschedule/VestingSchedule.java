package io.financialfuture.totalcompensation.vestingschedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.financialfuture.totalcompensation.TotalCompensation;
import io.financialfuture.totalcompensation.vestingschedule.vestingyear.VestingYear;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
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
@JsonIgnoreProperties({"totalCompensation"})
public class VestingSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;


  @Column(name = "rate", length = 20)
  private String rate;

  @OneToMany(mappedBy = "vestingSchedule")
  @OrderBy("year")
  private List<VestingYear> vestingYears;

  @Column(name = "comp")
  private Integer comp;

  @OneToOne
  @JoinColumn(name = "total_compensation_id")
  private TotalCompensation totalCompensation;


  public VestingSchedule(VestingScheduleDetail vestingScheduleDetail,
      TotalCompensation totalCompensation) {
    this.comp = vestingScheduleDetail.comp;
    this.rate = vestingScheduleDetail.rate;
    this.vestingYears = vestingScheduleDetail.vestingYears.stream()
        .map(e -> new VestingYear(e, this)).collect(Collectors.toList());
    this.totalCompensation = totalCompensation;
  }
}
