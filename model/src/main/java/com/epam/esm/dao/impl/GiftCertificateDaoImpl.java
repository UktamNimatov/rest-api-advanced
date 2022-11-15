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

    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;
    private final TagDao<Tag> tagDao;
    private final QueryCreator queryCreator;

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SELECT_GIFT_CERTIFICATES_OF_TAG = "SELECT * FROM gift_certificates_tags WHERE tag_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String DISCONNECT_TAGS = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String SEARCH_QUERY = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificates WHERE name LIKE CONCAT ('%', :searchKey, '%') OR description LIKE CONCAT ('%', :searchKey, '%')";
    private static final String SEARCH_BY_TAG_NAME = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date FROM gift_certificates as gc" +
            " JOIN gift_certificates_tags as gct ON gc.id = gct.gift_certificate_id JOIN tags ON tags.id = gct.tag_id WHERE tags.name=:tagName";

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
        return "gift_certificates";
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(Pageable pageable, String tagName, @Nullable Map<String, String> sortingParameters) throws DaoException {
        try {
            String currentQuery = SEARCH_BY_TAG_NAME;
            if ((sortingParameters != null) && !sortingParameters.isEmpty()) {
                currentQuery = queryCreator.createSortQuery(sortingParameters, currentQuery);
            }
            logger.info("dao layer: tagName is " + tagName);
            logger.info("dao: current query is " + currentQuery);
            TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(currentQuery, entityType);
            typedQuery.setParameter("tagName", tagName);
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
    public List<GiftCertificate> searchByNameOrDescription(Pageable pageable, String searchKey, @Nullable Map<String, String> sortingParameters) throws DaoException {
        try {
            String currentQuery = SEARCH_QUERY;
            if (sortingParameters != null && !sortingParameters.isEmpty()) {
                currentQuery = queryCreator.createSortQuery(sortingParameters, SEARCH_QUERY);
            }
            TypedQuery<GiftCertificate> searchQuery = entityManager.createQuery(currentQuery, entityType);
            searchQuery.setParameter("searchKey", searchKey);
            return searchQuery
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

}