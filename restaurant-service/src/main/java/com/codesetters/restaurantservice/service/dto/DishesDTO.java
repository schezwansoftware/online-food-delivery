package com.codesetters.restaurantservice.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Dishes entity.
 */
public class DishesDTO implements Serializable {

    private UUID id;

    private UUID menuId;

    private String dishName;

    private String dishType;

    private Double dishPrice;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishesDTO dishesDTO = (DishesDTO) o;
        if(dishesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishesDTO{" +
            "id=" + getId() +
            ", menuId='" + getMenuId() + "'" +
            ", dishName='" + getDishName() + "'" +
            ", dishType='" + getDishType() + "'" +
            ", dishPrice='" + getDishPrice() + "'" +
            "}";
    }
}
