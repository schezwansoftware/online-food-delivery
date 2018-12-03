package com.codesetters.restaurantservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.restaurantservice.service.RestaurantScheduleService;
import com.codesetters.restaurantservice.web.rest.errors.BadRequestAlertException;
import com.codesetters.restaurantservice.web.rest.util.HeaderUtil;
import com.codesetters.restaurantservice.service.dto.RestaurantScheduleDTO;
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
 * REST controller for managing RestaurantSchedule.
 */
@RestController
@RequestMapping("/api")
public class RestaurantScheduleResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantScheduleResource.class);

    private static final String ENTITY_NAME = "restaurantServiceRestaurantSchedule";

    private final RestaurantScheduleService restaurantScheduleService;

    public RestaurantScheduleResource(RestaurantScheduleService restaurantScheduleService) {
        this.restaurantScheduleService = restaurantScheduleService;
    }

    /**
     * POST  /restaurant-schedules : Create a new restaurantSchedule.
     *
     * @param restaurantScheduleDTO the restaurantScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurantScheduleDTO, or with status 400 (Bad Request) if the restaurantSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurant-schedules")
    @Timed
    public ResponseEntity<RestaurantScheduleDTO> createRestaurantSchedule(@RequestBody RestaurantScheduleDTO restaurantScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save RestaurantSchedule : {}", restaurantScheduleDTO);
        if (restaurantScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new restaurantSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        restaurantScheduleDTO.setId(UUID.randomUUID());
        RestaurantScheduleDTO result = restaurantScheduleService.save(restaurantScheduleDTO);
        return ResponseEntity.created(new URI("/api/restaurant-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurant-schedules : Updates an existing restaurantSchedule.
     *
     * @param restaurantScheduleDTO the restaurantScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurantScheduleDTO,
     * or with status 400 (Bad Request) if the restaurantScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the restaurantScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurant-schedules")
    @Timed
    public ResponseEntity<RestaurantScheduleDTO> updateRestaurantSchedule(@RequestBody RestaurantScheduleDTO restaurantScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update RestaurantSchedule : {}", restaurantScheduleDTO);
        if (restaurantScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RestaurantScheduleDTO result = restaurantScheduleService.save(restaurantScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurantScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurant-schedules : get all the restaurantSchedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restaurantSchedules in body
     */
    @GetMapping("/restaurant-schedules")
    @Timed
    public List<RestaurantScheduleDTO> getAllRestaurantSchedules() {
        log.debug("REST request to get all RestaurantSchedules");
        return restaurantScheduleService.findAll();
    }

    /**
     * GET  /restaurant-schedules/:id : get the "id" restaurantSchedule.
     *
     * @param id the id of the restaurantScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurantScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/restaurant-schedules/{id}")
    @Timed
    public ResponseEntity<RestaurantScheduleDTO> getRestaurantSchedule(@PathVariable UUID id) {
        log.debug("REST request to get RestaurantSchedule : {}", id);
        Optional<RestaurantScheduleDTO> restaurantScheduleDTO = restaurantScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurantScheduleDTO);
    }

    /**
     * DELETE  /restaurant-schedules/:id : delete the "id" restaurantSchedule.
     *
     * @param id the id of the restaurantScheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurant-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurantSchedule(@PathVariable UUID id) {
        log.debug("REST request to delete RestaurantSchedule : {}", id);
        restaurantScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
