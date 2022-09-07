package ru.redmadrobot.red_mad_robot_test.dto;

import ru.redmadrobot.red_mad_robot_test.domain.Role;

public record UserRecord(Role role,String email,String password) {
}
