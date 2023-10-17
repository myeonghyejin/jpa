package com.example.jpa.service;

import com.example.jpa.entity.DirectEntity;
import com.example.jpa.entity.IdentityEntity;
import com.example.jpa.entity.TableEntity;

import java.util.Optional;

public interface IdGenerationService {

    void insertDirectEntity(String name);

    Optional<DirectEntity> selectDirectEntity(String id);

    void insertIdentityEntity(String name);

    Optional<IdentityEntity> selectIdentityEntity(Long id);

    void insertTableEntity(String name);

    Optional<TableEntity> selectTableEntity(Long id);

    void insertSequenceEntity(String name);

    Optional<TableEntity> selectSequenceEntity(Long id);

}
