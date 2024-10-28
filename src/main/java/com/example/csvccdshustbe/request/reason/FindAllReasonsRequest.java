package com.example.csvccdshustbe.request.reason;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllReasonsRequest extends RequestPageBase {
    private Integer status;
    private Integer typeReason;
}
