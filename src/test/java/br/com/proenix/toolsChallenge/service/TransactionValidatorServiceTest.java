package br.com.proenix.toolsChallenge.service;

import br.com.proenix.toolsChallenge.entity.Description;
import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorServiceTest {

    @InjectMocks
    private TransactionValidatorService transactionValidatorService;

    @Test
    @DisplayName("should validator with approved status")
    void shouldValidatorWithApprovedStatus(){
        Description description = new Description();
        description.setValue(BigDecimal.valueOf(500.00));
        description.setDate(LocalDateTime.now());
        description.setEstablishment("Loja 1");

        Transaction transaction = new Transaction();
        transaction.setDescription(description);

        transactionValidatorService.validator(transaction);

        assertThat(transaction.getDescription().getNsu()).isNotNull();
        assertThat(transaction.getDescription().getAuthorizationCode()).isNotNull();
        assertThat(transaction.getDescription().getTransactionStatus()).isEqualTo(ETransactionStatus.AUTHORIZED);
    }

    @Test
    @DisplayName("should validator with denied status")
    void shouldValidatorWithDeniedStatus(){
        Description description = new Description();
        description.setValue(BigDecimal.valueOf(1500.00));
        description.setDate(LocalDateTime.now());
        description.setEstablishment("Loja 1");

        Transaction transaction = new Transaction();
        transaction.setDescription(description);

        transactionValidatorService.validator(transaction);

        assertThat(transaction.getDescription().getNsu()).isNull();
        assertThat(transaction.getDescription().getAuthorizationCode()).isNull();
        assertThat(transaction.getDescription().getTransactionStatus()).isEqualTo(ETransactionStatus.DENIED);
    }
}