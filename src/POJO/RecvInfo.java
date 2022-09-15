package POJO;

public class RecvInfo {
	String time;
	String equip;
	long recvID;
	long delay;
	byte CCID;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public long getRecvID() {
		return recvID;
	}
	public void setRecvID(long recvID) {
		this.recvID = recvID;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public byte getCCID() {
		return CCID;
	}
	public void setCCID(byte cCID) {
		CCID = cCID;
	}
	
}
