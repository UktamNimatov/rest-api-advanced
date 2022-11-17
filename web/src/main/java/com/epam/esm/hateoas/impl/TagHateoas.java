package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoas implements Hateoas<TagDto> {
    private static final Class<TagController> CONTROLLER = TagController.class;

    @Override
    public void addLinks(TagDto tagDto) throws ResourceNotFoundException, ServiceException, InvalidFieldException, DuplicateResourceException {
        tagDto.add(linkTo(methodOn(CONTROLLER)
                .findById(tagDto.getId())).withRel("find by id"));
        tagDto.add(linkTo(methodOn(CONTROLLER)
                .deleteTag(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(methodOn(CONTROLLER)
                .findByName(tagDto.getName())).withRel("find by name"));
        tagDto.add(linkTo(methodOn(CONTROLLER)
                .insertTag(tagDto)).withRel("insert new"));
    }
}
