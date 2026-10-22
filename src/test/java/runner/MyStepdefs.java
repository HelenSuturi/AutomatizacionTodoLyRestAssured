package runner;

import clients.FactoryRequest;
import clients.RequestInfo;
import config.Config;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyStepdefs {

    private Response respuesta;

    // Datos del usuario
    private String email;
    private final String password = "TestApi2026!";
    private long usuarioId;

    private final String nombreOriginal =
            "Usuario QA Automatizado";

    private final String nombreActualizado =
            "Usuario QA Actualizado";

    // ==========================================
    // HOOKS DE CUCUMBER
    // ==========================================

    @Before
    public void beforeCucumber() {
        System.out.println("Before Cucumber");
    }

    @After
    public void afterCucumber() {
        System.out.println("After Cucumber");
    }

    @Before("@Regression")
    public void beforeCucumberRegression() {
        System.out.println("Before Cucumber Regression");
    }

    // ==========================================
    // CREAR USUARIO
    // ==========================================

    @When("envio los datos para crear un usuario")
    public void envioDatosCrearUsuario() {

        email = "qa." + Instant.now().toEpochMilli()
                + "@example.com";

        Map<String, String> usuario = new HashMap<>();
        usuario.put("Email", email);
        usuario.put("Password", password);
        usuario.put("FullName", nombreOriginal);

        // POST sin autenticación, según la API de Todo.ly
        RequestInfo info = new RequestInfo(
                Config.USER_ENDPOINT,
                usuario,
                null,
                null
        );

        respuesta = FactoryRequest
                .getRequest("POST")
                .send(info);

        System.out.println("=== CREAR USUARIO ===");
        System.out.println("HTTP: " + respuesta.statusCode());
        System.out.println("Respuesta: " + respuesta.asString());
    }

    @Then("la respuesta debe confirmar que el usuario fue creado")
    public void verificarUsuarioCreado() {

        verificarRespuestaExitosa();

        String emailRespuesta =
                respuesta.jsonPath().getString("Email");

        assertEquals(
                email,
                emailRespuesta,
                "El correo devuelto no coincide"
        );
    }

    @And("la respuesta debe contener el identificador del usuario")
    public void verificarIdentificadorUsuario() {

        Number id = respuesta.jsonPath().get("Id");

        assertNotNull(
                id,
                "La respuesta no contiene el ID del usuario"
        );

        usuarioId = id.longValue();

        assertTrue(
                usuarioId > 0,
                "El ID debe ser mayor que cero"
        );

        System.out.println("ID del usuario creado: " + usuarioId);
    }

    // ==========================================
    // ACTUALIZAR USUARIO
    // ==========================================



    @When("actualizo los datos del usuario creado")
    public void actualizarDatosUsuario() {

        assertNotNull(email, "No existe un correo de usuario");
        assertNotNull(password, "No existe una contraseña de usuario");

        Map<String, String> datos = new HashMap<>();
        datos.put("FullName", nombreActualizado);

        RequestInfo info = new RequestInfo(
                Config.USER_CURRENT_ENDPOINT,
                datos,
                email,
                password
        );

        respuesta = FactoryRequest
                .getRequest("PUT")
                .send(info);

        System.out.println("=== ACTUALIZAR USUARIO ===");
        System.out.println("HTTP: " + respuesta.statusCode());
        System.out.println("Respuesta: " + respuesta.asString());
    }


    @Then("la respuesta debe confirmar que el usuario fue actualizado")
    public void verificarUsuarioActualizado() {

        verificarRespuestaExitosa();

        assertTrue(
                !respuesta.asString().isBlank(),
                "La API no devolvió datos para verificar la actualización"
        );

        String nombreRespuesta =
                respuesta.jsonPath().getString("FullName");

        assertNotNull(
                nombreRespuesta,
                "La respuesta no contiene el campo FullName"
        );

        assertEquals(
                nombreActualizado,
                nombreRespuesta,
                "El nombre del usuario no fue actualizado"
        );
    }


    // ==========================================
    // ELIMINAR USUARIO
    // ==========================================


    @When("elimino el usuario creado")
    public void eliminarUsuarioCreado() {

        assertNotNull(email, "No existe un correo de usuario");
        assertNotNull(password, "No existe una contraseña de usuario");

        RequestInfo info = new RequestInfo(
                Config.USER_CURRENT_ENDPOINT,
                null,
                email,
                password
        );

        respuesta = FactoryRequest
                .getRequest("DELETE")
                .send(info);

        System.out.println("=== ELIMINAR USUARIO ===");
        System.out.println("Endpoint: "
                + Config.USER_CURRENT_ENDPOINT);
        System.out.println("HTTP: " + respuesta.statusCode());
        System.out.println("Respuesta: " + respuesta.asString());
    }


    @Then("la respuesta debe confirmar que el usuario fue eliminado")
    public void verificarUsuarioEliminado() {

        verificarRespuestaExitosa();

        System.out.println(
                "La API aceptó la solicitud de eliminación del usuario."
        );
    }

    // ==========================================
    // VALIDACIÓN COMÚN
    // ==========================================

    private void verificarRespuestaExitosa() {

        assertNotNull(
                respuesta,
                "No se recibió respuesta de la API"
        );

        assertTrue(
                respuesta.statusCode() >= 200
                        && respuesta.statusCode() < 300,
                "La operación falló. HTTP: "
                        + respuesta.statusCode()
                        + ". Respuesta: "
                        + respuesta.asString()
        );
    }
}