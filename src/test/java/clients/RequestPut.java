
package clients;

import config.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPut implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        return given()
                .baseUri(Config.BASE_URL)
                .auth()
                .preemptive()
                .basic(info.getEmail(), info.getPassword())
                .contentType("application/json")
                .body(info.getBody())
                .when()
                .put(info.getEndpoint());
    }
}
