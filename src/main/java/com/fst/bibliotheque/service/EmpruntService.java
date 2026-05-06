package com.fst.bibliotheque.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.repository.EmpruntRepository;
import com.fst.bibliotheque.repository.LivreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public Page<Emprunt> findAll(StatutEmprunt statut, Pageable pageable) {
        if (statut == null) {
            return empruntRepository.findAll(pageable);
        }
        return empruntRepository.findByStatut(statut, pageable);
    }

    public Emprunt findById(Long id) {
        return empruntRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + id));
    }

    public Emprunt creerEmprunt(Emprunt emprunt) {
        Livre livre = emprunt.getLivre();
        if (livre.getQuantiteDisponible() <= 0) {
            throw new IllegalStateException("Ce livre n'est plus disponible.");
        }
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);
        emprunt.setStatut(StatutEmprunt.EN_COURS);
        emprunt.setDateEmprunt(LocalDate.now());
        return empruntRepository.save(emprunt);
    }

    public Emprunt enregistrerRetour(Long id) {
        Emprunt emprunt = findById(id);
        if (emprunt.getStatut() == StatutEmprunt.RENDU) {
            throw new IllegalStateException("Cet emprunt est déjà rendu.");
        }
        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RENDU);
        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);
        return empruntRepository.save(emprunt);
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void mettreAJourRetards() {
        List<Emprunt> enRetard = empruntRepository
                .findByStatutAndDateRetourPrevueBefore(StatutEmprunt.EN_COURS, LocalDate.now());
        enRetard.forEach(e -> e.setStatut(StatutEmprunt.EN_RETARD));
        empruntRepository.saveAll(enRetard);
    }

    public long countEnCours() {
        return empruntRepository.countByStatut(StatutEmprunt.EN_COURS);
    }

    public long countEnRetard() {
        return empruntRepository.countEnRetard(LocalDate.now());
    }
}
