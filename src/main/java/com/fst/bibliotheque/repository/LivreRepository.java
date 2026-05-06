package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LivreRepository extends JpaRepository<Livre, Long> {

    @Query("SELECT l FROM Livre l WHERE " +
           "(:search IS NULL OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :search, '%'))) OR " +
           "(:search IS NULL OR LOWER(l.auteur) LIKE LOWER(CONCAT('%', :search, '%'))) OR " +
           "(:search IS NULL OR LOWER(l.categorie) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Livre> search(@Param("search") String search, Pageable pageable);
}
