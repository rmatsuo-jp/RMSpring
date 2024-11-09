package jp.rm.personal.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jp.rm.personal.model.Department;
import jp.rm.personal.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
	
	private final DepartmentRepository departmentRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		
		var sales = new Department();
		sales.setName("営業");
		departmentRepository.save(sales);
		
		var development = new Department();
		development.setName("開発");
		departmentRepository.save(development);
	}
	
}
