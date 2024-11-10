package jp.rm.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.rm.personal.model.User;
import jp.rm.personal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {
	
	private final UserRepository userRepository;
	
	@GetMapping("/")
	public String showList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "index";
	}
	
	@GetMapping("/add")
	public String addUser(User user) {
		
		return "form";
	}
	
	@PostMapping("/process")
	public String process(@Validated User user, BindingResult result) {
		
		if(result.hasErrors()) {
			
			return "form";
		}
		
		userRepository.save(user);
		
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable Long id, Model model) {
		
		model.addAttribute("user", userRepository.findById(id));
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		
		userRepository.deleteById(id);
		return "redirect:/";
	}
}


