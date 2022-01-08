package com.zacharywarunek.financialfuture.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountInfo {
  private String firstName;

  public AccountInfo(Account account) {
    this.firstName = account.getFirstName();
    this.lastName = account.getLastName();
    this.username = account.getUsername();
  }

  private String lastName;
  private String username;

}
