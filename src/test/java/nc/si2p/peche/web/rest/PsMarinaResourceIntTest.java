package nc.si2p.peche.web.rest;

import nc.si2p.peche.PechencApp;

import nc.si2p.peche.domain.PsMarina;
import nc.si2p.peche.repository.PsMarinaRepository;
import nc.si2p.peche.repository.search.PsMarinaSearchRepository;
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
 * Test class for the PsMarinaResource REST controller.
 *
 * @see PsMarinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class PsMarinaResourceIntTest {

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LNG = 1F;
    private static final Float UPDATED_LNG = 2F;

    private static final String DEFAULT_ID_MARINA = "AAAAAAAAAA";
    private static final String UPDATED_ID_MARINA = "BBBBBBBBBB";

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
    private PsMarinaRepository psMarinaRepository;

    /**
     * This repository is mocked in the nc.si2p.peche.repository.search test package.
     *
     * @see nc.si2p.peche.repository.search.PsMarinaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PsMarinaSearchRepository mockPsMarinaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPsMarinaMockMvc;

    private PsMarina psMarina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PsMarinaResource psMarinaResource = new PsMarinaResource(psMarinaRepository, mockPsMarinaSearchRepository);
        this.restPsMarinaMockMvc = MockMvcBuilders.standaloneSetup(psMarinaResource)
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
    public static PsMarina createEntity(EntityManager em) {
        PsMarina psMarina = new PsMarina()
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .idMarina(DEFAULT_ID_MARINA)
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
        return psMarina;
    }

    @Before
    public void initTest() {
        psMarina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPsMarina() throws Exception {
        int databaseSizeBeforeCreate = psMarinaRepository.findAll().size();

        // Create the PsMarina
        restPsMarinaMockMvc.perform(post("/api/ps-marinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMarina)))
            .andExpect(status().isCreated());

        // Validate the PsMarina in the database
        List<PsMarina> psMarinaList = psMarinaRepository.findAll();
        assertThat(psMarinaList).hasSize(databaseSizeBeforeCreate + 1);
        PsMarina testPsMarina = psMarinaList.get(psMarinaList.size() - 1);
        assertThat(testPsMarina.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPsMarina.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testPsMarina.getIdMarina()).isEqualTo(DEFAULT_ID_MARINA);
        assertThat(testPsMarina.getAccess()).isEqualTo(DEFAULT_ACCESS);
        assertThat(testPsMarina.isAmenagee()).isEqualTo(DEFAULT_AMENAGEE);
        assertThat(testPsMarina.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testPsMarina.getLieuDit()).isEqualTo(DEFAULT_LIEU_DIT);
        assertThat(testPsMarina.getParkingPlace()).isEqualTo(DEFAULT_PARKING_PLACE);
        assertThat(testPsMarina.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testPsMarina.isParking()).isEqualTo(DEFAULT_PARKING);
        assertThat(testPsMarina.isPayant()).isEqualTo(DEFAULT_PAYANT);
        assertThat(testPsMarina.isPointEau()).isEqualTo(DEFAULT_POINT_EAU);
        assertThat(testPsMarina.isPoubelles()).isEqualTo(DEFAULT_POUBELLES);
        assertThat(testPsMarina.isToilettes()).isEqualTo(DEFAULT_TOILETTES);
        assertThat(testPsMarina.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testPsMarina.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testPsMarina.getPhotosContentType()).isEqualTo(DEFAULT_PHOTOS_CONTENT_TYPE);
        assertThat(testPsMarina.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the PsMarina in Elasticsearch
        verify(mockPsMarinaSearchRepository, times(1)).save(testPsMarina);
    }

    @Test
    @Transactional
    public void createPsMarinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = psMarinaRepository.findAll().size();

        // Create the PsMarina with an existing ID
        psMarina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsMarinaMockMvc.perform(post("/api/ps-marinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMarina)))
            .andExpect(status().isBadRequest());

        // Validate the PsMarina in the database
        List<PsMarina> psMarinaList = psMarinaRepository.findAll();
        assertThat(psMarinaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PsMarina in Elasticsearch
        verify(mockPsMarinaSearchRepository, times(0)).save(psMarina);
    }

    @Test
    @Transactional
    public void getAllPsMarinas() throws Exception {
        // Initialize the database
        psMarinaRepository.saveAndFlush(psMarina);

        // Get all the psMarinaList
        restPsMarinaMockMvc.perform(get("/api/ps-marinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psMarina.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].idMarina").value(hasItem(DEFAULT_ID_MARINA.toString())))
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
    public void getPsMarina() throws Exception {
        // Initialize the database
        psMarinaRepository.saveAndFlush(psMarina);

        // Get the psMarina
        restPsMarinaMockMvc.perform(get("/api/ps-marinas/{id}", psMarina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(psMarina.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.idMarina").value(DEFAULT_ID_MARINA.toString()))
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
    public void getNonExistingPsMarina() throws Exception {
        // Get the psMarina
        restPsMarinaMockMvc.perform(get("/api/ps-marinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePsMarina() throws Exception {
        // Initialize the database
        psMarinaRepository.saveAndFlush(psMarina);

        int databaseSizeBeforeUpdate = psMarinaRepository.findAll().size();

        // Update the psMarina
        PsMarina updatedPsMarina = psMarinaRepository.findById(psMarina.getId()).get();
        // Disconnect from session so that the updates on updatedPsMarina are not directly saved in db
        em.detach(updatedPsMarina);
        updatedPsMarina
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .idMarina(UPDATED_ID_MARINA)
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

        restPsMarinaMockMvc.perform(put("/api/ps-marinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPsMarina)))
            .andExpect(status().isOk());

        // Validate the PsMarina in the database
        List<PsMarina> psMarinaList = psMarinaRepository.findAll();
        assertThat(psMarinaList).hasSize(databaseSizeBeforeUpdate);
        PsMarina testPsMarina = psMarinaList.get(psMarinaList.size() - 1);
        assertThat(testPsMarina.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPsMarina.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testPsMarina.getIdMarina()).isEqualTo(UPDATED_ID_MARINA);
        assertThat(testPsMarina.getAccess()).isEqualTo(UPDATED_ACCESS);
        assertThat(testPsMarina.isAmenagee()).isEqualTo(UPDATED_AMENAGEE);
        assertThat(testPsMarina.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testPsMarina.getLieuDit()).isEqualTo(UPDATED_LIEU_DIT);
        assertThat(testPsMarina.getParkingPlace()).isEqualTo(UPDATED_PARKING_PLACE);
        assertThat(testPsMarina.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testPsMarina.isParking()).isEqualTo(UPDATED_PARKING);
        assertThat(testPsMarina.isPayant()).isEqualTo(UPDATED_PAYANT);
        assertThat(testPsMarina.isPointEau()).isEqualTo(UPDATED_POINT_EAU);
        assertThat(testPsMarina.isPoubelles()).isEqualTo(UPDATED_POUBELLES);
        assertThat(testPsMarina.isToilettes()).isEqualTo(UPDATED_TOILETTES);
        assertThat(testPsMarina.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testPsMarina.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testPsMarina.getPhotosContentType()).isEqualTo(UPDATED_PHOTOS_CONTENT_TYPE);
        assertThat(testPsMarina.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the PsMarina in Elasticsearch
        verify(mockPsMarinaSearchRepository, times(1)).save(testPsMarina);
    }

    @Test
    @Transactional
    public void updateNonExistingPsMarina() throws Exception {
        int databaseSizeBeforeUpdate = psMarinaRepository.findAll().size();

        // Create the PsMarina

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsMarinaMockMvc.perform(put("/api/ps-marinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(psMarina)))
            .andExpect(status().isBadRequest());

        // Validate the PsMarina in the database
        List<PsMarina> psMarinaList = psMarinaRepository.findAll();
        assertThat(psMarinaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PsMarina in Elasticsearch
        verify(mockPsMarinaSearchRepository, times(0)).save(psMarina);
    }

    @Test
    @Transactional
    public void deletePsMarina() throws Exception {
        // Initialize the database
        psMarinaRepository.saveAndFlush(psMarina);

        int databaseSizeBeforeDelete = psMarinaRepository.findAll().size();

        // Get the psMarina
        restPsMarinaMockMvc.perform(delete("/api/ps-marinas/{id}", psMarina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PsMarina> psMarinaList = psMarinaRepository.findAll();
        assertThat(psMarinaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PsMarina in Elasticsearch
        verify(mockPsMarinaSearchRepository, times(1)).deleteById(psMarina.getId());
    }

    @Test
    @Transactional
    public void searchPsMarina() throws Exception {
        // Initialize the database
        psMarinaRepository.saveAndFlush(psMarina);
        when(mockPsMarinaSearchRepository.search(queryStringQuery("id:" + psMarina.getId())))
            .thenReturn(Collections.singletonList(psMarina));
        // Search the psMarina
        restPsMarinaMockMvc.perform(get("/api/_search/ps-marinas?query=id:" + psMarina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psMarina.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].idMarina").value(hasItem(DEFAULT_ID_MARINA.toString())))
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
        TestUtil.equalsVerifier(PsMarina.class);
        PsMarina psMarina1 = new PsMarina();
        psMarina1.setId(1L);
        PsMarina psMarina2 = new PsMarina();
        psMarina2.setId(psMarina1.getId());
        assertThat(psMarina1).isEqualTo(psMarina2);
        psMarina2.setId(2L);
        assertThat(psMarina1).isNotEqualTo(psMarina2);
        psMarina1.setId(null);
        assertThat(psMarina1).isNotEqualTo(psMarina2);
    }
}
