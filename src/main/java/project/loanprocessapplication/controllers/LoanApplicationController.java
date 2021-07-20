package project.loanprocessapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import project.loanprocessapplication.LoanApplicationRequestBody;
import project.loanprocessapplication.services.LoanApplicationServiceImpl;


@RestController
@RequestMapping("/loan-application")
public class LoanApplicationController {
	
	
	LoanApplicationServiceImpl loanApplicationService;
	
	@Autowired
	public void setLoanApplicationService(LoanApplicationServiceImpl loanApplicationService) {
		this.loanApplicationService=loanApplicationService;
	}
	
	@RequestMapping(value = "/view-application-all",method = RequestMethod.GET)
	public List<LoanApplicationRequestBody> viewAllApplications() {
		return loanApplicationService.getAllLoanApplications();
	}
	
	@RequestMapping(value = "/submit-loan-application", method = RequestMethod.POST, produces="text/plain")
	public String submitLoanApplication(@RequestBody LoanApplicationRequestBody requestBody) {
		return loanApplicationService.submitNewApplication(requestBody);
	}
	
	@RequestMapping(value="/view-application/{id}",method = RequestMethod.GET)
	public LoanApplicationRequestBody viewApplicationbyId(@PathVariable(name="id") Integer id) {
		return loanApplicationService.getLoanApplicationById(id);
	}
}
