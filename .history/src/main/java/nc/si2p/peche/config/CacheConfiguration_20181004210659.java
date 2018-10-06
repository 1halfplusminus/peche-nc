package nc.si2p.peche.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(nc.si2p.peche.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(nc.si2p.peche.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.DCP.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.Markers.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.PsIlot.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.PsMarina.class.getName(), jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.PsMarina.class.getName() + ".miseAEaux", jcacheConfiguration);
            cm.createCache(nc.si2p.peche.domain.PsMiseAEau.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
