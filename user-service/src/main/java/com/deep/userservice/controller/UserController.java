package com.deep.userservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.userservice.dto.LoginRequestDTO;
import com.deep.userservice.dto.LoginResponseDTO;
import com.deep.userservice.entity.User;
import com.deep.userservice.service.UserService;
import com.deep.userservice.utils.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@GetMapping
	public String test() {
		return "testing";
	}

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		log.info(request.getUsername() + ":" + request.getPassword());
		Authentication auth = authenticationManager.authenticate(token);
		String jwtToken = jwtUtil.generateToken(request.getUsername());
		List<GrantedAuthority> roles = (List) auth.getAuthorities();
		List<String> rolesResponse = roles.stream().map((authority) -> authority.getAuthority())
				.collect(Collectors.toList());
		LoginResponseDTO response = new LoginResponseDTO(jwtToken, auth.getName(), rolesResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		User userSaved = userService.saveUser(user);
		Integer id = userSaved.getId();
		return new ResponseEntity<>("User with the " + id + " created", HttpStatus.CREATED);
	}

}
