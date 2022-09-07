package ru.redmadrobot.red_mad_robot_test.mapper;

import org.mapstruct.Mapper;
import ru.redmadrobot.red_mad_robot_test.domain.User;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;
import ru.redmadrobot.red_mad_robot_test.dto.UserRecord;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRecord userRecord);

    User toEntity(LoginRecord loginRecord);

    UserRecord toRecord(User user);
}
