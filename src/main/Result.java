package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class Result {

	/**
	 * Launch the application.
	 * @param args
	 */
	protected Shell shell;
	private static Text txTime;
	private static Text txSend;
	private static Text txRecv;
	private static Text txLoss;
	private static Text txMean;
	private static Text txMax;
	private static Text txBig;
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showResult(final ArrayList<Double> allDelay,final long runTime,Link link,final int Im) {
		
		ArrayList<Integer> recvIDs= link.getReceivedIDs();
		
		long sum=0,bigDelay=0;
		int len=allDelay.size();
		double[] adl2=new double[len];
		
		int max=Integer.MIN_VALUE;
		for(int i=0;i<len;i++){
			double delay=allDelay.get(i);
			adl2[i]=delay;
			sum+=delay;
			if (delay>max){
				max=(int) delay ;
			}
			if((int)delay>=150){
				bigDelay++;
			}
		}
		int average=(int) (sum/len);
				
		int firstID=recvIDs.get(0);
		int lastID=recvIDs.get(recvIDs.size()-1);
		Set<Integer> recvSet=new HashSet<Integer>();				
		for (int i=0;i<recvIDs.size();i++){
			recvSet.add(recvIDs.get(i));
			if(firstID>recvIDs.get(i)){
				firstID=recvIDs.get(i);
			}
			if(lastID<recvIDs.get(i)){
				lastID=recvIDs.get(i);
			}
		}
		int recvSum=recvSet.size();
		int sendSum=lastID-firstID+1;
		
		
		
		
		Display display = Display.getDefault();
		final Shell shlCbtcDataStream = new Shell();
		shlCbtcDataStream.setSize(924, 623);
		shlCbtcDataStream.setText("CBTC Data Stream Simulation Result--"+(Im==0?"Uplink":"Downlink"));
		
		Chart chart = new Chart(shlCbtcDataStream, SWT.NONE);
		chart.setBounds(10, 23, 729, 547);		
		
		txTime = new Text(shlCbtcDataStream, SWT.BORDER);
		txTime.setEditable(false);
		txTime.setBounds(760, 60, 123, 26);
		int hour=(int) Math.floor(runTime/3600000);
		int min=(int)Math.floor((runTime%3600000)/60000);
		int sec=(int)Math.floor(((runTime%3600000)%60000)/1000);
		txTime.setText((hour>=10?hour:"0"+hour)+":"+(min>=10?min:"0"+min)+":"+(sec>=10?sec:"0"+sec));
		
		txSend = new Text(shlCbtcDataStream, SWT.BORDER);
		txSend.setEditable(false);
		txSend.setBounds(760, 130, 123, 26);
		txSend.setText(Integer.toString(sendSum));
		
		txRecv = new Text(shlCbtcDataStream, SWT.BORDER);
		txRecv.setEditable(false);
		txRecv.setBounds(760, 200, 123, 26);
		txRecv.setText(Integer.toString(recvSum));
		
		txLoss = new Text(shlCbtcDataStream, SWT.BORDER);
		txLoss.setEditable(false);
		txLoss.setBounds(760, 270, 123, 26);
		txLoss.setText(String.format("%.2f", ((double)(sendSum-recvSum)*100)/sendSum)+"%");
		
		txMean = new Text(shlCbtcDataStream, SWT.BORDER);
		txMean.setEditable(false);
		txMean.setBounds(760, 340, 123, 26);
		txMean.setText(average+"ms");

		txMax = new Text(shlCbtcDataStream, SWT.BORDER);
		txMax.setEditable(false);
		txMax.setBounds(760, 410, 123, 26);
		txMax.setText(max+"ms");
		
		txBig = new Text(shlCbtcDataStream, SWT.BORDER);
		txBig.setEditable(false);
		txBig.setBounds(760, 480, 123, 26);
		txBig.setText(String.format("%.2f",((double)bigDelay*100)/len)+"%");
		
		Label lblTime = new Label(shlCbtcDataStream, SWT.NONE);
		lblTime.setBounds(760, 33, 191, 20);
		lblTime.setText("Elapsed time:");		
		
		Label lblSend = new Label(shlCbtcDataStream, SWT.NONE);
		lblSend.setText("Send packet:");
		lblSend.setBounds(760, 103, 191, 20);
		
		Label lblRecv = new Label(shlCbtcDataStream, SWT.NONE);
		lblRecv.setText("Receive packet:");
		lblRecv.setBounds(760, 173, 191, 20);
		
		Label lblLoseRate = new Label(shlCbtcDataStream, SWT.NONE);
		lblLoseRate.setText("Packet loss rate:");
		lblLoseRate.setBounds(760, 243, 191, 20);
		
		Label lblMeamDelay = new Label(shlCbtcDataStream, SWT.NONE);
		lblMeamDelay.setText("Mean delay:");
		lblMeamDelay.setBounds(760, 313, 191, 20);
		
		Label lblMaxDelay = new Label(shlCbtcDataStream, SWT.NONE);
		lblMaxDelay.setText("Max delay:");
		lblMaxDelay.setBounds(760, 383, 191, 20);
		
		Label lblBigRate = new Label(shlCbtcDataStream, SWT.NONE);
		lblBigRate.setText("Over 150ms rate:");
		lblBigRate.setBounds(760, 455, 191, 20);
		
		chart.getTitle().setText((Im==0?"Uplink":"Downlink")+" Data Stream Delay");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Data Packets");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Delay (ms)");

		ILineSeries lineSeries = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, "Data delay");				
		lineSeries.setVisibleInLegend(false);
		lineSeries.setYSeries(adl2);
		lineSeries.setSymbolType(PlotSymbolType.NONE);
		chart.getAxisSet().adjustRange();


		shlCbtcDataStream.open();
		shlCbtcDataStream.layout();

		
		while (!shlCbtcDataStream.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
}
