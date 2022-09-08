package ru.redmadrobot.red_mad_robot_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redmadrobot.red_mad_robot_test.service.AdvertisementService;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/board/")
public class BulletinBoardController {

    private final AdvertisementService advertisementService;

    public BulletinBoardController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAdverts() {
        return new ResponseEntity<>(advertisementService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "id", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertsById(@RequestParam(name = "id") long id) {
        return new ResponseEntity<>(advertisementService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "filter", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvertsByFilters(@RequestParam(name = "title", required = false) String title,
                                                 @RequestParam(name = "description", required = false) String description,
                                                 @RequestParam(name = "image", required = false) boolean image) {
        return new ResponseEntity<>(advertisementService.filter(title, description, image), HttpStatus.OK);
    }

    @PostMapping(value = "add", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewAdvert(@RequestParam(name = "title") String title,
                                          @RequestParam(name = "description") String description,
                                          @RequestParam(name = "image", required = false) String image,
                                          HttpServletRequest request) {
        return new ResponseEntity<>(advertisementService.add(title, description, image, request), HttpStatus.OK);
    }
}
