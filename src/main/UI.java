package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import POJO.RecvInfo;
import POJO.SendInfo;

public class UI {

    public static ArrayList<Link> links = new ArrayList<Link>();
    private static String equipCSV = "Equip_Data";
    private static String CCCSV = "CC_Data.csv";
    public static int Im = 0;
    public static File dataCSV;
    public static volatile boolean isRuning = true;
    // public static ArrayList<Integer> allDelay=new ArrayList<Integer>();
    // public static ArrayList<Integer> remoteDelay=new ArrayList<Integer>();
    public static Text Status;
    static Display display = Display.getDefault();
    public static Text txtLocalIp;
    public static Text txtRemoteIp;
    public static Text sndPort;
    public static Text rcvPort;
    public static long busiStartTime;
    public static long startTime;
    public static long nanostartTime;
    public static long nanoTime;
    // public static long remoteSend=0;
    // public static int elapsedTime=0;
    public static int runTimes = 0;
    // public static final int flagEnd=1<<30; //鍙戦�佹椂寤剁粨鏉熸椂鐨勬爣蹇椾綅
    // public static final int flagSDP=1<<29;
    // //鍙戦�佹椂寤剁粰瀵规柟鐨勫崗璁紝鎺ユ敹鍒版鍗忚鏃跺紑鍚疭endDelayToOther.sendToOther()绾跨▼

    /**
     * 鑾峰緱涓荤晫闈�
     */
    /**
     * @wbp.parser.entryPoint
     */

    public void getUI() throws UnknownHostException {

        final Shell mainShell = new Shell();
        mainShell.setSize(1062, 725);
        mainShell.setText("CBTC Data Stream Simulation");

        // start button
        Button btnStart = new Button(mainShell, SWT.NONE);
        btnStart.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
        btnStart.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                startRun();
            }
        });
        btnStart.setBounds(182, 583, 200, 60);
        btnStart.setText("Start");
        ///////

        // end button
        final Button btnEnd = new Button(mainShell, SWT.NONE);
        btnEnd.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
        btnEnd.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                btnEnd.setText("Show Losses");
                endRun();
            }
        });
        btnEnd.setBounds(639, 583, 200, 60);
        btnEnd.setText("End");
        //////////

        Status = new Text(mainShell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        Status.setBounds(38, 109, 970, 438);

        Button btnServer = new Button(mainShell, SWT.RADIO);
        btnServer.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Im = 0;
            }
        });
        btnServer.setBounds(68, 28, 119, 20);
        btnServer.setText("Server");

        Button btnClient = new Button(mainShell, SWT.RADIO);
        btnClient.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Im = 1;
            }
        });
        btnClient.setBounds(68, 69, 119, 20);
        btnClient.setText("Client");

        txtLocalIp = new Text(mainShell, SWT.BORDER);
        txtLocalIp.setText("192.168.13.35");
        txtLocalIp.setBounds(392, 25, 200, 26);

        txtRemoteIp = new Text(mainShell, SWT.BORDER);
        txtRemoteIp.setText("192.168.13.45");
        txtRemoteIp.setBounds(392, 66, 200, 26);

        sndPort = new Text(mainShell, SWT.BORDER);
        sndPort.setText("5555");
        sndPort.setBounds(814, 25, 162, 26);

        rcvPort = new Text(mainShell, SWT.BORDER);
        rcvPort.setText("5556");
        rcvPort.setBounds(814, 69, 162, 26);

        Label lblLocalIp = new Label(mainShell, SWT.NONE);
        lblLocalIp.setBounds(280, 30, 90, 20);
        lblLocalIp.setText("Local IP:");

        Label lblRemoteIp = new Label(mainShell, SWT.NONE);
        lblRemoteIp.setBounds(280, 71, 100, 20);
        lblRemoteIp.setText("Remote IP:");

        Label lblSendPort = new Label(mainShell, SWT.NONE);
        lblSendPort.setBounds(680, 28, 90, 20);
        lblSendPort.setText("Send Port:");

        Label lblReceivePort = new Label(mainShell, SWT.NONE);
        lblReceivePort.setBounds(680, 69, 115, 20);
        lblReceivePort.setText("Receive Port:");

        mainShell.open();
        mainShell.layout();
        mainShell.addShellListener(new ShellAdapter() {
            public void shellClosed(ShellEvent arg0) {
                System.exit(0);
            }
        });
        while (!mainShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /* Start鎸夐挳瑙﹀彂锛屼豢鐪熸祴璇曞紑濮� */
    private void startRun() {
        if (runTimes == 0) {
            runTimes++;
            init(); // 鍒濆鍖�
            Link link = new Link(getInetAddress(txtLocalIp), getInetAddress(txtRemoteIp),
                    Integer.parseInt(sndPort.getText()), Integer.parseInt(rcvPort.getText()));
            links.add(link);
            startTime = System.currentTimeMillis();
            nanoTime = System.nanoTime();
            if (Im == 0) {
                dataCSV = new File(equipCSV + txtRemoteIp.getText() + ".csv");
                UI.insertLineInUI("Waiting for client...");
                RecvThread.flag = false;
                new Server().start(link);
            } else if (Im == 1) {
                dataCSV = new File(CCCSV + txtRemoteIp.getText() + ".csv");
                UI.Status.insert("Waiting for server..." + "\r\n");
                new Client().start(link);
            }

        } else {

        }
    }

    /* End鎸夐挳瑙﹀彂锛屼豢鐪熸祴璇曠粨鏉� */
    private void endRun() {
        if (isRuning == true) {
            isRuning = false;
        } else {
            new getLossPackets(links, Im);
        }
    }

    /*
     * 鍒濆鍖栧弬鏁�
     *
     */
    private void init() {

    }

    private InetAddress getInetAddress(Text IpText) {
        InetAddress res = null;
        if (IpText.getText().length() != 0) {
            String[] ipStrArr = IpText.getText().split("\\.");
            byte[] ipByteArr = new byte[4];
            for (int i = 0; i < 4; i++) {
                ipByteArr[i] = (byte) Integer.parseInt(ipStrArr[i]);
            }
            try {
                res = InetAddress.getByAddress(ipByteArr);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void insertLineInUI(String string) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                UI.Status.insert(string + "\r\n");
            }
        });
    }

    public static void showResult(Link link) {
        writeCSV();
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                long elapsedTime = System.currentTimeMillis() - busiStartTime;
                Result show = new Result();
                show.showResult(Link.delayMill, elapsedTime, link, UI.Im);
            }
        });
    }

    public static void writeCSV() {
        String csvName;
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String dateTrans=dateFormat.format(date);
        if (UI.Im == 0) {
            csvName = dateTrans+"GPdata.csv";
        } else {
            csvName = dateTrans+"CCdata.csv";
        }

        try {
            System.out.println("startwrite");
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvName, true));
            bw.append("time" + "," + "Equip" + "," + "CCID" + "," + "MsgID" + "," + "DataLen" + "," + "Delay");
            bw.newLine();
            for (int i = 0; i < Link.SendPackages.size(); i++) {
                SendInfo temp = Link.SendPackages.get(i);
                bw.append(temp.getTime() + "," + temp.getEquip() + "," + temp.getCCID() + "," + temp.getMsgID() + ","
                        + temp.getDataLen());
                bw.newLine();
            }

            for (int i = 0; i < Link.ReceivePackages.size(); i++) {
                RecvInfo temp = Link.ReceivePackages.get(i);
                bw.append(temp.getTime() + "," + temp.getEquip() + "," + temp.getCCID() + "," + temp.getRecvID() + ","
                        + "" + "," + Link.delayMill.get(i));
                bw.newLine();
            }

            bw.close();
            System.out.println("endwrite");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getCurrentTime() {
        return (System.nanoTime() - nanoTime) / 1000 + startTime * 1000;
    }

}
