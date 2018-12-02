package com.codesetters.restaurantservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A Restaurant.
 */
@Table("restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private Set<String> cuisineTypes;

    private UUID currentMenuId;

    private String executiveLogin;

    private UUID locationId;

    private String name;

    private ZonedDateTime registrationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<String> getCuisineTypes() {
        return cuisineTypes;
    }

    public Restaurant cuisineTypes(Set<String> cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
        return this;
    }

    public void setCuisineTypes(Set<String> cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public UUID getCurrentMenuId() {
        return currentMenuId;
    }

    public Restaurant currentMenuId(UUID currentMenuId) {
        this.currentMenuId = currentMenuId;
        return this;
    }

    public void setCurrentMenuId(UUID currentMenuId) {
        this.currentMenuId = currentMenuId;
    }

    public String getExecutiveLogin() {
        return executiveLogin;
    }

    public Restaurant executiveLogin(String executiveLogin) {
        this.executiveLogin = executiveLogin;
        return this;
    }

    public void setExecutiveLogin(String executiveLogin) {
        this.executiveLogin = executiveLogin;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public Restaurant locationId(UUID locationId) {
        this.locationId = locationId;
        return this;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Restaurant registrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
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
        Restaurant restaurant = (Restaurant) o;
        if (restaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurant{" +
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
