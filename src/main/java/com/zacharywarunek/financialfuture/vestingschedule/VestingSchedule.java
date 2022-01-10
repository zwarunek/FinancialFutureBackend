package com.zacharywarunek.financialfuture.vestingschedule;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.company.Company;
import com.zacharywarunek.financialfuture.vestingschedule.stockgranttype.StockGrantType;
import com.zacharywarunek.financialfuture.vestingschedule.vestingyear.VestingYear;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name = "type_id")
  private StockGrantType type;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @Column(name = "rate", length = 1)
  private String rate;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @OneToMany(mappedBy = "vestingSchedule")
  private Set<VestingYear> vestingYears;

  public VestingSchedule(StockGrantType type, Company company, String rate) {
    this.type = type;
    this.company = company;
    this.rate = rate;
  }

  public VestingSchedule(VestingScheduleDetail vestingScheduleDetail) {
    this.type = vestingScheduleDetail.type;
    this.company = vestingScheduleDetail.company;
    this.rate = vestingScheduleDetail.rate;
  }
}
