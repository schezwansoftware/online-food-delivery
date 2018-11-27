package com.codesetters.restaurantservice.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the Restaurant entity.
 */
public class RestLocationDTO implements Serializable {

    private UUID id;

    private Set<String> cuisineTypes;

    private String name;

    private String locality;

    private Double longitude;

    private Double latitude;

    private String pincode;

    private String address;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<String> getCuisineTypes() {
        return cuisineTypes;
    }

    public void setCuisineTypes(Set<String> cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestLocationDTO restaurantDTO = (RestLocationDTO) o;
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
            ", name='" + getName() + "'" +
            ", locality='" + getLocality() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
