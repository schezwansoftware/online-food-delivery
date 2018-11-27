package com.codesetters.restaurantservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.restaurantservice.service.RestaurantService;
import com.codesetters.restaurantservice.service.dto.RestLocationDTO;
import com.codesetters.restaurantservice.web.rest.errors.BadRequestAlertException;
import com.codesetters.restaurantservice.web.rest.util.HeaderUtil;
import com.codesetters.restaurantservice.service.dto.RestaurantDTO;
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
 * REST controller for managing Restaurant.
 */
@RestController
@RequestMapping("/api")
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    private static final String ENTITY_NAME = "restaurantServiceRestaurant";

    private final RestaurantService restaurantService;

    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * POST  /restaurants : Create a new restaurant.
     *
     * @param restaurantDTO the restaurantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurantDTO, or with status 400 (Bad Request) if the restaurant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurants")
    @Timed
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurantDTO);
        if (restaurantDTO.getId() != null) {
            throw new BadRequestAlertException("A new restaurant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        restaurantDTO.setId(UUID.randomUUID());
        RestaurantDTO result = restaurantService.save(restaurantDTO);
        return ResponseEntity.created(new URI("/api/restaurants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurants : Updates an existing restaurant.
     *
     * @param restaurantDTO the restaurantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurantDTO,
     * or with status 400 (Bad Request) if the restaurantDTO is not valid,
     * or with status 500 (Internal Server Error) if the restaurantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurants")
    @Timed
    public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO restaurantDTO) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurantDTO);
        if (restaurantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestaurantDTO result = restaurantService.save(restaurantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurants : get all the restaurants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restaurants in body
     */
    @GetMapping("/restaurants")
    @Timed
    public List<RestaurantDTO> getAllRestaurants() {
        log.debug("REST request to get all Restaurants");
        return restaurantService.findAll();
    }

    /**
     * GET  /restaurants/:id : get the "id" restaurant.
     *
     * @param id the id of the restaurantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable UUID id) {
        log.debug("REST request to get Restaurant : {}", id);
        Optional<RestaurantDTO> restaurantDTO = restaurantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurantDTO);
    }

    /**
     * DELETE  /restaurants/:id : delete the "id" restaurant.
     *
     * @param id the id of the restaurantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID id) {
        log.debug("REST request to delete Restaurant : {}", id);
        restaurantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/restaurants-location")
    @Timed
    public ResponseEntity<RestLocationDTO> createRestaurantAndLocation(@RequestBody RestLocationDTO restLocationDTO) throws URISyntaxException {
        log.debug("REST request to save Restaurant and Location : {}", restLocationDTO);

        RestLocationDTO result = restaurantService.saveRestLocation(restLocationDTO);
        return ResponseEntity.created(new URI("/api/restaurants-location/"))
            .headers(HeaderUtil.createEntityCreationAlert("RestLocation","created"))
            .body(result);
    }
}
