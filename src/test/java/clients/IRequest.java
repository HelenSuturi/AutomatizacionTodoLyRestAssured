package clients;

import io.restassured.response.Response;

public interface IRequest {
    Response send(RequestInfo info);
}
