package co.zip.candidate.userapi.entity;

import co.zip.candidate.userapi.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "account_number", nullable = false, length = 30, unique = true)
    private String accountNumber;

    @Column(name = "account_balance", nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

    @Column(name = "interest", nullable = false, precision = 4, scale = 2)
    private BigDecimal interest;

    @Column(name = "monthly_account_fee", nullable = false, precision = 4, scale = 2)
    private BigDecimal monthlyAccountFee;

    @Column(name = "minimal_weekly_repayment", nullable = false, precision = 8, scale = 2)
    private BigDecimal minimalWeeklyRepayment;

    @Column(name = "date_created", nullable = false)
    private OffsetDateTime dateCreated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
