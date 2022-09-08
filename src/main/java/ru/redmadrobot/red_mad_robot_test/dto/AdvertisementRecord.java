package ru.redmadrobot.red_mad_robot_test.dto;

import ru.redmadrobot.red_mad_robot_test.domain.Status;

public record AdvertisementRecord(Long id,String title, String description, Status status, UserRecord user) {
    public AdvertisementRecord(String title, String description, Status status, UserRecord user){
        this(null,title,description,status,user);
    }
}
