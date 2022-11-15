package com.epam.esm.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

@javax.persistence.Entity
@Table(name = "gift_certificates_tags")
public class GiftCertificatesTags extends Entity {

    private static final long serialVersionUID = 1L;

    @Column(name = "gift_certificate_id")
    private long giftCertificateId;

    @Column(name = "tag_id")
    private long tagId;

    public GiftCertificatesTags() {
    }

    public GiftCertificatesTags(long giftCertificateId, long tagId) {
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificatesTags)) return false;
        if (!super.equals(o)) return false;
        GiftCertificatesTags that = (GiftCertificatesTags) o;
        return getGiftCertificateId() == that.getGiftCertificateId() &&
                getTagId() == that.getTagId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGiftCertificateId(), getTagId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GiftCertificatesTags.class.getSimpleName() + "[", "]")
                .add("giftCertificateId=" + giftCertificateId)
                .add("tagId=" + tagId)
                .toString();
    }
}
