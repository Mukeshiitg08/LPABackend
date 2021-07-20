package project.loanprocessapplication.jpaentities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="LoanApplication")

public class LoanApplicationEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="loan_application_id", insertable=false)
	private Integer applicationId;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="loan_application_date")
	private Calendar loanApplicationDate;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name="ssn_number",referencedColumnName="ssn_number")
	private PersonalDetailsEntity personalDetails;
	
	@Column(name="loan_amount")
	@NotNull(message="Loan amount is a required field")
	@Min(1)
	private Long loanAmount;
	
	@Column(name="loan_purpose")
	@NotNull(message="Loan purpose is a required field")
	@Size(min=1,max=254)
	private String loanPurpose;
	
	@Column(name="description")
	@Size(max=254)
	private String description;
	
	@Column(name="loan_status")
	private String loanStatus;
	
	@Column(name="decline_reason")
	private String declineReason;
	
	@Column(name="credit_score")
	private Integer creditScore;
	
	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public PersonalDetailsEntity getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(PersonalDetailsEntity personalDetails) {
		this.personalDetails = personalDetails;
	}

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getLoanApplicationDate() {
		return loanApplicationDate;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getDeclineReason() {
		return declineReason;
	}

	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}

	public void setLoanApplicationDate(Calendar loanApplicationDate) {
		this.loanApplicationDate = loanApplicationDate;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	
	
	
	
}
