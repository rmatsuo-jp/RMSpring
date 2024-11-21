package jp.rm.personal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.rm.personal.model.SiteUser;
import jp.rm.personal.model.Task;
import jp.rm.personal.repository.SiteUserRepository;
import jp.rm.personal.repository.TaskRepository;
import jp.rm.personal.util.Authority;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//finalフィールドに対してコンストラクタを生成
@Controller	//ControllerとしてDIされる
public class TaskController {
	
	private final SiteUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TaskRepository taskRepository;
	
	@GetMapping("/login")
	public String login() {
		return "userLogin";
	}
	
	@GetMapping("/register")
	public String register(@ModelAttribute("user") SiteUser user) {	//("user")を付加しないとエラーが出るのはなぜ？
		return "userRegister";
	}
	
	@PostMapping("/register")
	public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "userRegister";
		}
		
		//以下のコードはサービスクラスに記述したい
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(user.isAdmin()) {
			user.setAuthority(Authority.ADMIN);	
		}else {
			user.setAuthority(Authority.USER);
		}
		userRepository.save(user);
		
		return "redirect:/login?successRegister";
	}
	
	@GetMapping("/")
	public String showTaskList(Model model) {
		model.addAttribute("tasklist", taskRepository.findAll());
		return "taskList";
	}
	
	@GetMapping("/add")
	public String addTask(Task task) {
		return "taskForm";
	}
	
	@PostMapping("/process")
	public String process(@Validated Task task, BindingResult result) {
		
		if(result.hasErrors()) {
			return "taskForm";
		}
		
		taskRepository.save(task);
		return "redirect:/";
	}
	
	@GetMapping("/userInfo")
	public String showUserInfo(Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		return "userInfo";
	}
	
	@GetMapping("/admin/userList")
	public String showUserList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "userList";
	}
	
	
	@GetMapping("/edit/{id}")
	public String editTask(@PathVariable Long id, Model model) {	//@PathVariable:パスの値({id})を変数に格納
		model.addAttribute("task", taskRepository.findById(id));
		return "taskForm";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskRepository.deleteById(id);
		return "redirect:/";
	}
}