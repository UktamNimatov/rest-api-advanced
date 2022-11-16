package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")
    private String createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]XXX")
    private String lastUpdateDate;

    private List<Tag> tagList;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(long id, String name, String description, double price,
                              int duration, String createDate, String lastUpdateDate, List<Tag> tagList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDto)) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDto that = (GiftCertificateDto) o;
        return getId() == that.getId() &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                getDuration() == that.getDuration() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreateDate(), that.getCreateDate()) &&
                Objects.equals(getLastUpdateDate(), that.getLastUpdateDate()) &&
                Objects.equals(getTagList(), that.getTagList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName(), getDescription(), getPrice(), getDuration(), getCreateDate(), getLastUpdateDate(), getTagList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GiftCertificateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("price=" + price)
                .add("duration=" + duration)
                .add("createDate='" + createDate + "'")
                .add("lastUpdateDate='" + lastUpdateDate + "'")
                .toString();
    }
}
