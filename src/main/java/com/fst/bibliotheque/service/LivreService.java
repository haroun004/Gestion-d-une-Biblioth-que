package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.repository.LivreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;

    public Page<Livre> findAll(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return livreRepository.findAll(pageable);
        }
        return livreRepository.search(search, pageable);
    }

    public Livre findById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable : " + id));
    }

    public Livre save(Livre livre) {
        return livreRepository.save(livre);
    }

    public void deleteById(Long id) {
        livreRepository.deleteById(id);
    }

    public long count() {
        return livreRepository.count();
    }
}
