package com.zacharywarunek.financialfuture.account;

import com.zacharywarunek.financialfuture.passwordreset.token.PasswordResetToken;
import com.zacharywarunek.financialfuture.registration.token.ConfirmationToken;
import com.zacharywarunek.financialfuture.totalcompensation.TotalCompensation;
import com.zacharywarunek.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "account")
  private Set<ConfirmationToken> confirmationTokens;

  @OneToMany(mappedBy = "account")
  private Set<PasswordResetToken> passwordResetTokens;

  @OneToMany(mappedBy = "account")
  private Set<TotalCompensation> totalCompensations;

  @Enumerated(EnumType.STRING)
  private AccountRole role;

  private Boolean locked = false;
  private Boolean enabled = false;

  public Account(
      String firstName, String lastName, String username, String password, AccountRole role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
