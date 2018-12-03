package com.codesetters.restaurantservice.service.impl;

import com.codesetters.restaurantservice.security.SecurityUtils;
import com.codesetters.restaurantservice.service.LocationService;
import com.codesetters.restaurantservice.service.RestaurantService;
import com.codesetters.restaurantservice.domain.Restaurant;
import com.codesetters.restaurantservice.repository.RestaurantRepository;
import com.codesetters.restaurantservice.service.dto.LocationDTO;
import com.codesetters.restaurantservice.service.dto.RestLocationDTO;
import com.codesetters.restaurantservice.service.dto.RestaurantDTO;
import com.codesetters.restaurantservice.service.mapper.RestaurantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Restaurant.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantRepository restaurantRepository;

    private final LocationService locationService;

    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, LocationService locationService, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.locationService = locationService;
        this.restaurantMapper = restaurantMapper;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);

        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    /**
     * Get all the restaurants.
     *
     * @return the list of entities
     */
    @Override
    public List<RestaurantDTO> findAll() {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll().stream()
            .map(restaurantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<RestaurantDTO> findOne(UUID id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id)
            .map(restaurantMapper::toDto);
    }

    @Override
    public Optional<RestaurantDTO> findOneByRestaurantExecutive(String login) {
        List<RestaurantDTO> restaurantDTOS = this.findAll().stream().filter(restaurantDTO -> restaurantDTO.getExecutiveLogin().equals(login)).collect(Collectors.toList());
        if (restaurantDTOS.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(restaurantDTOS.get(0));
    }

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
    }

    @Override
    public RestLocationDTO saveRestLocation(RestLocationDTO restLocationDTO) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        LocationDTO locationDTO = new LocationDTO();

        restaurantDTO.setCuisineTypes(restLocationDTO.getCuisineTypes());
        restaurantDTO.setExecutiveLogin(SecurityUtils.getCurrentUserLogin().get());
        restaurantDTO.setId(UUID.randomUUID());
        restaurantDTO.setName(restLocationDTO.getName());
        restaurantDTO.setRegistrationDate(ZonedDateTime.now());

        locationDTO.setAddress(restLocationDTO.getAddress());
        locationDTO.setPincode(restLocationDTO.getPincode());
        locationDTO.setLatitude(restLocationDTO.getLatitude());
        locationDTO.setLongitude(restLocationDTO.getLongitude());
        locationDTO.setLocality(restLocationDTO.getLocality());
        locationDTO.setId(UUID.randomUUID());

        LocationDTO savelocationDTO = new LocationDTO();
        savelocationDTO = locationService.save(locationDTO);
        if(savelocationDTO !=null){
            restaurantDTO.setLocationId(savelocationDTO.getId());
            restaurantDTO.setZomatoId(restLocationDTO.getId());
            this.save(restaurantDTO);
        }
        return restLocationDTO;
    }
}
