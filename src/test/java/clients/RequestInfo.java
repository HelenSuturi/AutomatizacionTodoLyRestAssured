package clients;

public class RequestInfo {

    private final String endpoint;
    private final Object body;
    private final String email;
    private final String password;

    public RequestInfo(
            String endpoint,
            Object body,
            String email,
            String password
    ) {
        this.endpoint = endpoint;
        this.body = body;
        this.email = email;
        this.password = password;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Object getBody() {
        return body;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}