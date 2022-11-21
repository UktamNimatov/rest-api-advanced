package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateDtoConverter implements DtoConverter<GiftCertificate, GiftCertificateDto> {

    @Override
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setCreateDate(dto.getCreateDate());
        giftCertificate.setLastUpdateDate(dto.getLastUpdateDate());
        giftCertificate.setTagList(dto.getTagList());
        giftCertificate.setOrderList(dto.getOrderList());

        return giftCertificate;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(entity.getId());
        giftCertificateDto.setName(entity.getName());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setCreateDate(entity.getCreateDate());
        giftCertificateDto.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateDto.setTagList(entity.getTagList());
        giftCertificateDto.setOrderList(entity.getOrderList());

        return giftCertificateDto;
    }

}
