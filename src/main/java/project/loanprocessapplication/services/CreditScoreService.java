package project.loanprocessapplication.services;


import project.loanprocessapplication.classes.CreditScoreCard;
import project.loanprocessapplication.components.BureauDataComponent;
import project.loanprocessapplication.jpaentities.LoanApplicationEntity;

public interface CreditScoreService {
	public CreditScoreCard calculateCreditScore(LoanApplicationEntity loanApplication);
	
	public void setBureauDataComponent(BureauDataComponent bureauDataComponent);
	
	public boolean approveLoanApplication(int creditScore);
}
