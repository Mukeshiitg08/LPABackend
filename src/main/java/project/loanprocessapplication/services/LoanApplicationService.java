package project.loanprocessapplication.services;
import java.util.List;

import project.loanprocessapplication.LoanApplicationRequestBody;
import project.loanprocessapplication.jpaentities.LoanApplicationEntity;
import project.loanprocessapplication.repositories.LoanApplicationRepository;
import project.loanprocessapplication.repositories.PersonalDetailsRepository;

public interface LoanApplicationService {
	
	abstract void setCreditScoreServiceImpl(CreditScoreServiceImpl creditScoreServiceImpl);
	
	abstract String submitNewApplication(LoanApplicationRequestBody requestBody);
	
	abstract void setLoanApplicationRepository(LoanApplicationRepository loanApplicationRepository);
	
	abstract void setPersonalDetailsRepository(PersonalDetailsRepository personalDetailsRepository);
	
	abstract LoanApplicationRequestBody getLoanApplicationById(int applicationId);
	
	abstract List<LoanApplicationRequestBody> getAllLoanApplications();
	
	abstract boolean checkFrontEndDeclineRules(LoanApplicationEntity loanApplication);
	
	abstract void setLoanStatus(LoanApplicationEntity loanApplication);
	
}
