package com.codesetters.restaurantservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Restaurant entity.
 */
public class RestaurantDTO implements Serializable {

    private UUID id;

    private String cuisineTypes;

    private UUID currentMenuId;

    private String executiveLogin;

    private UUID locationId;

    private String name;

    private ZonedDateTime registrationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCuisineTypes() {
        return cuisineTypes;
    }

    public void setCuisineTypes(String cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public UUID getCurrentMenuId() {
        return currentMenuId;
    }

    public void setCurrentMenuId(UUID currentMenuId) {
        this.currentMenuId = currentMenuId;
    }

    public String getExecutiveLogin() {
        return executiveLogin;
    }

    public void setExecutiveLogin(String executiveLogin) {
        this.executiveLogin = executiveLogin;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;
        if (restaurantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", cuisineTypes='" + getCuisineTypes() + "'" +
            ", currentMenuId='" + getCurrentMenuId() + "'" +
            ", executiveLogin='" + getExecutiveLogin() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", name='" + getName() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            "}";
    }
}
