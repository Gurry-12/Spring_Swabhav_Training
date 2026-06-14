package com.example.studentverification;

import java.net.InetAddress;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentVerificationApplication {

    public static void main(String[] args) throws Exception {

        System.out.println("========== JAVA NETWORK TEST ==========");
        System.out.println("preferIPv4Stack = " + System.getProperty("java.net.preferIPv4Stack"));
        System.out.println("smtp.gmail.com resolves to = " + Arrays.toString(InetAddress.getAllByName("smtp.gmail.com")));
        System.out.println("=======================================");

        SpringApplication.run(StudentVerificationApplication.class, args);
    }
}
