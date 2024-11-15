package jp.rm.personal.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jp.rm.personal.model.Task;
import jp.rm.personal.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
	
	private final TaskRepository taskRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		var task = new Task();
		task.setName("task");
		taskRepository.save(task);
	}
}