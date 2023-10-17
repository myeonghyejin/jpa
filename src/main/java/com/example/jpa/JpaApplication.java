package com.example.jpa;

import com.example.jpa.factory.CustomEntityManagerFactory;
import com.example.jpa.service.ValueService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JpaApplication {

    public static void main(String[] args) throws IOException {

        CustomEntityManagerFactory.initialization();
        ValueService valueService = new ValueService();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input your Command // [command] [name]");
            String commandLine = br.readLine();
            String[] splitCommand = commandLine.split(" ");

            // 별도 값 검증하는 로직은 추가하지 않음
            if (splitCommand[0].equalsIgnoreCase("exit")) {
                System.out.println("System closed");
                break;

            } else if (splitCommand[0].equalsIgnoreCase("originalProvider")) {

                valueService.insertOriginalProvider();

            } else if (splitCommand[0].equalsIgnoreCase("provider")) {

                valueService.insertProvider();

            }

        }

    }

}
