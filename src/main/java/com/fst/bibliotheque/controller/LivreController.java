package com.fst.bibliotheque.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fst.bibliotheque.dto.LivreDTO;
import com.fst.bibliotheque.service.LivreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/livres")
@RequiredArgsConstructor
public class LivreController {

    private final LivreService livreService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "") String search,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size) {
        Page<LivreDTO> livresPage = livreService.findAll(search,
                PageRequest.of(page, size, Sort.by("titre")));
        model.addAttribute("livresPage", livresPage);
        model.addAttribute("search", search);
        return "livres/list";
    }

    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("livre", new LivreDTO());
        model.addAttribute("action", "Ajouter");
        return "livres/form";
    }

    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("livre", livreService.findById(id));
        model.addAttribute("action", "Modifier");
        return "livres/form";
    }

    @PostMapping("/sauvegarder")
    public String save(@Valid @ModelAttribute("livre") LivreDTO livreDTO,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("action", livreDTO.getId() == null ? "Ajouter" : "Modifier");
            return "livres/form";
        }
        livreService.save(livreDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Livre sauvegardé avec succès.");
        return "redirect:/livres";
    }

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        livreService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Livre supprimé avec succès.");
        return "redirect:/livres";
    }
}

