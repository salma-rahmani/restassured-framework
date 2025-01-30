package tests.JsonPlaceholderTests;

import api.JsonPlaceholder.JsonPlaceholderAPI;
import api.account.AccountAPI;
import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceholderTests extends BaseTest {

    String title = "This is the title";
    String body = "This is the body of the entry";

    @Test
    public void testCreateEntry() {

        Response response = JsonPlaceholderAPI.createEntry(title, body, 1);
        // Assert
        assertEquals(201, response.statusCode(), "Request failed.");
        System.out.println("title: " + response.jsonPath().getString("title"));
        System.out.println("body: " + response.jsonPath().getString("body"));

    }
}
