package gso.iot.acir.lib;

import fi.iki.elonen.NanoHTTPD;

public class NanoWsMiddleware {

    public NanoHTTPD.Response processResponse(NanoHTTPD.IHTTPSession session){

        String uri = session.getUri();

        if (uri.equals("/hello")) {
            String response = "HelloWorld";
            return NanoHTTPD.newFixedLengthResponse(response);

        }

        return null;

    }
}
