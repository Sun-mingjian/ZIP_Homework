package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.service.AccountService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @LocalServerPort
    private int port;

    private static final String URL = "http://localhost:";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Fetch all accounts would return 200 with list of account details")
    public void testFetchAllAccounts() {
        String testAccountNumberOne = "1211211";
        String testAccountNumberTwo = "2321321";
        ResponseEntity<List<AccountDetails>> response = restTemplate.
                exchange(URL + port + "/api/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<AccountDetails> result = response.getBody();
        assertEquals(result.size(), 2);
        assertTrue(result.stream().anyMatch(i -> i.getAccountNumber().equals(testAccountNumberOne)));
        assertTrue(result.stream().anyMatch(i -> i.getAccountNumber().equals(testAccountNumberTwo)));
        assertTrue(result.stream().anyMatch(i -> i.getAccountType().equals("ZIP PAY")));
        assertTrue(result.stream().anyMatch(i -> i.getAccountType().equals("ZIP MONEY")));
    }
}
