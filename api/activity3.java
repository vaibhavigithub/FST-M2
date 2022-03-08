package activities;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class activity3 {



        // Declare request specification
        RequestSpecification requestSpec;
        // Declare response specification
        ResponseSpecification responseSpec;

        @BeforeClass
        public void setUp() {
            // Create request specification
            requestSpec = new RequestSpecBuilder()
                    // Set content type
                    .setContentType(ContentType.JSON)
                    // Set base URL
                    .setBaseUri("https://petstore.swagger.io/v2/pet")
                    // Build request specification
                    .build();

            responseSpec = new ResponseSpecBuilder()
                    // Check status code in response
                    .expectStatusCode(200)
                    // Check response content type
                    .expectContentType("application/json")
                    // Check if response contains name property
                    .expectBody("status", equalTo("alive"))
                    // Build response specification
                    .build();
        }

        @DataProvider
        public Object[][] petInfoProvider() {
            // Setting parameters to pass to test case
            Object[][] testData = new Object[][] {
                    { 77803, "vai", "alive" },
                    { 77125, "reddy", "alive" }
            };
            return testData;
        }

        @Test(priority=1)
        // Test case using a DataProvider

        public void addPets() {
            String reqBody = "{\"id\": 77803, \"name\": \"vai\", \"status\": \"alive\"}";
            Response response = given().spec(requestSpec) // Use requestSpec
                    .body(reqBody) // Send request body
                    .when().post(); // Send POST request

          String  reqBody1 = "{\"id\": 77125, \"name\": \"reddy\", \"status\": \"alive\"}";
            Response response1 = given().spec(requestSpec) // Use requestSpec
                    .body(reqBody1) // Send request body
                    .when().post(); // Send POST request

            // Assertions
            response.then().spec(responseSpec); // Use responseSpec
            response1.then().spec(responseSpec);
        }

        // Test case using a DataProvider
        @Test(dataProvider = "petInfoProvider", priority=2)
        public void getPets(int id, String name, String status) {
            Response response = given().spec(requestSpec) // Use requestSpec
                    .pathParam("petId", id) // Add path parameter
                    .when().get("/{petId}"); // Send GET request

            // Print response
            System.out.println(response.asPrettyString());
            // Assertions
            response.then()
                    .spec(responseSpec) // Use responseSpec
                    .body("name", equalTo(name)); // Additional Assertion
        }

        // Test case using a DataProvider
        @Test(dataProvider = "petInfoProvider", priority=3)
        public void deletePets(int id, String name, String status) {
            Response response = given().spec(requestSpec) // Use requestSpec
                    .pathParam("petId", id) // Add path parameter
                    .when().delete("/{petId}"); // Send GET request

            // Assertions
            response.then().body("code", equalTo(200));

        }

    }
