package com.zacharywarunek.financialfuture.company.vestingschedule;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.company.Company;
import com.zacharywarunek.financialfuture.company.vestingschedule.stockgranttype.StockGrantType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VestingScheduleDetail {
  StockGrantType type;
  Company company;
  String rate;
  Account account;
}
