package com.codesetters.restaurantservice.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Location.
 */
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String address;

    private String state;

    private String city;

    private String locality;

    private Double longitude;

    private Double latitude;

    private String pincode;

    private String countryName;

    private String countryid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public Location address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public Location state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public Location locality(String locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPincode() {
        return pincode;
    }

    public Location pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountryName() {
        return countryName;
    }

    public Location countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryid() {
        return countryid;
    }

    public Location countryid(String countryid) {
        this.countryid = countryid;
        return this;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", locality='" + getLocality() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", countryid='" + getCountryid() + "'" +
            "}";
    }
}
