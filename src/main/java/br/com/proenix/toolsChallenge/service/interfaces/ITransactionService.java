package br.com.proenix.toolsChallenge.service.interfaces;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.entity.Transaction;

public interface ITransactionService {
    TransactionDto createTransaction(TransactionCreateDto transactionCreateDto);

    Transaction searchTransaction(String transactionId);

    TransactionDto reversalTransaction(String transactionId);

}
