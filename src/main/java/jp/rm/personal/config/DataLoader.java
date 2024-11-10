package jp.rm.personal.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jp.rm.personal.model.Department;
import jp.rm.personal.model.User;
import jp.rm.personal.repository.DepartmentRepository;
import jp.rm.personal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
	
	private final DepartmentRepository departmentRepository;
	private final UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		
		var sales = new Department();
		sales.setName("aSale");
		departmentRepository.save(sales);
		
		var testUser = new User();
		testUser.setName("aTest");
		userRepository.save(testUser);
	}
	
}
