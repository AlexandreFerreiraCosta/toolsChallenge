package br.com.proenix.toolsChallenge.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import br.com.proenix.toolsChallenge.dto.general.PageDto;
import br.com.proenix.toolsChallenge.dto.transaction.DescriptionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.DescriptionDto;
import br.com.proenix.toolsChallenge.dto.transaction.PaymentMethodCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.PaymentMethodDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionCreateDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionDto;
import br.com.proenix.toolsChallenge.dto.transaction.TransactionFilterDto;
import br.com.proenix.toolsChallenge.entity.Description;
import br.com.proenix.toolsChallenge.entity.Transaction;
import br.com.proenix.toolsChallenge.enums.ETransactionStatus;
import br.com.proenix.toolsChallenge.exception.FailureBadRequestException;
import br.com.proenix.toolsChallenge.mapper.TransactionMapper;
import br.com.proenix.toolsChallenge.repository.TransactionRepository;
import br.com.proenix.toolsChallenge.service.TransactionServiceImpl;
import br.com.proenix.toolsChallenge.service.interfaces.ITransactionValidatorService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private List<ITransactionValidatorService> validatorServiceList;

    @Test
    @DisplayName("should create a transaction with an approved status")
    void shouldCreateTransactionWithApprovedStatus() {
        TransactionCreateDto createDto = new TransactionCreateDto(
                "TXN-001",
                "1234567890123456",
                new DescriptionCreateDto(BigDecimal.valueOf(500.00), LocalDateTime.now().plusMinutes(1), "Loja Teste"),
                new PaymentMethodCreateDto("AVISTA", 1));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(createDto.transactionId());
        transaction.setCard(createDto.card());

        TransactionDto expectedDto = new TransactionDto(
                createDto.transactionId(),
                createDto.card(),
                new DescriptionDto(BigDecimal.valueOf(500.00), createDto.description().date(), "Loja Teste", "1234567890", "AUTH123",
                        ETransactionStatus.AUTHORIZED),
                new PaymentMethodDto("AVISTA", 1));

        when(transactionMapper.convertTransactionCreateDtoToTransaction(createDto)).thenReturn(transaction);
        when(transactionRepository.findByTransactionId(createDto.transactionId())).thenReturn(Optional.empty());
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.convertTransactionToTransactionDto(transaction)).thenReturn(expectedDto);

        TransactionDto result = transactionService.createTransaction(createDto);

        assertThat(result).isNotNull();
        assertThat(result.transactionId()).isEqualTo(createDto.transactionId());
        assertThat(result.card()).isEqualTo(createDto.card());
        assertThat(result.description().transactionStatus()).isEqualTo(ETransactionStatus.AUTHORIZED);
    }

    @Test
    @DisplayName("should create a transaction with an denied status")
    void shouldCreateTransactionWithDeniedStatus() {
        TransactionCreateDto createDto = new TransactionCreateDto(
                "TXN-001",
                "1234567890123456",
                new DescriptionCreateDto(BigDecimal.valueOf(500.00), LocalDateTime.now().plusMinutes(1), "Loja Teste"),
                new PaymentMethodCreateDto("AVISTA", 1));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(createDto.transactionId());
        transaction.setCard(createDto.card());

        TransactionDto expectedDto = new TransactionDto(
                createDto.transactionId(),
                createDto.card(),
                new DescriptionDto(BigDecimal.valueOf(1500.00), createDto.description().date(), "Loja Teste", "1234567890", "AUTH123",
                        ETransactionStatus.DENIED),
                new PaymentMethodDto("AVISTA", 1));

        when(transactionMapper.convertTransactionCreateDtoToTransaction(createDto)).thenReturn(transaction);
        when(transactionRepository.findByTransactionId(createDto.transactionId())).thenReturn(Optional.empty());
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.convertTransactionToTransactionDto(transaction)).thenReturn(expectedDto);

        TransactionDto result = transactionService.createTransaction(createDto);

        assertThat(result).isNotNull();
        assertThat(result.transactionId()).isEqualTo(createDto.transactionId());
        assertThat(result.card()).isEqualTo(createDto.card());
        assertThat(result.description().transactionStatus()).isEqualTo(ETransactionStatus.DENIED);
    }

    @Test
    @DisplayName("should throw an exception for a transaction ID that is already registered")
    void shouldThrowExceptionForTransactionIdIsAlreadyRegistered() {
        TransactionCreateDto createDto = new TransactionCreateDto(
                "TXN-001",
                "1234567890123456",
                new DescriptionCreateDto(BigDecimal.valueOf(500.00), LocalDateTime.now().plusMinutes(1), "Loja Teste"),
                new PaymentMethodCreateDto("AVISTA", 1));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(createDto.transactionId());
        transaction.setCard(createDto.card());

        String message = "Já existe uma transação cadastrada com esse 'ID'";

        when(transactionMapper.convertTransactionCreateDtoToTransaction(createDto)).thenReturn(transaction);
        when(transactionRepository.findByTransactionId(createDto.transactionId())).thenReturn(Optional.of(transaction));
        when(messageSource.getMessage(anyString(),any(),any(Locale.class))).thenReturn(message);

        FailureBadRequestException result = assertThrows(FailureBadRequestException.class,
                () -> transactionService.createTransaction(createDto));

        assertThat(result).isNotNull();
        assertThat(message).isEqualTo(result.getMessage());
    }

    @Test
    @DisplayName("should reverse a transaction with authorized status")
    void shouldReverseTransactionWithAuthorizedStatus() {
        Description description = new Description();
        description.setValue(BigDecimal.valueOf(500.00));
        description.setDate(LocalDateTime.now());
        description.setEstablishment("Loja Teste");
        description.setNsu("123");
        description.setAuthorizationCode("AUTH");
        description.setTransactionStatus(ETransactionStatus.AUTHORIZED);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN-001");
        transaction.setCard("1234567890123456");
        transaction.setDescription(description);

        TransactionDto expectedDto = new TransactionDto(
                transaction.getTransactionId(),
                transaction.getCard(),
                new DescriptionDto(BigDecimal.valueOf(500.00), LocalDateTime.now(), "Loja Teste", "123", "AUTH",
                        ETransactionStatus.REVERSED),
                new PaymentMethodDto("AVISTA", 1));

        when(transactionRepository.findByTransactionId(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.convertTransactionToTransactionDto(transaction)).thenReturn(expectedDto);

        TransactionDto result = transactionService.reversalTransaction(transaction.getTransactionId());

        assertThat(result).isNotNull();
        assertThat(result.description().transactionStatus()).isEqualTo(ETransactionStatus.REVERSED);
    }

    @Test
    @DisplayName("should throw an exception when reversing a transaction not found")
    void shouldThrowExceptionWhenReversingTransactionNotFound() {
        String message = "Transação não encontrada";

        when(transactionRepository.findByTransactionId(anyString())).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(),any(),any(Locale.class))).thenReturn(message);

        FailureBadRequestException result = assertThrows(FailureBadRequestException.class,
                () -> transactionService.reversalTransaction("TXN-999"));

        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("should return transaction when status is REVERSED")
    void shouldReturnTransactionWhenStatusIsReversed() {
        Description description = new Description();
        description.setTransactionStatus(ETransactionStatus.REVERSED);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN-001");
        transaction.setCard("1234567890123456");
        transaction.setDescription(description);

        TransactionDto expectedDto = new TransactionDto(
                transaction.getTransactionId(),
                transaction.getCard(),
                new DescriptionDto(BigDecimal.valueOf(500.00), LocalDateTime.now(), "Loja Teste", "123", "AUTH",
                        ETransactionStatus.REVERSED),
                new PaymentMethodDto("AVISTA", 1));

        when(transactionRepository.findByTransactionId(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(transactionMapper.convertTransactionToTransactionDto(transaction)).thenReturn(expectedDto);

        TransactionDto result = transactionService.searchTransactionById(transaction.getTransactionId());

        assertThat(result).isNotNull();
        assertThat(result.description().transactionStatus()).isEqualTo(ETransactionStatus.REVERSED);
    }

    @Test
    @DisplayName("should throw an exception when searching transaction with non-reversed status")
    void shouldThrowExceptionWhenSearchingTransactionWithNonReversedStatus() {
        Description description = new Description();
        description.setTransactionStatus(ETransactionStatus.AUTHORIZED);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN-001");
        transaction.setCard("1234567890123456");
        transaction.setDescription(description);

        String message = "Transação não reembolsável";

        when(transactionRepository.findByTransactionId(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(messageSource.getMessage(anyString(),any(),any(Locale.class))).thenReturn(message);

        FailureBadRequestException result = assertThrows(FailureBadRequestException.class,
                () -> transactionService.searchTransactionById(transaction.getTransactionId()));

        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("should throw an exception when searching transaction not found")
    void shouldThrowExceptionWhenSearchingTransactionNotFound() {
        String message = "Transação não encontrada";

        when(transactionRepository.findByTransactionId(anyString())).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(),any(),any(Locale.class))).thenReturn(message);

        FailureBadRequestException result = assertThrows(FailureBadRequestException.class,
                () -> transactionService.searchTransactionById("TXN-999"));

        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("should throw an exception when reversing a transaction with non-authorized status")
    void shouldThrowExceptionWhenReversingTransactionWithNonAuthorizedStatus() {
        Description description = new Description();
        description.setValue(BigDecimal.valueOf(500.00));
        description.setDate(LocalDateTime.now());
        description.setEstablishment("Loja Teste");
        description.setNsu("123");
        description.setAuthorizationCode("AUTH");
        description.setTransactionStatus(ETransactionStatus.DENIED);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN-001");
        transaction.setCard("1234567890123456");
        transaction.setDescription(description);

        String message = "Estorno processado apenas para transações com status AUTORIZADO";

        when(transactionRepository.findByTransactionId(transaction.getTransactionId())).thenReturn(Optional.of(transaction));
        when(messageSource.getMessage(anyString(),any(),any(Locale.class))).thenReturn(message);

        FailureBadRequestException result = assertThrows(FailureBadRequestException.class,
                () -> transactionService.reversalTransaction(transaction.getTransactionId()));

        assertThat(result.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("should return paginated transactions with filter")
    void shouldReturnPaginatedTransactionsWithFilter() {
        TransactionFilterDto filterDto = new TransactionFilterDto("TXN-001", null, null, null, null);
        Pageable pageable = PageRequest.of(0,10);

        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN-001");
        transaction.setCard("1234567890123456");

        TransactionDto transactionDto = new TransactionDto(
                "TXN-001",
                "1234567890123456",
                new DescriptionDto(BigDecimal.valueOf(500.00), LocalDateTime.now(), "Loja Teste", "123", "AUTH",
                        ETransactionStatus.AUTHORIZED),
                new PaymentMethodDto("AVISTA", 1));

        Page<Transaction> transactionPage = new PageImpl<>(List.of(transaction), pageable, 1);

        PageDto<TransactionDto> expectedPageDto = new PageDto<>();
        expectedPageDto.setContent(List.of(transactionDto));
        expectedPageDto.setPageNumber(0);
        expectedPageDto.setPageSize(10);
        expectedPageDto.setTotalElements(1);
        expectedPageDto.setTotalPages(1);
        expectedPageDto.setLast(true);

        when(transactionRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(transactionPage);
        when(transactionMapper.convertPageTransactionToPageTransactionDto(transactionPage)).thenReturn(expectedPageDto);

        PageDto<TransactionDto> result = transactionService.searchTransactions(filterDto,pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().transactionId()).isEqualTo("TXN-001");
    }

    @Test
    @DisplayName("should return empty page when no transactions match filter")
    void shouldReturnEmptyPageWhenNoTransactionsMatchFilter() {
        TransactionFilterDto filterDto = new TransactionFilterDto(null, null, "Loja Inexistente", null, null);
        Pageable pageable = PageRequest.of(0,10);

        Page<Transaction> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        PageDto<TransactionDto> expectedPageDto = new PageDto<>();
        expectedPageDto.setContent(List.of());
        expectedPageDto.setTotalElements(0);
        expectedPageDto.setTotalPages(0);
        expectedPageDto.setLast(true);

        when(transactionRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(emptyPage);
        when(transactionMapper.convertPageTransactionToPageTransactionDto(emptyPage)).thenReturn(expectedPageDto);

        PageDto<TransactionDto> result = transactionService.searchTransactions(filterDto,pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
    }
}
