package ru.redmadrobot.red_mad_robot_test.service;

import ru.redmadrobot.red_mad_robot_test.dto.AdvertisementRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdvertisementService {
    List<AdvertisementRecord> findAll();

    List<AdvertisementRecord> filter(String title,String description,boolean image);

    AdvertisementRecord findById(long id);

    AdvertisementRecord add(String title, String description, String image, HttpServletRequest request);
}
