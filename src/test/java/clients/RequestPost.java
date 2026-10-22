package clients;

import basicRestAssured.BasicRestAssured;
import io.restassured.response.Response;

public class RequestPost implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        return BasicRestAssured.request(info)
                .body(info.getBody())
                .when()
                .post(info.getEndpoint());
    }
}