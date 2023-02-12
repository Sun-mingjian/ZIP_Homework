package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    final String TEST_EMAIL = "Michael@gmail.com";
    final String TEST_EMAIL_TWO = "Jame@gmail.com";

    @BeforeEach
    public void setUp() {
        this.loadData();
    }

    @Test
    @DisplayName("Fetch user by email would work as expected")
    public void testFetchUserByEmail() {
        Optional<User> result = userRepository.findByEmail(TEST_EMAIL);
        assertTrue(result.isPresent());
        assertEquals(result.get().getEmail(), TEST_EMAIL);
    }

    @Test
    @DisplayName("Fetch user by non existing email would return nothing")
    public void testFetchUserByNonExistingEmail() {
        String testEmail = "notExisting@gmail.com";
        Optional<User> result = userRepository.findByEmail(testEmail);
        assertFalse(result.isPresent());
    }

    private void loadData() {
        Long testUserId = 1L;
        String testName = "Michael";
        BigDecimal monthlySalary = BigDecimal.valueOf(100000);
        BigDecimal monthlyExpense = BigDecimal.valueOf(20000);
        User testUser = User.builder().id(testUserId).name(testName).email(TEST_EMAIL).monthlySalary(monthlySalary).monthlyExpense(monthlyExpense).build();

        Long testUserTwoId = 2L;
        String testNameTwo = "Jame";
        BigDecimal monthlySalaryTwo = BigDecimal.valueOf(3000);
        BigDecimal monthlyExpenseTwo = BigDecimal.valueOf(2000);
        User testUserTwo = User.builder().id(testUserTwoId).name(testNameTwo).email(TEST_EMAIL_TWO).monthlySalary(monthlySalaryTwo).monthlyExpense(monthlyExpenseTwo).build();
        List<User> users = asList(testUser, testUserTwo);
        userRepository.saveAll(users);
    }
}
