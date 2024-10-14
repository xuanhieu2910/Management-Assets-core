package com.example.csvccdshustbe.request.documentAttack;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAttackVisibleRequest extends RequestPageBase {

    private List<Integer> idsDepartment;
}
