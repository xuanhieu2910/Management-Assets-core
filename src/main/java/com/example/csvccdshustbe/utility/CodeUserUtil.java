package com.example.csvccdshustbe.utility;

import java.util.UUID;

public class CodeUserUtil {

    public static String autoGenerateSecureRandomUser(String userName){
        return UUID.nameUUIDFromBytes(userName.getBytes()).toString();
    }
}
