package br.com.proenix.toolsChallenge.mapper;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {ETransactionStatus.class})
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description.transactionStatus", expression = "java(ETransactionStatus.PROCESSING)")
    Transaction convertTransactionCreateDtoToTransaction(TransactionCreateDto transactionCreateDto);

    TransactionDto convertTransactionToTransactionDto(Transaction transaction);
}
