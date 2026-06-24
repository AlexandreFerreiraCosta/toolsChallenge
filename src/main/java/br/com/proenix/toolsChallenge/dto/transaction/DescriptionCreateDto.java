package br.com.proenix.toolsChallenge.dto.transaction;

import br.com.proenix.toolsChallenge.util.deserializer.MoneyDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import tools.jackson.databind.annotation.JsonDeserialize;

public record DescriptionCreateDto(
        @JsonDeserialize(using = MoneyDeserializer.class)
        @NotNull(message = "{not-null}")
        @Positive(message = "{positive}") BigDecimal value,

        @NotNull(message = "{not-null}")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime date,

        @NotBlank(message = "{not-blank}") String establishment) {
}
