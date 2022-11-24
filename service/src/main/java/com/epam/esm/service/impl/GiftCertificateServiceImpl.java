package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class GiftCertificateServiceImpl extends AbstractEntityService<GiftCertificate> implements GiftCertificateService<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagDao<Tag> tagDao;
    private final TagService<Tag> tagService;

    private static final String TAG_NAME = "tagName";
    private static final String KEY_WORD = "keyWord";

    @Autowired
    public GiftCertificateServiceImpl(AbstractEntityDao<GiftCertificate> abstractEntityDao,
                                      GiftCertificateDao<GiftCertificate> giftCertificateDao,
                                      GiftCertificateValidator giftCertificateValidator, TagDao<Tag> tagDao, TagService<Tag> tagService) {
        super(abstractEntityDao);
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagDao = tagDao;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        try {
            if (!giftCertificateValidator.checkGiftCertificate(giftCertificate).isEmpty())
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE +
                                giftCertificateValidator.checkGiftCertificate(giftCertificate).toString());
            if (giftCertificateDao.findByName(giftCertificate.getName()).isPresent())
                throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                        ConstantMessages.EXISTING_GIFT_CERTIFICATE_NAME);
            tagService.checkTagsWithValidator(giftCertificate.getTagList());
            return giftCertificateDao.insert(giftCertificate);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(int page, int size, String tagName) throws ServiceException, ResourceNotFoundException {
        try {
            logger.info("service layer: tagName is " + tagName);
            if (!tagDao.findByName(tagName).isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.INVALID_TAG_NAME);
            }
            return giftCertificateDao.findGiftCertificatesOfTag(createPageRequest(page, size), tagName);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException {
        try {
            GiftCertificate giftCertificateToUpdate = findById(giftCertificate.getId());
            checkFieldsForUpdate(giftCertificate, giftCertificateToUpdate);
            return giftCertificateDao.update(giftCertificateToUpdate);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public boolean connectTags(List<Tag> tags, long giftCertificateId) throws ServiceException {
        return false;
    }

    @Override
    public boolean disconnectTags(long giftCertificateId) throws ServiceException {
        return false;
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(int page, int size, String searchKey) throws ServiceException, ResourceNotFoundException {
        try {
            List<GiftCertificate> result = giftCertificateDao
                    .searchByNameOrDescription(createPageRequest(page, size), searchKey);
            if (result.isEmpty()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.RESOURCE_NOT_FOUND);
            } else return result;
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<GiftCertificate> searchByGivenParams(int page, int size, String searchKey, String searchValue) throws ServiceException, ResourceNotFoundException {
        if (searchKey.equals(ColumnName.NAME)) {
            return Collections.singletonList(findByName(searchValue));
        }
        if (searchKey.equals(TAG_NAME)) {
            return findGiftCertificatesOfTag(page, size, searchValue);
        }
        if (searchKey.equals(KEY_WORD)) {
            return searchByNameOrDescription(page, size, searchValue);
        }
        return findAll(page, size);
    }

    private void checkFieldsForUpdate(GiftCertificate giftCertificate, GiftCertificate giftCertificateToUpdate) throws InvalidFieldException {
        if (giftCertificate.getName() != null) {
            logger.info("name is not null");
            if (!giftCertificateValidator.checkName(giftCertificate.getName())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME);
            }
            giftCertificateToUpdate.setName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            logger.info("description is not null");
            if (!giftCertificateValidator.checkDescription(giftCertificate.getDescription())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION);
            }
            giftCertificateToUpdate.setDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getDuration() != 0) {
            logger.info("duration is not null");
            if (!giftCertificateValidator.checkDuration(giftCertificate.getDuration())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION);
            }
            giftCertificateToUpdate.setDuration(giftCertificate.getDuration());
        }
        if (giftCertificate.getPrice() != 0d) {
            logger.info("price is not null");
            if (!giftCertificateValidator.checkPrice(giftCertificate.getPrice())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE);
            }
            giftCertificateToUpdate.setPrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getCreateDate() != null) {
            logger.info("create date is not null");
            if (!giftCertificateValidator.checkCreateDate(giftCertificate.getCreateDate())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE);
            }
            giftCertificateToUpdate.setCreateDate(giftCertificate.getCreateDate());
        }
        if (giftCertificate.getLastUpdateDate() != null) {
            logger.info("last update date is not null");
            if (!giftCertificateValidator.checkLastUpdateDate(giftCertificate.getLastUpdateDate())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE);
            }
            giftCertificateToUpdate.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        }
        if (giftCertificate.getTagList() != null) {
            logger.info("gift certificates tag list is not null");
            tagService.checkTagsWithValidator(giftCertificate.getTagList());
            giftCertificateToUpdate.setTagList(giftCertificate.getTagList());
        }
    }
}
