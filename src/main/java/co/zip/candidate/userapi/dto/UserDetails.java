package co.zip.candidate.userapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class UserDetails implements Serializable {
    @JsonProperty("userid")
    private Long userId;

    @JsonProperty("username")
    @NotBlank(message = "username is required and must not be blank")
    private String userName;

    @JsonProperty("email")
    @NotBlank(message = "email is required and must not be blank")
    @Email(message = "The email address is invalid.")
    private String email;

    @JsonProperty("monthly_salary")
    @NotNull(message = "monthly_salary is required")
    @Digits(integer = 7, fraction = 2, message = "monthly_salary is out of bounds (<7 digits>.<2 digits> expected)")
    @Positive(message = "monthly_salary must be greater than 0")
    private BigDecimal monthlySalary;

    @JsonProperty("monthly_expense")
    @NotNull(message = "monthly_expense is required")
    @Digits(integer = 7, fraction = 2, message = "monthly_expense is out of bounds (<7 digits>.<2 digits> expected)")
    @Positive(message = "monthly_expense must be greater than 0")
    private BigDecimal monthlyExpense;

    @JsonProperty("accounts")
    List<AccountDetails> accounts;

}
