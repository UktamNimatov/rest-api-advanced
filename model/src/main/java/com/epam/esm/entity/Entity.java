package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@MappedSuperclass
@EntityListeners({AuditListener.class})
public abstract class Entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Entity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
