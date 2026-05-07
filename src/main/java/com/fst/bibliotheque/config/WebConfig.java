package com.fst.bibliotheque.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC configuration placeholder.
 * String→Entity converters were removed after introducing EmpruntFormDTO,
 * which binds livreId/membreId as plain Long values.
 */
@Component
public class WebConfig implements WebMvcConfigurer {
}

