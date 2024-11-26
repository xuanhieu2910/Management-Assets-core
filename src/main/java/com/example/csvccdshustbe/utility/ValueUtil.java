package com.example.csvccdshustbe.utility;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ValueUtil {

    public final static String MESSAGE_STRONG_PASSWORD = "Must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";
    public final static String PATTERN_STRONG_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$";
    public final static String PATTERN_NUMBER_CHARACTER = "^[a-zA-Z0-9 ]+$";
    public final static String PATTERN_NON_NUMBER = "[^\\d+]";
    public final static String REGEX_letter_digit_period_underscore  = "[^a-zA-Z0-9._]";

    public static String convertToVietnamese(String str){
        str = str.replaceAll("A|Á|À|Ã|Ạ|Â|Ấ|Ầ|Ẫ|Ậ|Ă|Ắ|Ằ|Ẵ|Ặ", "A");
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("E|É|È|Ẽ|Ẹ|Ê|Ế|Ề|Ễ|Ệ", "E");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("I|Í|Ì|Ĩ|Ị", "I");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("O|Ó|Ò|Õ|Ọ|Ô|Ố|Ồ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ỡ|Ợ", "O");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("U|Ú|Ù|Ũ|Ụ|Ư|Ứ|Ừ|Ữ|Ự", "U");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("Y|Ý|Ỳ|Ỹ|Ỵ", "Y");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("đ", "d");
        // Some system encode vietnamese combining accent as individual utf-8 characters
        str = str.replaceAll("\u0300|\u0301|\u0303|\u0309|\u0323", ""); // Huyền sắc hỏi ngã nặng
        str = str.replace("\u02C6|\u0306|\u031B", ""); // Â, Ê, Ă, Ơ, Ư
        return str;
    }
    public static String convertMapKeyStringWithGuava(Map<String, ?> map) {
        return Joiner.on(",").withKeyValueSeparator(":").join(map);
    }

    private ValueUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStringByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Double getDoubleByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return Double.valueOf(obj.toString());
    }

    public static Date getDateByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return (Date) obj;
    }

    public static LocalDateTime getLocalDateTimeByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return ((Timestamp) obj).toLocalDateTime();
    }

    public static Long getLongByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Long.valueOf(obj.toString());
    }

    public static Integer getIntegerByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    public static Timestamp getTimestampByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return (Timestamp) obj;
    }

    public static Float getFloatByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Float.valueOf(obj.toString());
    }


    public static String getClobString(Clob clob){
        if (null != clob) {
            BufferedReader stringReader = null;
            try {
                stringReader = new BufferedReader(
                        clob.getCharacterStream());

                String singleLine = null;
                StringBuffer strBuff = new StringBuffer();
                while ((singleLine = stringReader.readLine()) != null) {
                    strBuff.append(singleLine);
                }
                return strBuff.toString();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String formatCurrency(Double fee) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(fee);
    }

    public static String formatCurrencyUSD(Double fee) {
        Locale localeVN = new Locale("en", "US");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(fee);
    }

    public static Boolean getBooleanByObject(Object obj) {
        return (obj != null && "1,true".contains(obj.toString()));
    }


    public static boolean validateStrongPassword(String password) throws ValidateFiledException {
        if (password != null) {
            return password.matches(PATTERN_STRONG_PASSWORD);
        } else {
            throw new ValidateFiledException(MESSAGE_STRONG_PASSWORD);
        }
    }


//    public static <T> Map<String, Object> convertObjectToMap(T obj) throws IllegalAccessException {
//        Class<?> clazz = obj.getClass();
//        Map<String, Object> map = new HashMap<>();
//        for (Field field : clazz.getDeclaredFields()) {
//            field.setAccessible(true);
//            Object value = field.get(obj);
//            map.put(field.getName(), value);
//        }
//        return map;
//    }
}
