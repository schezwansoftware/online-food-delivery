package com.codesetters.restaurantservice.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MenuItemDto implements Serializable {

    private LocalDate date;

    private UUID restaurantId;

   private List<DishesDTO> dishes;

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


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
