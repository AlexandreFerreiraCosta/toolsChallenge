package br.com.proenix.toolsChallenge.unit.specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.specification.TransactionSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionSpecificationTest {
    @Mock
    private Root<Transaction> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Test
    @DisplayName("hasTransactionId should return predicate when transactionId is provided")
    void hasTransactionIdShouldReturnPredicateWhenTransactionIdIsProvided() {
        Path<Object> path = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("transactionId")).thenReturn(path);
        when(criteriaBuilder.equal(path,"TXN-001")).thenReturn(predicate);

        Predicate result = TransactionSpecification.hasTransactionId("TXN-001").toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNotNull().isEqualTo(predicate);
        verify(criteriaBuilder).equal(path,"TXN-001");
    }

    @Test
    @DisplayName("hasTransactionId should return null when transactionId is null")
    void hasTransactionIdShouldReturnNullWhenTransactionIdIsNull() {
        Predicate result = TransactionSpecification.hasTransactionId(null).toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("hasCard should return predicate when card is provided")
    void hasCardShouldReturnPredicateWhenCardIsProvided() {
        Path<Object> path = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("card")).thenReturn(path);
        when(criteriaBuilder.equal(path,"1234567890123456")).thenReturn(predicate);

        Predicate result = TransactionSpecification.hasCard("1234567890123456").toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNotNull().isEqualTo(predicate);
        verify(criteriaBuilder).equal(path,"1234567890123456");
    }

    @Test
    @DisplayName("hasCard should return null when card is null")
    void hasCardShouldReturnNullWhenCardIsNull() {
        Predicate result = TransactionSpecification.hasCard(null).toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("hasEstablishment should return predicate when establishment is provided")
    void hasEstablishmentShouldReturnPredicateWhenEstablishmentIsProvided() {
        Path<Object> descriptionPath = mock(Path.class);
        Path<Object> establishmentPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("description")).thenReturn(descriptionPath);
        when(descriptionPath.get("establishment")).thenReturn(establishmentPath);
        when(criteriaBuilder.equal(establishmentPath,"Loja Teste")).thenReturn(predicate);

        Predicate result = TransactionSpecification.hasEstablishment("Loja Teste").toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNotNull().isEqualTo(predicate);
        verify(criteriaBuilder).equal(establishmentPath,"Loja Teste");
    }

    @Test
    @DisplayName("hasEstablishment should return null when establishment is null")
    void hasEstablishmentShouldReturnNullWhenEstablishmentIsNull() {
        Predicate result = TransactionSpecification.hasEstablishment(null).toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("hasTransactionStatus should return predicate when transactionStatus is provided")
    void hasTransactionStatusShouldReturnPredicateWhenTransactionStatusIsProvided() {
        Path<Object> descriptionPath = mock(Path.class);
        Path<Object> statusPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("description")).thenReturn(descriptionPath);
        when(descriptionPath.get("transactionStatus")).thenReturn(statusPath);
        when(criteriaBuilder.equal(statusPath,"AUTHORIZED")).thenReturn(predicate);

        Predicate result = TransactionSpecification.hasTransactionStatus("AUTHORIZED").toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNotNull().isEqualTo(predicate);
        verify(criteriaBuilder).equal(statusPath,"AUTHORIZED");
    }

    @Test
    @DisplayName("hasTransactionStatus should return null when transactionStatus is null")
    void hasTransactionStatusShouldReturnNullWhenTransactionStatusIsNull() {
        Predicate result = TransactionSpecification.hasTransactionStatus(null).toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("hasPaymentType should return predicate when paymentType is provided")
    void hasPaymentTypeShouldReturnPredicateWhenPaymentTypeIsProvided() {
        Path<Object> paymentMethodPath = mock(Path.class);
        Path<Object> paymentTypePath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("paymentMethod")).thenReturn(paymentMethodPath);
        when(paymentMethodPath.get("paymentType")).thenReturn(paymentTypePath);
        when(criteriaBuilder.equal(paymentTypePath,"CASH")).thenReturn(predicate);

        Predicate result = TransactionSpecification.hasPaymentType("CASH").toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNotNull().isEqualTo(predicate);
        verify(criteriaBuilder).equal(paymentTypePath,"CASH");
    }

    @Test
    @DisplayName("hasPaymentType should return null when paymentType is null")
    void hasPaymentTypeShouldReturnNullWhenPaymentTypeIsNull() {
        Predicate result = TransactionSpecification.hasPaymentType(null).toPredicate(root,query,criteriaBuilder);

        assertThat(result).isNull();
    }
}
