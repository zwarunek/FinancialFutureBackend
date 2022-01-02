package com.zacharywarunek.financialfuture.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDetails {
  private String first_name;
  private String last_name;
  private String username;
  private String password;
}
