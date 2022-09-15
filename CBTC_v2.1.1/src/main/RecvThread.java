package main;

import configure.CBTCparam;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import POJO.RecvInfo;

public class RecvThread {
	
	private int syncNum=100;
	private int diffThres=500; //us
	
	public Thread creatRecvThread(Link link){
		Runnable recv=new Runnable(){
			
			@Override
			public void run(){
				UI.isRuning=true;
				link.setStutas(0);
				try {
					while(true){
						byte[] recvBuff=new byte[2000];	
						DatagramPacket recvPacket=new DatagramPacket(recvBuff,recvBuff.length);
						link.getRecvSocket().receive(recvPacket);
						byte msgType=recvBuff[0];    	//表征消息类型的标志位
						if (msgType==0){				//0代表接收到业务数据
							handleBusiness(link,recvBuff,recvPacket.getLength());
						} else if(msgType==1){          //1代表接收到信令信息
							handleSignalling(link, recvBuff);
						} else if(msgType==2){          //2代表接收到时间同步信号
							handleTimeSync(link,recvBuff);
						} 
												
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		};
		return new Thread(recv);
	}
	
	private void handleSignalling(Link link,byte[] recvBuff){
		if(recvBuff[1]==1){               //1代表该信令信息为client向server传递首次同步所得两端时间差信息，Tc+differ=Ts。server接收到此信息后，设置link时间差，并开始发送业务数据
			long differ=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,2,10));
			link.setDifferB(differ);
			new SendThread().creatSendThread(link).start();
		}else if(recvBuff[1]==2){
			UI.isRuning=false;	//2代表该信令信息为client向server发送业务收发结束的信息，server接收到此信息后停止发送业务，并开始准备第二轮的时间同步
			link.setStutas(2);
		}else if(recvBuff[1]==3){         //3代表该信令信息为client向server传递第二次同步所得两端时间差信息，Tc+differ=Ts。server接收到此信息后，算出时间差偏移率，进行时延修正，并显示结果
			long differ=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,2,10));
			link.modifyDelay(differ,true);
			
			UI.showResult(link);
		}
	}
	
	private void handleBusiness(Link link,byte[] recvBuff,int packetLen) throws IOException {
		long recvTime=UI.getCurrentTime();
		byte equipID=recvBuff[1];
		long sendTime=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,2,10));
		long recvID=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,10,18));
		link.getReceivedIDs().add((int) recvID);
		long delay=recvTime-sendTime;
//		if(UI.Im==0){
//			delay-=link.getDifferB();
//		}else{
//			delay+=link.getDifferB();
//		}
		link.recvTimeA.add(recvTime);
		link.delay.add((int) delay);
		String equip=CBTCparam.getEquipName(equipID);
		String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (UI.Im==0){
			final String textOUT=time+": Receive success! NO."+recvID+", From equipment: "+equip+", data length:"+packetLen;
			UI.insertLineInUI(textOUT);
		}
		else{
			final String textOUT=time+": Receive success! NO."+recvID+", Equipment: "+equip+", From CC: NO."+link.getCCID()+", data length:"+packetLen;
			UI.insertLineInUI(textOUT);
		}
		
		//System.out.println(delay);
//		BufferedWriter bw = new BufferedWriter(new FileWriter(UI.dataCSV,true));
//        bw.append(time+"\t,"+equip+","+link.getCCID()+","+recvID+","+delay);	        
//        bw.newLine();
//        bw.close();
		
		RecvInfo temp=new RecvInfo();
		temp.setTime(time);
		temp.setEquip(equip);
		temp.setCCID(link.getCCID());
		temp.setRecvID(recvID);
		temp.setDelay(delay);
		Link.ReceivePackages.add(temp);
	}
	
	private void handleTimeSync(Link link,byte[] recvBuff) {
		if (UI.Im==0){
			handleTimeSyncInServer(link);
		}else{
			handleTimeSyncInClient(link,recvBuff);
		}
	}
	
	private void handleTimeSyncInServer(Link link) {
		long recvTime=UI.getCurrentTime();
		UI.insertLineInUI(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+": Synchronzing time with CC"+link.getCCID()+"... ");
		byte[] sendBuff=new byte[17];
		sendBuff[0]=2;
		byte[] recvTimeByte=otherFunctions.longToByteArray(recvTime);
		for (int b=0;b<8;b++){
			sendBuff[b+1]=recvTimeByte[b];
		}
		byte[] sendTimeByte=otherFunctions.longToByteArray(UI.getCurrentTime());
		for (int b=0;b<8;b++){
			sendBuff[b+9]=sendTimeByte[b];
		}
		DatagramPacket sendPacket=new DatagramPacket(sendBuff,sendBuff.length,link.getRemoteAddr(),link.getRecvPort());
		try {
			link.getSendSocket().send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}	        
	}
	
	private void handleTimeSyncInClient(Link link,byte[] recvBuff) {
		long tc1=UI.getCurrentTime();
		long tc0=link.getTc0();
		long ts0=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,1,9));
		long ts1=otherFunctions.byteArrayToLong(Arrays.copyOfRange(recvBuff,9,17));
		long diff=(ts0+ts1-tc0-tc1)/2;   //NTP
		link.getTimeDiffers().add(diff);   
		UI.insertLineInUI(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"：Synchronzing time with server: "+link.getTimeDiffers().size()+"%... Time difference: "+diff/1000+"ms");
	
//		String csvName;
//		if(link.getStutas()==0){
//        	csvName="FirstDiffer"+UI.startTime+".csv";
//		}else{
//			csvName="SecondDiffer"+UI.startTime+".csv";
//		}
//		
//		try {
//        	BufferedWriter bw = new BufferedWriter(new FileWriter(csvName,true));
//			bw.append(diff+",");
//			bw.newLine();
//	        bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		        			
		if (link.getTimeDiffers().size()==syncNum){
			long differ=calcDiffer(link.getTimeDiffers(),link.getStutas());
			if(link.getStutas()==0){
				link.setDifferB(differ);
				sendDifferToServer(link, differ, (byte) 1);
				new SendThread().creatSendThread(link).start();
			} else if(link.getStutas()==2){
				sendDifferToServer(link, differ, (byte) 3);
				link.modifyDelay(differ,false);
				UI.showResult(link);
			}
		}else{
			SendThread.sendSyncSignallingToServer(link);
		}
	}
	
	private long calcDiffer(ArrayList<Long> differs,int stutas){
		long res;
		for(int i=0;;i++){
			res=0;
			for(int j=0;j<differs.size();j++){
				res+=differs.get(j);
			}
			res/=differs.size();
			
			long max=0;
			int idx=0;
			for(int j=0;j<differs.size();j++){
				if(Math.abs(differs.get(j)-res)>max){
					idx=j;
					max=Math.abs(differs.get(j)-res);
				}
			}
			
			if(max<diffThres){
				System.out.println(i+"个奇异值去掉后均值:"+res);
				
//				String csvName;
//				if(stutas==0){
//					csvName="FirstDifferV"+UI.startTime+".csv";
//				}else{
//					csvName="SecondDifferV"+UI.startTime+".csv";
//				}
//				try {
//		        	BufferedWriter bw = new BufferedWriter(new FileWriter(csvName,true));
//		        	for(int v=0;v<differs.size();v++){
//		        		bw.append(differs.get(v)+",");
//						bw.newLine();
//					}
//		        	bw.append(res+",");
//					bw.newLine();
//			        bw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

				return res;
			}
			differs.remove(idx);
			//differs.set(idx, res);			
		}
		
	}
	
	private void sendDifferToServer(Link link,long differ,byte flag){
		byte[] sendBuff=new byte[10];
		sendBuff[0]=1;
		sendBuff[1]=flag;
		byte[] differByte=otherFunctions.longToByteArray(differ);
		for (int b=0;b<8;b++){
			sendBuff[b+2]=differByte[b];
		}
		DatagramPacket sendPacket=new DatagramPacket(sendBuff,sendBuff.length,link.getRemoteAddr(),link.getRecvPort());
		try {
			link.getSendSocket().send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
