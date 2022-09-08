package ru.redmadrobot.red_mad_robot_test.mapper;

import org.mapstruct.Mapper;
import ru.redmadrobot.red_mad_robot_test.domain.Advertisement;
import ru.redmadrobot.red_mad_robot_test.dto.AdvertisementRecord;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    List<AdvertisementRecord> toRecords(List<Advertisement> advertisements);
    AdvertisementRecord toRecord(Advertisement advertisement);
}
