package com.codesetters.restaurantservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.restaurantservice.service.DishesService;
import com.codesetters.restaurantservice.web.rest.errors.BadRequestAlertException;
import com.codesetters.restaurantservice.web.rest.util.HeaderUtil;
import com.codesetters.restaurantservice.service.dto.DishesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Dishes.
 */
@RestController
@RequestMapping("/api")
public class DishesResource {

    private final Logger log = LoggerFactory.getLogger(DishesResource.class);

    private static final String ENTITY_NAME = "dishes";

    private final DishesService dishesService;

    public DishesResource(DishesService dishesService) {
        this.dishesService = dishesService;
    }

    /**
     * POST  /dishes : Create a new dishes.
     *
     * @param dishesDTO the dishesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dishesDTO, or with status 400 (Bad Request) if the dishes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dishes")
    @Timed
    public ResponseEntity<DishesDTO> createDishes(@RequestBody DishesDTO dishesDTO) throws URISyntaxException {
        log.debug("REST request to save Dishes : {}", dishesDTO);
        if (dishesDTO.getId() != null) {
            throw new BadRequestAlertException("A new dishes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DishesDTO result = dishesService.save(dishesDTO);
        return ResponseEntity.created(new URI("/api/dishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dishes : Updates an existing dishes.
     *
     * @param dishesDTO the dishesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dishesDTO,
     * or with status 400 (Bad Request) if the dishesDTO is not valid,
     * or with status 500 (Internal Server Error) if the dishesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dishes")
    @Timed
    public ResponseEntity<DishesDTO> updateDishes(@RequestBody DishesDTO dishesDTO) throws URISyntaxException {
        log.debug("REST request to update Dishes : {}", dishesDTO);
        if (dishesDTO.getId() == null) {
            return createDishes(dishesDTO);
        }
        DishesDTO result = dishesService.save(dishesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dishesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dishes : get all the dishes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dishes in body
     */
    @GetMapping("/dishes")
    @Timed
    public List<DishesDTO> getAllDishes() {
        log.debug("REST request to get all Dishes");
        return dishesService.findAll();
        }

    /**
     * GET  /dishes/:id : get the "id" dishes.
     *
     * @param id the id of the dishesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dishesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dishes/{id}")
    @Timed
    public ResponseEntity<DishesDTO> getDishes(@PathVariable String id) {
        log.debug("REST request to get Dishes : {}", id);
        DishesDTO dishesDTO = dishesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dishesDTO));
    }

    /**
     * DELETE  /dishes/:id : delete the "id" dishes.
     *
     * @param id the id of the dishesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dishes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDishes(@PathVariable String id) {
        log.debug("REST request to delete Dishes : {}", id);
        dishesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
