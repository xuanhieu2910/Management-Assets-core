package com.example.csvccdshustbe.request.toolCategories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateToolCategoryRequest {
    @NonNull
    private Integer idToolCategory;
    @NonNull
    private String name;
    private String shortName;
    private String description;
    private Integer parentId;
    private Integer visible;
}
