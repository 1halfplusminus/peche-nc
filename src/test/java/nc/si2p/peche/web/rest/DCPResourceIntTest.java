package nc.si2p.peche.web.rest;

import nc.si2p.peche.PechencApp;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.repository.search.DCPSearchRepository;
import nc.si2p.peche.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static nc.si2p.peche.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nc.si2p.peche.domain.enumeration.DCPEtat;
/**
 * Test class for the DCPResource REST controller.
 *
 * @see DCPResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class DCPResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LNG = 1F;
    private static final Float UPDATED_LNG = 2F;

    private static final LocalDate DEFAULT_DATE_DERNIERE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_MAJ = LocalDate.now(ZoneId.systemDefault());

    private static final DCPEtat DEFAULT_ETAT = DCPEtat.DISPARU;
    private static final DCPEtat UPDATED_ETAT = DCPEtat.NORMAL;

    private static final String DEFAULT_LOCALISATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION = "BBBBBBBBBB";

    @Autowired
    private DCPRepository dCPRepository;

    /**
     * This repository is mocked in the nc.si2p.peche.repository.search test package.
     *
     * @see nc.si2p.peche.repository.search.DCPSearchRepositoryMockConfiguration
     */
    @Autowired
    private DCPSearchRepository mockDCPSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDCPMockMvc;

    private DCP dCP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DCPResource dCPResource = new DCPResource(dCPRepository, mockDCPSearchRepository);
        this.restDCPMockMvc = MockMvcBuilders.standaloneSetup(dCPResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCP createEntity(EntityManager em) {
        DCP dCP = new DCP()
            .nom(DEFAULT_NOM)
            .position(DEFAULT_POSITION)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .dateDerniereMaj(DEFAULT_DATE_DERNIERE_MAJ)
            .etat(DEFAULT_ETAT)
            .localisation(DEFAULT_LOCALISATION);
        return dCP;
    }

    @Before
    public void initTest() {
        dCP = createEntity(em);
    }

    @Test
    @Transactional
    public void createDCP() throws Exception {
        int databaseSizeBeforeCreate = dCPRepository.findAll().size();

        // Create the DCP
        restDCPMockMvc.perform(post("/api/dcps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dCP)))
            .andExpect(status().isCreated());

        // Validate the DCP in the database
        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeCreate + 1);
        DCP testDCP = dCPList.get(dCPList.size() - 1);
        assertThat(testDCP.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDCP.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testDCP.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testDCP.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testDCP.getDateDerniereMaj()).isEqualTo(DEFAULT_DATE_DERNIERE_MAJ);
        assertThat(testDCP.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testDCP.getLocalisation()).isEqualTo(DEFAULT_LOCALISATION);

        // Validate the DCP in Elasticsearch
        verify(mockDCPSearchRepository, times(1)).save(testDCP);
    }

    @Test
    @Transactional
    public void createDCPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dCPRepository.findAll().size();

        // Create the DCP with an existing ID
        dCP.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDCPMockMvc.perform(post("/api/dcps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dCP)))
            .andExpect(status().isBadRequest());

        // Validate the DCP in the database
        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeCreate);

        // Validate the DCP in Elasticsearch
        verify(mockDCPSearchRepository, times(0)).save(dCP);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCPRepository.findAll().size();
        // set the field null
        dCP.setNom(null);

        // Create the DCP, which fails.

        restDCPMockMvc.perform(post("/api/dcps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dCP)))
            .andExpect(status().isBadRequest());

        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDCPS() throws Exception {
        // Initialize the database
        dCPRepository.saveAndFlush(dCP);

        // Get all the dCPList
        restDCPMockMvc.perform(get("/api/dcps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCP.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDerniereMaj").value(hasItem(DEFAULT_DATE_DERNIERE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION.toString())));
    }
    
    @Test
    @Transactional
    public void getDCP() throws Exception {
        // Initialize the database
        dCPRepository.saveAndFlush(dCP);

        // Get the dCP
        restDCPMockMvc.perform(get("/api/dcps/{id}", dCP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dCP.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.dateDerniereMaj").value(DEFAULT_DATE_DERNIERE_MAJ.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.localisation").value(DEFAULT_LOCALISATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDCP() throws Exception {
        // Get the dCP
        restDCPMockMvc.perform(get("/api/dcps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDCP() throws Exception {
        // Initialize the database
        dCPRepository.saveAndFlush(dCP);

        int databaseSizeBeforeUpdate = dCPRepository.findAll().size();

        // Update the dCP
        DCP updatedDCP = dCPRepository.findById(dCP.getId()).get();
        // Disconnect from session so that the updates on updatedDCP are not directly saved in db
        em.detach(updatedDCP);
        updatedDCP
            .nom(UPDATED_NOM)
            .position(UPDATED_POSITION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .dateDerniereMaj(UPDATED_DATE_DERNIERE_MAJ)
            .etat(UPDATED_ETAT)
            .localisation(UPDATED_LOCALISATION);

        restDCPMockMvc.perform(put("/api/dcps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDCP)))
            .andExpect(status().isOk());

        // Validate the DCP in the database
        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeUpdate);
        DCP testDCP = dCPList.get(dCPList.size() - 1);
        assertThat(testDCP.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDCP.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testDCP.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testDCP.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testDCP.getDateDerniereMaj()).isEqualTo(UPDATED_DATE_DERNIERE_MAJ);
        assertThat(testDCP.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testDCP.getLocalisation()).isEqualTo(UPDATED_LOCALISATION);

        // Validate the DCP in Elasticsearch
        verify(mockDCPSearchRepository, times(1)).save(testDCP);
    }

    @Test
    @Transactional
    public void updateNonExistingDCP() throws Exception {
        int databaseSizeBeforeUpdate = dCPRepository.findAll().size();

        // Create the DCP

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCPMockMvc.perform(put("/api/dcps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dCP)))
            .andExpect(status().isBadRequest());

        // Validate the DCP in the database
        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCP in Elasticsearch
        verify(mockDCPSearchRepository, times(0)).save(dCP);
    }

    @Test
    @Transactional
    public void deleteDCP() throws Exception {
        // Initialize the database
        dCPRepository.saveAndFlush(dCP);

        int databaseSizeBeforeDelete = dCPRepository.findAll().size();

        // Get the dCP
        restDCPMockMvc.perform(delete("/api/dcps/{id}", dCP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DCP> dCPList = dCPRepository.findAll();
        assertThat(dCPList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DCP in Elasticsearch
        verify(mockDCPSearchRepository, times(1)).deleteById(dCP.getId());
    }

    @Test
    @Transactional
    public void searchDCP() throws Exception {
        // Initialize the database
        dCPRepository.saveAndFlush(dCP);
        when(mockDCPSearchRepository.search(queryStringQuery("id:" + dCP.getId())))
            .thenReturn(Collections.singletonList(dCP));
        // Search the dCP
        restDCPMockMvc.perform(get("/api/_search/dcps?query=id:" + dCP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCP.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDerniereMaj").value(hasItem(DEFAULT_DATE_DERNIERE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].localisation").value(hasItem(DEFAULT_LOCALISATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DCP.class);
        DCP dCP1 = new DCP();
        dCP1.setId(1L);
        DCP dCP2 = new DCP();
        dCP2.setId(dCP1.getId());
        assertThat(dCP1).isEqualTo(dCP2);
        dCP2.setId(2L);
        assertThat(dCP1).isNotEqualTo(dCP2);
        dCP1.setId(null);
        assertThat(dCP1).isNotEqualTo(dCP2);
    }
}
