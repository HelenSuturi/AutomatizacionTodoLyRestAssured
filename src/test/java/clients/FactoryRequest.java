
package clients;

import java.util.Locale;

public final class FactoryRequest {

    private FactoryRequest() {
    }

    public static IRequest getRequest(String method) {

        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException(
                    "El método HTTP es obligatorio"
            );
        }

        switch (method.toUpperCase(Locale.ROOT)) {
            case "GET":
                return new RequestGet();

            case "POST":
                return new RequestPost();

            case "PUT":
                return new RequestPut();

            case "DELETE":
                return new RequestDelete();

            default:
                throw new IllegalArgumentException(
                        "Método HTTP no soportado: " + method
                );
        }
    }
}
