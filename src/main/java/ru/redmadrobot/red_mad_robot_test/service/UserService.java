package ru.redmadrobot.red_mad_robot_test.service;

import ru.redmadrobot.red_mad_robot_test.domain.User;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;
import ru.redmadrobot.red_mad_robot_test.dto.UserRecord;

public interface UserService {
    void save(LoginRecord loginRecord);

    User findUser(String email);
}
