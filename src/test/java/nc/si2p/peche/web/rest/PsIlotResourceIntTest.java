package nc.si2p.peche.web.rest;

import nc.si2p.peche.PechencApp;

import nc.si2p.peche.domain.PsIlot;
import nc.si2p.peche.repository.PsIlotRepository;
import nc.si2p.peche.repository.search.PsIlotSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static nc.si2p.peche.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nc.si2p.peche.domain.enumeration.PSStatus;
/**
 * Test class for the PsIlotResource REST controller.
 *
 * @see PsIlotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class PsIlotResourceIntTest {

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LNG = 1F;
    private static final Float UPDATED_LNG = 2F;

    private static final String DEFAULT_ID_ILOT = "AAAAAAAAAA";
    private static final String UPDATED_ID_ILOT = "BBBBBBBBBB";

    private static final String DEFAULT_CALENDRIER = "AAAAAAAAAA";
    private static final String UPDATED_CALENDRIER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_POSEHELICO = "AAAAAAAAAA";
    private static final String UPDATED_POSEHELICO = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_COPYRIGHT = "AAAAAAAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final PSStatus DEFAULT_STATUS = PSStatus.VALIDE;
    private static final PSStatus UPDATED_STATUS = PSStatus.NONVALIDE;

    @Autowired
    private PsIlotRepository psIlotRepository;

    /**
     * This repository is mocked in the nc.si2p.peche.repository.search test package.
     *
     * @see nc.si2p.peche.repository.search.PsIlotSearchRepositoryMockConfiguration
     */
    @Autowired
    private PsIlotSearchRepository mockPsIlotSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPsIlotMockMvc;

    private PsIlot psIlot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PsIlotResource psIlotResource = new PsIlotResource(psIlotRepository, mockPsIlotSearchRepository);
        this.restPsIlotMockMvc = MockMvcBuilders.standaloneSetup(psIlotResource)
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
    public static PsIlot createEntity(EntityManager em) {
        PsIlot psIlot = new PsIlot()
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .idIlot(DEFAULT_ID_ILOT)
            .calendrier(DEFAULT_CALENDRIER)
            .commune(DEFAULT_COMMUNE)
            .posehelico(DEFAULT_POSEHELICO)
            .titre(DEFAULT_TITRE)
            .copyright(DEFAULT_COPYRIGHT)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        return psIlot;
    }

    @Before
    public void initTest() {
        psIlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createPsIlot() throws Exception {
        int databaseSizeBeforeCreate = psIlotRepository.findAll().size();

        // Create the PsIlot
        restPsIlotMockMvc.perform(post("/api/ps-ilots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psIlot)))
            .andExpect(status().isCreated());

        // Validate the PsIlot in the database
        List<PsIlot> psIlotList = psIlotRepository.findAll();
        assertThat(psIlotList).hasSize(databaseSizeBeforeCreate + 1);
        PsIlot testPsIlot = psIlotList.get(psIlotList.size() - 1);
        assertThat(testPsIlot.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPsIlot.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testPsIlot.getIdIlot()).isEqualTo(DEFAULT_ID_ILOT);
        assertThat(testPsIlot.getCalendrier()).isEqualTo(DEFAULT_CALENDRIER);
        assertThat(testPsIlot.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testPsIlot.getPosehelico()).isEqualTo(DEFAULT_POSEHELICO);
        assertThat(testPsIlot.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPsIlot.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testPsIlot.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPsIlot.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPsIlot.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PsIlot in Elasticsearch
        verify(mockPsIlotSearchRepository, times(1)).save(testPsIlot);
    }

    @Test
    @Transactional
    public void createPsIlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = psIlotRepository.findAll().size();

        // Create the PsIlot with an existing ID
        psIlot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsIlotMockMvc.perform(post("/api/ps-ilots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psIlot)))
            .andExpect(status().isBadRequest());

        // Validate the PsIlot in the database
        List<PsIlot> psIlotList = psIlotRepository.findAll();
        assertThat(psIlotList).hasSize(databaseSizeBeforeCreate);

        // Validate the PsIlot in Elasticsearch
        verify(mockPsIlotSearchRepository, times(0)).save(psIlot);
    }

    @Test
    @Transactional
    public void getAllPsIlots() throws Exception {
        // Initialize the database
        psIlotRepository.saveAndFlush(psIlot);

        // Get all the psIlotList
        restPsIlotMockMvc.perform(get("/api/ps-ilots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psIlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].idIlot").value(hasItem(DEFAULT_ID_ILOT.toString())))
            .andExpect(jsonPath("$.[*].calendrier").value(hasItem(DEFAULT_CALENDRIER.toString())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].posehelico").value(hasItem(DEFAULT_POSEHELICO.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getPsIlot() throws Exception {
        // Initialize the database
        psIlotRepository.saveAndFlush(psIlot);

        // Get the psIlot
        restPsIlotMockMvc.perform(get("/api/ps-ilots/{id}", psIlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(psIlot.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.idIlot").value(DEFAULT_ID_ILOT.toString()))
            .andExpect(jsonPath("$.calendrier").value(DEFAULT_CALENDRIER.toString()))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE.toString()))
            .andExpect(jsonPath("$.posehelico").value(DEFAULT_POSEHELICO.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPsIlot() throws Exception {
        // Get the psIlot
        restPsIlotMockMvc.perform(get("/api/ps-ilots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePsIlot() throws Exception {
        // Initialize the database
        psIlotRepository.saveAndFlush(psIlot);

        int databaseSizeBeforeUpdate = psIlotRepository.findAll().size();

        // Update the psIlot
        PsIlot updatedPsIlot = psIlotRepository.findById(psIlot.getId()).get();
        // Disconnect from session so that the updates on updatedPsIlot are not directly saved in db
        em.detach(updatedPsIlot);
        updatedPsIlot
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .idIlot(UPDATED_ID_ILOT)
            .calendrier(UPDATED_CALENDRIER)
            .commune(UPDATED_COMMUNE)
            .posehelico(UPDATED_POSEHELICO)
            .titre(UPDATED_TITRE)
            .copyright(UPDATED_COPYRIGHT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .status(UPDATED_STATUS);

        restPsIlotMockMvc.perform(put("/api/ps-ilots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPsIlot)))
            .andExpect(status().isOk());

        // Validate the PsIlot in the database
        List<PsIlot> psIlotList = psIlotRepository.findAll();
        assertThat(psIlotList).hasSize(databaseSizeBeforeUpdate);
        PsIlot testPsIlot = psIlotList.get(psIlotList.size() - 1);
        assertThat(testPsIlot.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPsIlot.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testPsIlot.getIdIlot()).isEqualTo(UPDATED_ID_ILOT);
        assertThat(testPsIlot.getCalendrier()).isEqualTo(UPDATED_CALENDRIER);
        assertThat(testPsIlot.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testPsIlot.getPosehelico()).isEqualTo(UPDATED_POSEHELICO);
        assertThat(testPsIlot.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPsIlot.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testPsIlot.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPsIlot.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPsIlot.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PsIlot in Elasticsearch
        verify(mockPsIlotSearchRepository, times(1)).save(testPsIlot);
    }

    @Test
    @Transactional
    public void updateNonExistingPsIlot() throws Exception {
        int databaseSizeBeforeUpdate = psIlotRepository.findAll().size();

        // Create the PsIlot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsIlotMockMvc.perform(put("/api/ps-ilots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psIlot)))
            .andExpect(status().isBadRequest());

        // Validate the PsIlot in the database
        List<PsIlot> psIlotList = psIlotRepository.findAll();
        assertThat(psIlotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PsIlot in Elasticsearch
        verify(mockPsIlotSearchRepository, times(0)).save(psIlot);
    }

    @Test
    @Transactional
    public void deletePsIlot() throws Exception {
        // Initialize the database
        psIlotRepository.saveAndFlush(psIlot);

        int databaseSizeBeforeDelete = psIlotRepository.findAll().size();

        // Get the psIlot
        restPsIlotMockMvc.perform(delete("/api/ps-ilots/{id}", psIlot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PsIlot> psIlotList = psIlotRepository.findAll();
        assertThat(psIlotList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PsIlot in Elasticsearch
        verify(mockPsIlotSearchRepository, times(1)).deleteById(psIlot.getId());
    }

    @Test
    @Transactional
    public void searchPsIlot() throws Exception {
        // Initialize the database
        psIlotRepository.saveAndFlush(psIlot);
        when(mockPsIlotSearchRepository.search(queryStringQuery("id:" + psIlot.getId())))
            .thenReturn(Collections.singletonList(psIlot));
        // Search the psIlot
        restPsIlotMockMvc.perform(get("/api/_search/ps-ilots?query=id:" + psIlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psIlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].idIlot").value(hasItem(DEFAULT_ID_ILOT.toString())))
            .andExpect(jsonPath("$.[*].calendrier").value(hasItem(DEFAULT_CALENDRIER.toString())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].posehelico").value(hasItem(DEFAULT_POSEHELICO.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsIlot.class);
        PsIlot psIlot1 = new PsIlot();
        psIlot1.setId(1L);
        PsIlot psIlot2 = new PsIlot();
        psIlot2.setId(psIlot1.getId());
        assertThat(psIlot1).isEqualTo(psIlot2);
        psIlot2.setId(2L);
        assertThat(psIlot1).isNotEqualTo(psIlot2);
        psIlot1.setId(null);
        assertThat(psIlot1).isNotEqualTo(psIlot2);
    }
}
