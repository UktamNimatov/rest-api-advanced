package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }


    @GetMapping
    public List<GiftCertificate> findAll(@RequestParam(required = false) Map<String, String> searchValueMap,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                         @RequestParam(value = "size", defaultValue = "5", required = false) int size
                                         ) throws ServiceException, ResourceNotFoundException, DaoException {
        if (searchValueMap != null && !searchValueMap.isEmpty()) {
            logger.info("map size is " + searchValueMap.size());
            Map.Entry<String, String> entry = searchValueMap.entrySet().iterator().next();
            logger.info("searchValueMap key: " + entry.getKey());
            logger.info("searchValueMap value: " + entry.getValue());
            return giftCertificateService.searchByGivenParams(page, size, entry.getKey(), entry.getValue());
        }
        return giftCertificateService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        return giftCertificateService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> insertGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        giftCertificateService.insert(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable long id) throws ServiceException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException {
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(giftCertificate);
        return new ResponseEntity<>(updatedGiftCertificate, HttpStatus.ACCEPTED);
    }

    @GetMapping("/name/{name}")
    public GiftCertificate findByName(@PathVariable("name") String name) throws ResourceNotFoundException, ServiceException {
        return giftCertificateService.findByName(name);
    }

    @GetMapping("/search/{searchKey}")
    public List<GiftCertificate> findBySearchKey(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                                 @PathVariable("searchKey") String searchKey)
            throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.searchByNameOrDescription(page, size, searchKey);
    }

    @GetMapping("/tagName/{tagName}")
    public List<GiftCertificate> findByTagName(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                               @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                               @PathVariable("tagName") String tagName)
            throws ServiceException, ResourceNotFoundException {
        logger.info(tagName + " is the tagName");
        return giftCertificateService.findGiftCertificatesOfTag(page, size, tagName);
    }
}
