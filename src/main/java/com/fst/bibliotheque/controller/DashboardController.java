package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.service.EmpruntService;
import com.fst.bibliotheque.service.LivreService;
import com.fst.bibliotheque.service.MembreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final LivreService livreService;
    private final MembreService membreService;
    private final EmpruntService empruntService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalLivres", livreService.count());
        model.addAttribute("totalMembres", membreService.count());
        model.addAttribute("empruntsEnCours", empruntService.countEnCours());
        model.addAttribute("empruntsEnRetard", empruntService.countEnRetard());
        return "dashboard";
    }
}
