package jp.rm.personal.config;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jp.rm.personal.model.Task;
import jp.rm.personal.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

/*
 * @RequiredArgsConstructor : finalフィールドに対してコンストラクタを生成
 * @Component : 一般的な構成要素のひとつとしてDIされる
 * ApplicationRunner : Spring Boot起動時に初期処理としてrunメソッドを実行
 */

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
	
	private final TaskRepository taskRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		var task = new Task();
		task.setName("taskA");
		task.setIsFinished(false);
		task.setStartDate(LocalDate.of(1900, 1, 1));
		task.setEndDate(LocalDate.of(1900, 12, 31));
		taskRepository.save(task);
	}
}