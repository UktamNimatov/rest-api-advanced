package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties("giftCertificateList")
@EntityListeners(AuditListener.class)
public class Order extends com.epam.esm.entity.Entity {

    private static final long serialVersionUID = 1L;

    @Column(name = "price")
    private double price;

    @Column(name = "purchase_time")
    private String purchaseTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "order_certificates",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")})
    private List<GiftCertificate> giftCertificateList;

    public Order() {
    }

    public Order(long id, double price, String purchaseTime, User user, List<GiftCertificate> giftCertificateList) {
        super(id);
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.user = user;
        this.giftCertificateList = giftCertificateList;
    }

    public Order(double price, String purchaseTime, User user, List<GiftCertificate> giftCertificateList) {
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.user = user;
        this.giftCertificateList = giftCertificateList;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPrice(), getPrice()) == 0 &&
                Objects.equals(getPurchaseTime(), order.getPurchaseTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrice(), getPurchaseTime());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("price=" + price)
                .add("purchaseTime='" + purchaseTime + "'")
                .toString();
    }
}
