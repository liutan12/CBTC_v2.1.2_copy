package main;

import java.nio.ByteBuffer;

public class otherFunctions {

	public static byte[] longToByteArray(long s) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, s); 
		return buffer.array(); 
    }
    public static long byteArrayToLong(byte[] b) {
    	ByteBuffer buffer = ByteBuffer.allocate(8);
    	buffer.put(b, 0, b.length); 
    	buffer.flip();//need flip 
    	return buffer.getLong(); 
    }
    
    public static byte[] intToByteArray(int s){
    	byte[] res = new byte[4];
        int i=0;
        while(s!=0){
        	res[i]=(byte)(s%256);
        	s>>=8;
        	i++;
        }
        return res;
    }
    public static int byteArrayToInt(byte[] b) {
        int res = 0;
        for (int i=0;i<4;i++){
        	int temp=b[i]<0?b[i]+256:b[i];
        	res+=temp*Math.pow(256,i);
        }
        return res;
    }
	
}
