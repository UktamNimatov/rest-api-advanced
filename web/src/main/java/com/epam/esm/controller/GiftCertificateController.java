package com.epam.esm.controller;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService<GiftCertificate> giftCertificateService;

//    private GiftCertificate giftCertificate = new GiftCertificate(1L, "3L", "dsf", "name", "2021-02-20T02:22:25.256",
//            "2021-08-29T06:12:15.156", Arrays.asList(new Tag( "tagName1"),
//            new Tag("tagName3"), new Tag("tagName5")));

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }


    @GetMapping
    public List<GiftCertificate> findAll() throws ServiceException {
        return giftCertificateService.findAll(0, 3);
    }
}
