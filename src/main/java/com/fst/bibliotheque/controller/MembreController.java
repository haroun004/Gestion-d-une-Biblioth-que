package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Membre;
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
@RequestMapping("/membres")
@RequiredArgsConstructor
public class MembreController {

    private final MembreService membreService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "") String search,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size) {
        Page<Membre> membresPage = membreService.findAll(search,
                PageRequest.of(page, size, Sort.by("nom")));
        model.addAttribute("membresPage", membresPage);
        model.addAttribute("search", search);
        return "membres/list";
    }

    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("membre", new Membre());
        model.addAttribute("action", "Ajouter");
        return "membres/form";
    }

    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("membre", membreService.findById(id));
        model.addAttribute("action", "Modifier");
        return "membres/form";
    }

    @PostMapping("/sauvegarder")
    public String save(@Valid @ModelAttribute Membre membre,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", membre.getId() == null ? "Ajouter" : "Modifier");
            return "membres/form";
        }
        membreService.save(membre);
        redirectAttributes.addFlashAttribute("successMessage", "Membre sauvegardé avec succès.");
        return "redirect:/membres";
    }

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        membreService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Membre supprimé avec succès.");
        return "redirect:/membres";
    }
}
