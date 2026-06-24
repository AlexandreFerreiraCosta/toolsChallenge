package br.com.proenix.toolsChallenge.service;

import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionValidatorService;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidatorService implements ITransactionValidatorService {
    private static final BigDecimal LIMIT = new BigDecimal("1000.00");

    @Override
    public void validator(Transaction transaction) {
        if (isLimit(transaction.getDescription().getValue())) {
            transaction.getDescription().setTransactionStatus(ETransactionStatus.DENIED);
            return;
        }

        transaction.getDescription().setNsu(generateNsu());
        transaction.getDescription().setAuthorizationCode(generateAuthorizationCode());
        transaction.getDescription().setTransactionStatus(ETransactionStatus.AUTHORIZED);
    }

    private boolean isLimit(BigDecimal valueTransaction) {
        return valueTransaction != null && valueTransaction.compareTo(LIMIT) >= 0;
    }

    private String generateNsu() {
        long nsu = ThreadLocalRandom.current().nextLong(100_000_000_000L,1_000_000_000_000L);
        return String.valueOf(nsu);
    }

    private String generateAuthorizationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(9);

        for (int i = 0; i < 9; i++) {
            int index = ThreadLocalRandom.current().nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
}
