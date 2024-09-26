package com.example.csvccdshustbe.enums;

public enum ContextLevelPattern {
    CONTEXT_SYSTEM(10),
    CONTEXT_DEPARTMENT(20),
    CONTEXT_USER(30),
    CONTEXT_ASSET(40),
    CONTEXT_TOOL(50),
    CONTEXT_ASSETCATEGORIES(60),
    CONTEXT_TOOLCATEGORIES(70);

    public final Integer contextLevel;
    ContextLevelPattern(Integer contextLevel) {
        this.contextLevel = contextLevel;
    }
}
