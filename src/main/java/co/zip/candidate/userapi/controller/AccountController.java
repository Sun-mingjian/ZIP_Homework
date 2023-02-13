package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDetails>> fetchAllAccounts() {
        log.debug("Fetching all account details");
        List<AccountDetails> accounts = accountService.fetchAllAccounts();
        return ResponseEntity.ok().body(accounts);
    }
}

