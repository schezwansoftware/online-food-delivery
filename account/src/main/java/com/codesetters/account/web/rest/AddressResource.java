package com.codesetters.account.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.account.service.AddressService;
import com.codesetters.account.web.rest.errors.BadRequestAlertException;
import com.codesetters.account.web.rest.util.HeaderUtil;
import com.codesetters.account.service.dto.AddressDTO;
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
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * POST  /addresses : Create a new address.
     *
     * @param addressDTO the addressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressDTO, or with status 400 (Bad Request) if the address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressDTO);
        if (addressDTO.getId() != null) {
            throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
        }
        addressDTO.setId(UUID.randomUUID());
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addresses : Updates an existing address.
     *
     * @param addressDTO the addressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressDTO,
     * or with status 400 (Bad Request) if the addressDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addresses")
    @Timed
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to update Address : {}", addressDTO);
        if (addressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressDTO result = addressService.save(addressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addresses : get all the addresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addresses in body
     */
    @GetMapping("/addresses")
    @Timed
    public List<AddressDTO> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressService.findAll();
    }

    /**
     * GET  /addresses/:id : get the "id" address.
     *
     * @param id the id of the addressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressDTO, or with status 404 (Not Found)
     */
    @GetMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<AddressDTO> getAddress(@PathVariable UUID id) {
        log.debug("REST request to get Address : {}", id);
        Optional<AddressDTO> addressDTO = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressDTO);
    }

    /**
     * DELETE  /addresses/:id : delete the "id" address.
     *
     * @param id the id of the addressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
