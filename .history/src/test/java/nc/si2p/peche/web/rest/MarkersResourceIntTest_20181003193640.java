package nc.si2p.peche.web.rest;

import nc.si2p.peche.PechencApp;

import nc.si2p.peche.domain.Markers;
import nc.si2p.peche.repository.MarkersRepository;
import nc.si2p.peche.repository.search.MarkersSearchRepository;
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

/**
 * Test class for the MarkersResource REST controller.
 *
 * @see MarkersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PechencApp.class)
public class MarkersResourceIntTest {

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PJ = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PJ = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PJ_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PJ_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LNG = 1F;
    private static final Float UPDATED_LNG = 2F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MarkersRepository markersRepository;

    /**
     * This repository is mocked in the nc.si2p.peche.repository.search test package.
     *
     * @see nc.si2p.peche.repository.search.MarkersSearchRepositoryMockConfiguration
     */
    @Autowired
    private MarkersSearchRepository mockMarkersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarkersMockMvc;

    private Markers markers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkersResource markersResource = new MarkersResource(markersRepository, mockMarkersSearchRepository);
        this.restMarkersMockMvc = MockMvcBuilders.standaloneSetup(markersResource)
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
    public static Markers createEntity(EntityManager em) {
        Markers markers = new Markers()
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .titre(DEFAULT_TITRE)
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL)
            .pj(DEFAULT_PJ)
            .pjContentType(DEFAULT_PJ_CONTENT_TYPE)
            .ip(DEFAULT_IP)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .date(DEFAULT_DATE);
        return markers;
    }

    @Before
    public void initTest() {
        markers = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarkers() throws Exception {
        int databaseSizeBeforeCreate = markersRepository.findAll().size();

        // Create the Markers
        restMarkersMockMvc.perform(post("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markers)))
            .andExpect(status().isCreated());

        // Validate the Markers in the database
        List<Markers> markersList = markersRepository.findAll();
        assertThat(markersList).hasSize(databaseSizeBeforeCreate + 1);
        Markers testMarkers = markersList.get(markersList.size() - 1);
        assertThat(testMarkers.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMarkers.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testMarkers.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testMarkers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMarkers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMarkers.getPj()).isEqualTo(DEFAULT_PJ);
        assertThat(testMarkers.getPjContentType()).isEqualTo(DEFAULT_PJ_CONTENT_TYPE);
        assertThat(testMarkers.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testMarkers.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testMarkers.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testMarkers.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Markers in Elasticsearch
        verify(mockMarkersSearchRepository, times(1)).save(testMarkers);
    }

    @Test
    @Transactional
    public void createMarkersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markersRepository.findAll().size();

        // Create the Markers with an existing ID
        markers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkersMockMvc.perform(post("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markers)))
            .andExpect(status().isBadRequest());

        // Validate the Markers in the database
        List<Markers> markersList = markersRepository.findAll();
        assertThat(markersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Markers in Elasticsearch
        verify(mockMarkersSearchRepository, times(0)).save(markers);
    }

    @Test
    @Transactional
    public void getAllMarkers() throws Exception {
        // Initialize the database
        markersRepository.saveAndFlush(markers);

        // Get all the markersList
        restMarkersMockMvc.perform(get("/api/markers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markers.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].pjContentType").value(hasItem(DEFAULT_PJ_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pj").value(hasItem(Base64Utils.encodeToString(DEFAULT_PJ))))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMarkers() throws Exception {
        // Initialize the database
        markersRepository.saveAndFlush(markers);

        // Get the markers
        restMarkersMockMvc.perform(get("/api/markers/{id}", markers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(markers.getId().intValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.pjContentType").value(DEFAULT_PJ_CONTENT_TYPE))
            .andExpect(jsonPath("$.pj").value(Base64Utils.encodeToString(DEFAULT_PJ)))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarkers() throws Exception {
        // Get the markers
        restMarkersMockMvc.perform(get("/api/markers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarkers() throws Exception {
        // Initialize the database
        markersRepository.saveAndFlush(markers);

        int databaseSizeBeforeUpdate = markersRepository.findAll().size();

        // Update the markers
        Markers updatedMarkers = markersRepository.findById(markers.getId()).get();
        // Disconnect from session so that the updates on updatedMarkers are not directly saved in db
        em.detach(updatedMarkers);
        updatedMarkers
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .pj(UPDATED_PJ)
            .pjContentType(UPDATED_PJ_CONTENT_TYPE)
            .ip(UPDATED_IP)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .date(UPDATED_DATE);

        restMarkersMockMvc.perform(put("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarkers)))
            .andExpect(status().isOk());

        // Validate the Markers in the database
        List<Markers> markersList = markersRepository.findAll();
        assertThat(markersList).hasSize(databaseSizeBeforeUpdate);
        Markers testMarkers = markersList.get(markersList.size() - 1);
        assertThat(testMarkers.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMarkers.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testMarkers.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testMarkers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMarkers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMarkers.getPj()).isEqualTo(UPDATED_PJ);
        assertThat(testMarkers.getPjContentType()).isEqualTo(UPDATED_PJ_CONTENT_TYPE);
        assertThat(testMarkers.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testMarkers.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testMarkers.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testMarkers.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Markers in Elasticsearch
        verify(mockMarkersSearchRepository, times(1)).save(testMarkers);
    }

    @Test
    @Transactional
    public void updateNonExistingMarkers() throws Exception {
        int databaseSizeBeforeUpdate = markersRepository.findAll().size();

        // Create the Markers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarkersMockMvc.perform(put("/api/markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markers)))
            .andExpect(status().isBadRequest());

        // Validate the Markers in the database
        List<Markers> markersList = markersRepository.findAll();
        assertThat(markersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Markers in Elasticsearch
        verify(mockMarkersSearchRepository, times(0)).save(markers);
    }

    @Test
    @Transactional
    public void deleteMarkers() throws Exception {
        // Initialize the database
        markersRepository.saveAndFlush(markers);

        int databaseSizeBeforeDelete = markersRepository.findAll().size();

        // Get the markers
        restMarkersMockMvc.perform(delete("/api/markers/{id}", markers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Markers> markersList = markersRepository.findAll();
        assertThat(markersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Markers in Elasticsearch
        verify(mockMarkersSearchRepository, times(1)).deleteById(markers.getId());
    }

    @Test
    @Transactional
    public void searchMarkers() throws Exception {
        // Initialize the database
        markersRepository.saveAndFlush(markers);
        when(mockMarkersSearchRepository.search(queryStringQuery("id:" + markers.getId())))
            .thenReturn(Collections.singletonList(markers));
        // Search the markers
        restMarkersMockMvc.perform(get("/api/_search/markers?query=id:" + markers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markers.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].pjContentType").value(hasItem(DEFAULT_PJ_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pj").value(hasItem(Base64Utils.encodeToString(DEFAULT_PJ))))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Markers.class);
        Markers markers1 = new Markers();
        markers1.setId(1L);
        Markers markers2 = new Markers();
        markers2.setId(markers1.getId());
        assertThat(markers1).isEqualTo(markers2);
        markers2.setId(2L);
        assertThat(markers1).isNotEqualTo(markers2);
        markers1.setId(null);
        assertThat(markers1).isNotEqualTo(markers2);
    }
}
