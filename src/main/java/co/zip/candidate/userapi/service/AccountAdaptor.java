package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDetails;
import co.zip.candidate.userapi.entity.Account;

public class AccountAdaptor {

    public static AccountDetails adaptToAccountDetails(Account account) {
        return AccountDetails.builder().balance(account.getBalance())
                .dateCreated(account.getDateCreated()).accountNumber(account.getAccountNumber()).build();
    }
}
