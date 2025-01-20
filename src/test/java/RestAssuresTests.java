import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

class TrelloAPITests {

    private static final String KEY = "123456test";
    private static final String TOKEN = "test123456";
 private static final String baseURI = "https://api.trello.com/1";
    String idList = "12345654321";

    @Test
    public void verifyCredentials() {


        Response response = RestAssured.given()
                .baseUri(baseURI)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .get("/members/me")
                .then()
                .statusCode(200)
                .body("username", notNullValue())
                .body("id", notNullValue())
                .extract()
                .response();

    }

        @Test
        public void createNewTrelloBoard() {

            Response response = given()
                    .baseUri(baseURI)
                    .header("Content-Type", "application/json")
                    .queryParam("key", KEY)
                    .queryParam("token", TOKEN)
                    .queryParam("name", "Test RestAssured Board")
                    .queryParam("desc", "This is a test board created with RestAssured according to the requirements of Task 2")
                    .post("/boards/");

            var responseBody = response.getBody();
            Assertions.assertNotNull(responseBody.jsonPath().getString("id"));
            Assertions.assertNotNull(responseBody.jsonPath().getString("name"));

        }

        @Test
    public void CrateNewTrelloList() {
        RestAssured.baseURI = "https://api.trello.com/1";

            String boardID = "12345678";
            Response response = RestAssured.given()
                    .baseUri(baseURI)
                    .header("Content-Type", "application/json")
                    .queryParam("key", KEY)
                    .queryParam("token", TOKEN)
                    .queryParam("name", "My first List on a Board")
                    .queryParam("idBoard", boardID)
                    .post("/lists");

            var responseBody = response.getBody();

            Assert.assertEquals("Status code check", 200, response.getStatusCode());
            Assertions.assertNotNull(responseBody.jsonPath().getString("id"));
            Assertions.assertNotNull(responseBody.jsonPath().getString("name"));
    }

    @Test
    public void CrateNewCard() {
        RestAssured.baseURI = "https://api.trello.com/1";


        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "My first Card on a List")
                .queryParam("idList", idList)
                .post("/cards");

        var responseBody = response.getBody();

        Assert.assertEquals("Status code check", 200, response.getStatusCode());
        Assertions.assertNotNull(responseBody.jsonPath().getString("id"));
        Assertions.assertNotNull(responseBody.jsonPath().getString("name"));
    }

    @Test
    public void UpdateACard() {
        RestAssured.baseURI = "https://api.trello.com/1";

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", "My first Card on a List")
                .queryParam("idList", idList)
                .put("/cards/12345678");

        var responseBody = response.getBody();

        Assert.assertEquals("Status code check", 200, response.getStatusCode());
        Assertions.assertNotNull(responseBody.jsonPath().getString("idCard"));
        Assertions.assertNotNull(responseBody.jsonPath().getString("name"));
    }
}