package project.loanprocessapplication.jpaentities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="PersonalDetails")
public class PersonalDetailsEntity {
	
	
	
	@Id
	@Column(name="ssn_number")
	@Max(999999999)
	@NotNull(message="SSN is a required field")
	private Integer ssnNumber;
	
	@Column(name="first_name")
	@Size(min=1,max=254)
	@NotNull(message="First name is a required field")
	private String firstName;
	
	@Column(name="middle_name")
	@Size(max=254)
	private String middleName;
	
	@Column(name="last_name")
	@Size(min=1,max=254)
	@NotNull(message="Last name is a required field")
	private String lastName;
	
	//Month greater by 1 error
	@Column(name="date_of_birth")
	@Temporal(TemporalType.DATE)
	@NotNull(message="Date of birth is a required field")
	private Calendar dateOfBirth;
	
	@Column(name="marital_status")
	@NotNull(message="Marital status is a required field")
	private String maritalStatus;
	
	@Column(name="address_line_1")
	@Size(min=1,max=254)
	@NotNull(message="Address Line 1 is a required field")
	private String addressLine1;
	
	@Column(name="address_line_2")
	@Size(max=254)
	private String addressLine2;
	
	//postal code must be string
	@Column(name="postal_code")
	@Size(min=5,max=5)
	@NotNull(message="Postal code is a required field")
	private String postalCode;
	
	@Column(name="city")
	@Size(min=1,max=254)
	@NotNull(message="City is a required field")
	private String city;
	
	@Column(name="state")
	@Size(min=1,max=254)
	@NotNull(message="State is a required field")
	private String state;
	
	@Column(name="employer_name")
	@Size(min=1,max=254)
	@NotNull(message="Employer name is a required field")
	private String employerName;
	
	@Column(name="employer_address_line_1")
	@Size(min=1,max=254)
	@NotNull(message="Employer address line 1 is a required field")
	private String employerAddressLine1;
	
	@Column(name="employer_address_line_2")
	@Size(max=254)
	private String employerAddressLine2;
	
	@Column(name="employer_postal_code")
	@Size(min=5,max=5)
	@NotNull(message="Employer postal code is a required field")
	private String employerPostalCode;
	
	@Column(name="employer_city")
	@Size(min=1,max=254)
	@NotNull(message="Employer city is a required field")
	private String employerCity;
	
	@Column(name="employer_state")
	@Size(min=1,max=254)
	@NotNull(message="Employer state is a required field")
	private String employerState;
	
	@Column(name="annual_salary")
	@NotNull(message="Annual salary is a required field")
	private Long annualSalary;
	
	@Column(name="designation")
	@Size(min=1,max=254)
	private String designation;
	
	@Column(name="home_phone", unique=true)
	@NotNull(message="Home phone is a required field")
	@Size(min=10,max=10)
	private String homePhone;
	
	//if office phone null, change request body
	@Column(name="office_phone", unique=true)
	@Size(max=10)
	private String officePhone;
	
	@Column(name="mobile_number", unique=true)
	@NotNull(message="Mobile Number is a required field")
	@Size(min=10,max=10)
	private String mobileNumber;
	
	@Column(name="email_address", unique=true)
	@Size(min=1)
	@NotNull(message="Email address is a required field")
	private String emailAddress;
	
	@Column(name="work_experience_in_months")
	@Min(0)
	@NotNull(message="Work experience is a required field")
	private Integer workExperienceInMonths;
	
	//Constructors
	
	public PersonalDetailsEntity() {
		
	}
	
	public PersonalDetailsEntity(Integer ssnNumber) {
		this.ssnNumber=ssnNumber;
	}
	
	//Getters and Setters

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmployerPostalCode() {
		return employerPostalCode;
	}

	public void setEmployerPostalCode(String employerPostalCode) {
		this.employerPostalCode = employerPostalCode;
	}

	public String getEmployerCity() {
		return employerCity;
	}

	public void setEmployerCity(String employerCity) {
		this.employerCity = employerCity;
	}

	public String getEmployerState() {
		return employerState;
	}

	public void setEmployerState(String employerState) {
		this.employerState = employerState;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}


	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Long getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(Long annualSalary) {
		this.annualSalary = annualSalary;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmployerAddressLine1() {
		return employerAddressLine1;
	}

	public void setEmployerAddressLine1(String employerAddressLine1) {
		this.employerAddressLine1 = employerAddressLine1;
	}

	public String getEmployerAddressLine2() {
		return employerAddressLine2;
	}

	public void setEmployerAddressLine2(String employerAddressLine2) {
		this.employerAddressLine2 = employerAddressLine2;
	}

	public Integer getSsnNumber() {
		return ssnNumber;
	}
	
	public void setSsnNumber(Integer ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getWorkExperienceInMonths() {
		return workExperienceInMonths;
	}

	public void setWorkExperienceInMonths(Integer workExperienceInMonths) {
		this.workExperienceInMonths = workExperienceInMonths;
	}
}
