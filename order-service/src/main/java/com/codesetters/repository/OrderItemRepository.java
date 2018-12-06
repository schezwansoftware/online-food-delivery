package com.codesetters.repository;

import com.codesetters.domain.OrderItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends CassandraRepository<OrderItem, UUID> {

}
