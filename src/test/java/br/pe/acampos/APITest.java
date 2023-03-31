package br.pe.acampos;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class APITest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
        .when()
                .get("/todo")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured.given()
                .log().all()
                .body("""
                        {
                            "task": "Teste via Api",
                            "dueDate": "2023-12-30"
                        }
                        """)
                .contentType(ContentType.JSON)
        .when()
                .post("/todo")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
                .log().all()
                .body("""
                        {
                            "task": "Teste via Api",
                            "dueDate": "2020-12-30"
                        }
                        """)
                .contentType(ContentType.JSON)
        .when()
                .post("/todo")
        .then()
                .statusCode(400)
                .body("message", Matchers.is("Due date must not be in past"))
        ;
    }
}
