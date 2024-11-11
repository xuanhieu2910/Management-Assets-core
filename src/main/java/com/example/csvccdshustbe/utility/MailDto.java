package com.example.csvccdshustbe.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String addressFrom;
    private String addressTo;
    private String addressCc;
    private String subject;
    private String content;

    @Override
    public String toString() {
        return "MailDto{" +
                "addressFrom='" + addressFrom + '\'' +
                ", addressTo='" + addressTo + '\'' +
                ", addressCc='" + addressCc + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
