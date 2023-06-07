package com.uca.TpBDAvanzadaMarcello.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uca.TpBDAvanzadaMarcello.entity.DemandaEnDia;
import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;

@Repository
public interface DemandaIntervaloRepository extends JpaRepository<DemandaIntervalo, Long> {
	@Query(value = "SELECT * FROM demanda_intervalo di WHERE DATE(di.fecha) = :fecha", nativeQuery = true)
	List<DemandaIntervalo> selectAvgDemandaOfDate(@Param("fecha") LocalDate fecha);

	@Query(value="SELECT subquery3.region_id as Region, subquery3.max_demand as Demanda, subquery2.fecha_trunc as Fecha FROM  (SELECT region_id, MAX(total_demand) AS max_demand\r\n"
			+ "  FROM (\r\n"
			+ "    SELECT region_id, DATE_TRUNC('DAY', fecha) AS fecha_trunc, SUM(dem) AS total_demand\r\n"
			+ "    FROM Demanda_intervalo\r\n"
			+ "    GROUP BY region_id, DATE_TRUNC('DAY', fecha)\r\n"
			+ "  ) AS subquery\r\n"
			+ "  GROUP BY region_id) AS subquery3\r\n"
			+ "INNER JOIN (SELECT region_id, DATE_TRUNC('DAY', fecha) AS fecha_trunc, SUM(dem) AS total_demand\r\n"
			+ "    FROM Demanda_intervalo\r\n"
			+ "    GROUP BY region_id, DATE_TRUNC('DAY', fecha)) AS subquery2\r\n"
			+ "ON subquery3.region_id = subquery2.region_id AND subquery3.max_demand = subquery2.total_demand", nativeQuery=true)
			List<DemandaEnDia> obtenerDiaMayorDemandaPorRegion();
}
