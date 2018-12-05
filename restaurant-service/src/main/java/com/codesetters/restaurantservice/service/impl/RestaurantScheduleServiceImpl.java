package com.codesetters.restaurantservice.service.impl;

import com.codesetters.restaurantservice.domain.Restaurant;
import com.codesetters.restaurantservice.repository.RestaurantRepository;
import com.codesetters.restaurantservice.service.RestaurantScheduleService;
import com.codesetters.restaurantservice.domain.RestaurantSchedule;
import com.codesetters.restaurantservice.repository.RestaurantScheduleRepository;
import com.codesetters.restaurantservice.service.dto.DailyScheduleDTO;
import com.codesetters.restaurantservice.service.dto.RestaurantScheduleDTO;
import com.codesetters.restaurantservice.service.mapper.RestaurantScheduleMapper;
import com.codesetters.restaurantservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RestaurantSchedule.
 */
@Service
public class RestaurantScheduleServiceImpl implements RestaurantScheduleService {

    private final Logger log = LoggerFactory.getLogger(RestaurantScheduleServiceImpl.class);

    private final RestaurantScheduleRepository restaurantScheduleRepository;

    private final RestaurantScheduleMapper restaurantScheduleMapper;

    private final RestaurantRepository restaurantRepository;

    public RestaurantScheduleServiceImpl(RestaurantScheduleRepository restaurantScheduleRepository, RestaurantScheduleMapper restaurantScheduleMapper, RestaurantRepository restaurantRepository) {
        this.restaurantScheduleRepository = restaurantScheduleRepository;
        this.restaurantScheduleMapper = restaurantScheduleMapper;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Save a restaurantSchedule.
     *
     * @param restaurantScheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RestaurantScheduleDTO save(RestaurantScheduleDTO restaurantScheduleDTO) {
        log.debug("Request to save RestaurantSchedule : {}", restaurantScheduleDTO);

        RestaurantSchedule restaurantSchedule = restaurantScheduleMapper.toEntity(restaurantScheduleDTO);
        restaurantSchedule = restaurantScheduleRepository.save(restaurantSchedule);
        return restaurantScheduleMapper.toDto(restaurantSchedule);
    }

    @Override
    public RestaurantScheduleDTO saveDailySchedule(DailyScheduleDTO dailyScheduleDTO) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(dailyScheduleDTO.getRestaurantId());
        if (!restaurant.isPresent()){
            throw new BadRequestAlertException("Invalid restaurantid","restaurant","restaurantnotexists");
        }
        RestaurantScheduleDTO scheduleDTO = new RestaurantScheduleDTO();
        for (RestaurantScheduleDTO restaurantScheduleDTO: dailyScheduleDTO.getSchedule()){
            restaurantScheduleDTO.setId(UUID.randomUUID());
            restaurantScheduleDTO.setRestaurantId(dailyScheduleDTO.getRestaurantId());

            RestaurantSchedule restaurantSchedule = restaurantScheduleMapper.toEntity(restaurantScheduleDTO);
             restaurantSchedule = restaurantScheduleRepository.save(restaurantSchedule);
             scheduleDTO = restaurantScheduleMapper.toDto(restaurantSchedule);
        }
        return scheduleDTO;
    }

    /**
     * Get all the restaurantSchedules.
     *
     * @return the list of entities
     */
    @Override
    public List<RestaurantScheduleDTO> findAll() {
        log.debug("Request to get all RestaurantSchedules");
        return restaurantScheduleRepository.findAll().stream()
            .map(restaurantScheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one restaurantSchedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<RestaurantScheduleDTO> findOne(UUID id) {
        log.debug("Request to get RestaurantSchedule : {}", id);
        return restaurantScheduleRepository.findById(id)
            .map(restaurantScheduleMapper::toDto);
    }

    /**
     * Delete the restaurantSchedule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete RestaurantSchedule : {}", id);
        restaurantScheduleRepository.deleteById(id);
    }
}
