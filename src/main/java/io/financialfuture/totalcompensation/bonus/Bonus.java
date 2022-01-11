package io.financialfuture.totalcompensation.bonus;

import io.financialfuture.totalcompensation.TotalCompensation;
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
@Table(name = "bonus")
public class Bonus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "year")
  private Integer year;

  @Column(name = "dollar_bonus")
  private Integer dollarBonus;

  @Column(name = "total_compensation_id")
  private Integer totalCompensationId;

  public Bonus(Integer year, Integer dollarBonus, Integer totalCompensationId) {
    this.year = year;
    this.dollarBonus = dollarBonus;
    this.totalCompensationId = totalCompensationId;
  }
}
