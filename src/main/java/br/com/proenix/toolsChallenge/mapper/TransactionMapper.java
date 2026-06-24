package br.com.proenix.toolsChallenge.mapper;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description.nsu", ignore = true)
    @Mapping(target = "description.authorizationCode", ignore = true)
    @Mapping(target = "description.transactionStatus", ignore = true)
    Transaction convertTransactionCreateDtoToTransaction(TransactionCreateDto transactionCreateDto);

    TransactionDto convertTransactionToTransactionDto(Transaction transaction);
}
