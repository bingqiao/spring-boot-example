package com.bqiao.service.impl;

import com.bqiao.domain.Developer;
import com.bqiao.repo.jpa.DeveloperRepo;
import com.bqiao.repo.jpa.RepositoryService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DeveloperServiceImplTests {

    private static final String TEST_EMAIL = "test-email";
    private static final String TEST_DEVELOPER_ID = "test-developer-id";

    @MockBean
    private DeveloperRepo developerRepo;

    @MockBean
    private RepositoryService repoService;

    private DeveloperServiceImpl underTest;

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Before
    public void beforeEach() {
        underTest = new DeveloperServiceImpl(repoService);
        given(repoService.getDeveloperRepo()).willReturn(developerRepo);
    }

    @Test
    public void givenOneDeveloper_whenGetDevelopers_thenReturnOneDeveloper() {
        List<Developer> developers = new ArrayList<>();
        Developer developer = new Developer();
        developers.add(developer);
        given(developerRepo.findAll()).willReturn(developers);
        List<Developer> retrieved = underTest.getDevelopers();
    }

    @Test
    public void givenNewDeveloper_whenCreateDeveloper_thenReturnCreatedDeveloper() {
        Developer toCreate = new Developer();

        toCreate.setEmail(TEST_EMAIL);

         given(developerRepo.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Developer created = underTest.createDeveloper(toCreate);
        then(developerRepo).should().save(created);
    }

    @Test
    public void givenNullDeveloper_whenCreateDeveloper_thenThrowException() {
        thrown.expect(ResponseStatusException.class);
        thrown.expectMessage("400 BAD_REQUEST \"Invalid object\"");
        underTest.createDeveloper(null);
    }

    @Test
    public void givenDeveloperWithInvalidEmail_whenCreateDeveloper_thenThrowException() {
        thrown.expect(ResponseStatusException.class);
        thrown.expectMessage("400 BAD_REQUEST \"Invalid object\"");
        Developer developer = new Developer();
        underTest.createDeveloper(developer);
    }

    @Test
    public void givenDeveloperAbsent_whenDeleteDeveloper_thenThrowException() {
        given(developerRepo.findById(TEST_DEVELOPER_ID)).willReturn(Optional.empty());
        thrown.expect(ResponseStatusException.class);
        thrown.expectMessage("404 NOT_FOUND \"Object not found\"");
        underTest.deleteDeveloper(TEST_DEVELOPER_ID);
    }

    @Test
    public void givenDeveloper_whenDeleteDeveloper_thenExpectSuccess() {
        Optional<Developer> developer = Optional.of(new Developer());
        given(developerRepo.findById(TEST_DEVELOPER_ID)).willReturn(developer);
        underTest.deleteDeveloper(TEST_DEVELOPER_ID);
        then(developerRepo).should().findById(TEST_DEVELOPER_ID);
        then(developerRepo).should().delete(developer.get());
    }
}
