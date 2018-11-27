package com.codesetters.restaurantservice.repository;

import com.codesetters.restaurantservice.domain.Restaurant;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends CassandraRepository<Restaurant, UUID> {

}
