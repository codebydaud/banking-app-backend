package com.codebydaud.training.banking_app;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.util.JsonUtil;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.Matchers;

public class AdminControllerTests extends BaseTest {

    @Test
    public void test_login_with_valid_credentials() throws Exception {
        LoginAdmin();
    }

    @Test
    public void test_login_with_invalid_email() throws Exception {
        val loginRequest = new LoginRequest("admin1@gmail.com", "admin123");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test_login_with_invalid_password() throws Exception {
        val loginRequest = new LoginRequest("admin@gmail.com", "admin1234");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void test_login_with_missing_email() throws Exception {
        val loginRequest = new LoginRequest("", "admin123");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test_login_with_missing_password() throws Exception {
        val loginRequest = new LoginRequest("admin@gmail.com", "");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void test_view_all_accounts_with_valid_credentials() throws Exception {
        String token = LoginAdmin();

        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/accounts")
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com")
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_view_all_accounts_with_invalid_credentials() throws Exception {
        val userDetails = createAndLoginUser();

        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/accounts")
                        .header("Authorization", "Bearer " + userDetails.get("token"))
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails.get("accountNumber"))
                                .authorities(new SimpleGrantedAuthority("customer"))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void test_view_user_details_by_account_number_with_valid_credentials() throws Exception {

        String token = LoginAdmin();
        val user = userRepository.findByAccountAccountNumber("ACC-78902424");
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/account/ACC-78902424")
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com")
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Ali")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("ali@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is("3496666666")));
    }

    @Test
    public void test_view_user_details_by_account_number_with_invalid_credentials() throws Exception {

        val userDetails = createAndLoginUser();
        val user = userRepository.findByAccountAccountNumber("ACC-78902424");
        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/account/ACC-78902424")
                        .header("Authorization", "Bearer " + userDetails.get("token"))
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails.get("accountNumber"))
                                .authorities(new SimpleGrantedAuthority("customer"))))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void test_logout_with_valid_token() throws Exception {
        String token = LoginAdmin();

        val response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/logout")
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com")
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andReturn().getResponse();

        val redirectedUrl = response.getRedirectedUrl();
        if (redirectedUrl != null) {
            Assertions.assertEquals("/logout", redirectedUrl);
            mockMvc.perform(MockMvcRequestBuilders
                            .get(redirectedUrl))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } else {
            Assertions.fail("Redirected URL is null");
        }
    }

    @Test
    public void test_logout_with_invalid_token() throws Exception {
        createAndLoginUser();

        val user = createAndRegisterUser();
        val accountNumber = userRepository
                .findByEmail(user.getEmail())
                .get()
                .getAccount()
                .getAccountNumber();

        val userDetails = tokenService.loadUserByUsername(accountNumber);
        val token = tokenService.generateToken(userDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/logout")
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com")
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void test_logout_without_login() throws Exception {

        val userDetails = tokenService.loadUserByUsername("admin@gmail.com");
        val token = tokenService.generateToken(userDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/logout")
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin@gmail.com")
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void test_logout_with_malformed_token() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/logout")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void test_logout_without_authorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users/logout"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
