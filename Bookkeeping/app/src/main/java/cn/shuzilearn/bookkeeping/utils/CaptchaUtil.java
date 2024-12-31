package cn.shuzilearn.bookkeeping.utils;

import java.util.Random;

public class CaptchaUtil {
    public static int getCaptchaCode() {
        Random random = new Random();
        int fourDigitRandomNumber = 1000 + random.nextInt(9000); // [1000, 9999]
        return fourDigitRandomNumber;
    }
}
