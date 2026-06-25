package br.com.proenix.toolsChallenge.specification;

import br.com.proenix.toolsChallenge.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    private TransactionSpecification() {
    }

    public static Specification<Transaction> hasTransactionId(String transactionId) {
        return (root,query,
                criteriaBuilder) -> (transactionId == null) ? null : criteriaBuilder.equal(root.get("transactionId"),transactionId);
    }

    public static Specification<Transaction> hasCard(String card) {
        return (root,query,criteriaBuilder) -> (card == null) ? null : criteriaBuilder.equal(root.get("card"),card);
    }

    public static Specification<Transaction> hasEstablishment(String establishment) {
        return (root,query,criteriaBuilder) -> (establishment == null)
                ? null
                : criteriaBuilder.equal(root.get("description").get("establishment"),establishment);
    }

    public static Specification<Transaction> hasTransactionStatus(String transactionStatus) {
        return (root,query,criteriaBuilder) -> (transactionStatus == null)
                ? null
                : criteriaBuilder.equal(root.get("description").get("transactionStatus"),transactionStatus);
    }

    public static Specification<Transaction> hasPaymentType(String paymentType) {
        return (root,query,criteriaBuilder) -> (paymentType == null)
                ? null
                : criteriaBuilder.equal(root.get("paymentMethod").get("paymentType"),paymentType);
    }
}
