package co.zip.candidate.userapi.service;


import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.User;

public class UserAdaptor {

    public static UserDetails adaptToUserDetails(User user) {
        return UserDetails.builder()
                .userId(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .monthlyExpense(user.getMonthlyExpense())
                .monthlySalary(user.getMonthlySalary())
                .account(AccountAdaptor.adaptToAccountDetails(user.getAccount()))
                .build();
    }

    public static User adaptToUserEntity(UserDetails userDetails) {
        return User.builder().name(userDetails.getUserName()).email(userDetails.getEmail())
                .monthlyExpense(userDetails.getMonthlyExpense()).monthlySalary(userDetails.getMonthlySalary()).build();
    }
}
