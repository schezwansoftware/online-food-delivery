package com.codesetters.service.impl;

import com.codesetters.service.OrderItemService;
import com.codesetters.domain.OrderItem;
import com.codesetters.repository.OrderItemRepository;
import com.codesetters.service.dto.OrderItemDTO;
import com.codesetters.service.mapper.OrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrderItem.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    /**
     * Save a orderItem.
     *
     * @param orderItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderItemDTO save(OrderItemDTO orderItemDTO) {
        log.debug("Request to save OrderItem : {}", orderItemDTO);

        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDto(orderItem);
    }

    /**
     * Get all the orderItems.
     *
     * @return the list of entities
     */
    @Override
    public List<OrderItemDTO> findAll() {
        log.debug("Request to get all OrderItems");
        return orderItemRepository.findAll().stream()
            .map(orderItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<OrderItemDTO> findOne(UUID id) {
        log.debug("Request to get OrderItem : {}", id);
        return orderItemRepository.findById(id)
            .map(orderItemMapper::toDto);
    }

    /**
     * Delete the orderItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete OrderItem : {}", id);
        orderItemRepository.deleteById(id);
    }
}
