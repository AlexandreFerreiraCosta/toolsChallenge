package br.com.proenix.toolsChallenge.controller;

import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionService;
import br.com.proenix.toolsChallenge.util.deserializer.RemoveSpecialCharacterDeserializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.annotation.JsonDeserialize;

@Tag(name = "Transação")
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final ITransactionService iTransactionService;

    @PostMapping
    @Operation(summary = "Cria uma nova transação.")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody @Valid TransactionCreateDto transactionCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTransactionService.createTransaction(transactionCreateDto));
    }

    @PostMapping("/{transactionId}/reversal")
    @Operation(summary = "Estorna transação.")
    public ResponseEntity<TransactionDto> reversalTransaction(
            @PathVariable("transactionId") @JsonDeserialize(using = RemoveSpecialCharacterDeserializer.class)
            @NotBlank(message = "{not-blank}")
            @Length(max = 20) String transactionId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTransactionService.reversalTransaction(transactionId));
    }
}
