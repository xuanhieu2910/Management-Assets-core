package com.example.csvccdshustbe.dto.reason;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FindAllReasonDto {
    private Integer idReason;
    private String name;
    private Integer typeReason;
    private Integer status;
    private Long timeCreated;
    private Long timeModified;
    private String typeAction;
}
