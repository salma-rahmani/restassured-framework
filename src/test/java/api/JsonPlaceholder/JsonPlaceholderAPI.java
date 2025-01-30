package api.JsonPlaceholder;

import api.common.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JsonPlaceholderAPI {

    public static Response createEntry(String title, String body, int userId) {
        String requestBody = "{ \"title\": \"" + title + "\", \"body\": \"" + body + "\", \"userId\": " + userId + " }";
        return RestAssured
                .given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(EndPoints.CREATE_AN_ENTRY);
    }

}
