package com.example.jpa;

import com.example.jpa.entity.UserEntity;
import com.example.jpa.exception.NotFoundException;
import com.example.jpa.factory.CustomEntityManagerFactory;
import com.example.jpa.service.UserService;
import com.example.jpa.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JpaApplication {

    public static void main(String[] args) throws IOException {

        CustomEntityManagerFactory.initialization();
        UserService userService = new UserServiceImpl();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input your Command // [command] [email] [name]");
            String commandLine = br.readLine();
            String[] splitCommand = commandLine.split(" ");

            // 별도 값 검증하는 로직은 추가하지 않음
            if (splitCommand[0].equalsIgnoreCase("exit")) {
                System.out.println("System closed");
                break;

            } else if (splitCommand[0].equalsIgnoreCase("insert")) {
                UserEntity userEntity = new UserEntity(splitCommand[1], splitCommand[2],
                        LocalDateTime.now(), LocalDateTime.now());
                userService.saveUser(userEntity);

            } else if (splitCommand[0].equalsIgnoreCase("select")) {
                Optional<UserEntity> userEntity = userService.getUser(splitCommand[1]);
                if (userEntity.isPresent()) {
                    UserEntity user = userEntity.get();
                    System.out.println("email : " + user.getEmail());
                    System.out.println("name : " + user.getName());
                    System.out.println("created date : " + user.getCreatedAt());
                    System.out.println("updated date : " + user.getUpdatedAt());

                } else {
                    System.out.println("값을 찾을 수 없습니다.");
                }

            } else if (splitCommand[0].equalsIgnoreCase("select2")) {
                Optional<UserEntity> userEntity = userService.getReferenceUser(splitCommand[1]);

                if (userEntity.isPresent()) {
                    UserEntity user = userEntity.get();
                    System.out.println("email : " + user.getEmail());
                    System.out.println("name : " + user.getName());
                    System.out.println("created date : " + user.getCreatedAt());
                    System.out.println("updated date : " + user.getUpdatedAt());

                } else {
                    System.out.println("값을 찾을 수 없습니다.");
                }

            } else {
                System.out.println(
                        "Please input Correct Command. ex) exit, insert, select, list, updateName, delete, getReference");
            }

        }

        CustomEntityManagerFactory.close();

    }

}
