package com.zacharywarunek.financialfuture.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationRequest {
  private final String firstName;
  private final String lastName;
  private final String username;
  private final String password;
}
