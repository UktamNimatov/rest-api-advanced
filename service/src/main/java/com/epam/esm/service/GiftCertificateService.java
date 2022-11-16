package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService<T> extends EntityService<GiftCertificate> {

    List<T> findGiftCertificatesOfTag(int page, int size, String tagName) throws ServiceException, ResourceNotFoundException;

    GiftCertificate update(T giftCertificate) throws ServiceException, InvalidFieldException;

    boolean connectTags(List<Tag> tags, long giftCertificateId) throws ServiceException;

    boolean disconnectTags(long giftCertificateId) throws ServiceException;

    List<T> searchByNameOrDescription(int page, int size, String searchKey) throws ServiceException, ResourceNotFoundException;

    List<T> searchByGivenParams(int page, int size, String searchKey, String searchValue) throws ServiceException, ResourceNotFoundException, DaoException;
}
