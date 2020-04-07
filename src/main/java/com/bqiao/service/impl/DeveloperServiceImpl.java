package com.bqiao.service.impl;

import com.bqiao.domain.Developer;
import com.bqiao.repo.jpa.RepositoryService;
import com.bqiao.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeveloperServiceImpl implements DeveloperService {

	private final RepositoryService repoService;

	public DeveloperServiceImpl(
			RepositoryService repoService) {
		this.repoService = repoService;
	}

	@Override
	public List<Developer> getDevelopers() {
		return this.repoService.getDeveloperRepo().findAll();
	}

	@Override
	public Developer getDeveloper(String id) {
		Optional<Developer> found = findDeveloper(id);
		return found.get();
	}

	@Override
	public void deleteDeveloper(String id) {
		Optional<Developer> found = findDeveloper(id);
		this.repoService.getDeveloperRepo().delete(found.get());
	}

	@Override
	public Developer updateDeveloper(String id, Developer toUpdate) {
		Optional<Developer> found = findDeveloper(id);
		found.get().setEmail(toUpdate.getEmail());
		return this.repoService.getDeveloperRepo().save(found.get());
	}

	@Override
	public Developer createDeveloper(Developer toCreate) {
		if (toCreate == null || StringUtils.isEmpty(toCreate.getEmail())) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Invalid object");
		}
		return this.repoService.getDeveloperRepo().save(toCreate);
	}

	private Optional<Developer> findDeveloper(String id) {
		Optional<Developer> found = this.repoService.getDeveloperRepo().findById(id);
		if (found.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Object not found");
		}
		return found;
	}
}
