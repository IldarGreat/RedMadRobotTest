package ru.redmadrobot.red_mad_robot_test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.redmadrobot.red_mad_robot_test.domain.Advertisement;
import ru.redmadrobot.red_mad_robot_test.domain.User;
import ru.redmadrobot.red_mad_robot_test.dto.AdvertisementRecord;
import ru.redmadrobot.red_mad_robot_test.mapper.AdvertisementMapper;
import ru.redmadrobot.red_mad_robot_test.repository.AdvertisementRepository;
import ru.redmadrobot.red_mad_robot_test.security.jwt.JwtTokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    @Value("${application.auth.cookie-name}")
    private String cookieName;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapper, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapper = advertisementMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }


    @Transactional(readOnly = true)
    @Override
    public List<AdvertisementRecord> findAll() {
        return advertisementMapper.toRecords(
                advertisementRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementRecord> filter(String title, String description, boolean image) {
        if (title == null && description == null && !image) {
            return advertisementMapper.toRecords(advertisementRepository.findAll());
        }
        if (title == null && description != null && !image) {
            return advertisementMapper.toRecords(advertisementRepository.findByDescription(description));
        } else if (title == null && description == null) {
            return advertisementMapper.toRecords(advertisementRepository.findByPhotoIsNotNull());
        } else if (title == null) {
            return advertisementMapper.toRecords(advertisementRepository.findByDescriptionAndPhotoIsNotNull(description));
        } else if (description != null && !image) {
            return advertisementMapper.toRecords(advertisementRepository.findByTitleAndDescription(title, description));
        } else if (description == null && image) {
            return advertisementMapper.toRecords(advertisementRepository.findByTitleAndPhotoIsNotNull(title));
        } else if (description == null) {
            return advertisementMapper.toRecords(advertisementRepository.findByTitle(title));
        }
        return advertisementMapper.toRecords(advertisementRepository.findByTitleAndDescriptionAndPhotoIsNotNull(title, description));
    }

    @Override
    @Transactional(readOnly = true)
    public AdvertisementRecord findById(long id) {
        return advertisementMapper.toRecord(
                advertisementRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id:" + id + " not found")));
    }

    @Override
    @Transactional
    public AdvertisementRecord add(String title, String description, String image, HttpServletRequest request) {
        Cookie[] requestCookies = request.getCookies();
        String token = "";
        for (Cookie cookie : requestCookies) {
            if (cookie.getName().equals(cookieName)) {
                token = cookie.getValue();
            }
        }
        String email = jwtTokenUtil.getEmail(token);
        User user = userService.findUser(email);
        Advertisement advertisement = Advertisement.builder()
                .title(title)
                .description(description)
                .photo(image)
                .user(user)
                .build();
        Advertisement generatedAdvertisement = advertisementRepository.save(advertisement);
        user.setAdvertisements(Collections.singletonList(advertisement));
        return advertisementMapper.toRecord(generatedAdvertisement);
    }


}
