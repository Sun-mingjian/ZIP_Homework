package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserDetails;
import co.zip.candidate.userapi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAdaptorTest {

    @Test
    @DisplayName("Adapt to user details would work as expected")
    public void testAdaptToUserDetails() {
        Long testUserId = 100L;
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().id(testUserId).name(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        UserDetails userDetails = UserAdaptor.adaptToUserDetails(testUser);
        assertEquals(userDetails.getUserId(), testUserId);
        assertEquals(userDetails.getUserName(), testName);
        assertEquals(userDetails.getEmail(), testEmail);
        assertEquals(userDetails.getMonthlySalary(), monthlySalary);
        assertEquals(userDetails.getMonthlyExpense(), monthlyExpense);
    }

    @Test
    @DisplayName("Adapt to user would work as expected")
    public void testAdaptToUser() {
        String testName = "Michael";
        String testEmail = "Michael@gmail.com";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        UserDetails testUserDetails = UserDetails.builder().userName(testName).email(testEmail).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();
        User user = UserAdaptor.adaptToUserEntity(testUserDetails);
        assertEquals(user.getName(), testName);
        assertEquals(user.getEmail(), testEmail);
        assertEquals(user.getMonthlySalary(), monthlySalary);
        assertEquals(user.getMonthlyExpense(), monthlyExpense);
    }
}
