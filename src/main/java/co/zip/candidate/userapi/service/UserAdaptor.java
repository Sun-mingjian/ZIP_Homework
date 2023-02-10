package co.zip.candidate.userapi.service;


import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.User;

import java.util.stream.Collectors;

public class UserAdaptor {

    public static UserDetails adaptToUserDetails(User user) {
        return UserDetails.builder()
                .userId(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .monthlyExpense(user.getMonthlyExpenses())
                .monthlySalary(user.getMonthlySalary())
                .accounts(
                        user.getAccountList().stream().map(AccountAdaptor::adaptToAccountDetails).collect(Collectors.toList()))
                .build();
    }

    public static User adaptToUserEntity(UserDetails userDetails) {
        return User.builder().name(userDetails.getUserName()).email(userDetails.getEmail())
                .monthlyExpenses(userDetails.getMonthlyExpense()).monthlySalary(userDetails.getMonthlySalary()).build();
    }
}
