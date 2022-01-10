package com.zacharywarunek.financialfuture.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(name = "logo", length = 1)
  private String logo;

  @Column(name = "url", length = 1)
  private String url;

  @Column(name = "ticker_symbol", length = 1)
  private String tickerSymbol;

  public Company(String name, String logo, String url, String tickerSymbol) {
    this.name = name;
    this.logo = logo;
    this.url = url;
    this.tickerSymbol = tickerSymbol;
  }

  public Company(CompanyDetail companyDetail) {
    this.name = companyDetail.name;
    this.logo = companyDetail.logo;
    this.url = companyDetail.url;
    this.tickerSymbol = companyDetail.tickerSymbol;
  }
}
