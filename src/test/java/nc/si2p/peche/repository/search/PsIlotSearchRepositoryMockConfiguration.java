package nc.si2p.peche.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PsIlotSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PsIlotSearchRepositoryMockConfiguration {

    @MockBean
    private PsIlotSearchRepository mockPsIlotSearchRepository;

}
