package main;

public class Server {
	
	public void start(Link link){
		new RecvThread().creatRecvThread2(link).start();
	}

}
