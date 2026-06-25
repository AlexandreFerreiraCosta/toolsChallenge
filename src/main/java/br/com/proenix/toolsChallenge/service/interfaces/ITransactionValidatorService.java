package br.com.proenix.toolsChallenge.service.interfaces;

import br.com.proenix.toolsChallenge.entity.Transaction;

public interface ITransactionValidatorService {
    void validator(Transaction transaction);
}
