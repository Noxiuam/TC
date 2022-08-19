package gq.noxiuam.tc.server.data;

import java.util.Map;

public class Handshake {

    private final Map<String, String> headers;

    public Handshake(Map<String, String> headerMap) {
        this.headers = headerMap;
    }

    public String getUsername() {
        return this.headers.get("username");
    }

}
