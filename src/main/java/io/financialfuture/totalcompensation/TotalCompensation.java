package io.financialfuture.totalcompensation;

import io.financialfuture.account.Account;
import io.financialfuture.company.Company;
import io.financialfuture.totalcompensation.bonus.Bonus;
import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class TotalCompensation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "salary")
  private Integer salary;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToOne(mappedBy = "totalCompensationId")
  private VestingSchedule vestingSchedules;

  @OneToMany(mappedBy = "totalCompensationId")
  private Set<Bonus> bonuses;

  @Column(name = "401k_match")
  private Integer _401kMatch;

  @Column(name = "401k_match_ends")
  private Integer _401kMatchEnds;

  @Lob
  @Column(name = "title")
  private String title;

}
