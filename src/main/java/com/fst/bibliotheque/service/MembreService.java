package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.MembreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MembreService {

    private final MembreRepository membreRepository;

    public Page<Membre> findAll(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return membreRepository.findAll(pageable);
        }
        return membreRepository.search(search, pageable);
    }

    public List<Membre> findAllActifs() {
        return membreRepository.findAll().stream()
                .filter(Membre::isActif)
                .toList();
    }

    public Membre findById(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membre introuvable : " + id));
    }

    public Membre save(Membre membre) {
        return membreRepository.save(membre);
    }

    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }

    public long count() {
        return membreRepository.count();
    }
}
