package com.codesetters.restaurantservice.repository;

import com.codesetters.restaurantservice.domain.RestaurantSchedule;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the RestaurantSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantScheduleRepository extends CassandraRepository<RestaurantSchedule, UUID> {

}
