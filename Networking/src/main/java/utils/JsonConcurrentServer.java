package utils;

import json.ClientJsonWorker;
import services.Service;

import java.net.Socket;

public class JsonConcurrentServer extends AbstractConcurrentServer{
    private Service server;
    public JsonConcurrentServer(int port, Service server) {
        super(port);
        this.server = server;
        System.out.println("Contest - JsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientJsonWorker worker = new ClientJsonWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
