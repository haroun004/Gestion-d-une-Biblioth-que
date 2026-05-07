package com.fst.bibliotheque.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fst.bibliotheque.dto.DtoMapper;
import com.fst.bibliotheque.dto.EmpruntFormDTO;
import com.fst.bibliotheque.dto.EmpruntViewDTO;
import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.repository.EmpruntRepository;
import com.fst.bibliotheque.repository.LivreRepository;
import com.fst.bibliotheque.repository.MembreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;

    public Page<EmpruntViewDTO> findAll(StatutEmprunt statut, Pageable pageable) {
        Page<Emprunt> page = (statut == null)
                ? empruntRepository.findAll(pageable)
                : empruntRepository.findByStatut(statut, pageable);
        return page.map(DtoMapper::toViewDTO);
    }

    public EmpruntViewDTO creerEmprunt(EmpruntFormDTO dto) {
        Livre livre = livreRepository.findById(dto.getLivreId())
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable : " + dto.getLivreId()));
        Membre membre = membreRepository.findById(dto.getMembreId())
                .orElseThrow(() -> new IllegalArgumentException("Membre introuvable : " + dto.getMembreId()));

        if (livre.getQuantiteDisponible() <= 0) {
            throw new IllegalStateException("Ce livre n'est plus disponible.");
        }

        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);

        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setMembre(membre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(dto.getDateRetourPrevue());
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        return DtoMapper.toViewDTO(empruntRepository.save(emprunt));
    }

    public EmpruntViewDTO enregistrerRetour(Long id) {
        Emprunt emprunt = empruntRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + id));
        if (emprunt.getStatut() == StatutEmprunt.RENDU) {
            throw new IllegalStateException("Cet emprunt est déjà rendu.");
        }
        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RENDU);
        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);
        return DtoMapper.toViewDTO(empruntRepository.save(emprunt));
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

