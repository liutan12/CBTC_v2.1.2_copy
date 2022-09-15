package main;

import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//import java.util.Date;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int now=Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));		
		if (true){
			Login login = new Login();
//			UI ui=new UI();
			try {
				login.getUI();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

	}

}
