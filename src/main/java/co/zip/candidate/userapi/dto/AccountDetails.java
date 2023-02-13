package co.zip.candidate.userapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails implements Serializable {

    static final long serialVersionUID = 43L;

    @JsonProperty("account_number")
    String accountNumber;
    @JsonProperty("balance")
    BigDecimal balance;
    @JsonProperty("create_date")
    OffsetDateTime dateCreated;
}
