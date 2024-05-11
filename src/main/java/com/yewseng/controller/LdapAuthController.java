package com.yewseng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yewseng.dto.LdapUser;
import com.yewseng.service.LdapService;

@RestController
public class LdapAuthController {

	  @Autowired
	  private LdapService ldapService;
	  
	  @GetMapping("/")
	  public String index() {
	    return "Welcome to the home page!";
	  }
	  
	  @GetMapping("/getUserDetails")
	  public String getUserDetails(Authentication authentication) {
		  UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		  // access user details
		  String username = userDetails.getUsername();
		  boolean accNonExpired = userDetails.isAccountNonExpired();
		  return "UserDetails: " + username + "\n Account Non Expired: " + accNonExpired;
	  }
	  
	  @GetMapping("/getAllUsers")
	  public List<LdapUser> getAllUsers() {
		  return ldapService.getAllUsers();
	  }
	  
	  @GetMapping("/getUserByUid/{uid}")
	  public LdapUser getUserByUid(@PathVariable String uid) {
		  return ldapService.getUserByUid(uid);
	  }
	  
	    @PutMapping("/updateUser/{uid}")
	    public ResponseEntity<String> updateUser(@PathVariable String uid, @RequestBody LdapUser modifiedUser) {
	        LdapUser updatedUser = ldapService.updateUser(uid, modifiedUser);
	        if (updatedUser != null) {
	            return ResponseEntity.ok("User with UID " + uid + " has been updated successfully.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	  
	  @GetMapping("/deleteUser/{uid}")
	  public String deleteUser(@PathVariable String uid) {
		  ldapService.deleteUser(uid);
		  return "User with uid " + uid + " has been deleted from LDAP Server";
	  }
}
