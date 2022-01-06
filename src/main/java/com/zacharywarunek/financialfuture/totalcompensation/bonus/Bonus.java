package com.zacharywarunek.financialfuture.totalcompensation.bonus;

import com.zacharywarunek.financialfuture.totalcompensation.TotalCompensation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
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

  @ManyToOne
  @JoinColumn(name = "total_compensation_id")
  private TotalCompensation totalCompensation;

  public TotalCompensation getTotalCompensation() {
    return totalCompensation;
  }

  public void setTotalCompensation(TotalCompensation totalCompensation) {
    this.totalCompensation = totalCompensation;
  }

  public Integer getDollarBonus() {
    return dollarBonus;
  }

  public void setDollarBonus(Integer dollarBonus) {
    this.dollarBonus = dollarBonus;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
