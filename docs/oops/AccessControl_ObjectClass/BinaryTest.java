package Lect4;

import java.util.Scanner;

public class BinaryTest {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n= scanner.nextInt();
        String s=Integer.toBinaryString(n);
        System.out.println(s);
    }
}
