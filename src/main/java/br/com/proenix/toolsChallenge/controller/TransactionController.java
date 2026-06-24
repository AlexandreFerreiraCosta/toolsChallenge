package br.com.proenix.toolsChallenge.controller;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transação")
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final ITransactionService iTransactionService;

    @PostMapping
    @Operation(summary = "Cria uma nova transação.")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody @Valid TransactionCreateDto transactionCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTransactionService.createTransaction(transactionCreateDto));
    }
}
