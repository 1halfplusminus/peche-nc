package nc.si2p.peche.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.si2p.peche.domain.PsMiseAEau;
import nc.si2p.peche.repository.PsMiseAEauRepository;
import nc.si2p.peche.repository.search.PsMiseAEauSearchRepository;
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
 * REST controller for managing PsMiseAEau.
 */
@RestController
@RequestMapping("/api")
public class PsMiseAEauResource {

    private final Logger log = LoggerFactory.getLogger(PsMiseAEauResource.class);

    private static final String ENTITY_NAME = "psMiseAEau";

    private final PsMiseAEauRepository psMiseAEauRepository;

    private final PsMiseAEauSearchRepository psMiseAEauSearchRepository;

    public PsMiseAEauResource(PsMiseAEauRepository psMiseAEauRepository, PsMiseAEauSearchRepository psMiseAEauSearchRepository) {
        this.psMiseAEauRepository = psMiseAEauRepository;
        this.psMiseAEauSearchRepository = psMiseAEauSearchRepository;
    }

    /**
     * POST  /ps-mise-a-eaus : Create a new psMiseAEau.
     *
     * @param psMiseAEau the psMiseAEau to create
     * @return the ResponseEntity with status 201 (Created) and with body the new psMiseAEau, or with status 400 (Bad Request) if the psMiseAEau has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ps-mise-a-eaus")
    @Timed
    public ResponseEntity<PsMiseAEau> createPsMiseAEau(@RequestBody PsMiseAEau psMiseAEau) throws URISyntaxException {
        log.debug("REST request to save PsMiseAEau : {}", psMiseAEau);
        if (psMiseAEau.getId() != null) {
            throw new BadRequestAlertException("A new psMiseAEau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PsMiseAEau result = psMiseAEauRepository.save(psMiseAEau);
        psMiseAEauSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ps-mise-a-eaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ps-mise-a-eaus : Updates an existing psMiseAEau.
     *
     * @param psMiseAEau the psMiseAEau to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated psMiseAEau,
     * or with status 400 (Bad Request) if the psMiseAEau is not valid,
     * or with status 500 (Internal Server Error) if the psMiseAEau couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ps-mise-a-eaus")
    @Timed
    public ResponseEntity<PsMiseAEau> updatePsMiseAEau(@RequestBody PsMiseAEau psMiseAEau) throws URISyntaxException {
        log.debug("REST request to update PsMiseAEau : {}", psMiseAEau);
        if (psMiseAEau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PsMiseAEau result = psMiseAEauRepository.save(psMiseAEau);
        psMiseAEauSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, psMiseAEau.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ps-mise-a-eaus : get all the psMiseAEaus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of psMiseAEaus in body
     */
    @GetMapping("/ps-mise-a-eaus")
    @Timed
    public List<PsMiseAEau> getAllPsMiseAEaus() {
        log.debug("REST request to get all PsMiseAEaus");
        return psMiseAEauRepository.findAll();
    }

    /**
     * GET  /ps-mise-a-eaus/:id : get the "id" psMiseAEau.
     *
     * @param id the id of the psMiseAEau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the psMiseAEau, or with status 404 (Not Found)
     */
    @GetMapping("/ps-mise-a-eaus/{id}")
    @Timed
    public ResponseEntity<PsMiseAEau> getPsMiseAEau(@PathVariable Long id) {
        log.debug("REST request to get PsMiseAEau : {}", id);
        Optional<PsMiseAEau> psMiseAEau = psMiseAEauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(psMiseAEau);
    }

    /**
     * DELETE  /ps-mise-a-eaus/:id : delete the "id" psMiseAEau.
     *
     * @param id the id of the psMiseAEau to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ps-mise-a-eaus/{id}")
    @Timed
    public ResponseEntity<Void> deletePsMiseAEau(@PathVariable Long id) {
        log.debug("REST request to delete PsMiseAEau : {}", id);

        psMiseAEauRepository.deleteById(id);
        psMiseAEauSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ps-mise-a-eaus?query=:query : search for the psMiseAEau corresponding
     * to the query.
     *
     * @param query the query of the psMiseAEau search
     * @return the result of the search
     */
    @GetMapping("/_search/ps-mise-a-eaus")
    @Timed
    public List<PsMiseAEau> searchPsMiseAEaus(@RequestParam String query) {
        log.debug("REST request to search PsMiseAEaus for query {}", query);
        return StreamSupport
            .stream(psMiseAEauSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
