package utils;

import rpc.ClientRpcReflectionWorker;
import services.Service;

import java.net.Socket;


public class RpcConcurrentServer extends AbstractConcurrentServer {
    private Service server;

    public RpcConcurrentServer(int port, Service server) {
        super(port);
        this.server = server;
        System.out.println("Contest - RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        ClientRpcReflectionWorker worker = new ClientRpcReflectionWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
