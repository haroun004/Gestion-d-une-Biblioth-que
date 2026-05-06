package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Membre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembreRepository extends JpaRepository<Membre, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT m FROM Membre m WHERE " +
           "LOWER(m.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.prenom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Membre> search(@Param("search") String search, Pageable pageable);
}
