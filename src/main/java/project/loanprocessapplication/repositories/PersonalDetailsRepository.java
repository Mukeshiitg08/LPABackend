package project.loanprocessapplication.repositories;

import org.springframework.data.repository.CrudRepository;

import project.loanprocessapplication.jpaentities.PersonalDetailsEntity;

public interface PersonalDetailsRepository extends CrudRepository<PersonalDetailsEntity,Integer>{
	
}
