package nc.si2p.peche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nc.si2p.peche.domain.Marker;

/**
 * Spring Data  repository for the Markers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkersRepository extends JpaRepository<Marker, Long> {

}
