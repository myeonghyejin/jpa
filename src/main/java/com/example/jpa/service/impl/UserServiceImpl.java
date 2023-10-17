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

        UserEntity userEntity = entityManager.find(UserEntity.class, email);

        // if() 객체에 값이 들어왔는지?

        entityManager.close();

        return Optional.ofNullable(userEntity);
    }

    @Override
    public void updateUserName(String email, String newName) {
        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        entityManager.getTransaction().begin();

        try {

            UserEntity userEntity = entityManager.find(UserEntity.class, email);

            if (userEntity == null) {
                throw new NotFoundException();
            }

            userEntity.changeName(newName);

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<UserEntity> getUserList() {

        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        try {
            //entityManager.getTransaction().begin();

            TypedQuery<UserEntity> query = entityManager.createQuery(
                    "select u from UserEntity u", UserEntity.class);
            List<UserEntity> userEntities = query.getResultList();

            /*
            아래 코드로 대체 가능
            Query query = entityManager.createQuery("select u from UserEntity u");
            List userEntities = query.getResultList();
             */

            //entityManager.getTransaction().commit();

            return userEntities;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return null;
    }

    @Override
    public void deleteUser(String email) {

        EntityManager entityManager = CustomEntityManagerFactory.createEntityManger();

        entityManager.getTransaction().begin();

        try{
            UserEntity userEntity = entityManager.find(UserEntity.class, email);
            if(userEntity==null){
                throw new NotFoundException();
            }
            entityManager.remove(userEntity);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }

    }

}
