package project.loanprocessapplication.services;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.loanprocessapplication.classes.CreditScoreCard;
import project.loanprocessapplication.classes.NoBureauDataException;
import project.loanprocessapplication.components.BureauDataComponent;
import project.loanprocessapplication.jpaentities.LoanApplicationEntity;
//import org.apache.commons.math3.analysis.*;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {
	
	BureauDataComponent bureauDataComponent;
	
	@Override
	@Autowired
	public void setBureauDataComponent(BureauDataComponent bureauDataComponent) {
		this.bureauDataComponent=bureauDataComponent;
	}

	@Override
	public CreditScoreCard calculateCreditScore(LoanApplicationEntity loanApplication) {
		Properties modelProperties = this.bureauDataComponent.getModelProperties();
		Properties loanPurposeProperties = this.bureauDataComponent.getLoanPurposeProperties();
		HashMap<String,Integer> columnNames = this.bureauDataComponent.getColumnNames();
		HashMap<Integer,String> csvFileAsMap = this.bureauDataComponent.getCsvFileAsMap();
		CreditScoreCard creditScoreCard = new CreditScoreCard();
		double totalScore = 0;
		try {
			totalScore = Double.parseDouble(modelProperties.getProperty("intercept"));
			//System.out.println(totalScore);
			double debtToIncomeRatio = loanApplication.getLoanAmount()/loanApplication.getPersonalDetails().getAnnualSalary();
			double debtToIncomeRatioScore=0;
			if(modelProperties.getProperty("debt_to_income_ratio")!=null) {
				debtToIncomeRatioScore=Double.parseDouble(modelProperties.getProperty("debt_to_income_ratio"));
				debtToIncomeRatioScore=debtToIncomeRatioScore*debtToIncomeRatio;
				//System.out.println(debtToIncomeRatioScore);
				creditScoreCard.addNewFeatureScore("debt_to_income_ratio", debtToIncomeRatioScore);
			}
				

			int empLength = loanApplication.getPersonalDetails().getWorkExperienceInMonths();
			double empLengthScore=0;
			if(modelProperties.getProperty("emp_length")!=null) {
				empLengthScore=Double.parseDouble(modelProperties.getProperty("emp_length"));
				empLengthScore=empLength*empLengthScore;
				//System.out.println(empLengthScore);
				creditScoreCard.addNewFeatureScore("emp_length", empLengthScore);
			}
							
			
			double loanPurposeScore=0;
			if(modelProperties.getProperty("loan_purpose")!=null && !loanPurposeProperties.isEmpty()) {
				int loanPurposeBinary = Integer.parseInt(loanPurposeProperties.getProperty(loanApplication.getLoanPurpose()));
				//System.out.println(loanPurposeBinary);
				loanPurposeScore = Double.parseDouble(modelProperties.getProperty("loan_purpose"));
				loanPurposeScore= loanPurposeScore*loanPurposeBinary;
				//System.out.println(loanPurposeScore);
				creditScoreCard.addNewFeatureScore("loan_purpose", loanPurposeScore);
			}
			
		
			totalScore+=debtToIncomeRatioScore+empLengthScore+loanPurposeScore;
			//System.out.println(totalScore);

			if(csvFileAsMap.get(loanApplication.getPersonalDetails().getSsnNumber())==null)
				throw new NoBureauDataException("No bureau data exists for given SSN");
			String[] bureauDetails = csvFileAsMap.get(loanApplication.getPersonalDetails().getSsnNumber()).split(",");
			for(String property : modelProperties.stringPropertyNames()) {
				if(columnNames.containsKey(property)) {
					Double propertyScore = Double.parseDouble(modelProperties.getProperty(property));
					Double propertyVal = Double.parseDouble(bureauDetails[columnNames.get(property)]);
					//System.out.println(property+" "+propertyScore*propertyVal);
					totalScore+=propertyScore*propertyVal;
					//System.out.println(totalScore);
					creditScoreCard.addNewFeatureScore(property, propertyScore*propertyVal);
				}
			}
			//System.out.println(totalScore);
			creditScoreCard.setTotalScore((int) Math.round(((1/(1+Math.exp(-totalScore)))*1000)));
		}
		catch(NullPointerException exception) {
			exception.printStackTrace();
			creditScoreCard.setTotalScore(-1);
		}
		catch(NumberFormatException exception) {
			exception.printStackTrace();
			creditScoreCard.setTotalScore(-1);
			System.out.println("Invalid values in properties files or bureau data");
		}
		catch(NoBureauDataException exception) {
			exception.printStackTrace();
			creditScoreCard.setTotalScore(-1);
			System.out.println(exception.getMessage());
		}
		//System.out.println(creditScoreCard.getTotalScore());
		return creditScoreCard;
		
	}

	@Override
	public boolean approveLoanApplication(int creditScore) {
		Properties modelProperties = this.bureauDataComponent.getModelProperties();
		int thresholdScore;
		try {
			thresholdScore = Integer.parseInt(modelProperties.getProperty("threshold_score"));
		}
		catch(NumberFormatException exception){
			System.out.println(exception.getMessage()+"/nThreshold score is a non-integer value"
					+ " check modelProperties.properties file");
			return false;
		}
		//System.out.println(creditScore+" "+thresholdScore);
		if(creditScore>=thresholdScore)
			return true;
		return false;
	}

	

}
