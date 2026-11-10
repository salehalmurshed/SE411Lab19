package psu.edu.ch06.crud04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import psu.edu.ch06.crud04.model.User;
import psu.edu.ch06.crud04.model.UserPrincipal;
import psu.edu.ch06.crud04.repository.UserRepository;

/**
 * Loads users from the MySQL "users" table instead of keeping them in memory.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		return new UserPrincipal(user);
	}
}
