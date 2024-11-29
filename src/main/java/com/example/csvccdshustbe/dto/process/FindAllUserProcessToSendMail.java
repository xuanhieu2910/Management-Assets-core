package com.example.csvccdshustbe.dto.process;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FindAllUserProcessToSendMail {


    private List<String> listUserName;
    private String codeDocument;
    private String descriptionDocument;
    private Integer idProcess;
    private String nameProcess;
}
