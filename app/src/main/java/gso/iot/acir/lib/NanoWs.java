package gso.iot.acir.lib;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class NanoWs extends NanoHTTPD {
    public static final int PORT = 8765;

    public NanoWs(int port) throws IOException {
        super(  port == 0 ? PORT : port  );
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        if (uri.equals("/hello")) {
            String response = "HelloWorld";
            return newFixedLengthResponse(response);
        }
        return  null;
    }
}