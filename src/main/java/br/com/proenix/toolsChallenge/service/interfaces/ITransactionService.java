package br.com.proenix.toolsChallenge.service.interfaces;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;

public interface ITransactionService {
    TransactionDto createTransaction(TransactionCreateDto transactionCreateDto);

    TransactionDto searchTransactionById(String transactionId);

    TransactionDto reversalTransaction(String transactionId);

}
