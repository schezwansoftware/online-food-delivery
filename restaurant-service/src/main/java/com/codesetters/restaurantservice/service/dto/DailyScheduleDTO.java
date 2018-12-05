package com.codesetters.restaurantservice.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class DailyScheduleDTO implements Serializable {

    private UUID restaurantId;

    private List<RestaurantScheduleDTO> schedule;

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<RestaurantScheduleDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<RestaurantScheduleDTO> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "DailySchedule{" +
            "restaurantId=" + getRestaurantId() +
            ", schedule='" + getSchedule() + "'" +
            "}";
    }
}
