package basicRestAssured;

import clients.RequestInfo;
import config.Config;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class BasicRestAssured {

    private BasicRestAssured() {
    }

    public static RequestSpecification request(RequestInfo info) {

        RequestSpecification specification = given()
                .baseUri(Config.BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();

        if (info.getEmail() != null
                && !info.getEmail().isBlank()
                && info.getPassword() != null
                && !info.getPassword().isBlank()) {

            specification = specification
                    .auth()
                    .preemptive()
                    .basic(info.getEmail(), info.getPassword());
        }

        return specification;
    }
}