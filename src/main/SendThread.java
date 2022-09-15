package main;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import POJO.SendInfo;
import configure.CBTCparam;

public class SendThread {
	
	
	private int packetSizeMulti=1;
	private int sleepMsPerPacket=10;  //越小发包越快，9时为正常数据量，1时近似看做增大十倍
	private int sleepMsPerLoop=1; //ms
	
	public void sendData(Link link,int dataLen,byte equipID){
		
		dataLen*=packetSizeMulti;
		if(equipID!=0){
			link.incMsgID();;
		}
		byte[] sendBuff=new byte[dataLen];
		sendBuff[0]=0;
		sendBuff[1]=equipID;
		byte[] msgIDByte=otherFunctions.longToByteArray(link.getMsgID());
		for (int i=0;i<8;i++){
			sendBuff[i+10]=msgIDByte[i]; 
		}
		byte[] timeByte=otherFunctions.longToByteArray(UI.getCurrentTime());
		for (int i=0;i<8;i++){
			sendBuff[i+2]=timeByte[i];
		}
		DatagramPacket sendPacket=new DatagramPacket(sendBuff,dataLen,link.getRemoteAddr(),link.getRecvPort());
		try {									
			link.getSendSocket().send(sendPacket);	        
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String equip=CBTCparam.getEquipName(equipID);
			if (UI.Im==0){
				final String textOUT=time+": A "+equip+" message has send success to train "+link.getCCID()+" ! NO."+link.getMsgID()+", data length:"+dataLen;
//				UI.insertLineInUI(textOUT);
			}else{
				final String textOUT=time+": A CC message has send success to "+equip+" ! NO."+link.getMsgID()+", data length:"+dataLen;
//				UI.insertLineInUI(textOUT);
			}
			
//			BufferedWriter bw = new BufferedWriter(new FileWriter(UI.dataCSV,true)); 
//	        bw.append(time+"\t,"+equip+","+link.getCCID()+","+link.getMsgID()+",,"+dataLen);	        
//	        bw.newLine();
//	        bw.close(); 
			SendInfo temp=new SendInfo();
			temp.setTime(time);
			temp.setEquip(equip);
			temp.setCCID(link.getCCID());
			temp.setMsgID(link.getMsgID());
			temp.setDataLen(dataLen);
			Link.SendPackages.add(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Thread creatSendThread(Link link){
		Runnable send=new Runnable(){
			public void run(){
				UI.isRuning=true;
				link.setStutas(1);
				UI.busiStartTime=System.currentTimeMillis();
				UI.nanostartTime=UI.getCurrentTime();
				try {							
					long t=0;
					while(UI.isRuning){
//						long startTime=System.currentTimeMillis();
						t++;
						if (UI.Im==0){
							if (t%5==0){
								sendData(link,107,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%5==0){
								sendData(link,174,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%5==0){
								sendData(link,120,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
//							if (t%6==0){
//								sendData(link,1006,(byte)1);
//							}
//							Thread.sleep(sleepMsPerPacket);
							if (t%15==0){
								sendData(link,116,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%15==0){
								sendData(link,121,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
//							if (t%30==0){
//								sendData(link,98,(byte)4);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%200==0){
//								sendData(link,1392,(byte)2); //1492
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%200==0){
//								sendData(link,90,(byte)6);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%300==0){
//								sendData(link,5902,(byte)5);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%600==0){
//								sendData(link,160,(byte)4);
//							}
//							Thread.sleep(sleepMsPerPacket);
							Thread.sleep(50);
						}
						else{
							if (t%2==0){
								sendData(link,120,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
//							if (t%6==0){
//								sendData(link,167,(byte)1);
//							}
//							Thread.sleep(sleepMsPerPacket);
							if (t%6==0){
								sendData(link,107,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%6==0){
								sendData(link,126,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%16==0){
								sendData(link,116,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
							if (t%16==0){
								sendData(link,121,(byte)3);
							}
							Thread.sleep(sleepMsPerPacket);
//							if (t%30==0){
//								sendData(link,241,(byte)4);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%200==0){
//								sendData(link,152,(byte)2);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%200==0){
//								sendData(link,90,(byte)6);
//							}
//							Thread.sleep(sleepMsPerPacket);
//							if (t%300==0){
//								sendData(link,56238,(byte)5);
//							}
//							Thread.sleep(sleepMsPerPacket);
							Thread.sleep(50);
//							Thread.sleep(sleepMsPerPacket);
						}
//						long loopRunTime=System.currentTimeMillis()-startTime;
//						Thread.sleep(sleepMsPerLoop-loopRunTime);
//						long loopRunTime=System.currentTimeMillis()-startTime;
//						Thread.sleep(1);
					}
					if(UI.Im!=0){
						link.setStutas(2);
						link.setTimeDiffers(new ArrayList<Long>());
						sendBusinessStopSignalling(link);
						sendSyncSignallingToServer(link);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		};
		return new Thread(send);
	}
	
	
	public static void sendSyncSignallingToServer(Link link){
		byte[] sendBuff=new byte[1];
		sendBuff[0]=2;
		DatagramPacket sendPacket=new DatagramPacket(sendBuff,sendBuff.length,link.getRemoteAddr(),link.getRecvPort());
		try {
			link.getSendSocket().send(sendPacket);
			link.setTc0(UI.getCurrentTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendBusinessStopSignalling(Link link){
		byte[] sendBuff=new byte[2];
		sendBuff[0]=1;
		sendBuff[1]=2;
		DatagramPacket sendPacket=new DatagramPacket(sendBuff,sendBuff.length,link.getRemoteAddr(),link.getRecvPort());
		try {
			link.getSendSocket().send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
