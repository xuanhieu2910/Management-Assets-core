package com.example.csvccdshustbe.dto.document.tool;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouncilDocumentInventoryDto {
    private String content;
    private List<CouncilInventory> councilInventory;

    @Getter
    @Setter
    public static class CouncilInventory {
        private String userName;
        private String fullName;
        private String codeUser;
        private String position;
        private String positionInstance;
        private int level;
    }
}
