package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.dto.ReservationFormDTO;
import com.fst.bibliotheque.entity.StatutReservation;
import com.fst.bibliotheque.service.LivreService;
import com.fst.bibliotheque.service.MembreService;
import com.fst.bibliotheque.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final LivreService livreService;
    private final MembreService membreService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) StatutReservation statut,
                       @RequestParam(required = false) Long livreId,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size) {
        model.addAttribute("reservationsPage", reservationService.findAll(
                statut, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateReservation"))));
        model.addAttribute("statuts", StatutReservation.values());
        model.addAttribute("statutFiltre", statut);
        ReservationFormDTO form = new ReservationFormDTO();
        form.setLivreId(livreId); // pre-fill when coming from livres page
        model.addAttribute("reservationForm", form);
        model.addAttribute("livres", livreService.findAll(null, PageRequest.of(0, 1000)).getContent());
        model.addAttribute("membres", membreService.findAllActifs());
        return "reservations/list";
    }

    @PostMapping("/sauvegarder")
    public String save(@Valid @ModelAttribute("reservationForm") ReservationFormDTO form,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez sélectionner un livre et un membre.");
            return "redirect:/reservations";
        }
        try {
            reservationService.creerReservation(form);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation créée avec succès.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reservations";
    }

    @PostMapping("/{id}/annuler")
    public String annuler(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.annulerReservation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation annulée.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reservations";
    }
}
