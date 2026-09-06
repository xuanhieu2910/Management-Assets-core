package com.example.csvccdshustbe.request.currentUsage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCurrentUsageRequest {
    @NonNull
    private String name;
    private String code;
}
