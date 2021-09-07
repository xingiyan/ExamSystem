package com.lwx.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/8/7 19:40
 * @desc:
 */
public class test {
    public void test() {
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.out.println(content);
        StringBuilder hzs = new StringBuilder();
        StringBuilder nums = new StringBuilder();
        StringBuilder engs = new StringBuilder();

        char ch;
        for (int i = 0; i < content.length(); i++) {
            ch = content.charAt(i);
            switch (Character.getType(ch)) {
                case 1:
                    engs.append(ch);
                    break;
                case 2:
                    engs.append(ch);
                    break;
                case 5:
                    hzs.append(ch);
                    break;
                case 9:
                    nums.append(ch);
                default:
                    break;

            }

        }
        System.out.println(hzs.toString());
        System.out.println(nums.toString());
        System.out.println(engs.toString());

        f(hzs.toString());
        f(nums.toString());
        f(engs.toString());

    }

    public void f(String str){
        char[] arr = str.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        int num = 0;
        for (char c : arr) {
            map.put(c, num);
        }

        for (char c : arr) {
            if (map.containsKey(c)) {
                num = map.get(c);
                num++;
                map.put(c, num);
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.print(entry.getKey().toString() + ":" + entry.getValue() + "\n");
        }


    }
}
