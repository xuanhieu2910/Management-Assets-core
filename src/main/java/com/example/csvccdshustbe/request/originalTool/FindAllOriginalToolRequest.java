package com.example.csvccdshustbe.request.originalTool;


import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalToolRequest extends RequestPageBase {
    private Integer visible;
}
