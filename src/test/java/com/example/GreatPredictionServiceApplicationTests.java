package com.example;

import com.example.dto.PredictionDto;
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

import static org.assertj.core.api.BDDAssertions.then;
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
            .put("predictedWinner", "Brentford")
            .put("userId", 1)
            ;

        createPrediction(predictionToCreate)
            .then()
            .log().body()
            .statusCode(equalTo(HttpStatus.SC_CREATED))
        ;
    }

    @Test
    void can_create_a_prediction() throws JSONException {
        var predictionToCreate = new JSONObject()
                .put("predictedWinner", "Brentford")
                .put("userId", 1)
                .put("matchId", 1)
                ;

        var creationResponse = createPrediction(predictionToCreate);

        // now use the location header to retrieve it
        PredictionDto retrievedPredictionDto = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(creationResponse.header("Location"))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PredictionDto.class)
                ;
        then(retrievedPredictionDto.predictionId())
                .isGreaterThan(0);
        then(retrievedPredictionDto.predictedWinner())
                .isEqualTo(predictionToCreate.getString("predictedWinner"));
        then(retrievedPredictionDto.userId())
                .isEqualTo(predictionToCreate.getLong("userId"));
        then(retrievedPredictionDto.matchId())
                .isEqualTo(predictionToCreate.getLong("matchId"));
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
