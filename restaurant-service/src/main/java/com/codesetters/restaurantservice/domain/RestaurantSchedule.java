package com.codesetters.restaurantservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A RestaurantSchedule.
 */
@Table("restaurantSchedule")
public class RestaurantSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private UUID restaurantId;

    private String day;

    private String openingTime;

    private String closingTime;

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

    public RestaurantSchedule restaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDay() {
        return day;
    }

    public RestaurantSchedule day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public RestaurantSchedule openingTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public RestaurantSchedule closingTime(String closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
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
        RestaurantSchedule restaurantSchedule = (RestaurantSchedule) o;
        if (restaurantSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantSchedule{" +
            "id=" + getId() +
            ", restaurantId='" + getRestaurantId() + "'" +
            ", day='" + getDay() + "'" +
            ", openingTime='" + getOpeningTime() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            "}";
    }
}
