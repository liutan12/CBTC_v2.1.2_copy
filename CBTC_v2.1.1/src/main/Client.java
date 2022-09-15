package main;

public class Client {
	
	public void start(Link link){
		new RecvThread().creatRecvThread(link).start();
		SendThread.sendSyncSignallingToServer(link);
	}

}
