package ru.redmadrobot.red_mad_robot_test.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.redmadrobot.red_mad_robot_test.dto.UserRecord;
import ru.redmadrobot.red_mad_robot_test.service.UserService;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord user = userService.findUser(username);
        return new org.springframework.security.core.userdetails.
                User(user.email(), user.password(), Set.of(new SimpleGrantedAuthority(user.role().toString())));
    }
}
