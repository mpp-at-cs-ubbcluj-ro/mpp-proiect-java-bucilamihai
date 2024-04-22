package utils;

import object.ClientObjectWorker;
import services.Service;

import java.net.Socket;


public class ObjectConcurrentServer extends AbstractConcurrentServer {
    private Service server;
    public ObjectConcurrentServer(int port, Service server) {
        super(port);
        this.server = server;
        System.out.println("Contest - ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker = new ClientObjectWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }


}
