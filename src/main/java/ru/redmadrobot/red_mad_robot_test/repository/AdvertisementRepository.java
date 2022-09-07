package ru.redmadrobot.red_mad_robot_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redmadrobot.red_mad_robot_test.domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
