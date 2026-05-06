package com.fst.bibliotheque.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.LivreRepository;
import com.fst.bibliotheque.repository.MembreRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Livre>() {
            @Override
            public Livre convert(@NonNull String id) {
                if (id.isBlank()) return null;
                return livreRepository.findById(Long.parseLong(id)).orElse(null);
            }
        });
        registry.addConverter(new Converter<String, Membre>() {
            @Override
            public Membre convert(@NonNull String id) {
                if (id.isBlank()) return null;
                return membreRepository.findById(Long.parseLong(id)).orElse(null);
            }
        });
    }
}
