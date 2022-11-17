package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateHateoas implements Hateoas<GiftCertificateDto> {
    private static final Class<GiftCertificateController> CONTROLLER = GiftCertificateController.class;
    private static final Class<Tag> TAG_CONTROLLER = Tag.class;

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) throws ResourceNotFoundException, ServiceException, InvalidFieldException, DuplicateResourceException {
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
                .findById(giftCertificateDto.getId())).withRel("find by id"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
                .deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
                .findByName(giftCertificateDto.getName())).withRel("find by name"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
                .updateGiftCertificate(giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
                .insertGiftCertificate(giftCertificateDto)).withRel("insert new"));
    }
}
