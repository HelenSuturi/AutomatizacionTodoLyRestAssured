
package config;

public final class Config {

    private Config() {
    }

    public static final String BASE_URL =
            "https://todo.ly";

    public static final String USER_ENDPOINT =
            "/api/user.json";

    // Endpoint para actualizar al usuario autenticado
    public static final String USER_CURRENT_ENDPOINT =
            "/api/user/0.json";

    public static final String PROJECT_ENDPOINT =
            "/api/projects.json";

    // Endpoint para consultar o eliminar un usuario por ID
    public static String userById(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID del usuario debe ser mayor que cero"
            );
        }

        return "/api/user/" + id + ".json";
    }

    // Endpoint para actualizar o eliminar un proyecto
    public static String projectById(long id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID del proyecto debe ser mayor que cero"
            );
        }

        return "/api/projects/" + id + ".json";
    }
}
