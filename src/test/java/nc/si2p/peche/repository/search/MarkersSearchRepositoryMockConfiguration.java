package nc.si2p.peche.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of MarkersSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MarkersSearchRepositoryMockConfiguration {

    @MockBean
    private MarkersSearchRepository mockMarkersSearchRepository;

}
