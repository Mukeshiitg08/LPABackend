package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import project.loanprocessapplication.LoanApplicationRequestBody;
import project.loanprocessapplication.jpaentities.LoanApplicationEntity;
import project.loanprocessapplication.jpaentities.PersonalDetailsEntity;
import project.loanprocessapplication.repositories.LoanApplicationRepository;
import project.loanprocessapplication.repositories.PersonalDetailsRepository;
import project.loanprocessapplication.services.CreditScoreServiceImpl;
import project.loanprocessapplication.services.LoanApplicationService;
import project.loanprocessapplication.services.LoanApplicationServiceImpl;

@SpringBootTest(classes=LoanApplicationService.class)
class FirstAppApplicationTests {

	@Mock
	LoanApplicationRepository loanApplicationRepository;
	
	@Mock
	PersonalDetailsRepository personalDetailsRepository;
	
	@Mock
	CreditScoreServiceImpl creditScoreServiceImpl;
	
	@InjectMocks
	LoanApplicationServiceImpl loanApplicationServiceImpl;
	
	@Test
	void checkForNullValuesInRequestBody() {
		
		LoanApplicationEntity loanApplicationEntity = new LoanApplicationEntity();
		LoanApplicationEntity entity = new LoanApplicationEntity();
		entity.setApplicationId(1);
		
		PersonalDetailsEntity personalDetails = new PersonalDetailsEntity(123);
		entity.setPersonalDetails(personalDetails);
		
		
		LoanApplicationRequestBody requestBody = new LoanApplicationRequestBody(entity);
		System.out.println(requestBody.getSsnNumber());
		
		ArrayList<LoanApplicationEntity> list = new ArrayList<LoanApplicationEntity>();
		list.add(loanApplicationEntity);
		when(loanApplicationRepository.findAll()).thenReturn(list);
		ArrayList<LoanApplicationRequestBody> larb = new ArrayList<LoanApplicationRequestBody>();
		larb.add(requestBody);
		
		ArrayList<LoanApplicationEntity> returnFromRepository = (ArrayList<LoanApplicationEntity>) loanApplicationRepository.findAll();
		
		LoanApplicationEntity lentity = returnFromRepository.get(0);
		
		assertThat(loanApplicationServiceImpl.getAllLoanApplications().get(0).applicationId).isEqualTo(lentity.getApplicationId());
		
	}
	

}
