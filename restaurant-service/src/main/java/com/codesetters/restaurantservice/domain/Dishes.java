package com.codesetters.restaurantservice.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Dishes.
 */
@Table(name = "dishes")
public class Dishes implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private UUID menuId;

    private String dishName;

    private String dishType;

    private Double dishPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Dishes menuId(UUID menuId) {
        this.menuId = menuId;
        return this;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public String getDishName() {
        return dishName;
    }

    public Dishes dishName(String dishName) {
        this.dishName = dishName;
        return this;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishType() {
        return dishType;
    }

    public Dishes dishType(String dishType) {
        this.dishType = dishType;
        return this;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public Dishes dishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
        return this;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
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
        Dishes dishes = (Dishes) o;
        if (dishes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dishes{" +
            "id=" + getId() +
            ", menuId='" + getMenuId() + "'" +
            ", dishName='" + getDishName() + "'" +
            ", dishType='" + getDishType() + "'" +
            ", dishPrice='" + getDishPrice() + "'" +
            "}";
    }
}
