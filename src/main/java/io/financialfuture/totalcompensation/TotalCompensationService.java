package io.financialfuture.totalcompensation;

import io.financialfuture.account.AccountService;
import io.financialfuture.exceptions.BadRequestException;
import io.financialfuture.exceptions.EntityNotFoundException;
import io.financialfuture.exceptions.ExceptionResponses;
import io.financialfuture.totalcompensation.bonus.Bonus;
import io.financialfuture.totalcompensation.bonus.BonusService;
import io.financialfuture.totalcompensation.vestingschedule.VestingSchedule;
import io.financialfuture.totalcompensation.vestingschedule.VestingScheduleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TotalCompensationService {

  private TotalCompensationRepo totalCompensationRepo;
  private BonusService bonusService;
  private AccountService accountService;
  private VestingScheduleService vestingScheduleService;

  public TotalCompensation findById(Integer totalCompensationId) throws EntityNotFoundException {
    return totalCompensationRepo.findById(totalCompensationId).orElseThrow(
        () -> new EntityNotFoundException(
            String.format(ExceptionResponses.ACCOUNT_NOT_FOUND.label, totalCompensationId)));
  }

  public void create(TotalCompensationDetail tcDetails)
      throws BadRequestException, EntityNotFoundException {

    if (tcDetails.getSalary() == null || tcDetails.getCompany() == null
        || tcDetails.getVestingSchedule() == null || tcDetails.getBonuses() == null
        || tcDetails.get_401kMatch() == null || tcDetails.get_401kMatchEnds() == null) {
      throw new BadRequestException(ExceptionResponses.NULL_VALUES.label);
    }
    TotalCompensation tc = new TotalCompensation(tcDetails);
    tc.setAccount(accountService.findByUsername(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername()));
    totalCompensationRepo.save(tc);
    bonusService.createBonus(
        tcDetails.getBonuses().stream().map(e -> new Bonus(e, tc)).collect(Collectors.toSet()));
    vestingScheduleService.createVestingSchedule(
        new VestingSchedule(tcDetails.getVestingSchedule(), tc));
  }

  public void delete(Integer totalCompensationId) throws EntityNotFoundException {
    TotalCompensation totalCompensation = findById(totalCompensationId);
    bonusService.deleteAllAtTotalCompensation(totalCompensation);
    vestingScheduleService.deleteAllAtTotalCompensation(totalCompensation);
    totalCompensationRepo.deleteById(totalCompensationId);

  }

  @Transactional
  public void update(Integer totalCompensationId, TotalCompensationDetail tcDetails)
      throws EntityNotFoundException {
    TotalCompensation tc = findById(totalCompensationId);
    if (tcDetails.getSalary() != null) {
      tc.setSalary(tcDetails.getSalary());
    }
    if (tcDetails.getCompany() != null) {
      tc.setCompany(tcDetails.getCompany());
    }
    if (tcDetails.get_401kMatch() != null) {
      tc.set_401kMatch(tcDetails.get_401kMatch());
    }
    if (tcDetails.get_401kMatchEnds() != null) {
      tc.set_401kMatchEnds(tcDetails.get_401kMatchEnds());
    }
    if (tcDetails.getTitle() != null) {
      tc.setTitle(tcDetails.getTitle());
    }
    bonusService.deleteAllAtTotalCompensation(tc);
    bonusService.createBonus(
        tcDetails.getBonuses().stream().map(e -> new Bonus(e, tc)).collect(Collectors.toSet()));
    vestingScheduleService.update(tc.getVestingSchedule().getId(), tcDetails.getVestingSchedule());
  }

  public List<TotalCompensation> findAllByAccount() throws EntityNotFoundException {
    return totalCompensationRepo.findAllByAccount(accountService.findByUsername(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername()));
  }

  public List<TotalCompensation> findAllByCompany(String company) throws EntityNotFoundException {
    return totalCompensationRepo.findAllByAccountAndCompany(accountService.findByUsername(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername()), company);
  }

  public List<TotalCompensation> findAllByCompanyList(List<String> companyList)
      throws EntityNotFoundException {
    return totalCompensationRepo.findAllByAccountAndCompanyIn(accountService.findByUsername(
        ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername()), companyList);
  }
}
