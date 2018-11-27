package com.codesetters.restaurantservice.repository;

import com.codesetters.restaurantservice.domain.Menu;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Menu entity.
 */
@Repository
public class MenuRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Menu> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public MenuRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Menu.class);
        this.findAllStmt = session.prepare("SELECT * FROM menu");
        this.truncateStmt = session.prepare("TRUNCATE menu");
    }

    public List<Menu> findAll() {
        List<Menu> menusList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Menu menu = new Menu();
                menu.setId(row.getUUID("id"));
                menu.setRestaurantId(row.getUUID("restaurantId"));
                menu.setStartDate(row.get("startDate", ZonedDateTime.class));
                menu.setEndDate(row.get("endDate", ZonedDateTime.class));
                return menu;
            }
        ).forEach(menusList::add);
        return menusList;
    }

    public Menu findOne(UUID id) {
        return mapper.get(id);
    }

    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            menu.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Menu>> violations = validator.validate(menu);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(menu);
        return menu;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
