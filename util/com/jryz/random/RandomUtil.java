package com.jryz.random;

import org.springframework.util.Assert;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static int getRandom(int length){
        Assert.state(length < 11);
        int x = 0;
        Random ra = new Random();
        for (int i = 0; i < length; i++){
            x += (ra.nextInt(10) * Math.pow(10,i));
        }
        int p = (int) Math.pow(10, length - 1);
        if (x < p) {
            return p + x;
        }
        return x;
    }

    public static void main(String[] args) {
        System.out.println(getRandomString(32));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
        System.out.println(getRandom(3));
    }
}
