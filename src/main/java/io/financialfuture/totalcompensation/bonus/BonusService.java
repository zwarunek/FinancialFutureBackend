package io.financialfuture.totalcompensation.bonus;

import io.financialfuture.totalcompensation.TotalCompensation;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BonusService {

  private BonusRepo bonusRepo;

  public void createBonus(Set<Bonus> bonuses) {
    bonusRepo.saveAll(bonuses);
  }

  public void deleteAllAtTotalCompensation(TotalCompensation totalCompensation) {
    bonusRepo.deleteAllByTotalCompensation(totalCompensation);
  }
}
