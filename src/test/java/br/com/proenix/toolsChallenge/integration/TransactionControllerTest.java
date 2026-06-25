package br.com.proenix.toolsChallenge.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import br.com.proenix.toolsChallenge.integration.configuration.BaseIntegrationTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

public class TransactionControllerTest extends BaseIntegrationTest {
    private static final String FUTURE_DATE = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private static final String PAST_DATE = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    private String validBody() {
        return """
                {
                  "transactionId": "1234567890",
                  "card": "1234567890123456",
                  "description": {
                    "value": "100.00",
                    "date": "%s",
                    "establishment": "Loja Teste"
                  },
                  "paymentMethod": {
                    "paymentType": "CASH",
                    "installments": 1
                  }
                }
                """.formatted(FUTURE_DATE);
    }

    @Test
    @DisplayName("Should create a transaction successfully")
    void shouldCreateTransactionWithApprovedStatus() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": {
                            "paymentType": "CASH",
                            "installments": 1
                          }
                        }
                        """.formatted(LocalDateTime.now().plusHours(1)
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
                .when()
                .post("/api/v1/transaction")
                .then()
                .statusCode(201)
                .body("description.authorizationCode",notNullValue())
                .body("description.nsu",notNullValue())
                .body("description.transactionStatus",equalTo("AUTHORIZED"));
    }

    @Test
    @DisplayName("Should create a transaction denied")
    void shouldCreateTransactionWithDeniedStatus() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "102030405066",
                          "card": "5353276659629461",
                          "description": {
                            "value": 10050.50,
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": {
                            "paymentType": "CASH",
                            "installments": "1"
                          }
                        }
                        """.formatted(LocalDateTime.now().plusHours(1)
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
                .when()
                .post("/api/v1/transaction")
                .then()
                .statusCode(201)
                .body("description.authorizationCode",nullValue())
                .body("description.nsu",nullValue())
                .body("description.transactionStatus",equalTo("DENIED"));
    }

    @Test
    @DisplayName("Should return 400 when transactionId is blank")
    void shouldReturn400WhenTransactionIdIsBlank() {
        given()
                .contentType("application/json")
                .body(validBody().replace("\"1234567890\"","\"\""))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when card is blank")
    void shouldReturn400WhenCardIsBlank() {
        given()
                .contentType("application/json")
                .body(validBody().replace("\"1234567890123456\"","\"\""))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when description is null")
    void shouldReturn400WhenDescriptionIsNull() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": null,
                          "paymentMethod": { "paymentType": "CASH", "installments": 1 }
                        }
                        """)
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when description value is null")
    void shouldReturn400WhenDescriptionValueIsNull() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": null,
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": { "paymentType": "CASH", "installments": 1 }
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when description value is negative")
    void shouldReturn400WhenDescriptionValueIsNegative() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "-10.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": { "paymentType": "CASH", "installments": 1 }
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when description date is in the past")
    void shouldReturn400WhenDescriptionDateIsInThePast() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": { "paymentType": "CASH", "installments": 1 }
                        }
                        """.formatted(PAST_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when establishment is blank")
    void shouldReturn400WhenEstablishmentIsBlank() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": ""
                          },
                          "paymentMethod": { "paymentType": "CASH", "installments": 1 }
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when paymentMethod is null")
    void shouldReturn400WhenPaymentMethodIsNull() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": null
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when paymentType is invalid")
    void shouldReturn400WhenPaymentTypeIsInvalid() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": { "paymentType": "INVALID", "installments": 1 }
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when installments is null")
    void shouldReturn400WhenInstallmentsIsNull() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "100.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": { "paymentType": "CASH", "installments": null }
                        }
                        """.formatted(FUTURE_DATE))
                .when().post("/api/v1/transaction")
                .then().statusCode(400);
    }

    @Sql({"/seeds/init.sql","/seeds/insert_transaction.sql"})
    @Test
    @DisplayName("Should reverse a transaction with authorized status")
    void shouldReverseTransactionWithAuthorizedStatus() {
        given()
                .when().post("/api/v1/transaction/102030405062/reversal")
                .then()
                .statusCode(201)
                .body("transactionId",equalTo("102030405062"))
                .body("description.transactionStatus",equalTo("REVERSED"));
    }

    @Test
    @DisplayName("Should return 400 when reversing a transaction not found")
    void shouldReturn400WhenReversingTransactionNotFound() {
        given()
                .when().post("/api/v1/transaction/NON-EXISTENT/reversal")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Should return 400 when reversing a transaction with non-authorized status")
    void shouldReturn400WhenReversingTransactionWithNonAuthorizedStatus() {
        given()
                .when().post("/api/v1/transaction/102030405068/reversal")
                .then()
                .statusCode(400);
    }
}
