package com.epam.esm.audit;

import com.epam.esm.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class AuditListener {
    private static final Logger logger = LogManager.getLogger();

    @PrePersist
    private void prePersistAudit(Entity entity) {
        logger.info("Persisting an entity: " + entity);
    }

    @PostPersist
    private void postPersistAudit(Entity entity) {
        logger.info("The entity persisted: " + entity + " with id: " + entity.getId());

    }

    @PreUpdate
    private void preUpdateAudit(Entity entity) {
        logger.info("The entity updated: " + entity + " with id: " + entity.getId());
    }

    @PreRemove
    private void preRemoveAudit(Entity entity) {
        logger.info("Removing entity: " + entity);

    }

    @PostRemove
    private void postRemoveAudit(Entity entity) {
        logger.info("Entity removed: " + entity);
    }
}
