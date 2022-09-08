package ru.redmadrobot.red_mad_robot_test.dto;

import ru.redmadrobot.red_mad_robot_test.domain.Role;

public record UserRecord(int id,Role role,String email,String password) {
}
