package br.com.proenix.toolsChallenge.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DescriptionDto(
        BigDecimal value,
        LocalDateTime date,
        String establishment) {
}
