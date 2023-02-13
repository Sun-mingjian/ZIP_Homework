package co.zip.candidate.userapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Data
public class AccountDetails implements Serializable {

    static final long serialVersionUID = 43L;

    @JsonProperty("account_type")
    @NotBlank(message = "account_type is required and must not be blank")
    private String accountType;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("interest")
    private BigDecimal interest;

    @JsonProperty("monthly_account_fee")
    private BigDecimal monthlyAccountFee;

    @JsonProperty("minimal_weekly_repayment")
    private BigDecimal minimalWeeklyRepayment;

    @JsonProperty("create_date")
    private OffsetDateTime dateCreated;
}
