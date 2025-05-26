package com.Airline.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

	public boolean hasUserId(Authentication auth, Integer userId) {
		if(auth == null || userId == null) return false;
			
		UserDetails user = (UserDetails) auth.getPrincipal();
		//if user is admin , Allow Access
		if(user.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			return true;
		}
		//for Regular users, check if the requested userId matched their own
		return user.getUsername().equals(userId.toString());
	}
}
