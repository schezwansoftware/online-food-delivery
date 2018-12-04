package com.codesetters.restaurantservice.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class MenuItemDto implements Serializable {

    private ZonedDateTime endDate;

    private UUID restaurantId;

   private List<DishesDTO> dishes;


    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<DishesDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishesDTO> dishes) {
        this.dishes = dishes;
    }
}
