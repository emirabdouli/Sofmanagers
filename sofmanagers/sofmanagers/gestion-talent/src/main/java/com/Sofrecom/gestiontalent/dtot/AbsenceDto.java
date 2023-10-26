package com.Sofrecom.gestiontalent.dtot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDto {
    private Long id;
    private TalentDto talent;
    private Date startDate;
    private Date endDate;
    private Boolean etat;
    private String type;
}
