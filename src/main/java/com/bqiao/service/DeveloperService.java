package com.bqiao.service;

import com.bqiao.domain.Developer;

import java.util.List;

public interface DeveloperService {
    List<Developer> getDevelopers();
    Developer getDeveloper(String id);
    void deleteDeveloper(String id);
    Developer updateDeveloper(String id, Developer toUpdate);
    Developer createDeveloper(Developer toCreate);
}
