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

    private static final String MOST_WIDELY_USED_TAG = "SELECT t FROM Order as o INNER JOIN o.giftCertificateList as gc INNER JOIN " +
            " gc.tagList as t WHERE o.user.id IN (SELECT o.user.id FROM Order as o WHERE o.user.id = :user_id) " +
            "GROUP BY t.id ORDER BY COUNT(t.id) DESC";
    private static final String MOST_WIDELY_USED_TAG_WITH_HIGHEST_COST = "SELECT t FROM Order as o INNER JOIN o.giftCertificateList as gc INNER JOIN " +
            " gc.tagList as t WHERE o.user.id IN (SELECT o.user.id FROM Order o ORDER BY o.price)" +
            "GROUP BY t.id ORDER BY COUNT(t.id) DESC";

    private static final String SELECT_TAGS_OF_GIFT_CERTIFICATE = "SELECT * FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO tags (name) values(?)";
    private static final String FIND_TAGS_OF_GIFT_CERTIFICATE = "SELECT t FROM Tag as t JOIN GiftCertificatesTags as gct " +
            "ON t.id = gct.tagId JOIN GiftCertificate as gc ON gc.id = gct.giftCertificateId WHERE gc.id=:gift_certificate_id";
    private static final String SQL_FIND_BY_CERTIFICATE_ID = "SELECT t FROM Tag as t INNER JOIN t.giftCertificateList gc WHERE gc.id=:gift_certificate_id";

    private QueryCreator queryCreator;

    @Autowired
    public TagDaoImpl(QueryCreator queryCreator) {
        super(Tag.class, queryCreator);
    }

    @Override
    protected String getTableName() {
        return "Tag";
    }


    @Override
    public List<Tag> findTagsOfCertificate(long certificateId, Pageable pageable) throws DaoException {
        logger.info("dao layer: certificateId is " + certificateId);
        logger.info("dao: current query is " + SQL_FIND_BY_CERTIFICATE_ID);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(SQL_FIND_BY_CERTIFICATE_ID, entityType);
        typedQuery.setParameter("gift_certificate_id", certificateId);
        return typedQuery.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTagOfUser(long userId) throws DaoException {
        try {
            logger.info("current query is: " + MOST_WIDELY_USED_TAG);
            return entityManager.createQuery(MOST_WIDELY_USED_TAG, entityType)
                    .setParameter("user_id", userId)
                    .setMaxResults(1)
                    .getResultList().stream()
                    .findFirst();
        }catch (IllegalArgumentException | NullPointerException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws DaoException {
        return false;
    }


}
