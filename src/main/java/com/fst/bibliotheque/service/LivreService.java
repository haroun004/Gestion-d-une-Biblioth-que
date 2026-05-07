package com.fst.bibliotheque.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fst.bibliotheque.dto.DtoMapper;
import com.fst.bibliotheque.dto.LivreDTO;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.repository.LivreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;

    public Page<LivreDTO> findAll(String search, Pageable pageable) {
        Page<Livre> page = (search == null || search.isBlank())
                ? livreRepository.findAll(pageable)
                : livreRepository.search(search, pageable);
        return page.map(DtoMapper::toDTO);
    }

    public LivreDTO findById(Long id) {
        return DtoMapper.toDTO(findEntityById(id));
    }

    /** Internal use only — returns the JPA entity for relationships. */
    public Livre findEntityById(Long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable : " + id));
    }

    public LivreDTO save(LivreDTO dto) {
        Livre livre = DtoMapper.toEntity(dto);
        return DtoMapper.toDTO(livreRepository.save(livre));
    }

    public void deleteById(Long id) {
        livreRepository.deleteById(id);
    }

    public long count() {
        return livreRepository.count();
    }
}

