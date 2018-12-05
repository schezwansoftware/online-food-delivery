package com.codesetters.account.repository;

import com.codesetters.account.domain.Address;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data Cassandra repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends CassandraRepository<Address, UUID> {

}
