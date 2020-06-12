package gso.iot.acir.lib;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class NanoWs extends NanoHTTPD {
    public static final int PORT = 8765;

    private NanoWsMiddleware middleware;

    public NanoWs(int port) throws IOException {
        super(  port == 0 ? PORT : port  );
        middleware = new NanoWsMiddleware();
    }

    @Override
    public Response serve(IHTTPSession session) {

        return middleware.processResponse(session);

    }
}