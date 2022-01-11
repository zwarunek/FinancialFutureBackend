package io.financialfuture.passwordreset.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.financialfuture.account.Account;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "password_reset_token", schema = "dbo", catalog = "zwarunek2_financial_future_qa")
public class PasswordResetToken {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Integer id;
  @Basic
  @Column(name = "token")
  private String token;
  @Basic
  @Column(name = "created_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  @Basic
  @Column(name = "expires_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime expiresAt;
  @Basic
  @Column(name = "used_at")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime usedAt;
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;



  public PasswordResetToken(
      String token, LocalDateTime created_at, LocalDateTime expires_at, Account account) {
    this.token = token;
    this.createdAt = created_at;
    this.expiresAt = expires_at;
    this.account = account;
  }
}
