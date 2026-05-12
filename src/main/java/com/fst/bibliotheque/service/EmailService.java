package com.fst.bibliotheque.service;

import java.time.LocalDate;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private static final String FROM = "noreply@bibliotheque.fst";

    private final JavaMailSender mailSender;

    /**
     * Sends (or simulates) an overdue-return reminder.
     * If no SMTP server is reachable, logs the message instead.
     */
    public void envoyerRappelRetard(String toEmail, String nomMembre,
                                    String titreLivre, LocalDate dateRetourPrevue) {
        SimpleMailMessage message = buildMessage(
                toEmail,
                "[Bibliothèque FST] Rappel : livre en retard",
                String.format("""
                        Bonjour %s,

                        Ce message est un rappel automatique concernant l'emprunt du livre «%s».
                        La date de retour prévue était le %s.

                        Merci de retourner ce livre à la bibliothèque dans les meilleurs délais.

                        Cordialement,
                        La Bibliothèque FST
                        """, nomMembre, titreLivre, dateRetourPrevue));
        try {
            mailSender.send(message);
            log.info("[MAIL] Rappel retard envoyé à {}", toEmail);
        } catch (MailException ex) {
            log.warn("[MOCK MAIL] Rappel retard simulé — À: {} | Livre: «{}» | Prévu le: {}",
                    toEmail, titreLivre, dateRetourPrevue);
        }
    }

    /**
     * Sends (or simulates) a notification when a reserved book becomes available.
     */
    public void envoyerConfirmationReservation(String toEmail, String nomMembre, String titreLivre) {
        SimpleMailMessage message = buildMessage(
                toEmail,
                "[Bibliothèque FST] Votre réservation est disponible !",
                String.format("""
                        Bonjour %s,

                        Bonne nouvelle ! Le livre «%s» que vous avez réservé est maintenant disponible.
                        Vous pouvez venir l'emprunter à la bibliothèque.

                        Cordialement,
                        La Bibliothèque FST
                        """, nomMembre, titreLivre));
        try {
            mailSender.send(message);
            log.info("[MAIL] Confirmation réservation envoyée à {}", toEmail);
        } catch (MailException ex) {
            log.warn("[MOCK MAIL] Confirmation réservation simulée — À: {} | Livre: «{}»",
                    toEmail, titreLivre);
        }
    }

    private SimpleMailMessage buildMessage(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(FROM);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        return msg;
    }
}
