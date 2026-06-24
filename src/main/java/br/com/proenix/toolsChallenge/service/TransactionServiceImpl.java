package br.com.proenix.toolsChallenge.service;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.exception.FailureBadRequestException;
import br.com.proenix.toolsChallenge.mapper.TransactionMapper;
import br.com.proenix.toolsChallenge.repository.TransactionRepository;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionService;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionValidatorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private static final String IS_ALREADY_TRANSACTION_REGISTERED_WITH_ID = "is-already-transaction-registered-with-id";

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final MessageSource messageSource;

    private final List<ITransactionValidatorService> validatorServiceList;

    @Override
    public TransactionDto createTransaction(TransactionCreateDto transactionCreateDto) {
        Transaction transaction = transactionMapper.convertTransactionCreateDtoToTransaction(transactionCreateDto);

        validateTransactionId(transactionCreateDto.transactionId());
        validatorServiceList.forEach(validatorService -> validatorService.validator(transaction));

        return transactionMapper.convertTransactionToTransactionDto(transactionRepository.save(transaction));
    }

    private void validateTransactionId(String transactionId) {
        if (transactionRepository.findByTransactionId(transactionId).isPresent()) {
            throw new FailureBadRequestException(
                    messageSource.getMessage(IS_ALREADY_TRANSACTION_REGISTERED_WITH_ID,null,LocaleContextHolder.getLocale()));
        }
    }
}
