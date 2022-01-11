package io.financialfuture.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", length = 1)
  private String name;

  @Lob
  @Column(name = "domain")
  private String domain;

  public Company(String name, String domain) {
    this.name = name;
    this.domain = domain;
  }

  public Company(CompanyDetail companyDetail) {
    this.name = companyDetail.name;
    this.domain = companyDetail.domain;
  }
}
