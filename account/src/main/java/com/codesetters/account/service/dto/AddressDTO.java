package com.codesetters.account.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private UUID id;

    private String latitude;

    private String longitude;

    private String locality;

    private String city;

    private String address;

    private String type;

    private String userId;

    private String landmark;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", locality='" + getLocality() + "'" +
            ", city='" + getCity() + "'" +
            ", address='" + getAddress() + "'" +
            ", type='" + getType() + "'" +
            ", userId='" + getUserId() + "'" +
            ", landmark='" + getLandmark() + "'" +
            "}";
    }
}
