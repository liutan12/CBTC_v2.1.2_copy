package main;

public class Client {

	public void start(Link link) {
		new RecvThread().creatRecvThread(link).start();
		while (RecvThread.flag) {
//			new RecvThread().creatRecvThread(link).start();
//			System.out.println(RecvThread.flag);
			try {
				SendThread.sendSyncSignallingToServer(link);
				Thread.sleep(2000);
			} catch (InterruptedException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println(RecvThread.flag);

	}
}
