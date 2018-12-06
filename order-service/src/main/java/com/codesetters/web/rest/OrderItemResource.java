package com.codesetters.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.service.OrderItemService;
import com.codesetters.web.rest.errors.BadRequestAlertException;
import com.codesetters.web.rest.util.HeaderUtil;
import com.codesetters.service.dto.OrderItemDTO;
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
 * REST controller for managing OrderItem.
 */
@RestController
@RequestMapping("/api")
public class OrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemResource.class);

    private static final String ENTITY_NAME = "orderServiceOrderItem";

    private final OrderItemService orderItemService;

    public OrderItemResource(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * POST  /order-items : Create a new orderItem.
     *
     * @param orderItemDTO the orderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderItemDTO, or with status 400 (Bad Request) if the orderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to save OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        orderItemDTO.setId(UUID.randomUUID());
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-items : Updates an existing orderItem.
     *
     * @param orderItemDTO the orderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderItemDTO,
     * or with status 400 (Bad Request) if the orderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-items")
    @Timed
    public ResponseEntity<OrderItemDTO> updateOrderItem(@RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to update OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-items : get all the orderItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderItems in body
     */
    @GetMapping("/order-items")
    @Timed
    public List<OrderItemDTO> getAllOrderItems() {
        log.debug("REST request to get all OrderItems");
        return orderItemService.findAll();
    }

    /**
     * GET  /order-items/:id : get the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<OrderItemDTO> getOrderItem(@PathVariable UUID id) {
        log.debug("REST request to get OrderItem : {}", id);
        Optional<OrderItemDTO> orderItemDTO = orderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderItemDTO);
    }

    /**
     * DELETE  /order-items/:id : delete the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderItem(@PathVariable UUID id) {
        log.debug("REST request to delete OrderItem : {}", id);
        orderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
