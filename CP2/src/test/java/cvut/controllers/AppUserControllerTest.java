package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.exception.BadCredentialException;
import cvut.exception.NotFoundException;
import cvut.model.AppUser;
import cvut.model.dto.AppUserInfoUpdateDTO;
import cvut.security.SecurityUtils;
import cvut.security.dto.AuthenticationRequest;
import cvut.security.dto.RegistrationRequest;
import cvut.services.AppUserService;
import cvut.services.AppUserServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


public class AppUserControllerTest extends TestHelper{

    @Autowired
    private AppUserController controller;

    @Autowired
    private AppUserServiceImpl service;


    private final AppUser user = Generator.generateUser();
    private final String rawPassword = user.getPassword();


    @WithAnonymousUser
    @Test
    public void registerSupportsAnonymousAccess() throws Exception {
        final AppUser toRegister = Generator.generateUser();
        mockMvc.perform(
                post("/api/register")
                        .content(toJson(toRegister))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Assertions.assertTrue(service.checkUserExisting(toRegister.getUsername()));
    }

    @WithAnonymousUser
    @Test
    public void registrationWithEmptyFields() throws Exception {
        RegistrationRequest request =  RegistrationRequest.builder()
                .build();
        mockMvc.perform(
                        post("/api/register")
                                .content(toJson(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        Assertions.assertFalse(service.checkUserExisting(request.getUsername()));
    }

    @WithAnonymousUser
    @Test
    public void registerAdminDoesNotRegister() throws Exception {
        final AppUser toRegister = Generator.generateUser();
        RegistrationRequest request =  RegistrationRequest.builder()
                        .username(toRegister.getUsername())
                        .password(toRegister.getPassword())
                        .email(toRegister.getEmail())
                        .firstname(toRegister.getFirstname())
                        .lastname(toRegister.getLastname())
                        .role("ROLE_ADMIN")
                        .build();

        mockMvc.perform(post("/api/register")
                                .content(toJson(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());

        Assertions.assertFalse(service.checkUserExisting(request.getUsername()));

    }



    @Test
    public void successfulAuthenticationSetsSecurityContext() {

        Authentication authentication = SecurityUtils.getAuthentication();
        assertNull(authentication);

        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("admin")
                .password("admin")
                .build();

        controller.authenticate(request);
        authentication = SecurityUtils.getAuthentication();
        assertNotNull(authentication);

        assertTrue(authentication.isAuthenticated());
        assertNotNull(SecurityContextHolder.getContext());
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertEquals(request.getUsername(), details.getUsername());
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    public void authenticateThrowsUserNotFoundExceptionForUnknownUsername() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username(user.getUsername())
                .password(rawPassword)
                .build();
        try {
            assertThrows(NotFoundException.class, () -> controller.authenticate(request));
        } finally {
            assertTrue(SecurityUtils.contextIsNull());
        }
    }

    @Test
    public void authenticateThrowsBadCredentialsForInvalidPassword() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("admin")
                .password(rawPassword)
                .build();
        try {
            assertThrows(BadCredentialException.class, () -> controller.authenticate(request));
        } finally {
            assertTrue(SecurityUtils.contextIsNull());
        }
    }

    @Test
    public void successfulLoginErasesPasswordInSecurityContextUser() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("admin")
                .password("admin")
                .build();
        controller.authenticate(request);
        assertNotNull(SecurityContextHolder.getContext());
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertNull(details.getPassword());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void updateUserWithAdmin() throws Exception {

        AppUser toUpdate = Generator.generateUser();
        service.save(toUpdate);

        toUpdate = service.findById(toUpdate.getId());

        AppUserInfoUpdateDTO dto = new AppUserInfoUpdateDTO();
        dto.setEmail(Generator.generateString()+"@gmail.com");

        mockMvc.perform(put("/api/profile/"+toUpdate.getId())
                                .with(pepaAdmin())
                                .content(toJson(dto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        assertEquals(toUpdate.getEmail(), dto.getEmail());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void updateUserWithUser() throws Exception {

        AppUser toUpdate = Generator.generateUser();
        service.save(toUpdate);

        toUpdate = service.findById(toUpdate.getId());

        AppUserInfoUpdateDTO dto = new AppUserInfoUpdateDTO();
        dto.setEmail(Generator.generateString()+"@gmail.com");


        mockMvc.perform(put("/api/profile/"+toUpdate.getId())
                        .with(pepaUserWithUsername(toUpdate.getUsername()))
                        .content(toJson(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        assertEquals(toUpdate.getEmail(), dto.getEmail());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void UserTryDeleteUser_canNotDelete_preconditionFailed() throws Exception {

        AppUser toDelete = Generator.generateUser();
        service.save(toDelete);

        toDelete = service.findById(toDelete.getId());

        mockMvc.perform(delete("/api/system/users/"+toDelete.getId())
                        .with(pepaUserWithUsername(toDelete.getUsername()))).andExpect(status().isPreconditionFailed());

        assertTrue(service.checkUserExisting(toDelete.getUsername()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Test
    public void adminDeletesUser() throws Exception {

        AppUser toDelete = Generator.generateUser();
        service.save(toDelete);

        toDelete = service.findById(toDelete.getId());

        //TRY WITH ROLE USER
        mockMvc.perform(delete("/api/system/users/"+toDelete.getId())
                .with(pepaUser())).andExpect(status().isPreconditionFailed());

        mockMvc.perform(delete("/api/system/users/"+toDelete.getId())
                .with(pepaAdmin())).andExpect(status().isOk());

        assertFalse(service.checkUserExisting(toDelete.getUsername()));
    }










}
