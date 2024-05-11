package com.yewseng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yewseng.dto.LdapUser;
import com.yewseng.service.LdapService;

@Controller
public class UserController {

	@Autowired
	private LdapService ldapService;
	
	  @GetMapping("/addUserForm")
	  public String addUserForm(Model model) {
		model.addAttribute("ldapUser", new LdapUser());
	    return "addUser";
	  }
	  
	  @PostMapping("/addUser")
	  public String addUser(LdapUser ldapUser) {
		ldapService.addUser(ldapUser);
	    return "success";
	  }
	  
	   @GetMapping("/modifyUserForm/{uid}")
	    public String modifyUserForm(@PathVariable String uid, Model model) {
	        LdapUser user = ldapService.getUserByUid(uid);
	        if (user != null) {
	            model.addAttribute("ldapUser", user);
	            return "modifyUser";
	        } else {
	            return "userNotFound"; // Handle case when user is not found
	        }
	    }

	    @PostMapping("/modifyUser/{uid}")
	    public String modifyUser(@PathVariable String uid, LdapUser modifiedUser) {
	        ldapService.updateUser(uid, modifiedUser);
	        return "redirect:/userList"; // Redirect to user list page after modification
	    }
	    
		@GetMapping("/userList")
		public String userList( Model model) {
			model.addAttribute("userList", ldapService.getAllUsers());
			return "userList";
		}
}
