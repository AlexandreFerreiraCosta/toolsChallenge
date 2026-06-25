package br.com.proenix.toolsChallenge.mapper;

import br.com.proenix.toolsChallenge.dto.general.PageDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.entity.Transaction;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "description.nsu", ignore = true)
    @Mapping(target = "description.authorizationCode", ignore = true)
    @Mapping(target = "description.transactionStatus", ignore = true)
    Transaction convertTransactionCreateDtoToTransaction(TransactionCreateDto transactionCreateDto);

    @Named("convertTransactionToTransactionDto")
    TransactionDto convertTransactionToTransactionDto(Transaction transaction);

    @IterableMapping(qualifiedByName = "convertTransactionToTransactionDto")
    List<TransactionDto> convertListTransactionToListTransactionDto(List<Transaction> transactions);

    @Mapping(target = "content", expression = "java(convertListTransactionToListTransactionDto(transactionPage.getContent()))")
    @Mapping(target = "pageNumber", expression = "java(transactionPage.getNumber())")
    @Mapping(target = "pageSize", expression = "java(transactionPage.getSize())")
    @Mapping(target = "totalElements", expression = "java(transactionPage.getTotalElements())")
    @Mapping(target = "totalPages", expression = "java(transactionPage.getTotalPages())")
    @Mapping(target = "last", expression = "java(transactionPage.isLast())")
    PageDto<TransactionDto> convertPageTransactionToPageTransactionDto(Page<Transaction> transactionPage);
}
