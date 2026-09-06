package com.example.csvccdshustbe.request.report;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllReportRequest extends RequestPageBase {

    private String codeReport;
    private Integer idGovernmentCircular;
    private List<Integer> idsDepartment;
    private Integer statusReport;
    private Integer statusGovernmentCircular;
}
