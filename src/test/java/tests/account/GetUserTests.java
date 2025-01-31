package tests.account;

import api.account.AccountAPI;
import api.bookstore.BookStoreAPI;
import auth.TokenGenerator;
import auth.TokenManager;
import base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserTests extends BaseTest {

    @Test
    public void getUnauthorizedUserByUUID() {
        // Arrange
        String userId = "bb06d06b-aeb3-4af9-a9c8-aec38547d3ec";

        // Act
        Response response = AccountAPI.getUserByUUIDWithoutToken(userId);

        // Assert
        assertEquals(401, response.statusCode(), "Request failed.");
    }

    @Test
    public void getAuthorizedUserByUUID() {

        // Arrange
        String userId = "bb06d06b-aeb3-4af9-a9c8-aec38547d3ec";
        String userToken = TokenManager.getGeneralUserToken();

        // Act
        Response response = AccountAPI.getUserByUUIDWithToken(userId, userToken);

        // Assert
        assertEquals(200, response.statusCode(), "Request failed.");

    }

    @Test
    public void createUser() {
        // Arrange
        String userName = DataGenerator.generateRandomUserName();
        String password = DataGenerator.generateRandomPassword();
        // Act
        Response response = AccountAPI.createUser(userName, password);
        // Assert
        assertEquals(201, response.statusCode(), "Request failed.");
        System.out.println( "userId: " + response.jsonPath().getString("userID") );
        System.out.println( "Username: " + response.jsonPath().getString("username") );
        System.out.println( "password: " + password );

        String userUUID = response.jsonPath().getString("userID");
        String userToken = TokenGenerator.getToken(userName, password);

        Response getUserInfo = AccountAPI.getUserByUUIDWithToken(userUUID, userToken);
        System.out.println( getUserInfo.asPrettyString() );

        Response deleteUser = AccountAPI.deleteUser(userUUID, userToken);
        System.out.println("user deleted!");
        System.out.println("Status Code: " + deleteUser.statusCode());
    }

    @Test
    public void updateUser() {
        // Arrange
        final String userName = DataGenerator.generateRandomUserName();
        final String password = DataGenerator.generateRandomPassword();
        final String ISBN = "9781449325862";
        final String UPDATE_ISBN = "9781449337711";

        // Act
        Response response = AccountAPI.createUser(userName, password);

        // Assert
        assertEquals(201, response.statusCode(), "Request failed.");
        System.out.println( "userId: " + response.jsonPath().getString("userID") );
        System.out.println( "Username: " + response.jsonPath().getString("username") );
        System.out.println( "password: " + password );

        String userUUID = response.jsonPath().getString("userID");
        String userToken = TokenGenerator.getToken(userName, password);

        Response getUserInfo = AccountAPI.getUserByUUIDWithToken(userUUID, userToken);
        System.out.println( "User Info Before Update: " + getUserInfo.asPrettyString() );

        Response postBookToUser = BookStoreAPI.assignBookToUser(userUUID, ISBN, userToken);
        System.out.println( postBookToUser.asPrettyString() );

//        Response updateUser = AccountAPI.updateUser(userUUID, UPDATE_ISBN, userToken);
//        assertEquals(201, response.statusCode(), "Request failed.");
//        System.out.println( updateUser.asPrettyString() );

        Response getUpdatedUserInfo = AccountAPI.getUserByUUIDWithToken(userUUID, userToken);
        System.out.println( getUpdatedUserInfo.asPrettyString() );

        Response deleteUser = AccountAPI.deleteUser(userUUID, userToken);
        System.out.println("user deleted!");
        System.out.println("Status Code: " + deleteUser.statusCode());

    }

}