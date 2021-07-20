package project.loanprocessapplication;

import java.util.Calendar;

import project.loanprocessapplication.jpaentities.LoanApplicationEntity;
import project.loanprocessapplication.jpaentities.PersonalDetailsEntity;


public class LoanApplicationRequestBody{
	
	public Integer applicationId;
	
	public Calendar applicationDate;
	
	public String declineReason;
	
	public String loanStatus;
	
	public Integer creditScore;
	
	public Long loanAmount;
	
	public String loanPurpose;
	
	public String description;
	
	public String homePhone;
	
	public String officePhone;
	
	public String mobileNumber;
	
	public String emailAddress;

	public Integer ssnNumber;
	
	public String firstName;
	
	public String middleName;
	
	public String lastName;
	
	public String birthDate;
	
	public String maritalStatus;
	
	public String addressLine1;
	
	public String addressLine2;
	
	public String postalCode;
	
	public String city;
	
	public String state;
	
	public String employerName;
	
	public String eaddressLine1;
	
	public String eaddressLine2;
	
	public String epostalCode;
	
	public String ecity;
	
	public String estate;
	
	public Long annual;
	
	public String designation;
	
	public Integer year;
	
	public Integer month;
	
	public LoanApplicationRequestBody() {
		
	}
	
	public LoanApplicationRequestBody(LoanApplicationEntity loanApplication) {
		
		if(loanApplication!=null && loanApplication.getPersonalDetails()!=null) {
			PersonalDetailsEntity personalDetails = loanApplication.getPersonalDetails();
			
			this.ssnNumber=personalDetails.getSsnNumber();
			Calendar dateOfBirth = personalDetails.getDateOfBirth();
			if(dateOfBirth!=null) {
				int monthAdjustmentCalendar = dateOfBirth.get(Calendar.MONTH)+1;
				this.birthDate= monthAdjustmentCalendar+"-"+dateOfBirth.get(Calendar.DATE)+"-"+dateOfBirth.get(Calendar.YEAR);
			}
				
			
			this.maritalStatus=personalDetails.getMaritalStatus();
			this.firstName=personalDetails.getFirstName();
			this.middleName=personalDetails.getMiddleName();
			this.lastName=personalDetails.getLastName();
			
			this.addressLine1=personalDetails.getAddressLine1();
			this.addressLine2=personalDetails.getAddressLine2();
			this.city=personalDetails.getCity();
			this.state=personalDetails.getState();
			this.postalCode=personalDetails.getPostalCode();
			
			this.employerName=personalDetails.getEmployerName();
			this.designation=personalDetails.getDesignation();
			this.annual=personalDetails.getAnnualSalary();
			this.eaddressLine1=personalDetails.getEmployerAddressLine1();
			this.eaddressLine2=personalDetails.getEmployerAddressLine2();
			this.ecity=personalDetails.getEmployerCity();
			this.estate=personalDetails.getEmployerState();
			this.epostalCode=personalDetails.getEmployerPostalCode();
			
			Integer totalMonthsWorkExperience=personalDetails.getWorkExperienceInMonths();
			if(totalMonthsWorkExperience!=null) {
				this.year=(totalMonthsWorkExperience/12);
				this.month=(totalMonthsWorkExperience%12);
			}
			else {
				this.year=null;
				this.month=null;
			}
			
		
			this.description=loanApplication.getDescription();
			this.emailAddress=personalDetails.getEmailAddress();
			this.homePhone=personalDetails.getHomePhone();
			this.loanAmount=loanApplication.getLoanAmount();
			this.loanPurpose=loanApplication.getLoanPurpose();
			this.mobileNumber=personalDetails.getMobileNumber();
			this.officePhone=personalDetails.getOfficePhone();
			
			this.applicationId=loanApplication.getApplicationId();
			this.applicationDate=loanApplication.getLoanApplicationDate();
			this.declineReason=loanApplication.getDeclineReason();
			this.loanStatus=loanApplication.getLoanStatus();
			this.creditScore=loanApplication.getCreditScore();
		}
		
	}
	
	
	public LoanApplicationEntity requestBodyToEntity() {
		LoanApplicationEntity loanApplication = new LoanApplicationEntity();
		PersonalDetailsEntity personalDetails = new PersonalDetailsEntity();
		
		personalDetails.setSsnNumber(this.ssnNumber);
		personalDetails.setFirstName(this.firstName);
		personalDetails.setMiddleName(this.middleName);
		personalDetails.setLastName(this.lastName);
		
		Calendar dateOfBirth = Calendar.getInstance();
		String[] dateStringArray = null;
		
		if(this.birthDate!=null) {
			dateStringArray = this.birthDate.split("-");
			if(dateStringArray.length==3) {
				dateOfBirth.set(Integer.parseInt(dateStringArray[0]), Integer.parseInt(dateStringArray[2])-1, Integer.parseInt(dateStringArray[1]));
			}
		}
		
		personalDetails.setDateOfBirth(dateOfBirth);
		personalDetails.setMaritalStatus(this.maritalStatus);
		
		
		personalDetails.setAddressLine1(this.addressLine1);
		personalDetails.setAddressLine2(this.addressLine2);
		personalDetails.setCity(this.city);
		personalDetails.setState(this.state);
		personalDetails.setPostalCode(this.postalCode);
		

		personalDetails.setEmployerName(this.employerName);
		personalDetails.setDesignation(this.designation);
		personalDetails.setAnnualSalary(this.annual);
		personalDetails.setEmployerAddressLine1(this.eaddressLine1);
		personalDetails.setEmployerAddressLine2(this.eaddressLine2);
		personalDetails.setEmployerCity(this.ecity);
		personalDetails.setEmployerState(this.estate);
		personalDetails.setEmployerPostalCode(this.epostalCode);
		Integer totalMonthsWorkExperience = null;
		if(this.year!=null && this.month!=null)
			totalMonthsWorkExperience = this.year*12+this.month;
		personalDetails.setWorkExperienceInMonths(totalMonthsWorkExperience);
		
		personalDetails.setMobileNumber(this.mobileNumber);
		personalDetails.setOfficePhone(this.officePhone);
		personalDetails.setEmailAddress(this.emailAddress);
		personalDetails.setHomePhone(this.homePhone);
		
		loanApplication.setDescription(this.description);
		loanApplication.setLoanAmount(this.loanAmount);
		loanApplication.setLoanPurpose(this.loanPurpose);
		loanApplication.setPersonalDetails(personalDetails);
		
		return loanApplication;
		
	}

	public Integer getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(Integer ssnNumber) {
		this.ssnNumber = ssnNumber;
	}
	
}
