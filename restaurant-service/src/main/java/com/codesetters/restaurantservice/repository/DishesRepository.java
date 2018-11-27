package com.codesetters.restaurantservice.repository;

import com.codesetters.restaurantservice.domain.Dishes;
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
 * Cassandra repository for the Dishes entity.
 */
@Repository
public class DishesRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Dishes> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public DishesRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Dishes.class);
        this.findAllStmt = session.prepare("SELECT * FROM dishes");
        this.truncateStmt = session.prepare("TRUNCATE dishes");
    }

    public List<Dishes> findAll() {
        List<Dishes> dishesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Dishes dishes = new Dishes();
                dishes.setId(row.getUUID("id"));
                dishes.setMenuId(row.getUUID("menuId"));
                dishes.setDishName(row.getString("dishName"));
                dishes.setDishType(row.getString("dishType"));
                dishes.setDishPrice(row.getDouble("dishPrice"));
                return dishes;
            }
        ).forEach(dishesList::add);
        return dishesList;
    }

    public Dishes findOne(UUID id) {
        return mapper.get(id);
    }

    public Dishes save(Dishes dishes) {
        if (dishes.getId() == null) {
            dishes.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Dishes>> violations = validator.validate(dishes);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(dishes);
        return dishes;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
