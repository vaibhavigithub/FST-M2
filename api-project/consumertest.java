package pactliveproject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class consumertest {
    Map<String, String> headers = new HashMap<>();
    String createUser = "/api/users";
    @Pact(provider = "UserProvider", consumer = "UserConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

// Create request JSON
        DslPart bodySentCreateUser = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .path(createUser)
                .method("POST")
                .headers(headers)
                .body(bodySentCreateUser)
                .willRespondWith()
                .status(200)
                .body(bodySentCreateUser)
                .toPact();
    }
        @Test
        @PactTestFor(providerName = "UserProvider", port = "8282")
        public void runTest() {
         //apiurl
            RestAssured.baseURI = "http://localhost:8282";

            Map<String, Object> requestbody = new HashMap<>();
            requestbody.put("id", 10);
            requestbody.put("firstName", "vai");
            requestbody.put("lastName", "reddy");
           requestbody.put("email", "vaireddy@mail.com");
         Response response =   RestAssured.given().headers(headers).when().body(requestbody).post(createUser);
            response.then().statusCode(200);









        }


}
