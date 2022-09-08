package ru.redmadrobot.red_mad_robot_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.redmadrobot.red_mad_robot_test.domain.Advertisement;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByTitle(String title);

    List<Advertisement> findByDescription(String description);

    List<Advertisement> findByPhotoIsNotNull();

    List<Advertisement> findByTitleAndDescriptionAndPhotoIsNotNull(String title, String description);

    List<Advertisement> findByTitleAndPhotoIsNotNull(String title);

    List<Advertisement> findByTitleAndDescription(String title, String description);

    List<Advertisement> findByDescriptionAndPhotoIsNotNull(String description);

}
