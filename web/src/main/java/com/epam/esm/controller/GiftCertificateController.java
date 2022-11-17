package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.*;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateService<GiftCertificate> giftCertificateService;
    private final DtoConverter<GiftCertificate, GiftCertificateDto> giftCertificateDtoConverter;
    private final Hateoas<GiftCertificateDto> giftCertificateHateoas;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService,
                                     DtoConverter<GiftCertificate, GiftCertificateDto> giftCertificateDtoConverter,
                                     Hateoas<GiftCertificateDto> giftCertificateHateoas) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
        this.giftCertificateHateoas = giftCertificateHateoas;
    }


    @GetMapping
    public List<GiftCertificateDto> findAll(@RequestParam(required = false) Map<String, String> searchValueMap,
                                            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                            @RequestParam(value = "size", defaultValue = "5", required = false) int size
                                            ) throws ServiceException, ResourceNotFoundException, DaoException {
        if (searchValueMap != null && !searchValueMap.isEmpty()) {
            logger.info("map size is " + searchValueMap.size());
            Map.Entry<String, String> entry = searchValueMap.entrySet().iterator().next();
            logger.info("searchValueMap key: " + entry.getKey());
            logger.info("searchValueMap value: " + entry.getValue());
            List<GiftCertificate> result = giftCertificateService.searchByGivenParams(page, size, entry.getKey(), entry.getValue());
            return convertToDtoList(result);
        }
        return convertToDtoList(giftCertificateService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificateService.findById(id));
        giftCertificateHateoas.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificateDto> insertGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        GiftCertificateDto giftCertificateDtoResult =
                giftCertificateDtoConverter.convertToDto(
                        giftCertificateService.insert(giftCertificateDtoConverter.convertToEntity(giftCertificateDto)));
        giftCertificateHateoas.addLinks(giftCertificateDtoResult);
        return new ResponseEntity<>(giftCertificateDtoResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable long id) throws ServiceException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) throws ServiceException, InvalidFieldException { ;
        GiftCertificateDto giftCertificateDtoResult =
                giftCertificateDtoConverter.convertToDto(
                        giftCertificateService.update(giftCertificateDtoConverter.convertToEntity(giftCertificateDto)));
        giftCertificateHateoas.addLinks(giftCertificateDtoResult);
        return new ResponseEntity<>(giftCertificateDtoResult, HttpStatus.ACCEPTED);
    }

    @GetMapping("/name/{name}")
    public GiftCertificateDto findByName(@PathVariable("name") String name) throws ResourceNotFoundException, ServiceException {
        GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificateService.findByName(name));
        giftCertificateHateoas.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @GetMapping("/search/{searchKey}")
    public List<GiftCertificateDto> findBySearchKey(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                                 @PathVariable("searchKey") String searchKey)
            throws ServiceException, ResourceNotFoundException {
        List<GiftCertificate> result = giftCertificateService.searchByNameOrDescription(page, size, searchKey);
        return convertToDtoList(result);
    }

    @GetMapping("/tagName/{tagName}")
    public List<GiftCertificateDto> findByTagName(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                               @RequestParam(value = "size", defaultValue = "5", required = false) int size,
                                               @PathVariable("tagName") String tagName)
            throws ServiceException, ResourceNotFoundException {
        logger.info(tagName + " is the tagName");
        List<GiftCertificate> result = giftCertificateService.findGiftCertificatesOfTag(page, size, tagName);
        return convertToDtoList(result);
    }

    private List<GiftCertificateDto> convertToDtoList(List<GiftCertificate> giftCertificateList) {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificateList) {
            GiftCertificateDto giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificate);
            giftCertificateHateoas.addLinks(giftCertificateDto);
            giftCertificateDtoList.add(giftCertificateDto);
        }
        return giftCertificateDtoList;
    }
}
