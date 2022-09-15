package configure;

public class CBTCparam {
	
	/*
	ZC的轨旁设备代号为1�?
	LC的轨旁设备代号为2�?
	CI的轨旁设备代号为3�?
	ATS的轨旁设备代号为4�?
	MSS的轨旁设备代号为5�?
	TimeServer的轨旁设备代号为6�?
	*/
	public static String getEquipName(byte equipID){
		switch(equipID){
	        case 1:return new String("ZC");
	        case 2:return new String("LC");
	        case 3:return new String("CI");
	        case 4:return new String("ATS");
	        case 5:return new String("MSS");
	        case 6:return new String("TimeServer");
	        default: return null;
        }
	}

}
