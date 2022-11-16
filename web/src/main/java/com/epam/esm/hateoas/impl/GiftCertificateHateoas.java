package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
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
    public void addLinks(GiftCertificateDto giftCertificateDto) {
//        giftCertificateDto.add(linkTo(methodOn(CONTROLLER)
//
//        ))
    }
}
