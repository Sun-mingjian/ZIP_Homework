package co.zip.candidate.userapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    String name;

    @Column(name = "email", nullable = false, length = 128, unique = true)
    String email;

    @Column(name = "monthly_salary", precision = 9, scale = 2, nullable = false)
    BigDecimal monthlySalary;

    @Column(name = "monthly_expense", precision = 9, scale = 2, nullable = false)
    BigDecimal monthlyExpenses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    List<Account> accountList = new ArrayList<>();
}
