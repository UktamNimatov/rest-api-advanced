package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DaoException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao<T> extends EntityDao<Tag> {

    List<T> findTagsOfCertificate(long certificateId, Pageable pageable) throws DaoException;

    Optional<Tag> findMostWidelyUsedTagOfUser(long userId) throws DaoException;

    boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws DaoException;


}
