package com.Airline.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Airline.Security.JwTUtil;
import com.Airline.servicesImpl.UserDetailsServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter{
	
	@Autowired
	private JwTUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImp userDetails;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String userName = null;
		String token = null;
		
		if(authHeader != null &&authHeader.startsWith("Bearer ") ) {
			token = authHeader.substring(7);
			userName = jwtUtil.extractUsername(token);
//			System.out.println("username: "+ userName );
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails user = userDetails.loadUserByUsername(userName);

			if(jwtUtil.validateToken(token, user)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				System.out.println("Logged In User's role: "+ user.getAuthorities()+" ");
				//authToken have all user Details and roles by using authToken we setting to the request..
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}

