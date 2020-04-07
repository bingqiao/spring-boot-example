package com.bqiao.repo.jpa;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class RepositoryService {
    @Autowired
    private DeveloperRepo developerRepo;
}
