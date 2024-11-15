package jp.rm.personal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.rm.personal.model.Task;
import jp.rm.personal.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TaskController {
	private final TaskRepository taskRepository;
	
	@GetMapping("/")
	public String showList(Model model) {
		model.addAttribute("tasklist", taskRepository.findAll());
		return "index";
	}
	
	@GetMapping("/add")
	public String addTask(Task task) {
		return "form";
	}
	
	@PostMapping("/process")
	public String process(@Validated Task task, BindingResult result) {
		
		if(result.hasErrors()) {
			return "form";
		}
		
		taskRepository.save(task);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editTask(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskRepository.findById(id));
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskRepository.deleteById(id);
		return "redirect:/";
	}
}