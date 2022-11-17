package com.epam.esm.hateoas;

import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import org.springframework.hateoas.RepresentationModel;

public interface Hateoas<T extends RepresentationModel<T>> {

        void addLinks(T entity) throws ResourceNotFoundException, ServiceException, InvalidFieldException, DuplicateResourceException;
}
