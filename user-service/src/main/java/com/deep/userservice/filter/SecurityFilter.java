package com.deep.userservice.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deep.userservice.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Read token from the authorization header
		String token = request.getHeader("Authorization");
		logger.info(token);
		if (token != null) {
			try {
				String username = jwtUtil.getUsername(token);

				// Username should not be empty
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					User user = (User) userDetailsService.loadUserByUsername(username);
					boolean isValid = jwtUtil.validateToken(token, username);
					if (isValid) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								user.getUsername(), user.getPassword(), user.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
						filterChain.doFilter(request, response);
					}
				}
			} catch (JwtException e) {
				Map<String, Object> errorDetails = new HashMap<>();
				errorDetails.put("message", "Invalid token");

				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				mapper.writeValue(response.getWriter(), errorDetails);
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

}
