package com.soa.soaProject.controllerr;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soa.soaProject.entity.User;
import com.soa.soaProject.repository.UserRepository;

@Controller
public class RarentController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/")
	public String index() {
			return "home";
		}
	
	@GetMapping("/loginPage")
	public String loginPage(User user) {
		return "loginPage";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String loginProcess(@RequestParam("username") String username,@RequestParam("password") String password) {
		User dbUser = userRepository.findByUsername(username);
		Boolean isPasswordMatch = BCrypt.checkpw(password,dbUser.getPassword());
		if(isPasswordMatch)
			return "welcome to Dashboard of user " + dbUser.getName();
		else
			return "Failed to logins";
	}
	
	@GetMapping("/registration")
	public String registrationPage(User user) { 
		return "registrationPage";
	}
	
	@PostMapping("/register")
	@ResponseBody
	public String regiter(@ModelAttribute("user") User user) {
		System.out.println(user);
		String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return "Data Saved Successfully!!";
	}
}
