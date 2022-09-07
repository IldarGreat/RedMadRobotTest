package ru.redmadrobot.red_mad_robot_test.dto;

import ru.redmadrobot.red_mad_robot_test.domain.Role;

public record LoginRecord(Role role, String email, String password) {
    public LoginRecord(String email, String password) {
        this(null, email, password);
    }
}
