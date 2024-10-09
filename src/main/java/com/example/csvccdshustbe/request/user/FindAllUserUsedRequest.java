package com.example.csvccdshustbe.request.user;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllUserUsedRequest extends RequestPageBase {
    private List<Integer> idsDepartment;
}
