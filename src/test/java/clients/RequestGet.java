package clients;

import basicRestAssured.BasicRestAssured;
import io.restassured.response.Response;

public class RequestGet implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        return BasicRestAssured.request(info)
                .when()
                .get(info.getEndpoint());
    }
}