package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
@Transactional
public class GiftCertificateDaoImpl extends AbstractEntityDao<GiftCertificate> implements GiftCertificateDao<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();

    private static final String TAG_NAME = "tagName";
    private static final String SEARCH_KEY = "searchKey";

    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;
    private final TagDao<Tag> tagDao;
    private final QueryCreator queryCreator;

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SELECT_GIFT_CERTIFICATES_OF_TAG = "SELECT * FROM gift_certificates_tags WHERE tag_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String DISCONNECT_TAGS = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String SEARCH_QUERY = "SELECT gc FROM GiftCertificate as gc WHERE gc.name LIKE CONCAT ('%', :searchKey, '%') OR gc.description LIKE CONCAT ('%', :searchKey, '%')";
    private static final String SEARCH_BY_TAG_NAME = "SELECT gc FROM GiftCertificate as gc " +
            " JOIN GiftCertificatesTags as gct ON gc.id = gct.giftCertificateId JOIN Tag as tag ON tag.id = gct.tagId WHERE tag.name=:tagName";

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateMapper giftCertificateMapper, GiftCertificatesTagsMapper giftCertificatesTagsMapper,
                                  TagDao<Tag> tagDao, QueryCreator queryCreator) {
        super(GiftCertificate.class, queryCreator);
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
        this.tagDao = tagDao;
        this.queryCreator = queryCreator;
    }

    @Override
    protected String getTableName() {
        return "GiftCertificate";
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(Pageable pageable, String tagName) throws DaoException {
        try {
            logger.info("dao layer: tagName is " + tagName);
            logger.info("dao: current query is " + SEARCH_BY_TAG_NAME);
            TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(SEARCH_BY_TAG_NAME, entityType);
            typedQuery.setParameter(TAG_NAME, tagName);
            return typedQuery.setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws DaoException {
        try {
            return entityManager.merge(giftCertificate);
        } catch (DataAccessException | IllegalArgumentException | TransactionRequiredException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(Pageable pageable, String searchKey) throws DaoException {
        try {
            TypedQuery<GiftCertificate> searchQuery = entityManager.createQuery(SEARCH_QUERY, entityType);
            searchQuery.setParameter(SEARCH_KEY, searchKey);
            return searchQuery
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

}