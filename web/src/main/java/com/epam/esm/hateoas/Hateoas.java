package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface Hateoas<T extends RepresentationModel<T>> {

        void addLinks(T entity);
}
