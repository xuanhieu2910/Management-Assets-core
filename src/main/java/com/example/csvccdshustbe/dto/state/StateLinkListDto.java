package com.example.csvccdshustbe.dto.state;

import com.example.csvccdshustbe.entity.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateLinkListDto {

    private State stateCurrent;
    private State stateNext;

}
