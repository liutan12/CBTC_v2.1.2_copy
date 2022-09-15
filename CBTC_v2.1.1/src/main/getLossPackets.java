package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class getLossPackets {
	private static Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public getLossPackets(ArrayList<Link> links,int Im) {
		
		ArrayList<Integer> recvIDs=links.get(0).getReceivedIDs();
		int max=recvIDs.get(0);
		int min=recvIDs.get(0);
		for (int id:recvIDs){
			if (max<id){
				max=id;
			}
			if (min>id){
				min=id;
			}
		}
		int[] getIDs=new int[max+1];
		ArrayList<Integer> lossIDs=new ArrayList<Integer>();
		for (int i=0;i<recvIDs.size();i++){
			getIDs[recvIDs.get(i)]=1;
		}
		for (int i=min;i<=max;i++){
			if (getIDs[i]==0){
				lossIDs.add(i);
			}
		}
		StringBuffer showIDs=new StringBuffer();
		for (int i=0;i<lossIDs.size();i++){
			showIDs.append(lossIDs.get(i)+" ");
		}
		
		Display display = Display.getDefault();
		Shell shlLossPacketId = new Shell();
		shlLossPacketId.setSize(465, 206);
		shlLossPacketId.setText("Loss Packet ID--"+(Im==0?"Uplink":"Downlink"));
		
		text = new Text(shlLossPacketId, SWT.BORDER);
		text.setEditable(false);
		text.setBounds(10, 10, 427, 141);
		text.setText(showIDs.toString());
		
		Label lblNoPacketLost = new Label(shlLossPacketId, SWT.NONE);
		lblNoPacketLost.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		lblNoPacketLost.setBounds(147, 57, 167, 27);
		lblNoPacketLost.setText("No Packet lost !");
		
		if (lossIDs.isEmpty()){
			text.setVisible(false);
		}else{
			lblNoPacketLost.setVisible(false);
		}

		shlLossPacketId.open();
		shlLossPacketId.layout();
		while (!shlLossPacketId.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
