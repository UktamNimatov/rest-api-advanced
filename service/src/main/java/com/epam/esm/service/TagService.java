package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TagService<T> extends EntityService<Tag> {

    List<T> findTagsOfCertificate(long certificateId, int page, int size) throws ServiceException;

    T findMostWidelyUsedTagOfUser(long userId) throws ServiceException;

    T findMostWidelyUsedTagWithHighestCostOrder() throws ServiceException;

    void checkTagsWithValidator(List<Tag> tagList) throws InvalidFieldException;

}
