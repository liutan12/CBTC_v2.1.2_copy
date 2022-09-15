package POJO;

public class SendInfo {
	String time;
	String equip;
	long MsgID;
	byte CCID;
	int dataLen;
	
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
	public long getMsgID() {
		return MsgID;
	}
	public void setMsgID(long msgID) {
		MsgID = msgID;
	}
	public byte getCCID() {
		return CCID;
	}
	public void setCCID(byte cCID) {
		CCID = cCID;
	}
	public int getDataLen() {
		return dataLen;
	}
	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
	
	
}
