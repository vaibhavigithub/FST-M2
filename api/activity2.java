package activities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class activity2 {


        // Set base URL
        final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

        @Test(priority=1)
        public void addNewUserFromFile() throws IOException {
            // Import JSON file
            FileInputStream inputJSON = new FileInputStream("src/test/java/activities/activity2fileip.json");
            // Read JSON file as String
            String reqBody = new String(inputJSON.readAllBytes());

            Response response =
                    given().contentType(ContentType.JSON) // Set headers
                            .body(reqBody) // Pass request body from file
                            .when().post(ROOT_URI); // Send POST request

            inputJSON.close();

            // Assertion
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("9946"));
        }

        @Test(priority=2)
        public void getUserInfo() {
            // Import JSON file to write to
            File outputJSON = new File("src/test/java/activities/userGETResponse.json");

            Response response =
                    given().contentType(ContentType.JSON) // Set headers
                            .pathParam("username", "vampired") // Pass request body from file
                            .when().get(ROOT_URI + "/{username}"); // Send POST request

            // Get response body
            String resBody = response.getBody().asPrettyString();

            try {
                // Create JSON file
                outputJSON.createNewFile();
                // Write response body to external file
                FileWriter writer = new FileWriter(outputJSON.getPath());
                writer.write(resBody);
                writer.close();
            } catch (IOException excp) {
                excp.printStackTrace();
            }

            // Assertion
            response.then().body("id", equalTo(9946));
            response.then().body("username", equalTo("vampired"));
            response.then().body("firstName", equalTo("vai"));
            response.then().body("lastName", equalTo("reddy"));
            response.then().body("email", equalTo("vaireddy@mail.com"));
            response.then().body("password", equalTo("password123"));
            response.then().body("phone", equalTo("9812763450"));
        }

        @Test(priority=3)
        public void deleteUser() throws IOException {
            Response response =
                    given().contentType(ContentType.JSON) // Set headers
                            .pathParam("username", "vampired") // Add path parameter
                            .when().delete(ROOT_URI + "/{username}"); // Send POST request

            // Assertion
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("vampired"));
        }
    }

