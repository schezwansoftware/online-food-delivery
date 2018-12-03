package com.codesetters.restaurantservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the RestaurantSchedule entity.
 */
public class RestaurantScheduleDTO implements Serializable {

    private UUID id;

    private UUID restaurantId;

    private String day;

    private String openingTime;

    private String closingTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestaurantScheduleDTO restaurantScheduleDTO = (RestaurantScheduleDTO) o;
        if (restaurantScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantScheduleDTO{" +
            "id=" + getId() +
            ", restaurantId='" + getRestaurantId() + "'" +
            ", day='" + getDay() + "'" +
            ", openingTime='" + getOpeningTime() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            "}";
    }
}
