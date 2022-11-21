package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;

    @NotNull
    @Min(0L)
    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private String purchaseTime;

    @NotNull
    @Min(1)
    private long userId;

    @JsonIgnore
    private List<GiftCertificate> giftCertificateList;

    public OrderDto() {
    }

    public OrderDto(long id, double price, String purchaseTime, long userId, List<GiftCertificate> giftCertificateList) {
        this.id = id;
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.userId = userId;
        this.giftCertificateList = giftCertificateList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        if (!(o instanceof OrderDto)) return false;
        if (!super.equals(o)) return false;
        OrderDto orderDto = (OrderDto) o;
        return getId() == orderDto.getId() &&
                Double.compare(orderDto.getPrice(), getPrice()) == 0 &&
                getUserId() == orderDto.getUserId() &&
                Objects.equals(getPurchaseTime(), orderDto.getPurchaseTime()) &&
                Objects.equals(getGiftCertificateList(), orderDto.getGiftCertificateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getPrice(), getPurchaseTime(), getUserId(), getGiftCertificateList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("price=" + price)
                .add("purchaseTime='" + purchaseTime + "'")
                .add("userId=" + userId)
                .toString();
    }
}
