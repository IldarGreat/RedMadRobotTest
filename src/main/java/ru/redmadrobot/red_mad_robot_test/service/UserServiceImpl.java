package ru.redmadrobot.red_mad_robot_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;
import ru.redmadrobot.red_mad_robot_test.dto.UserRecord;
import ru.redmadrobot.red_mad_robot_test.mapper.UserMapper;
import ru.redmadrobot.red_mad_robot_test.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void save(LoginRecord loginRecord) {
        userRepository.save(userMapper.toEntity(loginRecord));
    }

    @Transactional(readOnly = true)
    @Override
    public UserRecord findUser(String email){
        return userMapper.toRecord(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email "+email + " not found")));
    }
}
