package project.loanprocessapplication.repositories;

import project.loanprocessapplication.jpaentities.*;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoanApplicationRepository extends CrudRepository<LoanApplicationEntity,Integer> {
	List<LoanApplicationEntity> findByPersonalDetailsAndLoanPurpose(PersonalDetailsEntity personalDetails, String loanPurpose);
	
	@Query(value="SELECT MAX(loan_application_date) FROM loan_application WHERE loan_purpose= :loanPurpose AND ssn_number= :ssnNumber",nativeQuery=true)
	Date findLatestLoanApplicationByType(@Param("loanPurpose") String loanPurpose, @Param("ssnNumber") Integer ssnNumber);
	
}
