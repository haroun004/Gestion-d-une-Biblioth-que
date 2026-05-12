package com.fst.bibliotheque.service;

import com.fst.bibliotheque.dto.ReservationDTO;
import com.fst.bibliotheque.dto.ReservationFormDTO;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.entity.Reservation;
import com.fst.bibliotheque.entity.StatutReservation;
import com.fst.bibliotheque.repository.LivreRepository;
import com.fst.bibliotheque.repository.MembreRepository;
import com.fst.bibliotheque.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;
    private final EmailService emailService;

    public Page<ReservationDTO> findAll(StatutReservation statut, Pageable pageable) {
        Page<Reservation> page = (statut == null)
                ? reservationRepository.findAll(pageable)
                : reservationRepository.findByStatut(statut, pageable);
        return page.map(this::toDTO);
    }

    public ReservationDTO creerReservation(ReservationFormDTO form) {
        Livre livre = livreRepository.findById(form.getLivreId())
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable : " + form.getLivreId()));
        Membre membre = membreRepository.findById(form.getMembreId())
                .orElseThrow(() -> new IllegalArgumentException("Membre introuvable : " + form.getMembreId()));

        if (livre.getQuantiteDisponible() > 0) {
            throw new IllegalStateException("Ce livre est disponible, empruntez-le directement.");
        }
        if (reservationRepository.existsByLivreIdAndMembreIdAndStatut(
                form.getLivreId(), form.getMembreId(), StatutReservation.EN_ATTENTE)) {
            throw new IllegalStateException("Ce membre a déjà une réservation en attente pour ce livre.");
        }

        Reservation reservation = new Reservation();
        reservation.setLivre(livre);
        reservation.setMembre(membre);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        return toDTO(reservationRepository.save(reservation));
    }

    public void annulerReservation(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable : " + id));
        r.setStatut(StatutReservation.ANNULEE);
        reservationRepository.save(r);
    }

    /**
     * Called when a book is returned — notifies the first member in the waiting queue.
     */
    public void notifierDisponibilite(Livre livre) {
        List<Reservation> queue = reservationRepository
                .findByLivreIdAndStatutOrderByDateReservationAsc(livre.getId(), StatutReservation.EN_ATTENTE);
        if (!queue.isEmpty()) {
            Reservation prochaine = queue.get(0);
            prochaine.setStatut(StatutReservation.CONFIRMEE);
            reservationRepository.save(prochaine);
            emailService.envoyerConfirmationReservation(
                    prochaine.getMembre().getEmail(),
                    prochaine.getMembre().getPrenom() + " " + prochaine.getMembre().getNom(),
                    livre.getTitre());
        }
    }

    private ReservationDTO toDTO(Reservation r) {
        return new ReservationDTO(
                r.getId(),
                r.getLivre().getId(),
                r.getLivre().getTitre(),
                r.getLivre().getAuteur(),
                r.getMembre().getId(),
                r.getMembre().getPrenom() + " " + r.getMembre().getNom(),
                r.getMembre().getEmail(),
                r.getDateReservation(),
                r.getStatut());
    }
}
