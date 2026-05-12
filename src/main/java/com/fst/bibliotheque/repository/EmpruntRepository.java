package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.StatutEmprunt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    Page<Emprunt> findByStatut(StatutEmprunt statut, Pageable pageable);

    List<Emprunt> findByStatut(StatutEmprunt statut);

    List<Emprunt> findByStatutAndDateRetourPrevueBefore(StatutEmprunt statut, LocalDate date);

    List<Emprunt> findByMembreIdOrderByDateEmpruntDesc(Long membreId);

    long countByStatut(StatutEmprunt statut);

    @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.statut = 'EN_COURS' AND e.dateRetourPrevue < :today")
    long countEnRetard(@Param("today") LocalDate today);
}
