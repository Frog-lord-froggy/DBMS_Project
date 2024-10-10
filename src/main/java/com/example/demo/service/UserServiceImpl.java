package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(UserDto userDto) {
		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname());
		return userRepository.save(user);
	}

    @Override
    public UserDetails loadUserByUsername(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

	@Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null; // Ensure findByEmail method exists in the repository
    }

}
