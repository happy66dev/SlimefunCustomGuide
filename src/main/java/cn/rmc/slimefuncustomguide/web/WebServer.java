package cn.rmc.slimefuncustomguide.web;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.Map;

public class WebServer {

    private HttpServer server;

    public void start(String bind, int port, WebApiHandler handler) throws Exception {
        ResearchApiHandler researchHandler = new ResearchApiHandler(handler.getPlugin());
        server = HttpServer.create(new InetSocketAddress(bind, port), 0);
        server.createContext("/", handler);
        server.createContext("/editor.html", researchHandler);
        server.createContext("/api/researches", researchHandler);
        server.createContext("/api/slimefun-items", researchHandler);
        server.setExecutor(null);
        server.start();
    }

    public void start(String bind, int port, Map<String, HttpHandler> contexts) throws Exception {
        server = HttpServer.create(new InetSocketAddress(bind, port), 0);
        for (Map.Entry<String, HttpHandler> entry : contexts.entrySet()) {
            server.createContext(entry.getKey(), entry.getValue());
        }
        server.setExecutor(null);
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public boolean isRunning() {
        return server != null;
    }
}
