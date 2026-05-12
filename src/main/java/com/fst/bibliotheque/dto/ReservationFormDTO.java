package com.fst.bibliotheque.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFormDTO {

    @NotNull(message = "Le livre est obligatoire")
    private Long livreId;

    @NotNull(message = "Le membre est obligatoire")
    private Long membreId;
}
