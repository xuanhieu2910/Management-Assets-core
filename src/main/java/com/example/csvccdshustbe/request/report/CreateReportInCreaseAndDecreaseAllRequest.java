package com.example.csvccdshustbe.request.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateReportInCreaseAndDecreaseAllRequest {

    private List<Integer> idsDepartmentOriginal;
    private String timeStart;
    private String timeEnd;
}
