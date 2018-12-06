package com.codesetters.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Orders.
 */
@Table("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    private UUID restaurantId;

    @NotNull
    private String userId;

    @NotNull
    private String status;

    @NotNull
    private String paymentStatus;

    private Double totalPrice;

    private String deliveryAddress;

    private String specialNote;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public Orders restaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public Orders userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public Orders status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Orders paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Orders totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public Orders deliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public Orders specialNote(String specialNote) {
        this.specialNote = specialNote;
        return this;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if (orders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", restaurantId='" + getRestaurantId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", specialNote='" + getSpecialNote() + "'" +
            "}";
    }
}
