package main;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import POJO.RecvInfo;
import POJO.SendInfo;

public class Link {
	
	private byte CCID=1;   //杞﹁浇ID
	private InetAddress localAddr; 
	private InetAddress remoteAddr;
	private int sendPort;
	private int recvPort;
	private DatagramSocket sendSocket;
	private DatagramSocket recvSocket;
	private long msgID=0;  //鏁版嵁鍖呯紪鍙�
	private ArrayList<Integer> receivedIDs=new ArrayList<Integer>();    //褰撳墠閾捐矾鎺ユ敹鍒扮殑鏁版嵁鍖呯紪鍙�
	private int stutas=0;
	private volatile long tc0;
	private ArrayList<Long> timeDiffers=new ArrayList<Long>();
	private BigDecimal differK=new BigDecimal(0);
	private long differB=0;
	public ArrayList<Long> recvTimeA=new ArrayList<>();
	public ArrayList<Integer> delay=new ArrayList<Integer>();
	public static ArrayList<Double> delayMill=new ArrayList<Double>();
	
	public static ArrayList<SendInfo> SendPackages=new ArrayList<SendInfo>();
	public static ArrayList<RecvInfo> ReceivePackages=new ArrayList<RecvInfo>();
	
	
	public Link(InetAddress localAddr,InetAddress remoteAddr,int sendPort,int recvPort){
		setLocalAddr(localAddr);
		setRemoteAddr(remoteAddr);
		setSendPort(sendPort);
		setRecvPort(recvPort);
		try {
			if(localAddr==null){
				setLocalAddr(InetAddress.getLocalHost());
				setSendSocket(new DatagramSocket(sendPort));
				setRecvSocket(new DatagramSocket(recvPort));
			}else{
				setSendSocket(new DatagramSocket(sendPort,localAddr));
				setRecvSocket(new DatagramSocket(recvPort,localAddr));
			}
			
			if(remoteAddr==null){
				setRemoteAddr(InetAddress.getLocalHost());
			}
		} catch (SocketException | UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
	
	
//	long syncTimeWithServer(long time){
//		synchronized (this){
////			BigDecimal timeB=new BigDecimal(time);
////			BigDecimal tmp=timeB.multiply(differK);
////			long nowDiffer=tmp.longValue()+differB;
////			//long nowDiffer=time+differB;
////			time+=nowDiffer;
////			UI.insertLineInUI("time after inc "+ nowDiffer+" is: "+time);
//			return time;
//		}
//		
//	}
	
	public void modifyDelay(long differ2,boolean isServer){
		long busiRunTime=UI.getCurrentTime()-UI.nanostartTime;
		BigDecimal k=new BigDecimal(differ2-differB).divide(new BigDecimal(busiRunTime), 20, BigDecimal.ROUND_HALF_DOWN);
		setDifferK(k);

		for(int i=0;i<delay.size();i++){
			double inc=new BigDecimal(recvTimeA.get(i)-UI.nanostartTime).multiply(differK).doubleValue();
			double res=isServer?(delay.get(i)-differB-inc):(delay.get(i)+differB+inc);
			delayMill.add(res/1000);
			
//			try {
//	        	BufferedWriter bw = new BufferedWriter(new FileWriter("ModifyDelay"+UI.startTime+".csv",true));
//				bw.append(recvTimeA.get(i)+","+delay.get(i)+","+differB+","+inc+","+res+",");
//				bw.newLine();
//		        bw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}
		System.out.println("differ1:"+getDifferB()+", differ2:"+differ2);
		System.out.println("busiRunTime:"+busiRunTime+", differK:"+getDifferK().doubleValue());
	}
	
	
	
	public byte getCCID() {
		return CCID;
	}
	public void setCCID(byte cCID) {
		CCID = cCID;
	}
	public InetAddress getLocalAddr() {
		return localAddr;
	}
	public void setLocalAddr(InetAddress localAddr) {
		this.localAddr = localAddr;
	}
	public InetAddress getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(InetAddress remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public int getSendPort() {
		return sendPort;
	}
	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}
	public int getRecvPort() {
		return recvPort;
	}
	public void setRecvPort(int recvPort) {
		this.recvPort = recvPort;
	}
	public DatagramSocket getSendSocket() {
		return sendSocket;
	}
	public void setSendSocket(DatagramSocket sendSocket) {
		this.sendSocket = sendSocket;
	}
	public DatagramSocket getRecvSocket() {
		return recvSocket;
	}
	public void setRecvSocket(DatagramSocket recvSocket) {
		this.recvSocket = recvSocket;
	}
	public long getMsgID() {
		return msgID;
	}
	public void setMsgID(long msgID) {
		this.msgID = msgID;
	}
	public void incMsgID(){
		this.msgID++;
	}
	public ArrayList<Integer> getReceivedIDs() {
		return receivedIDs;
	}
	public void setReceivedIDs(ArrayList<Integer> receivedIDs) {
		this.receivedIDs = receivedIDs;
	}
	public int getStutas() {
		return stutas;
	}
	public void setStutas(int stutas) {
		this.stutas = stutas;
	}
	public BigDecimal getDifferK() {
		return differK;
	}
	public void setDifferK(BigDecimal differK) {
		this.differK = differK;
	}
	public long getDifferB() {
		return differB;
	}
	public void setDifferB(long differB) {
		this.differB = differB;
	}
	public ArrayList<Long> getTimeDiffers() {
		return timeDiffers;
	}
	public void setTimeDiffers(ArrayList<Long> timeDiffers) {
		this.timeDiffers = timeDiffers;
	}
	public long getTc0() {
		return tc0;
	}
	public void setTc0(long tc0) {
		this.tc0 = tc0;
	}

}
