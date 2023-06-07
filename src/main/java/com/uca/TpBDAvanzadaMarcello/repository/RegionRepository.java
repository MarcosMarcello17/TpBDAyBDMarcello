package com.uca.TpBDAvanzadaMarcello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uca.TpBDAvanzadaMarcello.entity.Region;

import jakarta.transaction.Transactional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
	@Modifying
	@Transactional
	@Query(value="DELETE FROM region WHERE id= :idRegion", nativeQuery = true)
	void deleteRegionWithID(@Param("idRegion") Integer idRegion);
}
