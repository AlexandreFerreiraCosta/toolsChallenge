package br.com.proenix.toolsChallenge.service;

import br.com.proenix.toolsChallenge.dto.general.PageDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionFilterDto;
import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import br.com.proenix.toolsChallenge.exception.FailureBadRequestException;
import br.com.proenix.toolsChallenge.mapper.TransactionMapper;
import br.com.proenix.toolsChallenge.repository.TransactionRepository;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionService;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionValidatorService;
import br.com.proenix.toolsChallenge.specification.TransactionSpecification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private static final String IS_ALREADY_TRANSACTION_REGISTERED_WITH_ID = "is-already-transaction-registered-with-id";
    private static final String TRANSACTION_NOT_FOUND = "transaction-not-found";
    private static final String REVERSAL_PROCESSED_FOR_TRANSACTIONS_AUTHORIZED_STATUS = "reversal-processed-for-transactions-authorized-status";
    private static final String TRANSACTION_NON_REFUNDABLE = "transaction-non-refundable";

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

    @Override
    @Transactional(readOnly = true)
    public TransactionDto searchTransactionById(String transactionId) {
        Transaction transaction = searchTransaction(transactionId);
        if (!transaction.getDescription().getTransactionStatus().equals(ETransactionStatus.REVERSED)) {
            throw new FailureBadRequestException(
                    messageSource.getMessage(TRANSACTION_NON_REFUNDABLE,null,LocaleContextHolder.getLocale()));
        }

        return transactionMapper.convertTransactionToTransactionDto(searchTransaction(transactionId));
    }

    @Override
    public TransactionDto reversalTransaction(String idTransaction) {
        Transaction transaction = searchTransaction(idTransaction);
        validateReversalTransaction(transaction);

        transaction.getDescription().setTransactionStatus(ETransactionStatus.REVERSED);

        return transactionMapper.convertTransactionToTransactionDto(transactionRepository.save(transaction));
    }

    @Override
    public PageDto<TransactionDto> searchTransactions(TransactionFilterDto transactionFilterDto,Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAll(
                TransactionSpecification.hasTransactionId(transactionFilterDto.transactionId())
                        .and(TransactionSpecification.hasCard(transactionFilterDto.card()))
                        .and(TransactionSpecification.hasEstablishment(transactionFilterDto.establishment()))
                        .and(TransactionSpecification.hasTransactionStatus(transactionFilterDto.transactionStatus()))
                        .and(TransactionSpecification.hasPaymentType(transactionFilterDto.paymentType())),
                pageable);

        return transactionMapper.convertPageTransactionToPageTransactionDto(transactionPage);
    }

    @Transactional(readOnly = true)
    private Transaction searchTransaction(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId).orElseThrow(() -> new FailureBadRequestException(
                messageSource.getMessage(TRANSACTION_NOT_FOUND,null,LocaleContextHolder.getLocale())));
    }

    @Transactional(readOnly = true)
    private void validateTransactionId(String transactionId) {
        if (transactionRepository.findByTransactionId(transactionId).isPresent()) {
            throw new FailureBadRequestException(
                    messageSource.getMessage(IS_ALREADY_TRANSACTION_REGISTERED_WITH_ID,null,LocaleContextHolder.getLocale()));
        }
    }

    private void validateReversalTransaction(Transaction transaction) {
        if (!transaction.getDescription().getTransactionStatus().equals(ETransactionStatus.AUTHORIZED)) {
            throw new FailureBadRequestException(
                    messageSource.getMessage(REVERSAL_PROCESSED_FOR_TRANSACTIONS_AUTHORIZED_STATUS,null,LocaleContextHolder.getLocale()));
        }
    }
}
