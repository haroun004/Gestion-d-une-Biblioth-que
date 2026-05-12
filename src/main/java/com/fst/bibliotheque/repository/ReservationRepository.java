package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Reservation;
import com.fst.bibliotheque.entity.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findByStatut(StatutReservation statut, Pageable pageable);

    List<Reservation> findByLivreIdAndStatutOrderByDateReservationAsc(Long livreId, StatutReservation statut);

    boolean existsByLivreIdAndMembreIdAndStatut(Long livreId, Long membreId, StatutReservation statut);
}
