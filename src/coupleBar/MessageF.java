package coupleBar;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessageF extends JFrame {
	int sendTableNum = -1, receiveTableNum = -1;
	String sendLog = "", receiveLog = "";
	JTextArea slta = new JTextArea("<보낸 쪽지 목록>");
	JTextArea rlta = new JTextArea("<받은 쪽지 목록>");
	
	public MessageF(int sendTableNum,int receiveTableNum) {
		super(sendTableNum + "번 테이블의 쪽지 함");
		setBounds(300, 200, 1000, 600);
		setLayout(null);
		
		JButton receiveListBtn = new JButton("받은 쪽지함");
		receiveListBtn.setBounds(50, 50, 200, 100);
		add(receiveListBtn);
		receiveListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rlta.append(receiveLog);
				slta.setVisible(false);
				rlta.setVisible(true);
			}
		});
		
		rlta.setBounds(300, 50, 650, 470);
		rlta.setFont(new Font("굴림", Font.TYPE1_FONT, 20));
		add(new JScrollPane(rlta),"Center");
		rlta.setEditable(false);
		rlta.setVisible(true);
		add(rlta);
		
		JButton sendListBtn = new JButton("보낸 쪽지함");
		sendListBtn.setBounds(50, 180, 200, 100);
		add(sendListBtn);
		sendListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slta.append(receiveLog);
				rlta.setVisible(false);
				slta.setVisible(true);
			}
		});
		
		slta.setBounds(300, 50, 650, 470);
		slta.setFont(new Font("굴림", Font.TYPE1_FONT, 20));
		slta.setEditable(false);
		add(new JScrollPane(slta),"Center");
		slta.setVisible(false);
		add(slta);
				
		JButton sendMessageBtn = new JButton("쪽지 보내기");
		sendMessageBtn.setBounds(50, 310, 200, 100);
		add(sendMessageBtn);
		sendMessageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new UniCastChatting();
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	DatagramSocket sender_socket = null;
	InetAddress sender_addr;

	JTextField tableNum, tf;
	JTextArea ta;
	JButton btnChat;
	
	public class UniCastChatting extends JFrame{
		
		class UniReceiver extends Thread{
			DatagramSocket socket = null;
			
			@Override
			public void run() {
				
				try {
					socket = new DatagramSocket(8888);
					while(true) {
										
						byte [] buf = new byte[1024];
						
						DatagramPacket dp = new DatagramPacket(buf, buf.length);
						
						socket.receive(dp);

						String msg = new String(buf);
						
						//System.out.println(dp.getAddress()+":"+msg);
						ta.append(dp.getAddress()+":"+msg+"\n");
						ta.setCaretPosition(ta.getDocument().getLength());
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}finally {
					
				}
			}
		}
		
		//UniSender us;
		UniReceiver ur;
		
		public UniCastChatting() {
			
			setBounds(50, 50, 400, 600);
			
			tableNum = new JTextField("테이블 숫자");
			btnChat = new JButton("연결");
			
			JPanel np = new JPanel();
			np.add(tableNum);
			np.add(btnChat);
			
			add(np,"North");
			
			ta = new JTextArea();
			ta.setEditable(false);
			add(new JScrollPane(ta),"Center");
			
			tf = new JTextField();
			tf.setEditable(false);
			add(tf,"South");
			
			

			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			tf.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					if(btnChat.getText().equals("연결종료")) { //연결 누르면 연결상태일때만 연결종료라고 나와서
						//연결 돼있을때만 텍스트 보내게 이거 안하면 에러남.
						try {
							byte [] buf = tf.getText().getBytes();
							
							DatagramPacket data = new DatagramPacket(
									buf, 
									buf.length, 
									sender_addr, 
									8888);
							
							sender_socket.send(data);
							
							ta.append("[나]: " + tf.getText() + "\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							tf.setText("");
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		
				}
			});
			
			
			btnChat.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(btnChat.getText().equals("연결")) {
					
						
						try {
							sender_socket = new DatagramSocket();
							switch(Integer.parseInt(tableNum.getText())) {
							case 15:
								sender_addr = InetAddress.getByName("192.168.20.30");
								break;
							default:
								sender_addr = InetAddress.getByName("192.168.20.30");
								break;
							}
							
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						ur = new UniReceiver();
						ur.start();
						
						tableNum.setEditable(false);
						
						btnChat.setText("연결종료");
						
						tf.setEditable(true);
						tf.requestFocus();
						
					}else {
						//us.stop();
						sender_socket.close();
						sender_socket = null; // 닫고 비워줘야 새로 받을 수 있음.
						
						ur.stop();
						ur.socket.close();
						ur.socket = null; // 끄고 비워줘야 새로 받을 수 있음.
						
						tableNum.setEditable(true);
						
						tf.setEditable(false);
						
						btnChat.setText("연결");
					}
					
				}
			});
		}
			
		}
	
	public static void main(String[] args) {
		new MessageF(14,15);
	}
}
