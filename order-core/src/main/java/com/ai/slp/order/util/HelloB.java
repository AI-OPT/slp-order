package com.ai.slp.order.util;

class HelloA {

    static {
        System.out.println("static A 1");
    }

    {
        System.out.println("I'm A class 1");
    }

    public HelloA() {

        System.out.println("HelloA");

    }

    {
        System.out.println("I'm A class 2");
    }

    static {
        System.out.println("static A 2");
    }

}
public class HelloB extends HelloA {


    static {
        System.out.println("static B 1");
    }

    {
        System.out.println("I'm B class 1");
    }

    public HelloB() {

        System.out.println("HelloB");

    }

    {
        System.out.println("I'm B class 2");
    }
    static {
        System.out.println("static B 2");
    }

    public static void main(String[] args) {
        new HelloB();
    }

    
    
}
