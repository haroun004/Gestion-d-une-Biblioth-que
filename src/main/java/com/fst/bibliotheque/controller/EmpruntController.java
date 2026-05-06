package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.service.EmpruntService;
import com.fst.bibliotheque.service.LivreService;
import com.fst.bibliotheque.service.MembreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprunts")
@RequiredArgsConstructor
public class EmpruntController {

    private final EmpruntService empruntService;
    private final LivreService livreService;
    private final MembreService membreService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) StatutEmprunt statut,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size) {
        Page<Emprunt> empruntsPage = empruntService.findAll(statut,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateEmprunt")));
        model.addAttribute("empruntsPage", empruntsPage);
        model.addAttribute("statuts", StatutEmprunt.values());
        model.addAttribute("statutFiltre", statut);
        return "emprunts/list";
    }

    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("emprunt", new Emprunt());
        model.addAttribute("livres", livreService.findAll(null, PageRequest.of(0, 1000)).getContent());
        model.addAttribute("membres", membreService.findAllActifs());
        return "emprunts/form";
    }

    @PostMapping("/sauvegarder")
    public String save(@Valid @ModelAttribute Emprunt emprunt,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("livres", livreService.findAll(null, PageRequest.of(0, 1000)).getContent());
            model.addAttribute("membres", membreService.findAllActifs());
            return "emprunts/form";
        }
        try {
            empruntService.creerEmprunt(emprunt);
            redirectAttributes.addFlashAttribute("successMessage", "Emprunt créé avec succès.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/emprunts";
    }

    @PostMapping("/{id}/retour")
    public String retour(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        empruntService.enregistrerRetour(id);
        redirectAttributes.addFlashAttribute("successMessage", "Retour enregistré avec succès.");
        return "redirect:/emprunts";
    }
}
