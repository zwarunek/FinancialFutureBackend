package com.zacharywarunek.financialfuture.config;

import com.zacharywarunek.financialfuture.account.Account;
import com.zacharywarunek.financialfuture.account.AccountRepo;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Secure {
  protected final Log logger = LogFactory.getLog(getClass());
  private AccountRepo accountRepo;

  public boolean checkAccountIdAuth(Authentication auth, Long account_id) {
    Optional<Account> result = accountRepo.findAccountByUsername(auth.getName());
    if (result.isPresent() && result.get().getId().equals(account_id)
        || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
      logger.info("\t\tAccessing account_id " + account_id);
      return true;
    }
    return false;
  }
}
