package com.epam.esm.entity;


import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "tags")
@JsonIgnoreProperties("giftCertificateList")
@EntityListeners(AuditListener.class)
public class Tag extends com.epam.esm.entity.Entity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tagList", fetch = FetchType.LAZY)
    private List<GiftCertificate> giftCertificateList;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getName(), tag.getName()) &&
                Objects.equals(giftCertificateList, tag.giftCertificateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), giftCertificateList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
