package cn.shuzilearn.bookkeeping.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtil {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // 正则表达式
        String regex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
