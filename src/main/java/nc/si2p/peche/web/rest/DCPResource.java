package nc.si2p.peche.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.repository.search.DCPSearchRepository;
import nc.si2p.peche.web.rest.errors.BadRequestAlertException;
import nc.si2p.peche.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DCP.
 */
@RestController
@RequestMapping("/api")
public class DCPResource {

    private final Logger log = LoggerFactory.getLogger(DCPResource.class);

    private static final String ENTITY_NAME = "dCP";

    private final DCPRepository dCPRepository;

    private final DCPSearchRepository dCPSearchRepository;

    public DCPResource(DCPRepository dCPRepository, DCPSearchRepository dCPSearchRepository) {
        this.dCPRepository = dCPRepository;
        this.dCPSearchRepository = dCPSearchRepository;
    }

    /**
     * POST  /dcps : Create a new dCP.
     *
     * @param dCP the dCP to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dCP, or with status 400 (Bad Request) if the dCP has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dcps")
    @Timed
    public ResponseEntity<DCP> createDCP(@Valid @RequestBody DCP dCP) throws URISyntaxException {
        log.debug("REST request to save DCP : {}", dCP);
        if (dCP.getId() != null) {
            throw new BadRequestAlertException("A new dCP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DCP result = dCPRepository.save(dCP);
        dCPSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dcps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dcps : Updates an existing dCP.
     *
     * @param dCP the dCP to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dCP,
     * or with status 400 (Bad Request) if the dCP is not valid,
     * or with status 500 (Internal Server Error) if the dCP couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dcps")
    @Timed
    public ResponseEntity<DCP> updateDCP(@Valid @RequestBody DCP dCP) throws URISyntaxException {
        log.debug("REST request to update DCP : {}", dCP);
        if (dCP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DCP result = dCPRepository.save(dCP);
        dCPSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dCP.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dcps : get all the dCPS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dCPS in body
     */
    @GetMapping("/dcps")
    @Timed
    public List<DCP> getAllDCPS() {
        log.debug("REST request to get all DCPS");
        return dCPRepository.findAll();
    }

    /**
     * GET  /dcps/:id : get the "id" dCP.
     *
     * @param id the id of the dCP to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dCP, or with status 404 (Not Found)
     */
    @GetMapping("/dcps/{id}")
    @Timed
    public ResponseEntity<DCP> getDCP(@PathVariable Long id) {
        log.debug("REST request to get DCP : {}", id);
        Optional<DCP> dCP = dCPRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dCP);
    }

    /**
     * DELETE  /dcps/:id : delete the "id" dCP.
     *
     * @param id the id of the dCP to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dcps/{id}")
    @Timed
    public ResponseEntity<Void> deleteDCP(@PathVariable Long id) {
        log.debug("REST request to delete DCP : {}", id);

        dCPRepository.deleteById(id);
        dCPSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dcps?query=:query : search for the dCP corresponding
     * to the query.
     *
     * @param query the query of the dCP search
     * @return the result of the search
     */
    @GetMapping("/_search/dcps")
    @Timed
    public List<DCP> searchDCPS(@RequestParam String query) {
        log.debug("REST request to search DCPS for query {}", query);
        return StreamSupport
            .stream(dCPSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
