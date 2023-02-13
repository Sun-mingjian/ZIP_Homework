package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.entity.Account;
import co.zip.candidate.userapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public List<AccountDetails> fetchAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        log.debug("There are {} accounts being fetched.", accounts.size());
        return accounts.stream().map(AccountAdaptor::adaptToAccountDetails).collect(Collectors.toList());
    }
}
