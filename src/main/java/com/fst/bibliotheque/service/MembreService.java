package com.fst.bibliotheque.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fst.bibliotheque.dto.DtoMapper;
import com.fst.bibliotheque.dto.MembreDTO;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.MembreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MembreService {

    private final MembreRepository membreRepository;

    public Page<MembreDTO> findAll(String search, Pageable pageable) {
        Page<Membre> page = (search == null || search.isBlank())
                ? membreRepository.findAll(pageable)
                : membreRepository.search(search, pageable);
        return page.map(DtoMapper::toDTO);
    }

    public List<MembreDTO> findAllActifs() {
        return membreRepository.findAll().stream()
                .filter(Membre::isActif)
                .map(DtoMapper::toDTO)
                .toList();
    }

    public MembreDTO findById(Long id) {
        return DtoMapper.toDTO(findEntityById(id));
    }

    /** Internal use only — returns the JPA entity for relationships. */
    public Membre findEntityById(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membre introuvable : " + id));
    }

    public MembreDTO save(MembreDTO dto) {
        Membre membre = DtoMapper.toEntity(dto);
        return DtoMapper.toDTO(membreRepository.save(membre));
    }

    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }

    public long count() {
        return membreRepository.count();
    }
}

