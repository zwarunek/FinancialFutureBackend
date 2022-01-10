package com.zacharywarunek.financialfuture.vestingschedule;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.company.Company;
import com.zacharywarunek.financialfuture.vestingschedule.stockgranttype.StockGrantType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VestingScheduleDetail {
  StockGrantType type;
  Company company;
  String rate;
  Account account;
}
