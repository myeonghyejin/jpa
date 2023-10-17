package com.example.jpa.service.impl;

import com.example.jpa.entity.UserEntity;
import com.example.jpa.exception.DuplicateException;
import com.example.jpa.exception.NotFoundException;
import com.example.jpa.factory.CustomEntityManagerFactory;
import com.example.jpa.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public void saveUser(UserEntity userEntity) {
        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        // EntityTransaction 으로 객체를 생성해서 트랜잭션 관리를 해도 되지만 EntityManager 만으로도 사용할 수 있음
        entityManager.getTransaction().begin();

        try {
            UserEntity foundUser = entityManager.find(UserEntity.class, userEntity.getEmail());

            // User 데이터를 추가하기 전에 같은 값이 있는지 체크
            if (foundUser != null) {
                // 기존에 동일한 데이터가 있다면 예외 호출
                throw new DuplicateException();
            }

            // Persistence Context 에 객체 추가
            entityManager.persist(userEntity);
            // 실제 DB 적용
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<UserEntity> getUser(String email) {
        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        // 조회만 할 경우 트랜잭션은 필요하지 않음

        System.out.println("checkpoint 1");
        UserEntity userEntity = entityManager.find(UserEntity.class, email);

        System.out.println("checkpoint 2");
        System.out.println(userEntity.getClass().getName());

        System.out.println("checkpoint 3");
        entityManager.close();

        System.out.println("checkpoint 4");
        return Optional.ofNullable(userEntity);
    }

    @Override
    public Optional<UserEntity> getReferenceUser(String email) {

        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        // 조회만 할 경우 트랜잭션은 필요하지 않음

        UserEntity userEntity;
        try {
            System.out.println("checkpoint 1");
            // getReference 메소드를 사용하면 엔티티 객체가 실제 사용되는 시점에 쿼리가 실행됨
            userEntity = entityManager.getReference(UserEntity.class, email);
            System.out.println("checkpoint 2");
            // 아래 코드가 주석처리되면 오류가 발생하고, 주석을 해제하고 실행하면 오류가 발생하지 않음
            userEntity.getName();
            System.out.println("checkpoint 3");
        } finally {
            entityManager.close();
            System.out.println("checkpoint 4");
        }
        /*
        콘솔 출력 내용을 보면 프록시 객체인 것을 확인할 수 있음
        만약 Entity 클래스를 final 로 선언했다면 프록시 객체를 생성할 수 없어서 오류가 발생함
         */
        System.out.println("checkpoint 5");
        System.out.println(userEntity.getClass().getName());

        /*
         세션이 이미 종료되어 있어서 값을 가져올 수 없음
         이 값을 사용하기 위해서는 entityManager 가 종료되기 전에 사용을 한번 해줘야 함
         */
        System.out.println("checkpoint 6");
        System.out.println(userEntity.getName());

        return Optional.ofNullable(userEntity);
    }

}
