package clients;

import basicRestAssured.BasicRestAssured;
import io.restassured.response.Response;

public class RequestDelete implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        return BasicRestAssured.request(info)
                .when()
                .delete(info.getEndpoint());
    }
}