package nc.si2p.peche.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.si2p.peche.domain.PsMarina;
import nc.si2p.peche.repository.PsMarinaRepository;
import nc.si2p.peche.repository.search.PsMarinaSearchRepository;
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
 * REST controller for managing PsMarina.
 */
@RestController
@RequestMapping("/api")
public class PsMarinaResource {

    private final Logger log = LoggerFactory.getLogger(PsMarinaResource.class);

    private static final String ENTITY_NAME = "psMarina";

    private final PsMarinaRepository psMarinaRepository;

    private final PsMarinaSearchRepository psMarinaSearchRepository;

    public PsMarinaResource(PsMarinaRepository psMarinaRepository, PsMarinaSearchRepository psMarinaSearchRepository) {
        this.psMarinaRepository = psMarinaRepository;
        this.psMarinaSearchRepository = psMarinaSearchRepository;
    }

    /**
     * POST  /ps-marinas : Create a new psMarina.
     *
     * @param psMarina the psMarina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new psMarina, or with status 400 (Bad Request) if the psMarina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ps-marinas")
    @Timed
    public ResponseEntity<PsMarina> createPsMarina(@RequestBody PsMarina psMarina) throws URISyntaxException {
        log.debug("REST request to save PsMarina : {}", psMarina);
        if (psMarina.getId() != null) {
            throw new BadRequestAlertException("A new psMarina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PsMarina result = psMarinaRepository.save(psMarina);
        psMarinaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ps-marinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ps-marinas : Updates an existing psMarina.
     *
     * @param psMarina the psMarina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated psMarina,
     * or with status 400 (Bad Request) if the psMarina is not valid,
     * or with status 500 (Internal Server Error) if the psMarina couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ps-marinas")
    @Timed
    public ResponseEntity<PsMarina> updatePsMarina(@RequestBody PsMarina psMarina) throws URISyntaxException {
        log.debug("REST request to update PsMarina : {}", psMarina);
        if (psMarina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PsMarina result = psMarinaRepository.save(psMarina);
        psMarinaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, psMarina.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ps-marinas : get all the psMarinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of psMarinas in body
     */
    @GetMapping("/ps-marinas")
    @Timed
    public List<PsMarina> getAllPsMarinas() {
        log.debug("REST request to get all PsMarinas");
        return psMarinaRepository.findAll();
    }

    /**
     * GET  /ps-marinas/:id : get the "id" psMarina.
     *
     * @param id the id of the psMarina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the psMarina, or with status 404 (Not Found)
     */
    @GetMapping("/ps-marinas/{id}")
    @Timed
    public ResponseEntity<PsMarina> getPsMarina(@PathVariable Long id) {
        log.debug("REST request to get PsMarina : {}", id);
        Optional<PsMarina> psMarina = psMarinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(psMarina);
    }

    /**
     * DELETE  /ps-marinas/:id : delete the "id" psMarina.
     *
     * @param id the id of the psMarina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ps-marinas/{id}")
    @Timed
    public ResponseEntity<Void> deletePsMarina(@PathVariable Long id) {
        log.debug("REST request to delete PsMarina : {}", id);

        psMarinaRepository.deleteById(id);
        psMarinaSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ps-marinas?query=:query : search for the psMarina corresponding
     * to the query.
     *
     * @param query the query of the psMarina search
     * @return the result of the search
     */
    @GetMapping("/_search/ps-marinas")
    @Timed
    public List<PsMarina> searchPsMarinas(@RequestParam String query) {
        log.debug("REST request to search PsMarinas for query {}", query);
        return StreamSupport
            .stream(psMarinaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
