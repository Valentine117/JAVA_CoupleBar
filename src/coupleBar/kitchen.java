package coupleBar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;


public class kitchen extends JFrame implements MouseWheelListener {
	
	ArrayList<JTextArea> txtAreaList  = new ArrayList<JTextArea>();
	ArrayList<JPanel> orderPanelArr = new ArrayList<JPanel>();
	Map<Integer, String> orderNumTableNumMap = new HashMap<Integer, String>();
	Map<JButton, Integer> btnOrderNumMap = new HashMap<JButton, Integer>();
	Map<JButton, JPanel> BtnPanelMap = new HashMap<JButton, JPanel>();
	JPanel kip;
	
	String counterIP = "192.168.20.28";
	
	int foodTableNum=0;
	
	public kitchen() {
		
		super("주방");
		setSize(1017, 770);
		setLocationRelativeTo(null);
		setLayout(null);
		
		new UniReceiver(8888).start();
		
		// 주문서 판넬
		JPanel order = new JPanel();
		order.setLayout(new BorderLayout());
		order.setBounds(0, 0, 1000, 150);
		order.setBackground(new Color(189,189,189));
		order.setVisible(true);
		add(order);
		
		// 사진 추가
		ImageIcon jjack_img = new ImageIcon("./img/jjack remove.png");
		JLabel img_label = new JLabel(jjack_img);
		img_label.setBounds(800, 20, 100, 100);
		order.add(img_label);
		
		// 주문서 라벨
		JLabel orderL = new JLabel("주문서");
		orderL.setHorizontalAlignment(orderL.CENTER);
		orderL.setFont(new Font("HY견고딕", Font.BOLD, 55));
		order.add(orderL);
		
		JPanel backP = new JPanel();	// 액자
		backP.setBounds(0, 150, 1000, 580);
		backP.setLayout(null);
		add(backP);
	
		// 주방 리스트 스크롤 추가 판넬
		kip = new JPanel();	// 길이를 늘릴 패널
		kip.setBounds(0, 0, 1000, 580);
		kip.setLayout(null);
		kip.setBackground(new Color(225,231,251));
		kip.setVisible(true);
		backP.add(kip);
		
		addMouseWheelListener(this);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class UniReceiver extends Thread {
		int port;
		
		public UniReceiver(int port) {
			super();
			this.port = port;
		}
		
		@Override
		public void run() {
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket(port);
				while(true) {
					byte [] buf = new byte[1024];
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					socket.receive(dp);
					String msg = new String(buf);
					msg = msg.trim();
					String tableNum = msg.substring(msg.indexOf("T")+1);
					msg = msg.substring(0,msg.indexOf("T"));
					addOrderList(msg,tableNum);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
	class UniSendCookingFinish extends Thread{
		
		InetAddress addr;
		int port;
		int orderNum = 0;
		String foodTableNum;
		String msg;
		
		public UniSendCookingFinish(String addr, int port, String msg) {
			super();
			try {
				this.addr = InetAddress.getByName(addr);
				this.port = port;
				this.msg = msg;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket();
				
				byte [] buf = msg.getBytes();
				DatagramPacket data = new DatagramPacket(
						buf, //버퍼
						buf.length, //버퍼길이
						addr, //주소(InetAddress 객체)
						port//int 포트번호
						);
				socket.send(data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
	int orderNum = 1;
	
	void addOrderList(String orderList, String tableNum) {
		
		int orderLine = 1+(orderPanelArr.size())/6;
		orderNumTableNumMap.put(orderNum, tableNum);
		
		if(orderLine > 2) {
			kip.setBounds(0, 590-(290*orderLine), 1000, 290*orderLine);
		} else {
			kip.setBounds(0, 0, 1000, 580);
		}
		
		JPanel jp_i = new JPanel();
		jp_i.setBounds(170*(orderPanelArr.size()%6), 290*(orderPanelArr.size()/6), 150, 270);
		jp_i.setLayout(null);
		
		JTextArea ppname = new JTextArea("");	// 주문내역이 적힐 ta
		JScrollPane jsp = new JScrollPane(ppname);
		jsp.setBounds(0,0,150,220);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		txtAreaList.add(ppname);
		ppname.setEditable(false);
		jp_i.add(jsp);
		
		JButton cpBtn = new JButton("조리완료");
		cpBtn.setBounds(0, 220, 150, 50);
		cpBtn.setBackground(Color.white);
		cpBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		jp_i.add(cpBtn);
		btnOrderNumMap.put(cpBtn, orderNum);
		BtnPanelMap.put(cpBtn, jp_i);
		
		orderPanelArr.add(jp_i);
		kip.add(jp_i);
		
		orderList = "\n" + orderList;
		
		txtAreaList.get(orderNum-1).setText("주문번호: " + orderNum);
		txtAreaList.get(orderNum-1).append(orderList);
		jp_i.repaint();
		
		orderNum +=1;
		
		cpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton a = (JButton)(e.getSource());
				int b = btnOrderNumMap.get(a);
				String msg = "K" + orderNumTableNumMap.get(b)+","+b;
				new UniSendCookingFinish(counterIP, 8888, msg).start();

				orderPanelArr.remove(BtnPanelMap.get(a));
				
				kip.removeAll();
				
				if(orderPanelArr.size() > 12) { 
					kip.setSize(1000, 290*(1+(orderPanelArr.size()-1)/6));
					int high = (580-kip.getSize().height>kip.getY() ? 580-kip.getSize().height : kip.getY());
					kip.setLocation(0, high);
				} else {
					kip.setBounds(0, 0, 1000, 580);
				}
				
				for(int i=0 ; i<orderPanelArr.size() ; i++) {
					orderPanelArr.get(i).setBounds(170*(i%6), 290*(i/6), 150, 270);
					kip.add(orderPanelArr.get(i));
				}
				
				kip.repaint();
				
			}
		});
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		int setY = (kip.getBounds().y)+(-30)*e.getWheelRotation();
		if(setY>0) {
			setY = 0;
		}
		if(setY<580-kip.getSize().height) {
			setY = 580-kip.getSize().height; //size 610 sety 최소 -310 최대 0
		}
		kip.setBounds(0, setY, 1000, kip.getSize().height);
	}
	
	
	public static void main(String[] args) {
		new kitchen();
	}


}