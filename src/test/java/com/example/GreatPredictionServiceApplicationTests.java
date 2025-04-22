package com.example;

import com.example.dto.PredictionDto;
import com.example.dto.PredictionResponse;
import com.example.repository.PredictionRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PredictionRepository predictionRepository;

    @AfterEach
    public void tearDown() {
        predictionRepository.deleteAll();
    }


    @Test
    void accepts_a_prediction_creation_message() throws JSONException {
        var predictionToCreate = new JSONObject()
            .put("predictedWinner", "Brentford")
            .put("userId", 1)
            .put("matchId", 1)
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

        // Now use the location header to retrieve it.
        // We don't know the id in advance, so the controller is not likely to be faking a response.
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
    void a_prediction_can_be_updated() throws JSONException {
        // given ...
        var prediction = new JSONObject()
                .put("predictedWinner", "Brentford")
                .put("userId", 1)
                .put("matchId", 1)
                ;

        var creationResponse = createPrediction(prediction);

        // when ...
        var predictionPatch = new JSONObject().put("predictedWinner", "Chelsea");

        // use API to update it
        PredictionResponse patchResponse = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(predictionPatch.toString())
                .patch(creationResponse.header("Location"))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PredictionResponse.class)
                ;
        // fetch it from API
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
        then(retrievedPredictionDto.predictedWinner()) .isEqualTo(predictionPatch.getString("predictedWinner"));
        then(retrievedPredictionDto.predictionId())    .isEqualTo(patchResponse.predictionDto().predictionId());
        then(retrievedPredictionDto.userId())          .isEqualTo(prediction.getLong("userId"));
        then(retrievedPredictionDto.matchId())         .isEqualTo(prediction.getLong("matchId"));
    }

    @Test
    void a_closed_prediction_cannot_be_updated() throws JSONException {
        // given ...
        long endedMatchId = 2L;

        var prediction = new JSONObject()
                .put("predictedWinner", "Brentford")
                .put("userId", 1)
                .put("matchId", endedMatchId)
                ;

        var creationResponse = createPrediction(prediction);
        creationResponse.then().log().all();

        // when ...
        var predictionPatch = new JSONObject().put("predictedWinner", "Chelsea");

        // use API to update it
        PredictionResponse predictionResponse = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(predictionPatch.toString())
                .patch(creationResponse.header("Location"))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .extract()
                .as(PredictionResponse.class)
                ;
        // fetch it from API
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
        then(retrievedPredictionDto.predictedWinner()) .isEqualTo(prediction.getString("predictedWinner"));
        then(retrievedPredictionDto.userId())          .isEqualTo(prediction.getLong("userId"));
        then(retrievedPredictionDto.matchId())         .isEqualTo(endedMatchId);
    }

    @Test
    void can_find_all_predictions_for_a_given_user() throws JSONException {
        // given ...
        long userId  = 1L;
        long matchId = 1L;
        var prediction1 = new JSONObject()
                .put("predictedWinner", "Brentford")
                .put("userId", userId)
                .put("matchId", matchId);
                ;
        var prediction2 = new JSONObject()
                .put("predictedWinner", "Chelsea")
                .put("userId", userId)
                .put("matchId", matchId);
                ;
        createPrediction(prediction1);
        createPrediction(prediction2);

        // when ...
        PredictionDto[] retrievedPredictions = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .get("/user/%d/predictions".formatted(userId))
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(PredictionDto[].class)
                ;

        then(retrievedPredictions.length).isEqualTo(2);

        final int first  = 0;
        then(retrievedPredictions[first].predictedWinner()). isEqualTo(prediction1.getString( "predictedWinner"));
        then(retrievedPredictions[first].userId()).          isEqualTo(prediction1.getLong( "userId"));
        then(retrievedPredictions[first].matchId()).         isEqualTo(prediction1.getLong( "matchId"));

        final int second = 1;
        then(retrievedPredictions[second].predictedWinner()). isEqualTo(prediction2.getString( "predictedWinner"));
        then(retrievedPredictions[second].userId()).          isEqualTo(prediction2.getLong( "userId"));
        then(retrievedPredictions[second].matchId()).         isEqualTo(prediction2.getLong( "matchId"));

        then(retrievedPredictions[second].predictionId()). isNotEqualTo(retrievedPredictions[first].predictionId());
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
