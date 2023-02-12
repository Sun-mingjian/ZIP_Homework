package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.dto.ErrorMessage;
import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerServiceTest {

    @LocalServerPort
    private int port;

    private static final String URL = "http://localhost:";

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Get existing user by Id would return 200")
    public void testGetUserById() {
        int testUserId = 1;
        ResponseEntity<UserDetails> response = restTemplate.
                getForEntity(URL + port + "/api/user/" + testUserId, UserDetails.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDetails result = response.getBody();
        assertEquals(result.getUserName(), "Michael");
        assertEquals(result.getEmail(), "Michael@gmail.com");
        assertEquals("20000.00", result.getMonthlySalary().toString());
        assertEquals("10000.00", result.getMonthlyExpense().toString());
    }

    @Test
    @DisplayName("Get non-existing user by Id would return 404")
    public void testGetNoneExistingUserById() {
        int testUserId = 100;
        ResponseEntity<UserDetails> response = restTemplate.
                getForEntity(URL + port + "/api/user/" + testUserId, UserDetails.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Fetch all users would return 200 with list of user details")
    public void testFetchAllUsers() {
        ResponseEntity<List<UserDetails>> response = restTemplate.
                exchange(URL + port + "/api/users", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<UserDetails> result = response.getBody();
        assertEquals(result.size(), 5);
        assertTrue(result.stream().anyMatch(i -> i.getEmail().equals("Peter@gmail.com")));
        assertTrue(result.stream().anyMatch(i -> i.getEmail().equals("Jane@gmail.com")));
        assertTrue(result.stream().anyMatch(i -> i.getEmail().equals("Jesse@gmail.com")));
        assertTrue(result.stream().anyMatch(i -> i.getEmail().equals("Tim@gmail.com")));
        assertTrue(result.stream().anyMatch(i -> i.getEmail().equals("Michael@gmail.com")));
        assertEquals(result.stream().filter(i -> i.getUserId().equals(3L)).findFirst().get().getMonthlyExpense().toString(), "2.00");
        assertEquals(result.stream().filter(i -> i.getUserId().equals(2L)).findFirst().get().getMonthlySalary().toString(), "2000.00");
    }

    @Test
    @DisplayName("Create new user would return 201 as expected")
    public void testCreateNewUser() {
        String testName = "Harden";
        String testEmail = "Harden@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userId(6l).userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        ResponseEntity<UserDetails> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, UserDetails.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        UserDetails result = response.getBody();
        assertEquals(result.getUserName(), testUserDetails.getUserName());
        assertEquals(result.getEmail(), testUserDetails.getEmail());
        assertEquals(result.getMonthlySalary().toString(), "100000");
        assertEquals(result.getMonthlyExpense().toString(), "20000");

        ResponseEntity<List<UserDetails>> newResponse = restTemplate.
                exchange(URL + port + "/api/users", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(newResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(newResponse.getBody().size(), 6);
    }

    @Test
    @DisplayName("Create new user with existing email would return 400")
    public void testCreateNewUserWithExistingEmail() {
        String testName = "Jesse";
        String testEmail = "Jesse@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        ResponseEntity<ErrorMessage> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, ErrorMessage.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        ErrorMessage errorMessage = response.getBody();
        assertTrue(errorMessage.getDescription().contains("Sorry, this email has been associated with user"));

        ResponseEntity<List<UserDetails>> newResponse = restTemplate.
                exchange(URL + port + "/api/users", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(newResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(newResponse.getBody().size(), 5);
    }

    @Test
    @DisplayName("Create new user would return 400 when email is null")
    public void testCreateNewUserWithNullEmail() {
        String invalidEmailErrorMsg = "email is required and must not be blank";
        String testName = "Harden";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userId(6l).userName(testName).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidEmailErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when email is invalid")
    public void testCreateNewUserWithInvalidEmail() {
        String invalidEmailErrorMsg = "The email address is invalid";
        String testName = "Harden";
        String testEmail = "hahahah";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userId(6l).userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidEmailErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when user name is null")
    public void testCreateNewUserWithNullName() {
        String invalidNameErrorMsg = "username is required and must not be blank";
        String testEmail = "Jesse@gmail.com";
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().email(testEmail).monthlyExpense(monthlyExpense).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidNameErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when monthly salary is null")
    public void testCreateNewUserWithNullSalary() {
        String invalidMonthlySalaryErrorMsg = "monthly_salary is required";
        String testName = "Harden";
        String testEmail = "Harden@gmail.com";
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlyExpense(monthlyExpense).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidMonthlySalaryErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when monthly salary is negative")
    public void testCreateNewUserWithNegativeSalary() {
        String invalidMonthlySalaryErrorMsg = "monthly_salary must be greater than 0";
        String testName = "Harden";
        String testEmail = "Harden@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(-20000);
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlySalary(monthlySalary).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidMonthlySalaryErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when monthly expense is null")
    public void testCreateNewUserWithNullMonthlyExpense() {
        String invalidMonthlyExpenseErrorMsg = "monthly_expense is required";
        String testName = "Harden";
        String testEmail = "Harden@gmail.com";
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidMonthlyExpenseErrorMsg));
    }

    @Test
    @DisplayName("Create new user would return 400 when monthly expense is negative")
    public void testCreateNewUserWithNegativeMonthlyExpense() {
        String invalidMonthlyExpenseErrorMsg = "monthly_expense must be greater than 0";
        String testName = "Harden";
        String testEmail = "Harden@gmail.com";
        BigDecimal monthlyExpense = BigDecimal.valueOf(-20000);
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlyExpense(monthlyExpense).build();
        ResponseEntity<Map> response = restTemplate.
                postForEntity(URL + port + "/api/user", testUserDetails, Map.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Map<String, List<String>> errorMessages = response.getBody();
        List<String> errors = errorMessages.get("errors");
        assertTrue(errors.contains(invalidMonthlyExpenseErrorMsg));
    }

}
