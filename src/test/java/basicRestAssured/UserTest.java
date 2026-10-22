
package basicRestAssured;

import clients.FactoryRequest;
import clients.RequestInfo;
import config.Config;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void crudUser() {

        // Datos del usuario nuevo
        String email = "qa." +
                UUID.randomUUID().toString().substring(0, 8) +
                "@example.com";

        String password = "TestApi2026!";
        String nombreUsuario = "Usuario QA Automatizado";

        Map<String, Object> body = new HashMap<>();
        body.put("Email", email);
        body.put("Password", password);
        body.put("FullName", nombreUsuario);

        // CREATE - POST
        RequestInfo infoPost = new RequestInfo(
                Config.USER_ENDPOINT,
                body,
                null,
                null
        );

        Response response = FactoryRequest
                .getRequest("POST")
                .send(infoPost);

        response.then().log().all();

        assertEquals(200, response.statusCode());
        assertEquals(email,
                response.jsonPath().getString("Email"));
        assertEquals(nombreUsuario,
                response.jsonPath().getString("FullName"));

        Number id = response.jsonPath().get("Id");
        assertNotNull(id, "No se recibió el ID del usuario");

        long userId = id.longValue();
        assertTrue(userId > 0);

        // UPDATE - PUT
        Map<String, Object> bodyUpdate = new HashMap<>();
        bodyUpdate.put("FullName", "Usuario QA Actualizado");

        RequestInfo infoPut = new RequestInfo(
                Config.USER_CURRENT_ENDPOINT,
                bodyUpdate,
                email,
                password
        );

        response = FactoryRequest
                .getRequest("PUT")
                .send(infoPut);

        response.then().log().all();

        assertEquals(200, response.statusCode());
        assertEquals("Usuario QA Actualizado",
                response.jsonPath().getString("FullName"));

        // DELETE - DELETE
        RequestInfo infoDelete = new RequestInfo(
                Config.USER_CURRENT_ENDPOINT,
                null,
                email,
                password
        );

        response = FactoryRequest
                .getRequest("DELETE")
                .send(infoDelete);

        response.then().log().all();

        assertTrue(
                response.statusCode() >= 200
                        && response.statusCode() < 300,
                "Error al eliminar el usuario: "
                        + response.statusCode()
        );
    }
}
