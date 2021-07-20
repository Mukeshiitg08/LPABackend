package project.loanprocessapplication.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;	
import project.loanprocessapplication.LoanApplicationRequestBody;
import project.loanprocessapplication.classes.CreditScoreCard;
import project.loanprocessapplication.jpaentities.LoanApplicationEntity;
import project.loanprocessapplication.jpaentities.PersonalDetailsEntity;
import project.loanprocessapplication.repositories.LoanApplicationRepository;
import project.loanprocessapplication.repositories.PersonalDetailsRepository;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
		
	LoanApplicationRepository loanApplicationRepository;
	PersonalDetailsRepository personalDetailsRepository;
	CreditScoreServiceImpl creditScoreServiceImpl;
	
	@Autowired
	@Override
	public void setLoanApplicationRepository(LoanApplicationRepository loanApplicationRepository) {
		this.loanApplicationRepository=loanApplicationRepository;
	}
	
	@Autowired
	@Override
	public void setPersonalDetailsRepository(PersonalDetailsRepository personalDetailsRepository) {
		this.personalDetailsRepository=personalDetailsRepository;
		
	}
	
	@Autowired
	@Override
	public void setCreditScoreServiceImpl(CreditScoreServiceImpl creditScoreServiceImpl) {
		this.creditScoreServiceImpl=creditScoreServiceImpl;
	}
	
	@Override
	public String submitNewApplication(LoanApplicationRequestBody requestBody) {
		LoanApplicationEntity loanApplication = requestBody.requestBodyToEntity();
		try {
			//Call calculateCreditScore() method instead of the following set statements
			loanApplication.setDeclineReason("N/A");
			loanApplication.setLoanStatus("PROCESSING");
					
			
			PersonalDetailsEntity savedPersonalEntity = personalDetailsRepository.save(loanApplication.getPersonalDetails());
			LoanApplicationEntity savedApplicationEntity = loanApplicationRepository.save(loanApplication);
			if(savedApplicationEntity==null && savedPersonalEntity==null)
				return "ERROR:-Loan Application not submitted, check log for details";
		
		}
		catch(TransactionSystemException exception) {
			exception.printStackTrace();
			return "(Transaction error)";
		}
		catch(ConstraintViolationException exception) {
			exception.printStackTrace();
			return "(Constraints violation found before transaction)";
		}
		catch(DataIntegrityViolationException exception) {
			exception.printStackTrace();
			return "(Possible duplicate data, verify your data before submitting)";
		}
		
		setLoanStatus(loanApplication);
		return "SUCCESS:-Loan application successfully submitted";
	}

	
	@Override
	public LoanApplicationRequestBody getLoanApplicationById(int applicationId) {
		LoanApplicationEntity loanApplication = null;
		try {
			loanApplication = this.loanApplicationRepository.findById(applicationId).orElseThrow();
		}
		catch(NoSuchElementException noSuchElementException) {

		}
		
		return new LoanApplicationRequestBody(loanApplication);
	}


	@Override
	public List<LoanApplicationRequestBody> getAllLoanApplications() {

		Iterable<LoanApplicationEntity> iterable = this.loanApplicationRepository.findAll();
		ArrayList<LoanApplicationRequestBody> listOfAllApplications = new ArrayList<LoanApplicationRequestBody>();
		
		for(LoanApplicationEntity loanApplication : iterable) {
			listOfAllApplications.add(new LoanApplicationRequestBody(loanApplication));
		}
		
		return listOfAllApplications;
	}

	@Override
	public boolean checkFrontEndDeclineRules(LoanApplicationEntity loanApplication) {
		PersonalDetailsEntity personalDetails = loanApplication.getPersonalDetails();
		
		Calendar dateOfBirth = personalDetails.getDateOfBirth();
		Calendar currentDate = Calendar.getInstance();
		
		currentDate.setTimeInMillis(System.currentTimeMillis());
		
		int years = currentDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		if(dateOfBirth.get(Calendar.MONTH)>currentDate.get(Calendar.MONTH) ||
				(dateOfBirth.get(Calendar.MONTH)==currentDate.get(Calendar.MONTH) && 
						dateOfBirth.get(Calendar.DATE)>currentDate.get(Calendar.DATE))) {
			years-=1;
		}
		
		if(years<18 || years>65) {
			loanApplication.setLoanStatus("DECLINED");
			loanApplication.setDeclineReason("Age must be between 18-65 years");
			this.loanApplicationRepository.save(loanApplication);
			return false;
		}
			
		
		int workExperienceInMonths = personalDetails.getWorkExperienceInMonths();
		//int workExperienceInMonths = 10;
		
		if(workExperienceInMonths<6) {
			loanApplication.setLoanStatus("DECLINED");
			loanApplication.setDeclineReason("Work experience must be at least 6 months");
			this.loanApplicationRepository.save(loanApplication);
			return false;
		}
			
		
		long salary = personalDetails.getAnnualSalary();
		if(salary<10000) {
			loanApplication.setLoanStatus("DECLINED");
			loanApplication.setDeclineReason("Salary must be equal to or greater than $10000");
			this.loanApplicationRepository.save(loanApplication);
			return false;
		}
		return true;	
	}

	@Override
	public void setLoanStatus(LoanApplicationEntity loanApplication) {
		if(checkFrontEndDeclineRules(loanApplication)==false)
			return;
		CreditScoreCard creditScoreCard = this.creditScoreServiceImpl.calculateCreditScore(loanApplication);
		
		
		boolean loanApproval = this.creditScoreServiceImpl.approveLoanApplication((int)Math.round(creditScoreCard.getTotalScore()));
		if(creditScoreCard.getTotalScore()>=0 && loanApproval==true) {
			loanApplication.setLoanStatus("APPROVED");
			loanApplication.setDeclineReason("N/A");
			loanApplication.setCreditScore((int)Math.round(creditScoreCard.getTotalScore()));
		}
		else if (creditScoreCard.getTotalScore()>=0 && loanApproval==false) {
			loanApplication.setLoanStatus("DECLINED");
			loanApplication.setDeclineReason(creditScoreCard.getStrongestNegativeFeature());
			loanApplication.setCreditScore((int)Math.round(creditScoreCard.getTotalScore()));
		}
		else if(creditScoreCard.getTotalScore()<0) {
			loanApplication.setLoanStatus("DECLINED");
			loanApplication.setDeclineReason("Error in calculating score, credit bureau data may not exist for user");
		}
		
		this.loanApplicationRepository.save(loanApplication);
	}

		

}
