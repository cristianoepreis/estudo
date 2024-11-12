package com.estudos.desafios.pontosdeinteresse.repository;

import com.estudos.desafios.pontosdeinteresse.entity.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointsOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
    @Query("""
            SELECT p FROM PointOfInterest p
            WHERE (p.x >= :xMin AND p.x <= :xMax AND p.y >= :yMin AND p.y <= :yMax)
            """)
    List<PointOfInterest> findNearMe(@Param("xMin") long xMin,
                                     @Param("xMax") long xMax,
                                     @Param("yMin") long yMin,
                                     @Param("yMax") long yMax);
}
