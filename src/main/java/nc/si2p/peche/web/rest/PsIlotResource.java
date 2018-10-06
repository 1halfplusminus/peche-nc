package nc.si2p.peche.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.si2p.peche.domain.PsIlot;
import nc.si2p.peche.repository.PsIlotRepository;
import nc.si2p.peche.repository.search.PsIlotSearchRepository;
import nc.si2p.peche.web.rest.errors.BadRequestAlertException;
import nc.si2p.peche.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PsIlot.
 */
@RestController
@RequestMapping("/api")
public class PsIlotResource {

    private final Logger log = LoggerFactory.getLogger(PsIlotResource.class);

    private static final String ENTITY_NAME = "psIlot";

    private final PsIlotRepository psIlotRepository;

    private final PsIlotSearchRepository psIlotSearchRepository;

    public PsIlotResource(PsIlotRepository psIlotRepository, PsIlotSearchRepository psIlotSearchRepository) {
        this.psIlotRepository = psIlotRepository;
        this.psIlotSearchRepository = psIlotSearchRepository;
    }

    /**
     * POST  /ps-ilots : Create a new psIlot.
     *
     * @param psIlot the psIlot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new psIlot, or with status 400 (Bad Request) if the psIlot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ps-ilots")
    @Timed
    public ResponseEntity<PsIlot> createPsIlot(@RequestBody PsIlot psIlot) throws URISyntaxException {
        log.debug("REST request to save PsIlot : {}", psIlot);
        if (psIlot.getId() != null) {
            throw new BadRequestAlertException("A new psIlot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PsIlot result = psIlotRepository.save(psIlot);
        psIlotSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ps-ilots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ps-ilots : Updates an existing psIlot.
     *
     * @param psIlot the psIlot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated psIlot,
     * or with status 400 (Bad Request) if the psIlot is not valid,
     * or with status 500 (Internal Server Error) if the psIlot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ps-ilots")
    @Timed
    public ResponseEntity<PsIlot> updatePsIlot(@RequestBody PsIlot psIlot) throws URISyntaxException {
        log.debug("REST request to update PsIlot : {}", psIlot);
        if (psIlot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PsIlot result = psIlotRepository.save(psIlot);
        psIlotSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, psIlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ps-ilots : get all the psIlots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of psIlots in body
     */
    @GetMapping("/ps-ilots")
    @Timed
    public List<PsIlot> getAllPsIlots() {
        log.debug("REST request to get all PsIlots");
        return psIlotRepository.findAll();
    }

    /**
     * GET  /ps-ilots/:id : get the "id" psIlot.
     *
     * @param id the id of the psIlot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the psIlot, or with status 404 (Not Found)
     */
    @GetMapping("/ps-ilots/{id}")
    @Timed
    public ResponseEntity<PsIlot> getPsIlot(@PathVariable Long id) {
        log.debug("REST request to get PsIlot : {}", id);
        Optional<PsIlot> psIlot = psIlotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(psIlot);
    }

    /**
     * DELETE  /ps-ilots/:id : delete the "id" psIlot.
     *
     * @param id the id of the psIlot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ps-ilots/{id}")
    @Timed
    public ResponseEntity<Void> deletePsIlot(@PathVariable Long id) {
        log.debug("REST request to delete PsIlot : {}", id);

        psIlotRepository.deleteById(id);
        psIlotSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ps-ilots?query=:query : search for the psIlot corresponding
     * to the query.
     *
     * @param query the query of the psIlot search
     * @return the result of the search
     */
    @GetMapping("/_search/ps-ilots")
    @Timed
    public List<PsIlot> searchPsIlots(@RequestParam String query) {
        log.debug("REST request to search PsIlots for query {}", query);
        return StreamSupport
            .stream(psIlotSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
