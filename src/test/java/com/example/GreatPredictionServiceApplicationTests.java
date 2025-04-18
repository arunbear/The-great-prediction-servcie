package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GreatPredictionServiceApplicationTests {

    @LocalServerPort
    private int localServerPort;

    @BeforeEach
    public void setUp() {
        RestAssured.port = localServerPort;
    }

    @Test
    void accepts_a_prediction_creation_message() throws JSONException {
        var predictionToCreate = new JSONObject()
            .put("predictedWinner", "Brentford");

        createPrediction(predictionToCreate)
            .then()
            .log().body()
            .statusCode(equalTo(HttpStatus.SC_CREATED))
        ;
    }

    @Test
    void contextLoads() {
    }

    Response createPrediction(JSONObject jsonObject) {
        return RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post("/prediction")
                ;
    }

}
