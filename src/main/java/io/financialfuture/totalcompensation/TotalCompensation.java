package io.financialfuture.totalcompensation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.financialfuture.account.Account;
import io.financialfuture.totalcompensation.bonus.Bonus;
import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "total_compensation")
@JsonIgnoreProperties({"account"})
public class TotalCompensation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "salary")
  private Integer salary;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "company_name")
  private String company;

  @OneToOne(mappedBy = "totalCompensation")
  private VestingSchedule vestingSchedule;

  @OneToMany(mappedBy = "totalCompensation")
  private Set<Bonus> bonuses;

  @Column(name = "_401k_match")
  private Integer _401kMatch;

  @Column(name = "_401k_match_ends")
  private Integer _401kMatchEnds;

  @Lob
  @Column(name = "title")
  private String title;

  public TotalCompensation(TotalCompensationDetail detail) {
    this.salary = detail.getSalary();
    this._401kMatch = detail.get_401kMatch();
    this._401kMatchEnds = detail.get_401kMatchEnds();
    this.title = detail.getTitle();
    this.company = detail.getCompany();
  }
}
