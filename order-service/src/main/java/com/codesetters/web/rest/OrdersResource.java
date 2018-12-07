package com.codesetters.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.service.OrdersService;
import com.codesetters.service.dto.Order;
import com.codesetters.service.dto.OrderItemDTO;
import com.codesetters.service.dto.OrderUpdateStatusDTO;
import com.codesetters.web.rest.errors.BadRequestAlertException;
import com.codesetters.web.rest.util.HeaderUtil;
import com.codesetters.service.dto.OrdersDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Orders.
 */
@RestController
@RequestMapping("/api")
public class OrdersResource {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);

    private static final String ENTITY_NAME = "orderServiceOrders";

    private final OrdersService ordersService;

    public OrdersResource(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * POST  /orders : Create a new orders.
     *
     * @param order the order to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordersDTO, or with status 400 (Bad Request) if the orders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orders")
    @Timed
    public ResponseEntity<Order> createOrders(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("Rest Request to Create Order, {}",order);

        Order result = ordersService.createOrder(order);
        return ResponseEntity.created(new URI("/api/orders/" + result.getOrderInfo().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderInfo().getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orders : Updates an existing orders.
     *
     * @param updateStatusDTO the OrderUpdateStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordersDTO,
     * or with status 400 (Bad Request) if the ordersDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orders")
    @Timed
    public ResponseEntity<OrdersDTO> updateOrders(@Valid @RequestBody OrderUpdateStatusDTO updateStatusDTO) throws URISyntaxException {
        log.debug("REST request to update Order : {}", updateStatusDTO.getOrderId());

        OrdersDTO result = ordersService.updateOrderStatus(updateStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orders : get all the orders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orders in body
     */
    @GetMapping("/orders")
    @Timed
    public List<OrdersDTO> getAllOrders() {
        log.debug("REST request to get all Orders");
        return ordersService.findAll();
    }


    /**
     * GET  /orders/:id : get the "id" orders.
     *
     * @param id the id of the ordersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}")
    @Timed
    public ResponseEntity<OrdersDTO> getOrders(@PathVariable UUID id) {
        log.debug("REST request to get Orders : {}", id);
        Optional<OrdersDTO> ordersDTO = ordersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordersDTO);
    }

    /**
     * DELETE  /orders/:id : delete the "id" orders.
     *
     * @param id the id of the ordersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrders(@PathVariable UUID id) {
        log.debug("REST request to delete Orders : {}", id);
        ordersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
