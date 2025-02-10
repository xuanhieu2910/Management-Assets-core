package com.example.csvccdshustbe.request.toolInstance;


import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolInstanceRequest extends RequestPageBase {
    private Integer isError;
}
