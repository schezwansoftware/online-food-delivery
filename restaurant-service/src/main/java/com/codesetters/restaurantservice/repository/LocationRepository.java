package com.codesetters.restaurantservice.repository;

import com.codesetters.restaurantservice.domain.Location;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Location entity.
 */
@Repository
public class LocationRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Location> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public LocationRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Location.class);
        this.findAllStmt = session.prepare("SELECT * FROM location");
        this.truncateStmt = session.prepare("TRUNCATE location");
    }

    public List<Location> findAll() {
        List<Location> locationsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Location location = new Location();
                location.setId(row.getUUID("id"));
                location.setAddress(row.getString("address"));
                location.setState(row.getString("state"));
                location.setCity(row.getString("city"));
                location.setLocality(row.getString("locality"));
                location.setLongitude(row.getDouble("longitude"));
                location.setLatitude(row.getDouble("latitude"));
                location.setPincode(row.getString("pincode"));
                location.setCountryName(row.getString("countryName"));
                location.setCountryid(row.getString("countryid"));
                return location;
            }
        ).forEach(locationsList::add);
        return locationsList;
    }

    public Location findOne(UUID id) {
        return mapper.get(id);
    }

    public Location save(Location location) {
        if (location.getId() == null) {
            location.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(location);
        return location;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
