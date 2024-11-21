package jp.rm.personal.config;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jp.rm.personal.model.SiteUser;
import jp.rm.personal.model.Task;
import jp.rm.personal.repository.SiteUserRepository;
import jp.rm.personal.repository.TaskRepository;
import jp.rm.personal.util.Authority;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//finalフィールドに対してコンストラクタを生成
@Component					//一般的な構成要素のひとつとしてDIされる
public class DataLoader implements ApplicationRunner {	//ApplicationRunner:起動時にrunメソッドを実行
	
	private final PasswordEncoder passwordEncoder;
	private final SiteUserRepository userRepository;
	private final TaskRepository taskRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception{
		//ユーザ情報の追加(ADMIN, USER権限を1アカウントずつ)
		addUserInfo("admin", "password", Authority.ADMIN);
		addUserInfo("user", "password", Authority.USER);
		
		//タスク情報の追加
		var task = new Task();
		task.setName("taskA");
		task.setIsFinished(false);
		task.setStartDate(LocalDate.of(1900, 1, 1));
		task.setEndDate(LocalDate.of(1900, 12, 31));
		taskRepository.save(task);
	}
	
	public void addUserInfo(String username, String password, Authority authority) {
		var user = new SiteUser();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(username + "@example.com");
		
		if(authority.equals(Authority.ADMIN)) {
			user.setAdmin(true);
		}else {
			user.setAdmin(false);
		}
		
		user.setAuthority(authority);
		
		//ユーザが存在しない場合のみ登録
		if(userRepository.findByUsername(user.getUsername()).isEmpty()) {
			userRepository.save(user);
		}
	}
}