package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.TagDao;
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

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public class TagDaoImpl extends AbstractEntityDao<Tag> implements TagDao<Tag>{
    private static final Logger logger = LogManager.getLogger();

    private static final String MOST_WIDELY_USED_TAG = "SELECT t.id, t.name FROM gift_certificates as gc INNER JOIN gc.tags as t" +
            " WHERE gc.id IN (SELECT o.gift_certificate_id FROM orders as o WHERE o.user_id = :user_id) " +
            "GROUP BY t.id ORDER BY COUNT(t.id) DESC";
    private static final String MOST_WIDELY_USED_TAG_WITH_HIGHEST_COST = "SELECT t.id, t.name FROM gift_certificates as gc INNER JOIN gc.tags as t " +
            " WHERE gc.id IN (SELECT o.gift_certificate_id FROM orders as o WHERE o.user_id IN (SELECT " +
            "o.user_id FROM orders as o GROUP BY o.user_id ORDER BY SUM(o.price) DESC)) GROUP BY t.id " +
            "ORDER BY COUNT(t.id) DESC";

    private static final String SELECT_TAGS_OF_GIFT_CERTIFICATE = "SELECT * FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO tags (name) values(?)";
    private static final String FIND_TAGS_OF_GIFT_CERTIFICATE = "SELECT t.id, t.name FROM tags as t JOIN gift_certificates_tags as gct " +
            "ON t.id = gct.tag_id JOIN gift_certificates as gc ON gc.id = gct.gift_certificate_id WHERE gc.id=:gift_certificate_id";

    private QueryCreator queryCreator;

    @Autowired
    public TagDaoImpl(QueryCreator queryCreator) {
        super(Tag.class, queryCreator);
    }

    @Override
    protected String getTableName() {
        return "tags";
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId, @Nullable Map<String, String> sortingParameters, Pageable pageable) throws DaoException {
        String currentQuery = FIND_TAGS_OF_GIFT_CERTIFICATE;
        if ((sortingParameters != null) && !sortingParameters.isEmpty()) {
            currentQuery = queryCreator.createSortQuery(sortingParameters, currentQuery);
        }
        logger.info("dao layer: certificateId is " + certificateId);
        logger.info("dao: current query is " + currentQuery);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(currentQuery, entityType);
        typedQuery.setParameter("gift_certificate_id", certificateId);
        return typedQuery.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTagOfUser(long userId) throws DaoException {
        return entityManager.createQuery(MOST_WIDELY_USED_TAG, entityType)
                .setParameter("user_id", userId)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTagWithHighestCostOrder() throws DaoException {
        return entityManager.createQuery(MOST_WIDELY_USED_TAG_WITH_HIGHEST_COST, entityType)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws DaoException {
        return false;
    }


}
