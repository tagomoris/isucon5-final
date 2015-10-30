package net.isucon.isucon5f.bench;

import java.nio.ByteBuffer;
import java.util.Random;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class I5FTenki {
    public static long VALID_CACHE_MILLIS = 4000; // 3s + 1

    private static char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static String secret = "happyhalloween";

    private static String[] list = new String[]{
        "晴れ","晴れ時々曇り","晴れ時々雨","晴れ時々雪","晴れ時々雷雨","晴れ時々雨か雪","晴れ時々雪か雨","晴れ時々雨か雷雨",
        "晴れ時々大雨","晴れ時々暴風雨","晴れ時々大雪","晴れ時々暴風雪","晴れ一時曇り","晴れ一時雨","晴れ一時雪","晴れ一時雷雨",
        "晴れ一時雨か雪","晴れ一時雪か雨","晴れ一時雨か雷雨","晴れ一時大雨","晴れ一時暴風雨","晴れ一時大雪","晴れ一時暴風雪",
        "晴れのち曇り","晴れのち雨","晴れのち雪","晴れのち雷雨","晴れのち雨か雪","晴れのち雪か雨","晴れのち雨か雷雨","晴れのち大雨",
        "晴れのち暴風雨","晴れのち大雪","晴れのち暴風雪","曇り","曇り時々晴れ","曇り時々雨","曇り時々雪","曇り時々雷雨",
        "曇り時々雨か雪","曇り時々雪か雨","曇り時々雨か雷雨","曇り時々大雨","曇り時々暴風雨","曇り時々大雪","曇り時々暴風雪",
        "曇り一時晴れ","曇り一時雨","曇り一時雪","曇り一時雷雨","曇り一時雨か雪","曇り一時雪か雨","曇り一時雨か雷雨",
        "曇り一時大雨","曇り一時暴風雨","曇り一時大雪","曇り一時暴風雪","曇りのち晴れ","曇りのち雨","曇りのち雪",
        "曇りのち雷雨","曇りのち雨か雪","曇りのち雪か雨","曇りのち雨か雷雨","曇りのち大雨","曇りのち暴風雨","曇りのち大雪",
        "曇りのち暴風雪","雨","雨時々晴れ","雨時々曇り","雨時々雪","雨時々雷雨","雨時々雨か雪","雨時々雪か雨","雨時々雨か雷雨",
        "雨時々大雨","雨時々暴風雨","雨時々大雪","雨時々暴風雪","雨一時晴れ","雨一時曇り","雨一時雪","雨一時雷雨","雨一時雨か雪",
        "雨一時雪か雨","雨一時雨か雷雨","雨一時大雨","雨一時暴風雨","雨一時大雪","雨一時暴風雪","雨のち晴れ","雨のち曇り",
        "雨のち雪","雨のち雷雨","雨のち雨か雪","雨のち雪か雨","雨のち雨か雷雨","雨のち大雨","雨のち暴風雨","雨のち大雪",
        "雨のち暴風雪","雪","雪時々晴れ","雪時々曇り","雪時々雨","雪時々雷雨","雪時々雨か雪","雪時々雪か雨","雪時々雨か雷雨",
        "雪時々大雨","雪時々暴風雨","雪時々大雪","雪時々暴風雪","雪一時晴れ","雪一時曇り","雪一時雨","雪一時雷雨","雪一時雨か雪",
        "雪一時雪か雨","雪一時雨か雷雨","雪一時大雨","雪一時暴風雨","雪一時大雪","雪一時暴風雪","雪のち晴れ","雪のち曇り","雪のち雨",
        "雪のち雷雨","雪のち雨か雪","雪のち雪か雨","雪のち雨か雷雨","雪のち大雨","雪のち暴風雨","雪のち大雪","雪のち暴風雪","雷雨",
        "雷雨時々晴れ","雷雨時々曇り","雷雨時々雨","雷雨時々雪","雷雨時々雨か雪","雷雨時々雪か雨","雷雨時々雨か雷雨","雷雨時々大雨",
        "雷雨時々暴風雨","雷雨時々大雪","雷雨時々暴風雪","雷雨一時晴れ","雷雨一時曇り","雷雨一時雨","雷雨一時雪","雷雨一時雨か雪",
        "雷雨一時雪か雨","雷雨一時雨か雷雨","雷雨一時大雨","雷雨一時暴風雨","雷雨一時大雪","雷雨一時暴風雪","雷雨のち晴れ","雷雨のち曇り",
        "雷雨のち雨","雷雨のち雪","雷雨のち雨か雪","雷雨のち雪か雨","雷雨のち雨か雷雨","雷雨のち大雨","雷雨のち暴風雨","雷雨のち大雪",
        "雷雨のち暴風雪","雨か雪","雨か雪時々晴れ","雨か雪時々曇り","雨か雪時々雨","雨か雪時々雪","雨か雪時々雷雨","雨か雪時々雪か雨",
        "雨か雪時々雨か雷雨","雨か雪時々大雨","雨か雪時々暴風雨","雨か雪時々大雪","雨か雪時々暴風雪","雨か雪一時晴れ","雨か雪一時曇り",
        "雨か雪一時雨","雨か雪一時雪","雨か雪一時雷雨","雨か雪一時雪か雨","雨か雪一時雨か雷雨","雨か雪一時大雨","雨か雪一時暴風雨",
        "雨か雪一時大雪","雨か雪一時暴風雪","雨か雪のち晴れ","雨か雪のち曇り","雨か雪のち雨","雨か雪のち雪","雨か雪のち雷雨",
        "雨か雪のち雪か雨","雨か雪のち雨か雷雨","雨か雪のち大雨","雨か雪のち暴風雨","雨か雪のち大雪","雨か雪のち暴風雪","雪か雨",
        "雪か雨時々晴れ","雪か雨時々曇り","雪か雨時々雨","雪か雨時々雪","雪か雨時々雷雨","雪か雨時々雨か雪","雪か雨時々雨か雷雨",
        "雪か雨時々大雨","雪か雨時々暴風雨","雪か雨時々大雪","雪か雨時々暴風雪","雪か雨一時晴れ","雪か雨一時曇り","雪か雨一時雨","雪か雨一時雪",
        "雪か雨一時雷雨","雪か雨一時雨か雪","雪か雨一時雨か雷雨","雪か雨一時大雨","雪か雨一時暴風雨","雪か雨一時大雪","雪か雨一時暴風雪",
        "雪か雨のち晴れ","雪か雨のち曇り","雪か雨のち雨","雪か雨のち雪","雪か雨のち雷雨","雪か雨のち雨か雪","雪か雨のち雨か雷雨",
        "雪か雨のち大雨","雪か雨のち暴風雨","雪か雨のち大雪","雪か雨のち暴風雪","雨か雷雨","雨か雷雨時々晴れ","雨か雷雨時々曇り",
        "雨か雷雨時々雨","雨か雷雨時々雪","雨か雷雨時々雷雨","雨か雷雨時々雨か雪","雨か雷雨時々雪か雨","雨か雷雨時々大雨","雨か雷雨時々暴風雨",
        "雨か雷雨時々大雪","雨か雷雨時々暴風雪","雨か雷雨一時晴れ","雨か雷雨一時曇り","雨か雷雨一時雨","雨か雷雨一時雪","雨か雷雨一時雷雨",
        "雨か雷雨一時雨か雪","雨か雷雨一時雪か雨","雨か雷雨一時大雨","雨か雷雨一時暴風雨","雨か雷雨一時大雪","雨か雷雨一時暴風雪",
        "雨か雷雨のち晴れ","雨か雷雨のち曇り","雨か雷雨のち雨","雨か雷雨のち雪","雨か雷雨のち雷雨","雨か雷雨のち雨か雪","雨か雷雨のち雪か雨",
        "雨か雷雨のち大雨","雨か雷雨のち暴風雨","雨か雷雨のち大雪","雨か雷雨のち暴風雪","大雨","大雨時々晴れ","大雨時々曇り","大雨時々雨",
        "大雨時々雪","大雨時々雷雨","大雨時々雨か雪","大雨時々雪か雨","大雨時々雨か雷雨","大雨時々暴風雨","大雨時々大雪","大雨時々暴風雪",
        "大雨一時晴れ","大雨一時曇り","大雨一時雨","大雨一時雪","大雨一時雷雨","大雨一時雨か雪","大雨一時雪か雨","大雨一時雨か雷雨",
        "大雨一時暴風雨","大雨一時大雪","大雨一時暴風雪","大雨のち晴れ","大雨のち曇り","大雨のち雨","大雨のち雪","大雨のち雷雨","大雨のち雨か雪",
        "大雨のち雪か雨","大雨のち雨か雷雨","大雨のち暴風雨","大雨のち大雪","大雨のち暴風雪","暴風雨","暴風雨時々晴れ","暴風雨時々曇り",
        "暴風雨時々雨","暴風雨時々雪","暴風雨時々雷雨","暴風雨時々雨か雪","暴風雨時々雪か雨","暴風雨時々雨か雷雨","暴風雨時々大雨","暴風雨時々大雪",
        "暴風雨時々暴風雪","暴風雨一時晴れ","暴風雨一時曇り","暴風雨一時雨","暴風雨一時雪","暴風雨一時雷雨","暴風雨一時雨か雪","暴風雨一時雪か雨",
        "暴風雨一時雨か雷雨","暴風雨一時大雨","暴風雨一時大雪","暴風雨一時暴風雪","暴風雨のち晴れ","暴風雨のち曇り","暴風雨のち雨","暴風雨のち雪",
        "暴風雨のち雷雨","暴風雨のち雨か雪","暴風雨のち雪か雨","暴風雨のち雨か雷雨","暴風雨のち大雨","暴風雨のち大雪","暴風雨のち暴風雪",
        "大雪","大雪時々晴れ","大雪時々曇り","大雪時々雨","大雪時々雪","大雪時々雷雨","大雪時々雨か雪","大雪時々雪か雨","大雪時々雨か雷雨",
        "大雪時々大雨","大雪時々暴風雨","大雪時々暴風雪","大雪一時晴れ","大雪一時曇り","大雪一時雨","大雪一時雪","大雪一時雷雨","大雪一時雨か雪",
        "大雪一時雪か雨","大雪一時雨か雷雨","大雪一時大雨","大雪一時暴風雨","大雪一時暴風雪","大雪のち晴れ","大雪のち曇り","大雪のち雨","大雪のち雪",
        "大雪のち雷雨","大雪のち雨か雪","大雪のち雪か雨","大雪のち雨か雷雨","大雪のち大雨","大雪のち暴風雨","大雪のち暴風雪","暴風雪",
        "暴風雪時々晴れ","暴風雪時々曇り","暴風雪時々雨","暴風雪時々雪","暴風雪時々雷雨","暴風雪時々雨か雪","暴風雪時々雪か雨","暴風雪時々雨か雷雨",
        "暴風雪時々大雨","暴風雪時々暴風雨","暴風雪時々大雪","暴風雪一時晴れ","暴風雪一時曇り","暴風雪一時雨","暴風雪一時雪","暴風雪一時雷雨",
        "暴風雪一時雨か雪","暴風雪一時雪か雨","暴風雪一時雨か雷雨","暴風雪一時大雨","暴風雪一時暴風雨","暴風雪一時大雪","暴風雪のち晴れ","暴風雪のち曇り",
        "暴風雪のち雨","暴風雪のち雪","暴風雪のち雷雨","暴風雪のち雨か雪","暴風雪のち雪か雨","暴風雪のち雨か雷雨","暴風雪のち大雨","暴風雪のち暴風雨",
        "暴風雪のち大雪"
    };

    public static String zipRandom(Random random) {
        char[] zip = new char[7];
        for (int i = 0 ; i < 7 ; i++) {
            zip[i] = chars[random.nextInt(chars.length)];
        }
        return String.valueOf(zip);
    }

    public static String getYoho(String date) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Checksum algorithm not found: MD5");
        }
        md.update((date + " " + secret).getBytes());
        byte[] digest = md.digest();
        return list[Integer.remainderUnsigned(ByteBuffer.wrap(digest, 0, 4).getInt(), list.length)];
    }
}
