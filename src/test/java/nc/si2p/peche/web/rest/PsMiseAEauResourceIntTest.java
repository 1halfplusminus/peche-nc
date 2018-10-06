package nc.si2p.peche.web.rest;

import nc.si2p.peche.PechencApp;

import nc.si2p.peche.domain.PsMiseAEau;
import nc.si2p.peche.repository.PsMiseAEauRepository;
import nc.si2p.peche.repository.search.PsMiseAEauSearchRepository;
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
 * Test class for the PsMiseAEauResource REST controller.
 *
 * @see PsMiseAEauResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class PsMiseAEauResourceIntTest {

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LNG = 1F;
    private static final Float UPDATED_LNG = 2F;

    private static final String DEFAULT_ACCESS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AMENAGEE = false;
    private static final Boolean UPDATED_AMENAGEE = true;

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_DIT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_DIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARKING_PLACE = 1;
    private static final Integer UPDATED_PARKING_PLACE = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARKING = false;
    private static final Boolean UPDATED_PARKING = true;

    private static final Boolean DEFAULT_PAYANT = false;
    private static final Boolean UPDATED_PAYANT = true;

    private static final Boolean DEFAULT_POINT_EAU = false;
    private static final Boolean UPDATED_POINT_EAU = true;

    private static final Boolean DEFAULT_POUBELLES = false;
    private static final Boolean UPDATED_POUBELLES = true;

    private static final Boolean DEFAULT_TOILETTES = false;
    private static final Boolean UPDATED_TOILETTES = true;

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTOS_CONTENT_TYPE = "image/png";

    private static final PSStatus DEFAULT_STATUS = PSStatus.VALIDE;
    private static final PSStatus UPDATED_STATUS = PSStatus.NONVALIDE;

    @Autowired
    private PsMiseAEauRepository psMiseAEauRepository;

    /**
     * This repository is mocked in the nc.si2p.peche.repository.search test package.
     *
     * @see nc.si2p.peche.repository.search.PsMiseAEauSearchRepositoryMockConfiguration
     */
    @Autowired
    private PsMiseAEauSearchRepository mockPsMiseAEauSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPsMiseAEauMockMvc;

    private PsMiseAEau psMiseAEau;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PsMiseAEauResource psMiseAEauResource = new PsMiseAEauResource(psMiseAEauRepository, mockPsMiseAEauSearchRepository);
        this.restPsMiseAEauMockMvc = MockMvcBuilders.standaloneSetup(psMiseAEauResource)
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
    public static PsMiseAEau createEntity(EntityManager em) {
        PsMiseAEau psMiseAEau = new PsMiseAEau()
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .access(DEFAULT_ACCESS)
            .amenagee(DEFAULT_AMENAGEE)
            .commune(DEFAULT_COMMUNE)
            .lieuDit(DEFAULT_LIEU_DIT)
            .parkingPlace(DEFAULT_PARKING_PLACE)
            .observation(DEFAULT_OBSERVATION)
            .parking(DEFAULT_PARKING)
            .payant(DEFAULT_PAYANT)
            .pointEau(DEFAULT_POINT_EAU)
            .poubelles(DEFAULT_POUBELLES)
            .toilettes(DEFAULT_TOILETTES)
            .titre(DEFAULT_TITRE)
            .photos(DEFAULT_PHOTOS)
            .photosContentType(DEFAULT_PHOTOS_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        return psMiseAEau;
    }

    @Before
    public void initTest() {
        psMiseAEau = createEntity(em);
    }

    @Test
    @Transactional
    public void createPsMiseAEau() throws Exception {
        int databaseSizeBeforeCreate = psMiseAEauRepository.findAll().size();

        // Create the PsMiseAEau
        restPsMiseAEauMockMvc.perform(post("/api/ps-mise-a-eaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMiseAEau)))
            .andExpect(status().isCreated());

        // Validate the PsMiseAEau in the database
        List<PsMiseAEau> psMiseAEauList = psMiseAEauRepository.findAll();
        assertThat(psMiseAEauList).hasSize(databaseSizeBeforeCreate + 1);
        PsMiseAEau testPsMiseAEau = psMiseAEauList.get(psMiseAEauList.size() - 1);
        assertThat(testPsMiseAEau.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPsMiseAEau.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testPsMiseAEau.getAccess()).isEqualTo(DEFAULT_ACCESS);
        assertThat(testPsMiseAEau.isAmenagee()).isEqualTo(DEFAULT_AMENAGEE);
        assertThat(testPsMiseAEau.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testPsMiseAEau.getLieuDit()).isEqualTo(DEFAULT_LIEU_DIT);
        assertThat(testPsMiseAEau.getParkingPlace()).isEqualTo(DEFAULT_PARKING_PLACE);
        assertThat(testPsMiseAEau.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testPsMiseAEau.isParking()).isEqualTo(DEFAULT_PARKING);
        assertThat(testPsMiseAEau.isPayant()).isEqualTo(DEFAULT_PAYANT);
        assertThat(testPsMiseAEau.isPointEau()).isEqualTo(DEFAULT_POINT_EAU);
        assertThat(testPsMiseAEau.isPoubelles()).isEqualTo(DEFAULT_POUBELLES);
        assertThat(testPsMiseAEau.isToilettes()).isEqualTo(DEFAULT_TOILETTES);
        assertThat(testPsMiseAEau.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPsMiseAEau.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testPsMiseAEau.getPhotosContentType()).isEqualTo(DEFAULT_PHOTOS_CONTENT_TYPE);
        assertThat(testPsMiseAEau.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PsMiseAEau in Elasticsearch
        verify(mockPsMiseAEauSearchRepository, times(1)).save(testPsMiseAEau);
    }

    @Test
    @Transactional
    public void createPsMiseAEauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = psMiseAEauRepository.findAll().size();

        // Create the PsMiseAEau with an existing ID
        psMiseAEau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsMiseAEauMockMvc.perform(post("/api/ps-mise-a-eaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMiseAEau)))
            .andExpect(status().isBadRequest());

        // Validate the PsMiseAEau in the database
        List<PsMiseAEau> psMiseAEauList = psMiseAEauRepository.findAll();
        assertThat(psMiseAEauList).hasSize(databaseSizeBeforeCreate);

        // Validate the PsMiseAEau in Elasticsearch
        verify(mockPsMiseAEauSearchRepository, times(0)).save(psMiseAEau);
    }

    @Test
    @Transactional
    public void getAllPsMiseAEaus() throws Exception {
        // Initialize the database
        psMiseAEauRepository.saveAndFlush(psMiseAEau);

        // Get all the psMiseAEauList
        restPsMiseAEauMockMvc.perform(get("/api/ps-mise-a-eaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psMiseAEau.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].access").value(hasItem(DEFAULT_ACCESS.toString())))
            .andExpect(jsonPath("$.[*].amenagee").value(hasItem(DEFAULT_AMENAGEE.booleanValue())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].lieuDit").value(hasItem(DEFAULT_LIEU_DIT.toString())))
            .andExpect(jsonPath("$.[*].parkingPlace").value(hasItem(DEFAULT_PARKING_PLACE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].payant").value(hasItem(DEFAULT_PAYANT.booleanValue())))
            .andExpect(jsonPath("$.[*].pointEau").value(hasItem(DEFAULT_POINT_EAU.booleanValue())))
            .andExpect(jsonPath("$.[*].poubelles").value(hasItem(DEFAULT_POUBELLES.booleanValue())))
            .andExpect(jsonPath("$.[*].toilettes").value(hasItem(DEFAULT_TOILETTES.booleanValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].photosContentType").value(hasItem(DEFAULT_PHOTOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTOS))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getPsMiseAEau() throws Exception {
        // Initialize the database
        psMiseAEauRepository.saveAndFlush(psMiseAEau);

        // Get the psMiseAEau
        restPsMiseAEauMockMvc.perform(get("/api/ps-mise-a-eaus/{id}", psMiseAEau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(psMiseAEau.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.access").value(DEFAULT_ACCESS.toString()))
            .andExpect(jsonPath("$.amenagee").value(DEFAULT_AMENAGEE.booleanValue()))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE.toString()))
            .andExpect(jsonPath("$.lieuDit").value(DEFAULT_LIEU_DIT.toString()))
            .andExpect(jsonPath("$.parkingPlace").value(DEFAULT_PARKING_PLACE))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()))
            .andExpect(jsonPath("$.parking").value(DEFAULT_PARKING.booleanValue()))
            .andExpect(jsonPath("$.payant").value(DEFAULT_PAYANT.booleanValue()))
            .andExpect(jsonPath("$.pointEau").value(DEFAULT_POINT_EAU.booleanValue()))
            .andExpect(jsonPath("$.poubelles").value(DEFAULT_POUBELLES.booleanValue()))
            .andExpect(jsonPath("$.toilettes").value(DEFAULT_TOILETTES.booleanValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.photosContentType").value(DEFAULT_PHOTOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.photos").value(Base64Utils.encodeToString(DEFAULT_PHOTOS)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPsMiseAEau() throws Exception {
        // Get the psMiseAEau
        restPsMiseAEauMockMvc.perform(get("/api/ps-mise-a-eaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePsMiseAEau() throws Exception {
        // Initialize the database
        psMiseAEauRepository.saveAndFlush(psMiseAEau);

        int databaseSizeBeforeUpdate = psMiseAEauRepository.findAll().size();

        // Update the psMiseAEau
        PsMiseAEau updatedPsMiseAEau = psMiseAEauRepository.findById(psMiseAEau.getId()).get();
        // Disconnect from session so that the updates on updatedPsMiseAEau are not directly saved in db
        em.detach(updatedPsMiseAEau);
        updatedPsMiseAEau
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .access(UPDATED_ACCESS)
            .amenagee(UPDATED_AMENAGEE)
            .commune(UPDATED_COMMUNE)
            .lieuDit(UPDATED_LIEU_DIT)
            .parkingPlace(UPDATED_PARKING_PLACE)
            .observation(UPDATED_OBSERVATION)
            .parking(UPDATED_PARKING)
            .payant(UPDATED_PAYANT)
            .pointEau(UPDATED_POINT_EAU)
            .poubelles(UPDATED_POUBELLES)
            .toilettes(UPDATED_TOILETTES)
            .titre(UPDATED_TITRE)
            .photos(UPDATED_PHOTOS)
            .photosContentType(UPDATED_PHOTOS_CONTENT_TYPE)
            .status(UPDATED_STATUS);

        restPsMiseAEauMockMvc.perform(put("/api/ps-mise-a-eaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPsMiseAEau)))
            .andExpect(status().isOk());

        // Validate the PsMiseAEau in the database
        List<PsMiseAEau> psMiseAEauList = psMiseAEauRepository.findAll();
        assertThat(psMiseAEauList).hasSize(databaseSizeBeforeUpdate);
        PsMiseAEau testPsMiseAEau = psMiseAEauList.get(psMiseAEauList.size() - 1);
        assertThat(testPsMiseAEau.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPsMiseAEau.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testPsMiseAEau.getAccess()).isEqualTo(UPDATED_ACCESS);
        assertThat(testPsMiseAEau.isAmenagee()).isEqualTo(UPDATED_AMENAGEE);
        assertThat(testPsMiseAEau.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testPsMiseAEau.getLieuDit()).isEqualTo(UPDATED_LIEU_DIT);
        assertThat(testPsMiseAEau.getParkingPlace()).isEqualTo(UPDATED_PARKING_PLACE);
        assertThat(testPsMiseAEau.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testPsMiseAEau.isParking()).isEqualTo(UPDATED_PARKING);
        assertThat(testPsMiseAEau.isPayant()).isEqualTo(UPDATED_PAYANT);
        assertThat(testPsMiseAEau.isPointEau()).isEqualTo(UPDATED_POINT_EAU);
        assertThat(testPsMiseAEau.isPoubelles()).isEqualTo(UPDATED_POUBELLES);
        assertThat(testPsMiseAEau.isToilettes()).isEqualTo(UPDATED_TOILETTES);
        assertThat(testPsMiseAEau.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPsMiseAEau.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testPsMiseAEau.getPhotosContentType()).isEqualTo(UPDATED_PHOTOS_CONTENT_TYPE);
        assertThat(testPsMiseAEau.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PsMiseAEau in Elasticsearch
        verify(mockPsMiseAEauSearchRepository, times(1)).save(testPsMiseAEau);
    }

    @Test
    @Transactional
    public void updateNonExistingPsMiseAEau() throws Exception {
        int databaseSizeBeforeUpdate = psMiseAEauRepository.findAll().size();

        // Create the PsMiseAEau

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsMiseAEauMockMvc.perform(put("/api/ps-mise-a-eaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMiseAEau)))
            .andExpect(status().isBadRequest());

        // Validate the PsMiseAEau in the database
        List<PsMiseAEau> psMiseAEauList = psMiseAEauRepository.findAll();
        assertThat(psMiseAEauList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PsMiseAEau in Elasticsearch
        verify(mockPsMiseAEauSearchRepository, times(0)).save(psMiseAEau);
    }

    @Test
    @Transactional
    public void deletePsMiseAEau() throws Exception {
        // Initialize the database
        psMiseAEauRepository.saveAndFlush(psMiseAEau);

        int databaseSizeBeforeDelete = psMiseAEauRepository.findAll().size();

        // Get the psMiseAEau
        restPsMiseAEauMockMvc.perform(delete("/api/ps-mise-a-eaus/{id}", psMiseAEau.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PsMiseAEau> psMiseAEauList = psMiseAEauRepository.findAll();
        assertThat(psMiseAEauList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PsMiseAEau in Elasticsearch
        verify(mockPsMiseAEauSearchRepository, times(1)).deleteById(psMiseAEau.getId());
    }

    @Test
    @Transactional
    public void searchPsMiseAEau() throws Exception {
        // Initialize the database
        psMiseAEauRepository.saveAndFlush(psMiseAEau);
        when(mockPsMiseAEauSearchRepository.search(queryStringQuery("id:" + psMiseAEau.getId())))
            .thenReturn(Collections.singletonList(psMiseAEau));
        // Search the psMiseAEau
        restPsMiseAEauMockMvc.perform(get("/api/_search/ps-mise-a-eaus?query=id:" + psMiseAEau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psMiseAEau.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].access").value(hasItem(DEFAULT_ACCESS.toString())))
            .andExpect(jsonPath("$.[*].amenagee").value(hasItem(DEFAULT_AMENAGEE.booleanValue())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].lieuDit").value(hasItem(DEFAULT_LIEU_DIT.toString())))
            .andExpect(jsonPath("$.[*].parkingPlace").value(hasItem(DEFAULT_PARKING_PLACE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].payant").value(hasItem(DEFAULT_PAYANT.booleanValue())))
            .andExpect(jsonPath("$.[*].pointEau").value(hasItem(DEFAULT_POINT_EAU.booleanValue())))
            .andExpect(jsonPath("$.[*].poubelles").value(hasItem(DEFAULT_POUBELLES.booleanValue())))
            .andExpect(jsonPath("$.[*].toilettes").value(hasItem(DEFAULT_TOILETTES.booleanValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].photosContentType").value(hasItem(DEFAULT_PHOTOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTOS))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsMiseAEau.class);
        PsMiseAEau psMiseAEau1 = new PsMiseAEau();
        psMiseAEau1.setId(1L);
        PsMiseAEau psMiseAEau2 = new PsMiseAEau();
        psMiseAEau2.setId(psMiseAEau1.getId());
        assertThat(psMiseAEau1).isEqualTo(psMiseAEau2);
        psMiseAEau2.setId(2L);
        assertThat(psMiseAEau1).isNotEqualTo(psMiseAEau2);
        psMiseAEau1.setId(null);
        assertThat(psMiseAEau1).isNotEqualTo(psMiseAEau2);
    }
}
