package com.codesetters.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A OrderItem.
 */
@Table("orderItem")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @NotNull
    private UUID orderId;

    @NotNull
    private String itemName;

    private UUID itemId;

    @NotNull
    @Min(value = 1)
    private Double itemPrice;

    @NotNull
    @Min(value = 1)
    private Integer itemQuantity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderItem orderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public OrderItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public UUID getItemId() {
        return itemId;
    }

    public OrderItem itemId(UUID itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public OrderItem itemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public OrderItem itemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
        return this;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
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
        OrderItem orderItem = (OrderItem) o;
        if (orderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", itemId='" + getItemId() + "'" +
            ", itemPrice=" + getItemPrice() +
            ", itemQuantity=" + getItemQuantity() +
            "}";
    }
}
