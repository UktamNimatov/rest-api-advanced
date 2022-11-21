package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private static final Logger logger = LogManager.getLogger();

    private final TagService<Tag> tagService;
    private final DtoConverter<Tag, TagDto> tagDtoConverter;
    private final Hateoas<TagDto> tagHateoas;
    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public TagController(TagService<Tag> tagService, DtoConverter<Tag, TagDto> tagDtoConverter, Hateoas<TagDto> tagHateoas, GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.tagService = tagService;
        this.tagDtoConverter = tagDtoConverter;
        this.tagHateoas = tagHateoas;
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<TagDto> findAll(@RequestParam(required = false) String giftCertificateName,
                             @RequestParam(required = false) Long giftCertificateId,
                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                             @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        if (giftCertificateId != null) {
            return convertToDtoList(tagService.findTagsOfCertificate(giftCertificateId, page, size));
        }
        if (giftCertificateName != null) {
            return convertToDtoList(tagService.findTagsOfCertificate(giftCertificateService.findByName(giftCertificateName).getId(), page, size));
        }
        return convertToDtoList(tagService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        TagDto tagDto = tagDtoConverter.convertToDto(tagService.findById(id));
        tagHateoas.addLinks(tagDto);
        return tagDto;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TagDto> insertTag(@RequestBody TagDto tagDto) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        TagDto tagDtoResult =
                tagDtoConverter.convertToDto(
                        tagService.insert(tagDtoConverter.convertToEntity(tagDto)));
        tagHateoas.addLinks(tagDtoResult);
        return new ResponseEntity<>(tagDtoResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @GetMapping("/name/{name}")
    public TagDto findByName(@PathVariable("name") String name) throws ResourceNotFoundException, ServiceException {
        TagDto tagDto = tagDtoConverter.convertToDto(tagService.findByName(name));
        tagHateoas.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping("/{userId}/most-frequently")
    public TagDto findMostFrequentlyUsedTag(@PathVariable("userId") String userId) {
        TagDto tagDto = tagDtoConverter.convertToDto(tagService.findMostWidelyUsedTagOfUser(Long.parseLong(userId)));
        tagHateoas.addLinks(tagDto);
        return tagDto;
    }

    private List<TagDto> convertToDtoList(List<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = tagDtoConverter.convertToDto(tag);
            tagHateoas.addLinks(tagDto);
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }
}
