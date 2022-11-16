package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao<T> extends EntityDao<GiftCertificate> {

    List<T> findGiftCertificatesOfTag(Pageable pageable, String tagName) throws DaoException;

    T update(T giftCertificate) throws DaoException;

//    boolean connectTags(List<Tag> tags, long giftCertificateId) throws DaoException;

//    boolean disconnectTags(long giftCertificateId) throws DaoException;

    List<T> searchByNameOrDescription(Pageable pageable, String searchKey) throws DaoException;

//    List<T> sortByRequirements(List<T> giftCertificatesList, Map<String, String> requirements);
}
