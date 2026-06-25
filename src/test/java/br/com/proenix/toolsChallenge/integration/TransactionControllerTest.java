package br.com.proenix.toolsChallenge.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import br.com.proenix.toolsChallenge.integration.configuration.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionControllerTest extends BaseIntegrationTest {

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
                        """.formatted(java.time.LocalDateTime.now().plusHours(1)
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
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
                          "transactionId": "1234567890",
                          "card": "1234567890123456",
                          "description": {
                            "value": "1500.00",
                            "date": "%s",
                            "establishment": "Loja Teste"
                          },
                          "paymentMethod": {
                            "paymentType": "CASH",
                            "installments": 1
                          }
                        }
                        """.formatted(java.time.LocalDateTime.now().plusHours(1)
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))))
                .when()
                .post("/api/v1/transaction")
                .then()
                .statusCode(201)
                .body("description.authorizationCode",nullValue())
                .body("description.nsu",nullValue())
                .body("description.transactionStatus",equalTo("DENIED"));
    }
}
