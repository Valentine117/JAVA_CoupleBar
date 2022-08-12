package coupleBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class TableOrder extends JFrame {
	
	String s = "green";	// 관리자 id
	String p = "1234";	// 관리자 비번
	String kitchenIP = "192.168.20.99";	// 주방 ip
	ArrayList<String> tableIP = new ArrayList<String>();	// 테이블 아이피 목록
	
	firstScreen fs;
	Counter ct;
	
	int tableNum = 29;	// 테이블 개수
	TableInfo[] tableInfoArr = new TableInfo[tableNum];	// 테이블별 인포 생성
	
	boolean openState;	// 영업중 여부
	String openDay;		// 영업일자
	String openTime;	// 오픈시간
	
	ArrayList<JLabel> stateArr;			// 주문창 상태표시줄
	ArrayList<JButton> tableBtnArr;		// 테이블버튼
	ArrayList<JLabel> tableBtnInfoArr;	// 테이블배치도 라벨
	Map<Integer,JButton> menuBtnMap;	// 메뉴id, 버튼 맵
	
	int moveNum = 0;	// 자리이동용 변수
	JLabel move;		// 자리이동시 표시될 라벨
	
	
	// 서식 정리
	Font f1 = new Font("맑은 고딕", Font.PLAIN, 15);
	Font f2 = new Font("맑은 고딕", Font.BOLD, 15);

	DecimalFormat dfmoney = new DecimalFormat("#,##0");
	DecimalFormat dfcard = new DecimalFormat("0000");
	
	SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm:ss");

	Color mainColor = new Color(225,231,251);

	LineBorder lb_black = new LineBorder(Color.black);
	LineBorder lb_white = new LineBorder(Color.white);
	LineBorder lb_main = new LineBorder(mainColor);
	
	
	// 로그인창
	public TableOrder() {
		
		for(int i=1 ; i<=29 ; i++) {
            if(i==4)tableIP.add("192.168.20.47");		// 4번 테이블
            else if(i==11)tableIP.add("192.168.20.38");	// 11번 테이블
            else if(i==20)tableIP.add("192.168.20.30");	// 20번 테이블
            else tableIP.add("192.168.20.99");			// 나머지 테이블
        }

		setTitle("로그인");
		JTextField id = new JTextField();
		JPasswordField pass = new JPasswordField();
		JLabel idl = new JLabel("ID");
		JLabel passl = new JLabel("PASSWORD");
		JPanel lop = new JPanel();
		JButton login = new JButton("로그인");
		JLabel user = new JLabel("관리자");

		setSize(250,200);
		setLocationRelativeTo(null);

		lop.setBounds(0, 0, 350, 200);
		lop.setBackground(Color.white);
		lop.setVisible(true);
		lop.setLayout(null);

		user.setBounds(100, 10, 100, 20);
		user.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));

		idl.setBounds(30, 50, 50, 20);
		passl.setBounds(30, 80, 100, 20);

		id.setBounds(110, 50, 100, 20);
		pass.setBounds(110, 80, 100, 20);

		login.setBounds(85, 120, 70, 30);
		login.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		login.setBackground(new Color(246, 246, 246));

		lop.add(user);
		lop.add(idl);
		lop.add(id);
		lop.add(passl);
		lop.add(pass);
		lop.add(login);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JDialog win = new JDialog();
				win.setSize(200, 80);
				win.setLayout(new FlowLayout());
				win.setLocationRelativeTo(null);

				JLabel jl = new JLabel();
				win.add(jl);
				JButton jb = new JButton("확인");
				jb.setBackground(Color.white);

				try {
					if(s.equals(id.getText())) {
						if(p.equals(pass.getText())) {
							// 같으면 로그인 성공
							jl.setText("로그인 성공");
							openState = false;
							for(int i = 0; i<tableInfoArr.length; i++) {
								TableInfo tt = new TableInfo(i+1, false);
								tableInfoArr[i] = tt;
							}
							fs = new firstScreen();
							dispose();
						}	
						else {
							//비번이 틀립니다.
							jl.setText("비밀번호 불일치");
							win.setVisible(true);
							win.add(jb);
						}
					}
					else {
						// 아이디가 틀립니다.
						jl.setText("아이디 불일치");
						win.setVisible(true);
						win.add(jb);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		pass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JDialog win = new JDialog();
				win.setSize(200, 80);
				win.setLayout(new FlowLayout());
				win.setLocationRelativeTo(null);

				JLabel jl = new JLabel();
				win.add(jl);
				JButton jb = new JButton("확인");
				jb.setBackground(Color.white);

				try {
					if(s.equals(id.getText())) {
						if(p.equals(pass.getText())) {
							// 같으면 로그인 성공
							jl.setText("로그인 성공");
							openState = false;
							for(int i = 0; i<tableInfoArr.length; i++) {
								TableInfo tt = new TableInfo(i+1, false);
								tableInfoArr[i] = tt;
							}
							fs = new firstScreen();
							dispose();
						}	
						else {
							//비번이 틀립니다.
							jl.setText("비밀번호 불일치");
							win.setVisible(true);
							win.add(jb);
						}
					}
					else {
						// 아이디가 틀립니다.
						jl.setText("아이디 불일치");
						win.setVisible(true);
						win.add(jb);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		setResizable(false);
		add(lop);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	// 메인화면
	class firstScreen extends JFrame{
		
		public firstScreen() {
			super("Main");
			setSize(400, 400);
			setLocationRelativeTo(null);
			getContentPane().setBackground(new Color(255, 251, 255));
			setLayout(null);

			new UniReceiver(8888).start();

			ImageIcon first_img = new ImageIcon("./img/jjack.png");
			JLabel first_label = new JLabel(first_img);
			first_label.setBounds(50, 100, 150, 150);

			JButton open = new JButton("영업시작");
			open.setBounds(250, 50, 100, 50);
			open.setBackground(Color.white);
			open.setFocusable(false);
			open.setFont(f2);
			open.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!openState) {
						openState = true;
						ct = new Counter();
						ct.setVisible(true);
						JOptionPane.showMessageDialog(null, "<html><body>　"+daySdf.format(new Date()) + " " + timeSdf.format(new Date())+"<br>　 영업을 시작하였습니다.</body></html>");
					} else {
						ct.setVisible(true);
					}
					setVisible(false);
				}
			});

			JButton close = new JButton("영업마감");
			close.setBounds(250, 120, 100, 50);
			close.setBackground(Color.white);
			close.setFocusable(false);
			close.setFont(f2);
			close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int seated = 0;
					for(TableInfo ti : tableInfoArr) {
						if(ti.isSeated) seated =+ 1;
					}
					if(!openState) {
						JOptionPane.showMessageDialog(null, "영업중이 아닙니다.");
					} else if(seated>0) {
						JOptionPane.showMessageDialog(null, "이용중인 테이블이 있습니다.");
					} else {
						new SalesClose();
						openState = false;
						ct.dispose();
					}
				}
			});

			JButton sales = new JButton("매출현황");
			sales.setBounds(250, 190, 100, 50);
			sales.setBackground(Color.white);
			sales.setFocusable(false);
			sales.setFont(f2);
			sales.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					new SalesDay();
				}
			});

			JButton inventory = new JButton("재고관리");
			inventory.setBounds(250, 260, 100, 50);
			inventory.setBackground(Color.white);
			inventory.setFocusable(false);
			inventory.setFont(f2);
			inventory.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Management();
				}
			});

			add(open);
			add(close);
			add(sales);
			add(inventory);
			add(first_label);

			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setResizable(false);
			setVisible(true);
		}
	}	
		
	// 테이블 선택 화면
	class Counter extends JFrame {

		public Counter() {

			super("카운터");
			setSize(1316,859);
			setLocationRelativeTo(null);
			Container con = getContentPane();
			con.setBackground(Color.white);
			setLayout(null);

			openDay = daySdf.format(new Date());	// 영업일 저장
			openTime = timeSdf.format(new Date());	// 오픈시간 저장

			// 벽세우기
			ArrayList<JPanel> wallArr = new ArrayList<JPanel>();
			for (int i=0 ; i<11 ; i++) {
				JPanel wallP = new JPanel();
				wallArr.add(wallP);
			}
			
			wallArr.get(0).setBounds(30, 30, 1120, 20);
			wallArr.get(1).setBounds(30, 50, 20, 720);
			wallArr.get(2).setBounds(30, 770, 1220, 20);
			wallArr.get(3).setBounds(1250, 30, 20, 760);
			wallArr.get(4).setBounds(290, 400, 580, 20);
			wallArr.get(5).setBounds(870, 280, 20, 260);
			wallArr.get(6).setBounds(50, 220, 120, 20);
			wallArr.get(7).setBounds(50, 580, 120, 20);
			wallArr.get(8).setBounds(270, 280, 20, 260);
			wallArr.get(9).setBounds(990, 50, 20, 120);
			wallArr.get(10).setBounds(990, 650, 20, 120);
			
			for (int i=0 ; i<11 ; i++) {
				wallArr.get(i).setBackground(mainColor);
				add(wallArr.get(i));
			}

			
			// 테이블 배치
			tableBtnArr = new ArrayList<JButton>();
			
			JPanel jp1 = new JPanel();
			jp1.setBounds(50, 50, 940, 120);
			jp1.setBackground(mainColor);
			jp1.setLayout(new GridLayout(1, 8, 20, 0));
			for(int i=8 ; i>0 ; i--) {
				JButton table_i = new JButton(i+"");
				table_i.setSize(100, 120);
				jp1.add(table_i);
				tableBtnArr.add(table_i);
			}
			add(jp1);

			JPanel jp2 = new JPanel();
			jp2.setBounds(50, 240, 120, 340);
			jp2.setBackground(mainColor);
			jp2.setLayout(new GridLayout(3, 1, 0, 20));
			for(int i=9 ; i<=11 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setSize(120, 100);
				jp2.add(table_i);
				tableBtnArr.add(table_i);
			}
			add(jp2);

			JPanel jp3 = new JPanel();
			jp3.setBounds(50, 650, 940, 120);
			jp3.setBackground(mainColor);
			jp3.setLayout(new GridLayout(1, 8, 20, 0));
			for(int i=12 ; i<=19 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setSize(100, 120);
				jp3.add(table_i);
				tableBtnArr.add(table_i);
			}
			add(jp3);

			JPanel jp4 = new JPanel();
			jp4.setBounds(290, 420, 580, 120);
			jp4.setBackground(mainColor);
			jp4.setLayout(new GridLayout(1, 5, 20, 0));
			for(int i=24 ; i>=20 ; i--) {
				JButton table_i = new JButton(i+"");
				table_i.setSize(100, 120);
				jp4.add(table_i);
				tableBtnArr.add(table_i);
			}
			add(jp4);

			JPanel jp5 = new JPanel();
			jp5.setBounds(290, 280, 580, 120);
			jp5.setBackground(mainColor);
			jp5.setLayout(new GridLayout(1, 5, 20, 0));
			for(int i=25 ; i<=29 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setSize(100, 120);
				jp5.add(table_i);
				tableBtnArr.add(table_i);
			}
			add(jp5);

			// 셀프바
			JPanel selfbarplace = new JPanel();
			selfbarplace.setBounds(890, 280, 120, 260);
			selfbarplace.setBackground(new Color(255, 218, 195));
			selfbarplace.setLayout(null);
			selfbarplace.setBorder(new LineBorder(new Color(255, 218, 195)));
			ImageIcon drinks = new ImageIcon("./img/drinks.png");
			JLabel selfbar = new JLabel(drinks);
			selfbar.setBounds(25, 80, 64, 64);
			selfbarplace.add(selfbar);
			JLabel selfbarStr = new JLabel("셀프바");
			selfbarStr.setBounds(0, 130, 120, 50);
			selfbarStr.setHorizontalAlignment(JLabel.CENTER);
			selfbarStr.setFont(f2);
			selfbarplace.add(selfbarStr);
			add(selfbarplace);

			// 남자화장실
			JPanel menstoiletplace = new JPanel();
			menstoiletplace.setBounds(1100, 280, 150, 185);
			menstoiletplace.setLayout(null);
			menstoiletplace.setBackground(new Color(193, 223, 255));
			menstoiletplace.setBorder(new LineBorder(new Color(193, 223, 255)));
			ImageIcon toilet_img = new ImageIcon("./img/toilet.png");
			JLabel toilet_label = new JLabel(toilet_img);
			toilet_label.setBounds(43, 50, 64, 64);
			menstoiletplace.add(toilet_label);
			JLabel menstoiletlabel = new JLabel("남자화장실");
			menstoiletlabel.setBounds(0, 100, 150, 50);
			menstoiletlabel.setHorizontalAlignment(JLabel.CENTER);
			menstoiletlabel.setFont(f2);
			menstoiletplace.add(menstoiletlabel);
			add(menstoiletplace);
			
			// 여자화장실
			JPanel ladiestoiletplace = new JPanel();
			ladiestoiletplace.setBounds(1100, 465, 150, 185);
			ladiestoiletplace.setLayout(null);
			ladiestoiletplace.setBackground(Color.pink);
			ladiestoiletplace.setBorder(new LineBorder(Color.pink));
			JLabel toilet_label_1 = new JLabel(toilet_img);
			toilet_label_1.setBounds(43, 50, 64, 64);
			ladiestoiletplace.add(toilet_label_1);
			JLabel ladiestoiletlabel = new JLabel("여자화장실");
			ladiestoiletlabel.setBounds(0, 100, 150, 50);
			ladiestoiletlabel.setHorizontalAlignment(JLabel.CENTER);
			ladiestoiletlabel.setFont(f2);
			ladiestoiletplace.add(ladiestoiletlabel);
			add(ladiestoiletplace);

			// 흡연실
			JPanel smokeplace = new JPanel();
			smokeplace.setBounds(1100, 650, 150, 120);
			smokeplace.setLayout(null);
			smokeplace.setBorder(new LineBorder(new Color(238, 238, 238)));
			ImageIcon smoke_img = new ImageIcon("./img/smoke.png");
			JLabel smoke_label = new JLabel(smoke_img);
			smoke_label.setBounds(43, 18, 64, 64);
			smokeplace.add(smoke_label);
			JLabel smoking = new JLabel("흡연실");
			smoking.setBounds(0, 82, 150, 20);
			smoking.setHorizontalAlignment(JLabel.CENTER);
			smoking.setFont(f2);
			smokeplace.add(smoking);
			add(smokeplace);

			// 카운터
			JPanel counterplace = new JPanel();
			counterplace.setBounds(1010, 50, 120, 120);
			counterplace.setBackground(new Color(255, 233, 146));
			counterplace.setLayout(null);
			counterplace.setBorder(new LineBorder(new Color(255, 233, 146)));
			ImageIcon counter_img = new ImageIcon("./img/counter.png");
			JLabel counter_label = new JLabel(counter_img);
			counter_label.setBounds(28, 18, 64, 64);
			counterplace.add(counter_label);
			JLabel countlabel = new JLabel("계산대");
			countlabel.setBounds(0, 80, 120, 20);
			countlabel.setFont(f2);
			countlabel.setHorizontalAlignment(JLabel.CENTER);
			counterplace.add(countlabel);
			add(counterplace);
			
			// exit
			JPanel exitPanel = new JPanel();
			exitPanel.setBounds(1150, 30, 100, 140);
			exitPanel.setLayout(null);
			exitPanel.setBackground(new Color(195,255,202));
			ImageIcon exit_img = new ImageIcon("./img/exit.png");
			JButton exit = new JButton(exit_img);
			exit.setBounds(10, 30, 80, 80);
			exit.setBorderPainted(false);
			exit.setBackground(new Color(195, 255, 202));
			JLabel exit_label = new JLabel("나가기");
			exit_label.setBounds(30, 110, 100, 20);
			exit_label.setFont(f2);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (moveNum == 0)  {	// 버튼기능 - 메인화면으로 나가기
						fs.setVisible(true);
						setVisible(false);
					} else if (moveNum != 0) {	// 버튼기능 - 자리이동 취소
						new Order(moveNum);
						move.setVisible(false);
						setVisible(false);
					}
				}
			});
			exitPanel.add(exit_label);
			exitPanel.add(exit);
			add(exitPanel);
			
			// 자리이동시 표시할 라벨
			move = new JLabel("<html><body>이동할 테이블을 선택해주세요<br><br><br><br>"
					+ "<br><br><br><br><br>이동할 테이블을 선택해주세요</body></html>");
			move.setBounds(400, 160, 450, 500);
			move.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			move.setVisible(false);
			add(move);

			// 테이블배치도 라벨
			tableBtnInfoArr = new ArrayList<JLabel>();
			for(int i=0 ; i<tableBtnArr.size() ; i++) {
				String num = tableBtnArr.get(i).getText();
				tableBtnArr.get(i).setBorder(lb_main);
				tableBtnArr.get(i).setBackground(Color.white);
				tableBtnArr.get(i).setForeground(Color.white);
				tableBtnArr.get(i).setLayout(null);
				tableBtnArr.get(i).setFocusable(false);
				JLabel jlTNum_i = new JLabel(num);
				jlTNum_i.setBounds(8, 8, 20, 20);
				jlTNum_i.setFont(f2);
				JLabel jlBtn_i = new JLabel("<html><body>남 " + tableInfoArr[i].boy_Num + " 여 " + tableInfoArr[i].girl_Num 
						+ "<br><br>Total : " + dfmoney.format(tableInfoArr[i].calcTotalPrice()) + "</body></html>");
				jlBtn_i.setBounds(8, tableBtnArr.get(i).getHeight()-55, 100, 50);
				jlBtn_i.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
				tableBtnInfoArr.add(jlBtn_i);
				tableBtnArr.get(i).add(jlTNum_i);
				tableBtnArr.get(i).add(jlBtn_i);
			}

			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			// 테이블버튼 액션리스너
			for(int i=0 ; i<tableBtnArr.size() ; i++) {
				tableBtnArr.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						int num_T = Integer.parseInt(e.getActionCommand());	// 테이블 번호
						
						if (moveNum == 0)  {	// 버튼기능 - 오더창
							moveNum = num_T;
							new Order(num_T);
							setVisible(false);
							
						} else {	// 버튼기능 - 자리이동
							if(!tableInfoArr[num_T-1].isSeated) {	// 클릭한 자리가 이용중인 좌석이 아니라면
								move.setVisible(false);
								tableInfoArr[num_T-1].tableInfo(tableInfoArr[moveNum-1].boy_Num, tableInfoArr[moveNum-1].girl_Num);
								tableInfoArr[num_T-1].OrderMap.putAll(tableInfoArr[moveNum-1].OrderMap);
								tableInfoArr[num_T-1].getMoney = tableInfoArr[moveNum-1].getMoney;
								tableInfoArr[num_T-1].entrance = tableInfoArr[moveNum-1].entrance;
								
								tableInfoArr[moveNum-1].leave();
								
								for(int i=0 ; i<tableBtnArr.size() ; i++) {
									int tableArrIndex = Integer.parseInt(tableBtnArr.get(i).getText())-1;	// 테이블버튼 인덱스
									tableBtnInfoArr.get(tableArrIndex).setText("<html><body>남 " + tableInfoArr[i].boy_Num + " 여 " + tableInfoArr[i].girl_Num 
											+ "<br><br>Total : " + dfmoney.format(tableInfoArr[i].calcTotalPrice()) + "</body></html>");
									tableBtnArr.get(tableArrIndex).setBackground(tableInfoArr[i].tableColor);
									tableBtnArr.get(tableArrIndex).setForeground(tableInfoArr[i].tableColor);
								}

								moveNum = num_T;
								new Order(num_T);
								setVisible(false);
								
								for(String ip : tableIP) {	// 인원정보 각 테이블 전송
									new UniSendtoTableInfo(ip, 8888).start();
								}
								
							} else {
								JOptionPane.showMessageDialog(null, "이미 이용중인 좌석입니다.");
							}
							
						}
					}
				});
			}

		}

	}

	// 주문화면
	class Order extends JFrame implements MouseWheelListener {

		int tableNum;						// 테이블 번호
		int tableArrIndex;					// 테이블버튼 어레이 인덱스
		JPanel orderScrollPanel;			// 주문내역 표시패널
		ButtonGroup orderButtonGroup;		// 주문내역 토글버튼그룹
		JPanel categoryP;					// 카테고리 패널
		ArrayList<JPanel> catArr;			// 카테고리별 메뉴 패널 arr
		ArrayList<JToggleButton> ctbArr;	// 카테고리버튼 arr
		Map<Integer, Integer> orderM;		// 임시 주문내역(Order창을 킬때마다 생성), 주문하기 버튼늘 누르면 TableInfo로 전송
		ArrayList<JToggleButton> orderButtonArr;	//	주문내역 토글버튼 arr
		Map<JToggleButton, ArrayList> orderBtnM;	// 주문내역 토글버튼 + 라벨 어레이
		ArrayList<JLabel> orderTotArr;		// 주문내역 금액총합 라벨어레이
		
		public Order(int tableNum) {
			
			super("주문하기");
			this.tableNum = tableNum;	
			setBounds(400, 150, 1015, 694);	// 실 사이즈 1000*655
			Container con = getContentPane();
			con.setBackground(mainColor);
			setLayout(null);
			
			tableArrIndex = Integer.parseInt(tableBtnArr.get(tableNum-1).getText())-1;
			orderM = new LinkedHashMap<Integer, Integer>();
			orderButtonArr = new ArrayList<JToggleButton>();
			orderButtonGroup = new ButtonGroup();
			orderBtnM = new HashMap<JToggleButton, ArrayList>();
			orderTotArr = new ArrayList<JLabel>();
			menuBtnMap = new LinkedHashMap<Integer, JButton>();
			catArr = new ArrayList<JPanel>();
			ctbArr = new ArrayList<JToggleButton>();

			JLabel title = new JLabel("Order");
			title.setBounds(30, 30, 150, 40);
			title.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 40));
			title.setForeground(new Color(30, 38, 80));
			add(title);	

			// 상태표시줄
			JPanel statePanel = new JPanel();
			statePanel.setBounds(200, 30, 715, 40);
			statePanel.setBackground(Color.black);
			statePanel.setLayout(null);
			stateArr = new ArrayList<JLabel>();
			for(int i=0 ; i<8 ; i++) {
				String [] labelStr = {"영업일자 : ", openDay, "테이블 : ", (tableNum+""),
						"인원 : ", "남 " + tableInfoArr[tableNum-1].boy_Num + " 여 " + tableInfoArr[tableNum-1].girl_Num,
						"입장시간 : ", (tableInfoArr[tableNum-1].isSeated ? timeSdf.format(tableInfoArr[tableNum-1].entrance) : "-")};
				int [] labelWidth = {30, 105, 265, 325, 400, 445, 550, 625, 715};
				JLabel label = new JLabel(labelStr[i]);
				label.setBounds(labelWidth[i], 0, labelWidth[i+1]-labelWidth[i], 40);
				label.setFont(f1);
				label.setForeground(Color.yellow);
				stateArr.add(label);
				statePanel.add(label);
			}
			add(statePanel);

			// 탈출버튼
			ImageIcon return_img = new ImageIcon("./img/return.png");
			JButton exit = new JButton(return_img);
			exit.setBounds(925, 30, 40, 40);
			exit.setBackground(mainColor);
			exit.setFocusable(false);
			exit.setBorderPainted(false);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					for(int i=0 ; i<tableBtnArr.size() ; i++) {	// 모든 테이블 인원정보, 주문가격 업데이트
						int num = Integer.parseInt(tableBtnArr.get(i).getText())-1;
						tableBtnInfoArr.get(num).setText("<html><body>남 " + tableInfoArr[i].boy_Num + " 여 " + tableInfoArr[i].girl_Num 
								+ "<br><br>Total : " + dfmoney.format(tableInfoArr[i].calcTotalPrice()) + "</body></html>");
						tableBtnArr.get(num).setBackground(tableInfoArr[i].tableColor);
						tableBtnArr.get(num).setForeground(tableInfoArr[i].tableColor);
					}
					moveNum = 0;
					ct.setVisible(true);
					dispose();
				}
			});
			add(exit);
			
			// 주문내역
			orderM.putAll(tableInfoArr[tableNum-1].OrderMap);	// 기존 주문내역에 개수 0인거 제거
			for (Entry<Integer, Integer> od : orderM.entrySet()) {
				if(od.getValue()==0) {
					tableInfoArr[tableNum-1].OrderMap.remove(od.getKey());
				}
			}
			orderM.clear();
			orderM.putAll(tableInfoArr[tableNum-1].OrderMap);

			JPanel orderP = new JPanel();
			orderP.setBounds(30, 90, 500, 370);
			orderP.setLayout(null);
			orderP.setBorder(lb_black);
						
			JPanel orderP_1 = new JPanel();
			orderP_1.setBounds(1, 1, 498, 29);
			orderP_1.setBackground(Color.black);
			orderP_1.setLayout(null);
			for(int i=0 ; i<6 ; i++) {
				int [] nameLabelWidth = {0, 40, 238, 318, 378, 458, 498};
				JLabel nameLabel = new JLabel("No/메 뉴 명/단가/수량/금액/비고".split("/")[i]);
				nameLabel.setBounds(nameLabelWidth[i], 0, nameLabelWidth[i+1]-nameLabelWidth[i], 30);
				nameLabel.setFont(f1);
				nameLabel.setHorizontalAlignment(JLabel.CENTER);
				nameLabel.setForeground(Color.yellow);
				nameLabel.setBorder(lb_white);
				orderP_1.add(nameLabel);
			}
			orderP.add(orderP_1);
			
			JPanel orderP_2 = new JPanel();
			orderP_2.setBounds(1, 30, 498, 310);
			orderP_2.setLayout(null);
			orderP.add(orderP_2);

			int high = 310 + (orderM.size()>10 ? (orderM.size()-10)*30 : 0);	// 스크롤패널 기본사이즈(=310)
			orderScrollPanel = new JPanel();
			orderScrollPanel.setBounds(1, 0, 498, high);
			orderScrollPanel.setBackground(Color.white);
			orderScrollPanel.setLayout(null);
			for(int i=0 ; i<orderM.size() ; i++) {	// 주문내역 개수만큼 토글버튼 생성
				JToggleButton menuBtn = new JToggleButton();
				menuBtn.setBounds(0, 30*i, 498, 30);
				menuBtn.setLayout(null);
				menuBtn.setBackground(Color.white);
				menuBtn.setBorderPainted(false);
				ArrayList<JLabel> btnLabelArr = new ArrayList<JLabel>();
				for(int j=0 ; j<6 ; j++) {
					int menuNo = (int) orderM.keySet().toArray()[i];
					String menuName = new MenuDAO().detail(menuNo).getName();
					String menuPrice = dfmoney.format(new MenuDAO().detail(menuNo).getPrice())+"　";
					int qty = orderM.get(menuNo);
					String total = dfmoney.format(new MenuDAO().detail(menuNo).getPrice()*qty)+"　";
					
					String [] labelStr = {i+1+"", menuName, menuPrice, qty+"", total, (qty>0 ? "" : "취소")};
					int [] labelWidth = {0, 40, 238, 318, 378, 458, 498};
					
					JLabel label = new JLabel(labelStr[j]);
					label.setBounds(labelWidth[j], 0, labelWidth[j+1]-labelWidth[j], 30);
					label.setFont(f1);
					label.setHorizontalAlignment(JLabel.CENTER);
					btnLabelArr.add(label);
					menuBtn.add(label);
				}
				btnLabelArr.get(2).setHorizontalAlignment(JLabel.RIGHT);
				btnLabelArr.get(4).setHorizontalAlignment(JLabel.RIGHT);
				orderBtnM.put(menuBtn, btnLabelArr);
				orderScrollPanel.add(menuBtn);
				orderButtonGroup.add(menuBtn);
				orderButtonArr.add(menuBtn);
			}
			orderP_2.add(orderScrollPanel);
			
			JPanel orderP_3 = new JPanel();
			orderP_3.setBounds(1, 339, 498, 29);
			orderP_3.setBackground(Color.black);
			orderP_3.setLayout(null);
			for(int i=0 ; i<4 ; i++) {
				String [] labelStr = {"합  계", calcTotalQty()+"", dfmoney.format(calcTotalPrice()), ""};
				int [] labelWidth = {0, 318, 378, 458, 498};
				JLabel label = new JLabel(labelStr[i]);
				label.setBounds(labelWidth[i], 0, labelWidth[i+1]-labelWidth[i], 30);
				label.setFont(f1);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setForeground(Color.yellow);
				label.setBorder(lb_white);
				orderP_3.add(label);
				orderTotArr.add(label);
			}
			orderP.add(orderP_3);
			
			add(orderP);
			
			// 제어 버튼
			ArrayList<JButton> fBtnArr = new ArrayList<JButton>();
			JPanel fBtnP = new JPanel();
			fBtnP.setBounds(30, 480, 500, 145);
			fBtnP.setBackground(mainColor);
			fBtnP.setLayout(new GridLayout(2, 5, 5, 5));
			for(int i=0 ; i<10 ; i++) {
				String [] fBtnStr = {"전체취소", "선택취소", "수량변경", "+", "ㅡ",
						"카드결제", "예비", "자리이동", (!tableInfoArr[tableNum-1].isSeated ? "손님입장" : "인원변경" ), "주문하기"};
				JButton fBtn = new JButton(fBtnStr[i]);
				fBtn.setBackground(Color.white);
				fBtn.setFocusable(false);
				fBtn.setFont(f2);
				fBtn.setHorizontalAlignment(JLabel.CENTER);
				fBtn.setBorderPainted(false);
				fBtnP.add(fBtn);
				fBtnArr.add(fBtn);
			}
			add(fBtnP);
			fBtnArr.get(3).setFont(new Font("맑은 고딕", Font.BOLD, 35));
			fBtnArr.get(4).setFont(new Font("맑은 고딕", Font.BOLD, 25));
			fBtnArr.get(9).setBackground(Color.yellow);
			
			// 결제창 패널
			ArrayList<JLabel> payArr = new ArrayList<JLabel>();	
			JPanel payP = new JPanel();
			payP.setBounds(550, 90, 415, 535);
			payP.setLayout(null);
			payP.setBackground(Color.black);
			JPanel payP_1 = new JPanel();
			payP_1.setBounds(5, 5, 405, 525);
			payP_1.setLayout(null);
			payP_1.setBackground(mainColor);
			
			JLabel payL_1 = new JLabel("카드 결제");
			payL_1.setBounds(25, 20, 100, 25);
			payL_1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			payP_1.add(payL_1);

			ImageIcon return_img2 = new ImageIcon("./img/return2.png");
			JButton payBtn_1 = new JButton(return_img2);
			payBtn_1.setBounds(350, 15, 35, 35);
			payBtn_1.setBackground(mainColor);
			payBtn_1.setBorderPainted(false);
			payBtn_1.setFocusable(false);
			payP_1.add(payBtn_1);
			payBtn_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					categoryP.setVisible(true);
					catArr.get(0).setVisible(true);
					for(int i=0 ; i<ctbArr.size() ; i++) {
						if(ctbArr.get(i).isSelected() == true) {
							catArr.get(0).setVisible(false);
							catArr.get(i).setVisible(true);
						}
					}
					payP.setVisible(false);
				}
			});

			JPanel payP_2 = new JPanel();
			payP_2.setBounds(20, 70, 365, 95);
			payP_2.setLayout(null);
			payP_2.setBackground(Color.black);
			for (int i=0 ; i<8 ; i++) {
				JLabel payL_i = new JLabel("총 금 액/0/받은금액/0/할인금액/0/받을금액/0".split("/")[i]);
				payL_i.setBounds(17+87*(i%4), 5+45*(i/4), 70-((i%2)*2-1)*5, 40);
				payL_i.setFont(f2);
				payL_i.setForeground(Color.yellow);
				payL_i.setHorizontalAlignment(JLabel.RIGHT);
				if(i%2==0) {
					payL_i.setHorizontalAlignment(JLabel.LEFT);
					payL_i.setFont(f1);
				}
				payArr.add(payL_i);
				payP_2.add(payL_i);
			}
			payP_1.add(payP_2);
			
			JLabel payL_2 = new JLabel("결제 대상금액");
			payL_2.setBounds(70, 190, 100, 25);
			payL_2.setFont(f1);
			payP_1.add(payL_2);
			
			JButton payBtn_2 = new JButton("0");
			payBtn_2.setBounds(215, 190, 120, 25);
			payBtn_2.setBackground(Color.white);
			payBtn_2.setFont(f1);
			payBtn_2.setHorizontalAlignment(JButton.RIGHT);
			payBtn_2.setBorderPainted(false);
			payBtn_2.setFocusable(false);
			payP_1.add(payBtn_2);
			payBtn_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFrame key = new JFrame("NumPad");
					key.setBounds(1050, 350, 245, 400);
					JPanel back = new JPanel();
					back.setBackground(mainColor);
					back.setLayout(null);
					JTextField numArea = new JTextField();
					numArea.setBounds(5, 5, 220, 50);
					numArea.setFont(new Font("맑은 고딕", Font.BOLD, 40));
					numArea.setHorizontalAlignment(JTextField.RIGHT);
					back.add(numArea);
					JPanel numKeyP = new JPanel();
					numKeyP.setBounds(5,60,220,295);
					numKeyP.setBackground(mainColor);
					numKeyP.setLayout(new GridLayout(4,3,5,5));
					back.add(numKeyP);
					ArrayList<JButton> numKeyArr = new ArrayList<JButton>();
					for(int i=0 ; i<12 ; i++) {
						JButton numBtn_i = new JButton("7,8,9,4,5,6,1,2,3,◀,0,OK".split(",")[i]);
						numBtn_i.setBackground(Color.white);
						numBtn_i.setFont(new Font("맑은 고딕", Font.BOLD, 40));
						numBtn_i.setFocusable(false);
						numKeyP.add(numBtn_i);
						numKeyArr.add(numBtn_i);
						if(i!=9 && i!=11) {
							numBtn_i.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									numArea.setText(numArea.getText()+numBtn_i.getText());
								}
							});
						}
					}
					key.add(back);
					key.setVisible(true);
					key.setResizable(false);
					
					numKeyArr.get(9).setFont(new Font("맑은 고딕", Font.BOLD, 36));
					numKeyArr.get(11).setFont(new Font("맑은 고딕", Font.BOLD, 25));

					numKeyArr.get(9).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(!numArea.getText().equals("")) {
								numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
							}
						}
					});
					
					numKeyArr.get(11).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								int money = Integer.parseInt(numArea.getText());
								key.dispose();
								if(money>0 && money<=calcTotalPrice()-tableInfoArr[tableNum-1].getMoney) {
									payBtn_2.setText(dfmoney.format(money));
								}
							} catch(Exception e1) {
								
							}
						}
					});

					numArea.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								int money = Integer.parseInt(numArea.getText());
								key.dispose();
								if(money>0 && money<=calcTotalPrice()-tableInfoArr[tableNum-1].getMoney) {
									payBtn_2.setText(dfmoney.format(money));
								}
							} catch(Exception e1) {
								
							}
						}
					});
				}
			});
			
			JLabel payL_3 = new JLabel("카드 정보");
			payL_3.setBounds(70, 230, 100, 25);
			payL_3.setFont(f1);
			payP_1.add(payL_3);
			
			JButton payBtn_3 = new JButton();
			payBtn_3.setBounds(155, 230, 180, 25);
			payBtn_3.setBackground(Color.white);
			payBtn_3.setFont(f1);
			payBtn_3.setHorizontalAlignment(JButton.CENTER);
			payBtn_3.setBorderPainted(false);
			payBtn_3.setFocusable(false);
			payP_1.add(payBtn_3);
			payBtn_3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFrame key = new JFrame("NumPad");
					key.setBounds(650, 400, 472, 250);
					JPanel back = new JPanel();
					back.setBackground(mainColor);
					back.setLayout(null);
					JTextField numArea = new JTextField();
					numArea.setBounds(5, 5, 445, 50);
					numArea.setFont(new Font("맑은 고딕", Font.BOLD, 40));
					numArea.setHorizontalAlignment(JTextField.CENTER);
					numArea.setEnabled(false);
					numArea.setDisabledTextColor(Color.black);
					back.add(numArea);
					JPanel numKeyP = new JPanel();
					numKeyP.setBounds(5,60,445,145);
					numKeyP.setBackground(mainColor);
					numKeyP.setLayout(new GridLayout(2,6,5,5));
					back.add(numKeyP);
					ArrayList<JButton> numKeyArr = new ArrayList<JButton>();
					for(int i=0 ; i<12 ; i++) {
						JButton numBtn_i = new JButton("1,2,3,4,5,◀,6,7,8,9,0,OK".split(",")[i]);
						numBtn_i.setBackground(Color.white);
						numBtn_i.setFont(new Font("맑은 고딕", Font.BOLD, 40));
						numBtn_i.setFocusable(false);
						numKeyP.add(numBtn_i);
						numKeyArr.add(numBtn_i);
						if(i!=5 && i!=11) {
							numBtn_i.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(numArea.getText().length()<19) {
										if(numArea.getText().length()==4) {
											numArea.setText(numArea.getText()+"-"+numBtn_i.getText());
										} else if(numArea.getText().length()==9) {
											numArea.setText(numArea.getText()+"-"+numBtn_i.getText());
										} else if(numArea.getText().length()==14) {
											numArea.setText(numArea.getText()+"-"+numBtn_i.getText());
										} else {
											numArea.setText(numArea.getText()+numBtn_i.getText());
										}
									}
								}
							});
						}
					}
					key.add(back);
					key.setVisible(true);
					key.setResizable(false);
					
					numKeyArr.get(5).setFont(new Font("맑은 고딕", Font.BOLD, 36));
					numKeyArr.get(11).setFont(new Font("맑은 고딕", Font.BOLD, 25));

					numKeyArr.get(5).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(!numArea.getText().equals("")) {
								numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
								if(numArea.getText().length()==15) {
									numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
								} else if(numArea.getText().length()==10) {
									numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
								} else if(numArea.getText().length()==5) {
									numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
								}
							}
						}
					});
					
					numKeyArr.get(11).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String card = numArea.getText();
							if(card.length()==19) {
								payBtn_3.setText(card);
								key.dispose();
							}
						}
					});
					
				}
			});
			
			JLabel payL_4 = new JLabel("할부 개월");
			payL_4.setBounds(70, 260, 100, 25);
			payL_4.setFont(f1);
			payP_1.add(payL_4);
			
			JButton payBtn_4 = new JButton("일시불");
			payBtn_4.setBounds(235, 260, 100, 25);
			payBtn_4.setBackground(Color.white);
			payBtn_4.setFont(f1);
			payBtn_4.setHorizontalAlignment(JButton.CENTER);
			payBtn_4.setBorderPainted(false);
			payBtn_4.setFocusable(false);
			payP_1.add(payBtn_4);
			payBtn_4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFrame select = new JFrame("할부개월");
					select.setBounds(1050, 350, 245, 370);
					JPanel back = new JPanel();
					back.setBackground(mainColor);
					back.setLayout(null);
					JPanel btnP = new JPanel();
					btnP.setBounds(5, 5, 220, 320);
					btnP.setBackground(mainColor);
					btnP.setLayout(new GridLayout(5,1,5,5));
					ArrayList<JButton> btnArr = new ArrayList<JButton>();
					for(int i=0 ; i<5 ; i++) {
						JButton btn_i = new JButton("일시불,3개월,6개월,12개월,직접입력".split(",")[i]);
						btn_i.setFont(f2);
						btn_i.setBackground(Color.white);
						btn_i.setFocusable(false);
						btnP.add(btn_i);
						btnArr.add(btn_i);
					}
					back.add(btnP);
					select.add(back);
					select.setVisible(true);
					select.setResizable(false);
					
					for(int i=0 ; i<4 ; i++) {
						btnArr.get(i).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								payBtn_4.setText(e.getActionCommand());
								select.dispose();
							}
						});
					}
					
					btnArr.get(4).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							select.dispose();
							
							JFrame key = new JFrame("NumPad");
							key.setBounds(1050, 350, 245, 400);
							JPanel back = new JPanel();
							back.setBackground(mainColor);
							back.setLayout(null);
							JTextField numArea = new JTextField();
							numArea.setBounds(5, 5, 220, 50);
							numArea.setFont(new Font("맑은 고딕", Font.BOLD, 40));
							numArea.setHorizontalAlignment(JTextField.RIGHT);
							back.add(numArea);
							JPanel numKeyP = new JPanel();
							numKeyP.setBounds(5,60,220,295);
							numKeyP.setBackground(mainColor);
							numKeyP.setLayout(new GridLayout(4,3,5,5));
							back.add(numKeyP);
							ArrayList<JButton> numKeyArr = new ArrayList<JButton>();
							for(int i=0 ; i<12 ; i++) {
								JButton numBtn_i = new JButton("7,8,9,4,5,6,1,2,3,◀,0,OK".split(",")[i]);
								numBtn_i.setBackground(Color.white);
								numBtn_i.setFont(new Font("맑은 고딕", Font.BOLD, 40));
								numBtn_i.setFocusable(false);
								numKeyP.add(numBtn_i);
								numKeyArr.add(numBtn_i);
								if(i!=9 && i!=11) {
									numBtn_i.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											numArea.setText(numArea.getText()+numBtn_i.getText());
										}
									});
								}
							}
							key.add(back);
							key.setVisible(true);
							key.setResizable(false);
							
							numKeyArr.get(9).setFont(new Font("맑은 고딕", Font.BOLD, 36));
							numKeyArr.get(11).setFont(new Font("맑은 고딕", Font.BOLD, 25));

							numKeyArr.get(9).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(!numArea.getText().equals("")) {
										numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
									}
								}
							});
							
							numKeyArr.get(11).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int monthly = Integer.parseInt(numArea.getText());
										key.dispose();
										if(monthly>0 && monthly<=60) {
											payBtn_4.setText(monthly + "개월");
										}
									} catch(Exception e1) {
										
									}
								}
							});

							numArea.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int monthly = Integer.parseInt(numArea.getText());
										key.dispose();
										if(monthly>0 && monthly<=60) {
											payBtn_4.setText(monthly + "개월");
										}
									} catch(Exception e1) {
										
									}
								}
							});
							
							
						}
					});
				}
			});
			
			JLabel payL_5 = new JLabel("서명");
			payL_5.setBounds(70, 305, 100, 25);
			payL_5.setFont(f1);
			payP_1.add(payL_5);
			
			JPanel payP_3 = new JPanel();	// 서명패널
			payP_3.setBounds(70, 335, 265, 120);
			payP_3.setBackground(Color.white);
			payP_1.add(payP_3);
			
			JButton payBtn_5 = new JButton("승인 요청");
			payBtn_5.setBounds(150, 465, 100, 40);
			payBtn_5.setBackground(Color.yellow);
			payBtn_5.setBorderPainted(false);
			payBtn_5.setFocusable(false);
			payBtn_5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			payBtn_5.setHorizontalAlignment(JButton.CENTER);
			payP_1.add(payBtn_5);
			payBtn_5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(calcTotalPrice()==0) {	// 주문내역이 없을때(인원만 있을경우)
						tableInfoArr[tableNum-1].leave();	// 테이블정보 초기화
						
						new UniSendtoOne(tableIP.get(tableNum-1), 8888, "@CP").start();	// 테이블에 컴플리트 전송
						for(String ip : tableIP) {		// 인원정보 각 테이블 전송
							new UniSendtoTableInfo(ip, 8888).start();
						}
						
						tableBtnInfoArr.get(tableArrIndex).setText("<html><body>남 0 여 0<br><br>Total : 0</body></html>");
						tableBtnArr.get(tableArrIndex).setBackground(tableInfoArr[tableNum-1].tableColor);
						tableBtnArr.get(tableArrIndex).setForeground(tableInfoArr[tableNum-1].tableColor);
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						moveNum = 0;
						ct.setVisible(true);
						dispose();
						
					} else {	// 결제할 품목이 있을때
						
						for (Entry<Integer, Integer> od : orderM.entrySet()) {	// 주문하기 누르지 않은 메뉴가 있을경우 ==> 재고조정
							int a = (tableInfoArr[tableNum-1].OrderMap.get(od.getKey())!=null ?	// 기존 주문내역 주문수량 가져옴 
									tableInfoArr[tableNum-1].OrderMap.get(od.getKey()) : 0);
							if(od.getValue() != a) {
								new MenuDAO().stock(od.getKey(), od.getValue()-a);
								if(new MenuDAO().detail(od.getKey()).getCnt()<=0) {
									for(String ip : tableIP) {
										new UniSendtoOne(ip, 8888, "#SO"+od.getKey()).start();
									}
									try {
										Thread.sleep(100);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						}
						
						tableInfoArr[tableNum-1].OrderMap.putAll(orderM);	// 부분결제만 할 경우에 TableInfo에 주문내역을 저장해야함
						
						int payMoney = Integer.parseInt(payBtn_2.getText().replaceAll(",", ""));	// 결제할 금액
						
						if(payMoney == calcTotalPrice()-tableInfoArr[tableNum-1].getMoney) {	// 결제완료

							payArr.get(3).setText(dfmoney.format(calcTotalPrice()));
							payArr.get(7).setText("0");
							payBtn_2.setText("0");
							
							// 영수증 DB저장
							String time = daySdf.format(new Date());			// 승인시간
							int billNum = new SalesDAO().list(time).size()+1;	// 영수증번호
							String odList = "";
							for (Entry<Integer, Integer> od : orderM.entrySet()) {
								if(od.getValue() != 0) {
									odList += od.getKey()+":"+od.getValue()+",";
								}
							}
							
							odList = odList.substring(0, odList.length()-1);	// 주문내역
							
							SalesDTO dto = new SalesDTO(openDay, billNum, time+","+timeSdf.format(new Date()), odList,
									dfcard.format(((int)(Math.random()*10000)))+"-"+dfcard.format(((int)(Math.random()*10000))),
									payBtn_3.getText(),calcTotalPrice());
							new SalesDAO().write(dto);	// 영수증 DB저장
							
							tableInfoArr[tableNum-1].leave();	// 테이블정보 초기화
							
							new UniSendtoOne(tableIP.get(tableNum-1), 8888, "@CP").start();
							for(String ip : tableIP) {
								new UniSendtoTableInfo(ip, 8888).start();
							}
							
							tableBtnInfoArr.get(tableArrIndex).setText("<html><body>남 0 여 0<br><br>Total : 0</body></html>");
							tableBtnArr.get(tableArrIndex).setBackground(tableInfoArr[tableNum-1].tableColor);
							tableBtnArr.get(tableArrIndex).setForeground(tableInfoArr[tableNum-1].tableColor);
							
							JOptionPane.showMessageDialog(null, "결제가 완료되었습니다.");
							
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							moveNum = 0;
							ct.setVisible(true);
							dispose();
							
						} else {	// 부분결제
							tableInfoArr[tableNum-1].getMoney += payMoney;
							payArr.get(3).setText(dfmoney.format(tableInfoArr[tableNum-1].getMoney));
							payArr.get(7).setText(dfmoney.format(calcTotalPrice()-tableInfoArr[tableNum-1].getMoney));
							payBtn_2.setText(dfmoney.format(calcTotalPrice()-tableInfoArr[tableNum-1].getMoney));
							JOptionPane.showMessageDialog(null, dfmoney.format(payMoney) + "원 승인되었습니다.");
						}
					}
					
				}
			});
			
			payP.add(payP_1);
			add(payP);
			payP.setVisible(false);
			
			// 메뉴 버튼
			for(int i=1 ; i<=8 ; i++) {	// 카테고리별로 8개 패널 생성
				JPanel menuPanel = new JPanel();
				menuPanel.setBounds(550, 255, 415, 370);
				menuPanel.setLayout(new GridLayout(5, 4, 5, 5));
				menuPanel.setBackground(mainColor);
				
				for(int j=1 ; j<=20 ; j++) {
					int menuNo = i*100+j;
					String menuName = "";
					String menuPrice = "";
					if(new MenuDAO().detail(menuNo)!=null) {	// DB에 있는경우
						menuName = new MenuDAO().detail(menuNo).getName(); 
						menuPrice = dfmoney.format(new MenuDAO().detail(menuNo).getPrice());	
					}
					
					JButton menuBtn = new JButton("<html><body><center>"+menuName+"<br>"+menuPrice+"</center></body></html>");
					menuBtn.setBackground(Color.white);
					if(new MenuDAO().detail(menuNo)!=null && new MenuDAO().detail(menuNo).getCnt()<=0) {
						menuBtn.setBackground(Color.yellow);
					}
					menuBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
					menuBtn.setHorizontalAlignment(JLabel.CENTER);
					menuBtn.setBorder(lb_white);
					menuBtn.setFocusable(false);
					
					if(new MenuDAO().detail(menuNo) == null) {	// GridLayout 배열을 위해 버튼 20개 생성 후, DB에 메뉴 없는 버튼 숨김
						menuBtn.setVisible(false); 
					}
					menuBtnMap.put(menuNo,menuBtn);
					menuPanel.add(menuBtn);
					menuBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							addOrderM(menuNo);
						}
					});
				}
				add(menuPanel);
				menuPanel.setVisible(false);
				catArr.add(menuPanel);
			}
			catArr.get(0).setVisible(true);
			
			// 카테고리 버튼
			categoryP = new JPanel();
			categoryP.setBounds(550, 90, 415, 145);
			categoryP.setLayout(new GridLayout(2, 4, 5, 5));
			categoryP.setBackground(mainColor);
			ButtonGroup cbg = new ButtonGroup();
			for(int i=0 ; i<8 ; i++) {
				String [] cat = {"튀김", "마른안주", "탕", "과일", "주류", "음료", "예비1", "예비2"};
				JToggleButton catBtn = new JToggleButton(cat[i]);
				catBtn.setBackground(new Color(30, 38, 80));
				catBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
				catBtn.setForeground(Color.white);
				catBtn.setFocusable(false);
				catBtn.setHorizontalAlignment(JLabel.CENTER);
				catBtn.setBorderPainted(false);
				categoryP.add(catBtn);
				ctbArr.add(catBtn);
				cbg.add(catBtn);
				catBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(int j = 0 ; j<ctbArr.size() ; j++) {
							if(ctbArr.get(j).isSelected() == false) {
								catArr.get(j).setVisible(false);
							} else if(ctbArr.get(j).isSelected() == true) {
								catArr.get(j).setVisible(true);
							}
						}
					}
				});
			}
			ctbArr.get(0).setSelected(true);
			add(categoryP);
			
			// 제어버튼 기능설정
			fBtnArr.get(0).addActionListener(new ActionListener() {	// 전체취소
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JToggleButton jtb : orderButtonArr) {
						ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
						int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
						orderM.put(id, 0);
						menuArr.get(3).setText(orderM.get(id)+"");
						menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
						menuArr.get(5).setText("취소");
						orderTotArr.get(1).setText(calcTotalQty()+"");
						orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
					}
				}
			});
			
			fBtnArr.get(1).addActionListener(new ActionListener() {	// 선택취소
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JToggleButton jtb : orderButtonArr) {
						if(jtb.isSelected() == true) {
							ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
							int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
							orderM.put(id, 0);
							menuArr.get(3).setText(orderM.get(id)+"");
							menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
							menuArr.get(5).setText("취소");
							orderTotArr.get(1).setText(calcTotalQty()+"");
							orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
						}
					}
				}
			});
			
			fBtnArr.get(2).addActionListener(new ActionListener() {	// 수량변경
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JToggleButton jtb : orderButtonArr) {
						if(jtb.isSelected() == true) {
							ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
							int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
							
							JFrame key = new JFrame("NumPad");
							key.setBounds(650, 400, 245, 400);
							JPanel back = new JPanel();
							back.setBackground(mainColor);
							back.setLayout(null);
							JTextField numArea = new JTextField();
							numArea.setBounds(5, 5, 220, 50);
							numArea.setFont(new Font("맑은 고딕", Font.BOLD, 40));
							numArea.setHorizontalAlignment(JTextField.RIGHT);
							back.add(numArea);
							JPanel numKeyP = new JPanel();
							numKeyP.setBounds(5,60,220,295);
							numKeyP.setBackground(mainColor);
							numKeyP.setLayout(new GridLayout(4,3,5,5));
							back.add(numKeyP);
							ArrayList<JButton> numKeyArr = new ArrayList<JButton>();
							for(int i=0 ; i<12 ; i++) {
								String [] num = "7,8,9,4,5,6,1,2,3,◀,0,OK".split(",");
								JButton numBtn_i = new JButton(num[i]);
								numBtn_i.setBackground(Color.white);
								numBtn_i.setFont(new Font("맑은 고딕", Font.BOLD, 40));
								numBtn_i.setFocusable(false);
								numKeyP.add(numBtn_i);
								numKeyArr.add(numBtn_i);
								if(i!=9 && i!=11) {
									numBtn_i.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											numArea.setText(numArea.getText()+numBtn_i.getText());
										}
									});
								}
							}
							key.add(back);
							key.setVisible(true);
							key.setResizable(false);
							
							numKeyArr.get(9).setFont(new Font("맑은 고딕", Font.BOLD, 36));
							numKeyArr.get(11).setFont(new Font("맑은 고딕", Font.BOLD, 25));

							numKeyArr.get(9).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(!numArea.getText().equals("")) {
										numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
									}
								}
							});
							
							numKeyArr.get(11).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int qty = Integer.parseInt(numArea.getText());
										key.dispose();
										if(qty>0) {
											orderM.put(id, qty);
											menuArr.get(3).setText(orderM.get(id)+"");
											menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
											menuArr.get(5).setText("");
											orderTotArr.get(1).setText(calcTotalQty()+"");
											orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
										} else if(qty==0) {
											orderM.put(id, 0);
											menuArr.get(3).setText(orderM.get(id)+"");
											menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
											menuArr.get(5).setText("취소");
											orderTotArr.get(1).setText(calcTotalQty()+"");
											orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
										}
									} catch(Exception e1) {
										
									}
								}
							});

							numArea.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int qty = Integer.parseInt(numArea.getText());
										key.dispose();
										if(qty>0) {
											orderM.put(id, qty);
											menuArr.get(3).setText(orderM.get(id)+"");
											menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
											menuArr.get(5).setText("");
											orderTotArr.get(1).setText(calcTotalQty()+"");
											orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
										} else if(qty==0) {
											orderM.put(id, 0);
											menuArr.get(3).setText(orderM.get(id)+"");
											menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
											menuArr.get(5).setText("취소");
											orderTotArr.get(1).setText(calcTotalQty()+"");
											orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
										}
									} catch(Exception e1) {
										
									}
								}
							});
						}
					}
				}
			});
			
			fBtnArr.get(3).addActionListener(new ActionListener() {	// + 버튼
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JToggleButton jtb : orderButtonArr) {
						if(jtb.isSelected() == true) {
							ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
							int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
							orderM.put(id, orderM.get(id)+1);
							menuArr.get(3).setText(orderM.get(id)+"");
							menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
							menuArr.get(5).setText("");
							orderTotArr.get(1).setText(calcTotalQty()+"");
							orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
						}
					}
				}
			});
			
			fBtnArr.get(4).addActionListener(new ActionListener() {	// - 버튼
				@Override
				public void actionPerformed(ActionEvent e) {
					for(JToggleButton jtb : orderButtonArr) {
						if(jtb.isSelected() == true) {
							ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
							int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
							if(orderM.get(id)>1) {
								orderM.put(id, orderM.get(id)-1);
								menuArr.get(3).setText(orderM.get(id)+"");
								menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
								orderTotArr.get(1).setText(calcTotalQty()+"");
								orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
							} 
						}
					}
				}
			});
			
			fBtnArr.get(5).addActionListener(new ActionListener() {	// 카드결제
				@Override
				public void actionPerformed(ActionEvent e) {
					payP.setVisible(true);
					categoryP.setVisible(false);
					for(int i=0 ; i<8 ; i++) {
						catArr.get(i).setVisible(false);
					}
					
					int getMoney = tableInfoArr[tableNum-1].getMoney;
					payArr.get(1).setText(dfmoney.format(calcTotalPrice()));
					payArr.get(3).setText(dfmoney.format(getMoney));
					payArr.get(7).setText(dfmoney.format(calcTotalPrice()-getMoney));
					payBtn_2.setText(dfmoney.format(calcTotalPrice()-getMoney));
					if(calcTotalPrice()>0) {	// 주문내역이 있을때만 카드번호 표시
						payBtn_3.setText(dfcard.format(((int)(Math.random()*10000)))+"-"+dfcard.format(((int)(Math.random()*10000)))+
								"-"+dfcard.format(((int)(Math.random()*10000)))+"-"+dfcard.format(((int)(Math.random()*10000))));	
					}
				}
			});
			
			fBtnArr.get(6).addActionListener(new ActionListener() {	// 예비버튼
				@Override
				public void actionPerformed(ActionEvent e) {
//					System.out.println(e.getActionCommand());
				}
			});
			
			fBtnArr.get(7).addActionListener(new ActionListener() {	// 자리이동
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tableInfoArr[tableNum-1].isSeated) {
						move.setVisible(true);
						ct.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "이용중인 테이블이 아닙니다.");
					}
				}
			});
			
			fBtnArr.get(8).addActionListener(new ActionListener() {	// 인원변경
				@Override
				public void actionPerformed(ActionEvent e) {
					new MemberChg(tableNum-1);
				}
			});
			
			fBtnArr.get(9).addActionListener(new ActionListener() {	// 주문하기
				@Override
				public void actionPerformed(ActionEvent e) {
					String orderListStr = tableNum+"번 테이블\n" + timeSdf.format(new Date()) + "\n------------\n";
					String tOrderListStr = "#OD";
					int newOrder = 0;
					
					for (Entry<Integer, Integer> od : orderM.entrySet()) {
						int a = (tableInfoArr[tableNum-1].OrderMap.get(od.getKey())!=null ? tableInfoArr[tableNum-1].OrderMap.get(od.getKey()) : 0);

						if(od.getValue() != a) {
							
							if(new MenuDAO().detail(od.getKey()).getCnt() < (od.getValue()-a)) {	// 재고가 없을때 (주문은 됨)
								String so = "재고가 부족합니다.(재고량 : " + new MenuDAO().detail(od.getKey()).getCnt() + ")";
								JOptionPane.showMessageDialog(null, so);
							}
							
							if(new MenuDAO().detail(od.getKey()).getCnt() <= (od.getValue()-a)) {	// 매진처리
								menuBtnMap.get(od.getKey()).setBackground(Color.yellow);
								
								for(String ip : tableIP) {
									new UniSendtoOne(ip, 8888, "#SO"+od.getKey()).start();
								}
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							orderListStr += new MenuDAO().detail(od.getKey()).getName() + "    " + (od.getValue()-a) + "\n";
							tOrderListStr += od.getKey()+":"+(od.getValue()-a)+",";
							
							new MenuDAO().stock(od.getKey(), od.getValue()-a);	// DB 재고 변경
							newOrder ++;
						}
					}
					
					orderListStr += "T"+tableNum;
					tOrderListStr = tOrderListStr.substring(0,tOrderListStr.length()-1);
					
					tableInfoArr[tableNum-1].OrderMap.putAll(orderM);
					
					if(newOrder>0) {
						new UniSendtoOne(kitchenIP, 8888, orderListStr).start();
						new UniSendtoOne(tableIP.get(tableNum-1), 8888, tOrderListStr).start();
					}
				}
			});
			
			addMouseWheelListener(this);
			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		}
		
		// 메뉴버튼 눌렀을때
		public void addOrderM(int id) {
			if(!orderM.containsKey(id)) {
				orderM.put(id, 1);
				JToggleButton newMenuBtn = new JToggleButton();
				newMenuBtn.setBounds(0, 30*orderM.size()-30, 498, 30);
				newMenuBtn.setLayout(null);
				newMenuBtn.setBackground(Color.white);
				newMenuBtn.setBorderPainted(false);
				ArrayList<JLabel> newMenuArr = new ArrayList<JLabel>();
				for(int i=0 ; i<6 ; i++) {
					String menuName = new MenuDAO().detail(id).getName();
					String menuPrice = dfmoney.format(new MenuDAO().detail(id).getPrice())+"　";
					String [] labelStr = {orderM.size()+"", menuName, menuPrice, 1+"", menuPrice, ""};
					int [] labelWidth = {0, 40, 238, 318, 378, 458, 498};
					JLabel label = new JLabel(labelStr[i]);
					label.setBounds(labelWidth[i], 0, labelWidth[i+1]-labelWidth[i], 30);
					label.setFont(f1);
					label.setHorizontalAlignment(JLabel.CENTER);
					newMenuArr.add(label);
					newMenuBtn.add(label);
				}
				newMenuArr.get(2).setHorizontalAlignment(JLabel.RIGHT);
				newMenuArr.get(4).setHorizontalAlignment(JLabel.RIGHT);
				orderBtnM.put(newMenuBtn, newMenuArr);
				orderScrollPanel.add(newMenuBtn);
				orderButtonGroup.add(newMenuBtn);
				orderButtonArr.add(newMenuBtn);
				newMenuBtn.setSelected(true);
				int high = 310 + (orderButtonArr.size()>10 ? (orderButtonArr.size()-10)*30 : 0);
				int setY = (orderButtonArr.size()>10 ? (orderButtonArr.size()-10)*(-30) : 0);
				orderScrollPanel.setBounds(1, setY, 498, high);
				orderScrollPanel.repaint();
				orderTotArr.get(1).setText(calcTotalQty()+"");
				orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
				
			} else {
				orderM.put(id, orderM.get(id)+1);
				for(Object aa : orderBtnM.keySet()) {
					JToggleButton bb = (JToggleButton)aa;
					ArrayList<JLabel> lbArr = orderBtnM.get(bb);
					if(new MenuDAO().detail(id).getName().equals(lbArr.get(1).getText())) {
						lbArr.get(3).setText(orderM.get(id)+"");
						lbArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice())+"　");
						lbArr.get(5).setText("");
						bb.setSelected(true);
						int setY = orderScrollPanel.getY();
						if(orderButtonArr.indexOf(bb) < orderScrollPanel.getY()/(-30)) {
							setY = orderButtonArr.indexOf(bb)*(-30);
						} else if (orderButtonArr.indexOf(bb)-9 > orderScrollPanel.getY()/(-30)) {
							setY = (orderButtonArr.indexOf(bb)-9)*(-30);
						}
						orderScrollPanel.setBounds(1, setY, 498, orderScrollPanel.getSize().height);	// 선택한 메뉴에 따라 화면 이동
					}
				}
				orderTotArr.get(1).setText(calcTotalQty()+"");
				orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
			}
		}
		
		// 현재 테이블 주문총액
		public int calcTotalPrice() {
			int totalPrice = 0;
			for (Entry<Integer, Integer> od : orderM.entrySet()) {
				int menuPrice = new MenuDAO().detail(od.getKey()).getPrice();
				totalPrice += menuPrice * od.getValue();
			}
			return totalPrice;
		}
		
		// 테이블 주문품목 총계
		public int calcTotalQty() {
			int totalQty = 0;
			for (Entry<Integer, Integer> od : orderM.entrySet()) {
				totalQty += od.getValue();
			}
			return totalQty;
		}

		// 주문내역 패널 휠이벤트
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if(e.getX()>30 && e.getX()<530 && e.getY()>150 && e.getY()<460) {
				int setY = (orderScrollPanel.getBounds().y)+(-30)*e.getWheelRotation();
				if(setY>0) {
					setY = 0;
				}
				if(setY<310-orderScrollPanel.getSize().height) {
					setY = 310-orderScrollPanel.getSize().height; //size 610 sety 최소 -310 최대 0
				}
				orderScrollPanel.setBounds(1, setY, 498, orderScrollPanel.getSize().height);
			}
		}
	}
	
	// 주문화면 - 인원변경
	class MemberChg extends JFrame{
		
		int boyNum = 0;
		int girlNum = 0;
		
		public MemberChg(int tableNum) {
			super(tableNum+1 + "번 테이블 인원 정보 입력");

			boyNum = tableInfoArr[tableNum].boy_Num;
			girlNum = tableInfoArr[tableNum].girl_Num;
			
			setSize(380,360);
			setLocationRelativeTo(null);
			Container con = getContentPane();
			con.setBackground(Color.white);
			setLayout(null);
			
			JLabel boyNumText = new JLabel("남성 수 : " + boyNum);
			boyNumText.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			boyNumText.setBounds(30, 70, 150, 35);
			add(boyNumText);
			
			JLabel girlNumText = new JLabel("여성 수 : " + girlNum);
			girlNumText.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			girlNumText.setBounds(30, 220, 150, 35);
			add(girlNumText);
			
			JButton boyPlus = new JButton("▲");
			boyPlus.setBounds(190, 30, 50, 50);
			boyPlus.setBackground(Color.white);
			boyPlus.setFocusable(false);
			add(boyPlus);
			boyPlus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if((boyNum+girlNum)<6) {
						boyNum += 1;
						boyNumText.setText("남성 수 : " + boyNum);
					}
				}
			});
			
			JButton boyMinus = new JButton("▼");
			boyMinus.setBounds(190, 90, 50, 50);
			boyMinus.setBackground(Color.white);
			boyMinus.setFocusable(false);
			add(boyMinus);
			boyMinus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(boyNum > 0) {
						boyNum -= 1;
						boyNumText.setText("남성 수 : " + boyNum);
					}
				}
			});
			
			JButton girlPlus = new JButton("▲");
			girlPlus.setBounds(190, 180, 50, 50);
			girlPlus.setBackground(Color.white);
			girlPlus.setFocusable(false);
			add(girlPlus);
			girlPlus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if((boyNum+girlNum)<6) {
						girlNum += 1;
						girlNumText.setText("여성 수 : " + girlNum);
					}
				}
			});

			JButton girlMinus = new JButton("▼");
			girlMinus.setBounds(190, 240, 50, 50);
			girlMinus.setBackground(Color.white);
			girlMinus.setFocusable(false);
			add(girlMinus);
			girlMinus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(girlNum > 0) {
						girlNum -= 1;
						girlNumText.setText("여성 수 : " + girlNum);
					}
				}
			});
			
			JButton startSeat = new JButton("변경");
			startSeat.setBounds(260, 130, 80, 60);
			startSeat.setFont(f2);
			startSeat.setBackground(Color.white);
			startSeat.setFocusable(false);
			add(startSeat);
			startSeat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if(!tableInfoArr[tableNum].isSeated) {	// 신규입장시
						tableInfoArr[tableNum].tableInfo(boyNum, girlNum);	// 신규입장
						stateArr.get(7).setText(timeSdf.format(new Date()));	// 오더창 입장시간 업뎃

						new UniSendtoOne(tableIP.get(tableNum), 8888, "@EN,B"+boyNum+",G"+girlNum).start();	// 입장시키기
						
						for(MenuDTO md : new MenuDAO().list()) {	// 입장한 테이블로 매진인 메뉴 전송
							if(md.cnt<=0) {
								String soldOutStr = "#SO" + md.id; 
								new UniSendtoOne(tableIP.get(tableNum), 8888, soldOutStr).start();
								
								try {	// 0.1초 딜레이
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
						
					} else {	// 인원변경시
						tableInfoArr[tableNum].tableInfo(boyNum, girlNum);
					}
					
					stateArr.get(5).setText("남 " + tableInfoArr[tableNum].boy_Num + " 여 " + tableInfoArr[tableNum].girl_Num);	// 포스창 인원정보 업데이트

					int tableArrIndex = Integer.parseInt(tableBtnArr.get(tableNum).getText())-1;	// 테이블번호
					tableBtnArr.get(tableArrIndex).setBackground(tableInfoArr[tableNum].tableColor);
					tableBtnArr.get(tableArrIndex).setForeground(tableInfoArr[tableNum].tableColor);
					
					for(String ip : tableIP) {
						new UniSendtoTableInfo(ip, 8888).start();
					}
					
					dispose();
				}
			});

			setVisible(true);
			setResizable(false);
		}
	}

	// 영업마감
	class SalesClose extends JFrame {
		
		public SalesClose() {
			
			super(openDay + " 영업마감");
			setBounds(400, 200, 690, 600);
			setLayout(null);
			Container con = getContentPane();
			con.setBackground(Color.white);
			JPanel backP = new JPanel();
			backP.setBounds(20, 20, 630, 510);
			backP.setLayout(null);
			backP.setBackground(mainColor);
			
			JLabel title = new JLabel(openDay + " 영업마감");
			title.setBounds(30, 20, 250, 30);
			title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			backP.add(title);
			
			ImageIcon return_img = new ImageIcon("./img/return.png");
			JButton exit = new JButton(return_img);
			exit.setBounds(560, 20, 40, 40);
			exit.setBackground(mainColor);
			exit.setBorderPainted(false);
			exit.setFocusable(false);
			backP.add(exit);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			JLabel mainLabel = new JLabel("영업내역");
			mainLabel.setBounds(30, 80, 250, 30);
			mainLabel.setFont(f2);
			backP.add(mainLabel);
			
			JLabel openTimeLabel = new JLabel("영업시간 : " + openTime + " ~ " + timeSdf.format(new Date()));
			openTimeLabel.setBounds(40, 115, 250, 25);
			openTimeLabel.setFont(f1);
			backP.add(openTimeLabel);
			
			int daily = (new SalesDAO().dailySales(openDay)!=null ? Integer.parseInt(new SalesDAO().dailySales(openDay)) : 0);
			
			JLabel dailySalesLabel = new JLabel("매출 : " + dfmoney.format(daily));
			dailySalesLabel.setBounds(40, 145, 150, 25);
			dailySalesLabel.setFont(f1);
			backP.add(dailySalesLabel);
			
			JLabel dailySalesNumLabel = new JLabel("주문건수 : " + new SalesDAO().list(openDay).size());
			dailySalesNumLabel.setBounds(40, 175, 150, 25);
			dailySalesNumLabel.setFont(f1);
			backP.add(dailySalesNumLabel);
			
			JLabel tableCostLabel = new JLabel("테이블단가 : " + dfmoney.format(daily/(daily!=0 ? new SalesDAO().list(openDay).size() : 1)));
			tableCostLabel.setBounds(40, 205, 150, 25);
			tableCostLabel.setFont(f1);
			backP.add(tableCostLabel);
			
			JLabel catSalesLabel = new JLabel("카테고리별 단가");
			catSalesLabel.setBounds(30, 255, 150, 30);
			catSalesLabel.setFont(f2);
			backP.add(catSalesLabel);
			
			JLabel tableTitleLabel = new JLabel("상품별 판매현황");
			tableTitleLabel.setBounds(330, 80, 150, 30);
			tableTitleLabel.setFont(f2);
			backP.add(tableTitleLabel);
			
			Map<Integer, Integer> dsMap = new LinkedHashMap<Integer, Integer>();
			for(MenuDTO md : new MenuDAO().list()) {	// 상품별 깡통맵 입력
				dsMap.put(md.id, 0);
			}
			
			for (int i=1 ; i<=new SalesDAO().list(openDay).size() ; i++) {	// 깡통맵에 판매수량 입력
				SalesDTO bill = new SalesDAO().detail(openDay, i);
				String [] odList = bill.orderlist.split(",");
				for(int j=0 ; j<odList.length ; j++) {
					int id = Integer.parseInt(odList[j].split(":")[0]);
					int qty = Integer.parseInt(odList[j].split(":")[1]);
					dsMap.put(id, dsMap.get(id)+qty);
				}
			}
			
			for(MenuDTO md : new MenuDAO().list()) {	// 판매수량 0인 키 삭제
				if(dsMap.get(md.id)==0) {
					dsMap.remove(md.id);
				}
			}
			
			Object [] tableTitle = {"메뉴명", "판매량", "매출"};
			Object [][] tableData = new Object[dsMap.size()][3];
			int size = 0;
			for(Entry<Integer,Integer> dm : dsMap.entrySet()) {
				tableData[size][0] = " " + new MenuDAO().detail(dm.getKey()).name;
				tableData[size][1] = dm.getValue();
				tableData[size][2] = dfmoney.format(dm.getValue()*(new MenuDAO().detail(dm.getKey()).price))+"　";
				size++;
			}
			
			JTable table = new JTable(tableData, tableTitle);
			DefaultTableCellRenderer dtcrCenter = new DefaultTableCellRenderer();
			DefaultTableCellRenderer dtcrRight = new DefaultTableCellRenderer();
			dtcrCenter.setHorizontalAlignment(SwingConstants.CENTER);
			dtcrRight.setHorizontalAlignment(SwingConstants.RIGHT);
			table.getColumnModel().getColumn(0).setPreferredWidth(140);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setCellRenderer(dtcrCenter);
			table.getColumnModel().getColumn(2).setCellRenderer(dtcrRight);

			JScrollPane salesTable = new JScrollPane(table);
			salesTable.setBounds(330, 120, 270, 360);
			backP.add(salesTable);
			
			String [] cat = {"튀김", "마른안주", "탕", "과일", "주류", "음료"};
			for(int i=0 ; i<cat.length ; i++) {
				
				int catSales = 0;
				for(Entry<Integer,Integer> dm : dsMap.entrySet()) {
					if(dm.getKey()/100 == (i+1)) {
						catSales += dm.getValue()*(new MenuDAO().detail(dm.getKey()).price);
					}
				}
				
				JLabel cat_i_Label = new JLabel(cat[i] + " : " + dfmoney.format(catSales));
				cat_i_Label.setBounds(40, 290+(30*i), 150, 25);
				cat_i_Label.setFont(f1);
				backP.add(cat_i_Label);
			}
						
			add(backP);
			setVisible(true);
			setResizable(false);
			
		}
		
	}
	
	// 매출현황
	class SalesDay extends JFrame {
		
		public SalesDay() {
			super("매출");

			setSize(400, 400);
			setLocationRelativeTo(null);
			setLayout(null);

			JPanel jpsales = new JPanel();
			jpsales.setBounds(0, 0, 400, 400);
			jpsales.setBackground(mainColor);
			jpsales.setLayout(null);
			
			ImageIcon first_img = new ImageIcon("./img/jjack2.png");
			JLabel first_label = new JLabel(first_img);
			first_label.setBounds(50, 100, 150, 150);
			add(first_label);

			JButton daysales = new JButton("일별매출");
			daysales.setBounds(250, 100, 100, 50);
			daysales.setBackground(Color.white);
			daysales.setFocusable(false);
			daysales.setFont(f2);
			jpsales.add(daysales);
			daysales.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new CalendarMain();
				}
			});

			JButton monthsales = new JButton("월별매출");
			monthsales.setBounds(250, 170, 100, 50);
			monthsales.setBackground(Color.white);
			monthsales.setFocusable(false);
			monthsales.setFont(f2);
			jpsales.add(monthsales);
			monthsales.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new MonthlySales();
				}
			});

			JButton exitsales = new JButton("나가기");
			exitsales.setBounds(250, 240, 100, 50);
			exitsales.setBackground(new Color(204, 63, 63));
			exitsales.setForeground(Color.white);
			exitsales.setFocusable(false);
			exitsales.setFont(f2);
			exitsales.setBorder(lb_black);
			jpsales.add(exitsales);
			exitsales.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 04-15
					fs.setVisible(true);
					dispose();
				}
			});

			add(jpsales);
			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	// 일별 매출
	class CalendarMain extends JFrame implements ActionListener {

		Calendar cal; //캘린더
		int year, month, date;
		
		JPanel pane = new JPanel();
	
		JButton prebtn = new JButton("◀");  //이전버튼
		JLabel jl1 = new JLabel("　　　　　  　　　　　　　　");
		JLabel yearlb = new JLabel("년");
		JLabel monthlb = new JLabel("월");
		JButton nextbtn = new JButton("▶"); //다음버튼
		JLabel jl2 = new JLabel("　　　　　　　");
		JButton exitbtn = new JButton("나가기");
		
		JComboBox<Integer> yearCombo = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
		JComboBox<Integer> monthCombo = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();
		
		JPanel pane2 = new JPanel(new BorderLayout());
		JPanel title = new JPanel(new GridLayout(1, 7));
		String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
		JPanel datePane = new JPanel(new GridLayout(0, 7));

		//화면디자인
		public CalendarMain() {

			cal = Calendar.getInstance(); //현재날짜
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;
			date = cal.get(Calendar.DATE);

			for(int i=year-1 ; i<=year ; i++) {		//2021 ~ 2022
				yearModel.addElement(i);
			}
			yearCombo.setModel(yearModel);
			yearCombo.setSelectedItem(year);
			
			for(int i=1 ; i<=12 ; i++) {
				monthModel.addElement(i);
			}
			monthCombo.setModel(monthModel);
			monthCombo.setSelectedItem(month);
			
			for(int i=0 ; i<titleStr.length ; i++) {
				JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
				if(i == 0) {
					lbl.setForeground(Color.red);
				} else if(i == 6) {
					lbl.setForeground(Color.blue);
				}
				lbl.setFont(new Font("맑은 고딕", Font.BOLD, 12));
				title.add(lbl);
			}
			
			day(year, month);
			
			//----------------------------
			setTitle("매출확인");
			pane.add(jl1);
			pane.add(prebtn);
			pane.add(yearCombo);
			pane.add(yearlb);
			pane.add(monthCombo);
			pane.add(monthlb);
			pane.add(nextbtn);
			pane.add(jl2);
			pane.add(exitbtn);
			pane.setBackground(mainColor);
			add(BorderLayout.NORTH, pane);
			pane2.add(title,"North");
			pane2.add(datePane);
			title.setBackground(Color.white);
			datePane.setBackground(mainColor);
			add(BorderLayout.CENTER, pane2);
			prebtn.setBackground(Color.white);
			nextbtn.setBackground(Color.white);
			exitbtn.setBackground(Color.white);
			yearlb.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			monthlb.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			exitbtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			
			yearCombo.setBackground(Color.white);
			monthCombo.setBackground(Color.white);
					
			//각종 명령어
	        setVisible(true);
	        setSize(600,400);
	        setLocationRelativeTo(null);
	        setResizable(false);
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        
	        //----------기능구현----------
	        prebtn.addActionListener(this);
	        nextbtn.addActionListener(this);
	        exitbtn.addActionListener(this);
	        yearCombo.addActionListener(this);
	        monthCombo.addActionListener(this);
		}

		//기능구현
		public void actionPerformed(ActionEvent e) {
			Object eventObj = e.getSource();
			if(eventObj instanceof JComboBox) {
				datePane.setVisible(false);	//보여지는 패널을 숨킨다.
				datePane.removeAll();	//라벨 지우기
				day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
				datePane.setVisible(true);	//패널 재출력
			} else if(eventObj instanceof JButton) {
				JButton eventBtn = (JButton) eventObj;
				int yy = (Integer)yearCombo.getSelectedItem();
				int mm = (Integer)monthCombo.getSelectedItem();
				if(eventBtn.equals(prebtn)) {	//전달
					if(mm==1) {
						yy--; mm=12;
					} else {
						mm--;
					}				
				} else if(eventBtn.equals(nextbtn)) {	//다음달
					if(mm==12 ){
						yy++; mm=1;
					} else {
						mm++;
					}
				} else if(eventBtn.equals(exitbtn)) {	// 나가기
					dispose();
				}
				yearCombo.setSelectedItem(yy);
				monthCombo.setSelectedItem(mm);
			}	
		}
		
		//날짜출력
		public void day(int year, int month) {
			
			Calendar date = Calendar.getInstance();	//오늘날짜 + 시간
			date.set(year, month-1, 1);
			
			int week = date.get(Calendar.DAY_OF_WEEK);
			int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			for(int space=1 ; space<week ; space++) {
				datePane.add(new JLabel("\t"));
			}
			
			//날짜 출력
			for (int day = 1 ; day<=lastDay ; day++) {
				JButton daybtn = new JButton(String.valueOf(day));
				daybtn.setBackground(Color.white);
				daybtn.setFont(f2);
				daybtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	// 날짜 누르면 나오는 창
						setVisible(false);
						
						DecimalFormat dMM = new DecimalFormat("00");
						String yymmdd = yearModel.getSelectedItem()+"-"+ dMM.format(monthModel.getSelectedItem())+"-"+ dMM.format(Integer.parseInt(daybtn.getText()));
						
						JFrame j1 = new JFrame(yymmdd);
						
						int daily = (new SalesDAO().dailySales(yymmdd)!=null ? Integer.parseInt(new SalesDAO().dailySales(yymmdd)) : 0);
						
						j1.setBounds(600, 250, 738, 600);
						j1.setLayout(null);
						j1.setResizable(false);

						Container con = j1.getContentPane();
						con.setBackground(Color.white);
						
						JPanel backP = new JPanel();
						backP.setBounds(20, 20, 680, 520);
						backP.setLayout(null);
						backP.setBackground(mainColor);
						j1.add(backP);
						
						JButton exit = new JButton("나가기");
						exit.setBounds(570, 30, 80, 25);
						exit.setBackground(Color.white);
						exit.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
						exit.setFocusable(false);
						backP.add(exit);
						exit.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								setVisible(true);
								j1.dispose();
							}
						});
						
						JLabel salesLabel = new JLabel("일자 : " +yymmdd +" / 매출 : "+dfmoney.format(daily) + "원");
						salesLabel.setBounds(30, 30, 400, 20);
						salesLabel.setFont(f1);
						backP.add(salesLabel);
						j1.setVisible(true);
						
						Map<Integer, Integer> dsMap = new LinkedHashMap<Integer, Integer>();
						for(MenuDTO md : new MenuDAO().list()) {	// 상품별 깡통맵 입력
							dsMap.put(md.id, 0);
						}
						
						for (int i=1 ; i<=new SalesDAO().list(yymmdd).size() ; i++) {	// 깡통맵에 판매수량 입력
							SalesDTO bill = new SalesDAO().detail(yymmdd, i);
							String [] odList = bill.orderlist.split(",");
							for(int j=0 ; j<odList.length ; j++) {
								int id = Integer.parseInt(odList[j].split(":")[0]);
								int qty = Integer.parseInt(odList[j].split(":")[1]);
								dsMap.put(id, dsMap.get(id)+qty);
							}
						}
						
						for(MenuDTO md : new MenuDAO().list()) {	// 판매수량 0인 키 삭제
							if(dsMap.get(md.id)==0) {
								dsMap.remove(md.id);
							}
						}
						
						Object [] title = {"메뉴명", "판매량", "매출"};
						Object [][] data = new Object[dsMap.size()][3];
						int size = 0;
						for(Entry<Integer,Integer> dm : dsMap.entrySet()) {
							data[size][0] = " " + new MenuDAO().detail(dm.getKey()).name;
							data[size][1] = dm.getValue();
							data[size][2] = dfmoney.format(dm.getValue()*(new MenuDAO().detail(dm.getKey()).price));
							size++;
						}
						
						JTable table = new JTable(data, title);
						DefaultTableCellRenderer dtcrCenter = new DefaultTableCellRenderer();
						DefaultTableCellRenderer dtcrRight = new DefaultTableCellRenderer();
						dtcrCenter.setHorizontalAlignment(SwingConstants.CENTER);
						dtcrRight.setHorizontalAlignment(SwingConstants.RIGHT);
						table.getColumnModel().getColumn(0).setPreferredWidth(140);
						table.getColumnModel().getColumn(1).setPreferredWidth(60);
						table.getColumnModel().getColumn(1).setCellRenderer(dtcrCenter);
						table.getColumnModel().getColumn(2).setCellRenderer(dtcrRight);

						JScrollPane salesTable = new JScrollPane(table);
						salesTable.setBounds(30, 80, 270, 410);
						backP.add(salesTable);
						
						JLabel billNoLabel = new JLabel("영수증 번호");
						billNoLabel.setBounds(350, 30, 400, 20);
						billNoLabel.setFont(f1);
						backP.add(billNoLabel);
						
						Vector<Integer> no = new Vector<Integer>();
						for(int i=1 ; i<=new SalesDAO().list(yymmdd).size() ; i++) {
							no.add(i);
						}
						JComboBox<Integer> billNo = new JComboBox<Integer>(no);
						billNo.setBounds(445, 30, 60, 25);
						billNo.setBackground(Color.white);
						billNo.setSelectedIndex(-1);
						backP.add(billNo);
						
						JTextArea billArea = new JTextArea();
						billArea.setEditable(false);
						JScrollPane bsc = new JScrollPane(billArea);
						bsc.setBounds(350, 80, 300, 410);
						backP.add(bsc);
						
						billNo.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								SalesDTO bill = new SalesDAO().detail(yymmdd, (int)billNo.getSelectedItem());
								String [] odList = bill.orderlist.split(",");
								String rec = "\n　　　　　　　　영　　　수　　　증\n\n";
								rec += "　　점        포　:　짝... 친구찾는포차\n";
								rec += "　　대  표  자　:　백종현\n";
								rec += "　　사  업  자　:　그린컴퓨터아카데미\n";
								rec += "　　주        소　:　서울 강남구 테헤란로5길 24\n";
								rec += "　　담  당  자　:　박수지\n";
								rec += "　　판매일자　:　" + bill.date + "\n";
								rec += "　ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n";
								for(int i=0 ; i<odList.length ; i++) {
									int id = Integer.parseInt(odList[i].split(":")[0]);
									int qty = Integer.parseInt(odList[i].split(":")[1]);
									int price = (new MenuDAO().detail(id).price)*qty;
									rec += "　　" + (i+1) + ". " + new MenuDAO().detail(id).name 
											+ (((new MenuDAO().detail(id).name)+i).length()<6 ? "\t\t" : "\t")
											+ qty + "　    " + dfmoney.format(price) + "원\n"; 
								}
								rec += "　ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n";
								rec += "　　승인금액 : " + dfmoney.format(bill.total) + "\n";
								rec += "　　카드번호 : " + bill.card_no + "\n";
								rec += "　　승인번호 : " + bill.approval + "\n";
								rec += "　　승인시간 : " + bill.TIME.split(",")[0] + "　" + bill.TIME.split(",")[1] + "\n";
								rec += "　ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n";
								rec += "　　★친구찾는포차 이벤트★\n";
								rec += "　　1. 합석시 주류 1병 제공\n";
								rec += "　　2. 연애성공시 주류 5병 제공\n";
								rec += "　　3. 결혼성공시 주류 무제한 제공\n";
								rec += "　ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n";
								
								billArea.setText(rec);
								
							}
						});
					}
				});
				
				cal.set(year, month-1, day);
				int Week = cal.get(Calendar.DAY_OF_WEEK);
				if(Week==1) {
					daybtn.setForeground(Color.red);				
				} else if(Week==7) {
					daybtn.setForeground(Color.BLUE);
				}
				datePane.add(daybtn);
			}
		}
	}

	// 월별 매출
	class MonthlySales extends JFrame {
		
		//년월 추가 
		JComboBox<Integer> yearCombo = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
		JComboBox<Integer> monthCombo = new JComboBox<Integer>();
		DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();
		
		ArrayList<JLabel> monthlyTotalPricelbArr = new ArrayList<JLabel>();
		
		JScrollPane dailySalesTable;
		DrawingPanel graphP_2021, graphP_2022;
		JPanel plusPanel;
		
		Calendar cal; //캘린더
		int year, month, date;
		
		public MonthlySales() {
			super("월별 매출");
			
			cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH)+1;

            for(int i=year-1; i<=year; i++){
               yearModel.addElement(i);
            }
			
            for(int i=1; i<=12; i++) {
				monthModel.addElement(i);
			}
            
			monthCombo.setModel(monthModel);
			monthCombo.setSelectedItem(month);
			monthCombo.setBounds(650, 30, 70, 30);
			monthCombo.setFont(f2);
			monthCombo.setBackground(Color.white);
			
			setSize(1000, 650);
			setLocationRelativeTo(null);
			setLayout(null);
			setResizable(false);

			// 월매출 패널 
			JPanel backP = new JPanel();
			backP.setLayout(null);
			backP.setBounds(0, 0, 980, 650);	// 배경패널 사이즈
			backP.setBackground(mainColor);
               
   			yearCombo.setBounds(510, 30, 100, 30);
			yearCombo.setFont(f2);
			yearCombo.setBackground(Color.white);
			yearCombo.setModel(yearModel);
			yearCombo.setSelectedItem(year);
               
			JLabel month_label = new JLabel("월별 매출 그래프");	//
			month_label.setBounds(30, 20, 400, 50);
			month_label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			backP.add(month_label);
			
			// 나가기 버튼
			ImageIcon cls_img = new ImageIcon("./img/return.png");
			JButton closeBtn = new JButton(cls_img);
			closeBtn.setBounds(910, 20, 40, 40);
			closeBtn.setBackground(mainColor);
			closeBtn.setFocusable(false);
			closeBtn.setBorderPainted(false);
			closeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					dispose();
				}
			});
			backP.add(closeBtn);
			
			graphP_2021 = new DrawingPanel(2021);
			graphP_2021.setBounds(30, 80, 580, 500);		// 차트 패널 사이즈
			
			graphP_2022 = new DrawingPanel(2022);
			graphP_2022.setBounds(30, 80, 580, 500);

			backP.add(graphP_2021);
			backP.add(graphP_2022);
			
			plusPanel = new JPanel();
			
			JLabel salesLabel = new JLabel();
			
			backP.add(yearCombo);
			backP.add(monthCombo);
			backP.add(plusPanel);
			add(backP);

			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			yearCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {	
					int year = Integer.parseInt(yearCombo.getSelectedItem().toString());
					
					if(year==2021) {
						graphP_2022.setVisible(false);
						graphP_2021.setVisible(true);
					} else if(year==2022) {
						graphP_2022.setVisible(true);
						graphP_2021.setVisible(false);
					}
					
				}
			});
			
			monthCombo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if((new SalesDAO().showMonthlySales(yearCombo.getSelectedIndex()+2021,Integer.parseInt(monthCombo.getSelectedItem().toString())))!=null){

						if(dailySalesTable!=null) {
							plusPanel.remove(dailySalesTable);
						}

						plusPanel.setLayout(null);
						plusPanel.setBounds(650, 80, 300, 500);	// 위치, 사이즈 조절
						plusPanel.setBackground(Color.white);

						int yy = Integer.parseInt(yearCombo.getSelectedItem().toString());
						int mm = Integer.parseInt(monthCombo.getSelectedItem().toString());
						int monthly = Integer.parseInt(new SalesDAO().showMonthlySales(yy,mm));

						salesLabel.setText((yearCombo.getSelectedIndex()+2021) + "-" + new DecimalFormat("00").format(mm) +" / 매출 : "+ dfmoney.format(monthly)+ "원");
						salesLabel.setBounds(23, 20, 400, 30);
						salesLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
						plusPanel.add(salesLabel);

						// 일자별 매출 테이블
						Calendar today = Calendar.getInstance();
						today.set(yy,mm-1,1);
						Object [] title = {"일자", "주문건수", "매출"};
						Object [][] data = new Object[today.getActualMaximum(Calendar.DATE)][3];

						for(int i=1 ; i<=today.getActualMaximum(Calendar.DATE) ; i++) {
							String date = "";
							date += yy+"-";
							date += new DecimalFormat("00").format(mm)+"-";
							date += new DecimalFormat("00").format(i);
							int daily = (new SalesDAO().dailySales(date)!=null ? Integer.parseInt(new SalesDAO().dailySales(date)) : 0); 
							data[i-1][0] = date;
							data[i-1][1] = new SalesDAO().list(date).size()+" ";
							data[i-1][2] = dfmoney.format(daily)+"  ";	
						}

						DefaultTableModel model = new DefaultTableModel(data, title);
						JTable salesTable = new JTable(model);
						dailySalesTable = new JScrollPane(salesTable);
						dailySalesTable.setBounds(25, 80, 250, 400);
						salesTable.getColumnModel().getColumn(0).setPreferredWidth(90);
						salesTable.getColumnModel().getColumn(1).setPreferredWidth(60);
						DefaultTableCellRenderer dtcrCenter = new DefaultTableCellRenderer();
						DefaultTableCellRenderer dtcrRight = new DefaultTableCellRenderer();
						dtcrCenter.setHorizontalAlignment(SwingConstants.CENTER);
						dtcrRight.setHorizontalAlignment(SwingConstants.RIGHT);
						salesTable.getColumnModel().getColumn(0).setCellRenderer(dtcrCenter);
						salesTable.getColumnModel().getColumn(1).setCellRenderer(dtcrCenter);
						salesTable.getColumnModel().getColumn(2).setCellRenderer(dtcrRight);

						plusPanel.add(dailySalesTable);

					}
				}

			});
			
			yearCombo.actionPerformed(null);
			monthCombo.actionPerformed(null);
			
		}
		
	}
	
	// 그래프 그리는애
	class DrawingPanel extends JPanel {
		
		int year;
		int [] monthly = new int[12];
		
		public DrawingPanel(int year) {
			this.year = year;
		}
		
		public void paint(Graphics g) {
			
			for(int i=0 ; i<12 ; i++) {
				if(new SalesDAO().showMonthlySales(year,i+1)!=null) {
					monthly[i] = (int)(Double.parseDouble(new SalesDAO().showMonthlySales(year,i+1))/1000000);
				} else {
					monthly[i] = 0;
				}
			}
			
			g.clearRect(0,0,getWidth(),getHeight());
			g.drawLine(50,450,550,450);	// x축
			g.drawLine(50,20,50,450);	// y축
			g.drawString("(단위 : 천원)", 500, 485);
			
			for(int i = 1 ; i<=12 ; i++) {
				g.drawString(i+"월",20-7*(i/10),465-35*i);
			}
			
			for(int i=0 ; i<4 ; i++) {
				g.setColor(Color.lightGray);
				g.drawLine(150+100*i, 20, 150+100*i, 450);
				g.setColor(Color.black);
				g.drawString(dfmoney.format(25000*(i+1)), 130+100*i, 470);
			}

			g.setColor(Color.blue);
			for(int i=0 ; i<12 ; i++) {
				if(monthly[i]>0) {
					g.fillRect(50, 420-35*i, monthly[i]*4, 15);
				}
			}
			
			g.setColor(Color.red);
			for(int i=1 ; i<=12 ; i++) {
				String month;
				if(new SalesDAO().showMonthlySales(year, i) != null) {
					month = dfmoney.format(Integer.parseInt(new SalesDAO().showMonthlySales(year, i))/1000);
				} else {
					month = "";
				}
				
				if(monthly[i-1]>0) {
					g.drawString(month, 60+monthly[i-1]*4, 466-35*i);
				}
			}
		}
	}

	// 재고관리
	class Management extends JFrame {
		
		ArrayList<JPanel> catArr = new ArrayList<JPanel>();
		
		public Management() {
			
			super("재고관리");
			setSize(876 ,749);
			setLocationRelativeTo(null);
			Container con = getContentPane();
			con.setBackground(Color.white);
			setLayout(null);
			
			JPanel backP = new JPanel();
			backP.setBounds(20, 20, 820, 670);
			backP.setBackground(mainColor);
			backP.setLayout(null);
			add(backP);
			
			JLabel dateLb = new JLabel("재고관리");
			dateLb.setBounds(50, 30, 200, 50);
			dateLb.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			backP.add(dateLb);
			
			ImageIcon exit_img = new ImageIcon("./img/return.png");
			JButton exit = new JButton(exit_img);
			exit.setBounds(725, 40, 40, 40);
			exit.setBackground(mainColor);
			exit.setBorderPainted(false);
			exit.setFocusable(false);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			backP.add(exit);
			
			ArrayList<MenuDTO> md = new MenuDAO().list();
			Object [] title = {"메뉴명", "가격", "재고량"};
			Object [][] data = new Object[md.size()][3];
			for(int i=0 ; i<md.size() ; i++) {
				data[i][0] = " " + md.get(i).name;
				data[i][1] = dfmoney.format(md.get(i).price)+" ";
				data[i][2] = md.get(i).cnt;
			}
			
			DefaultTableModel model = new DefaultTableModel(data, title);
			JTable salesTable = new JTable(model);
			JScrollPane salesScrollTable = new JScrollPane(salesTable);
			salesScrollTable.setBounds(50, 90, 270, 535);
			salesTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			salesTable.getColumnModel().getColumn(1).setPreferredWidth(70);
			salesTable.getColumnModel().getColumn(2).setPreferredWidth(60);
			DefaultTableCellRenderer dtcrCenter = new DefaultTableCellRenderer();
			DefaultTableCellRenderer dtcrRight = new DefaultTableCellRenderer();
			dtcrCenter.setHorizontalAlignment(SwingConstants.CENTER);
			dtcrRight.setHorizontalAlignment(SwingConstants.RIGHT);
			salesTable.getColumnModel().getColumn(1).setCellRenderer(dtcrRight);
			salesTable.getColumnModel().getColumn(2).setCellRenderer(dtcrCenter);
			backP.add(salesScrollTable);
			
			// 메뉴 버튼
			for(int i=1 ; i<=8 ; i++) {
				JPanel menuPanel = new JPanel();
				menuPanel.setBounds(350, 255, 415, 370);
				menuPanel.setLayout(new GridLayout(5, 4, 5, 5));
				menuPanel.setBackground(mainColor);
				for(int j=1 ; j<=20 ; j++) {
					int menuNo = i*100+j;
					String menuName = "";
					String menuCnt = "";
					if(new MenuDAO().detail(menuNo)!=null) {
						menuName = new MenuDAO().detail(menuNo).getName(); 
						menuCnt += dfmoney.format(new MenuDAO().detail(menuNo).getCnt());	
					}
					JButton menuBtn = new JButton("<html><body><center>"+menuName+"<br>재고 : "+menuCnt+"</center></body></html>");
					menuBtn.setBackground(Color.white);
					
					if(new MenuDAO().detail(menuNo)!=null && new MenuDAO().detail(menuNo).getCnt()<=0) {
						menuBtn.setBackground(Color.yellow);
					}
					menuBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
					menuBtn.setHorizontalAlignment(JLabel.CENTER);
					menuBtn.setBorder(lb_white);
					menuBtn.setFocusable(false);
					
					if(new MenuDAO().detail(menuNo)==null) {
						menuBtn.setVisible(false); 
					}
					menuPanel.add(menuBtn);
					menuBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int before = new MenuDAO().detail(menuNo).getCnt();	// 매진이 풀리는 상황을 위해 기존재고 저장
							
							JFrame key = new JFrame("NumPad");
							key.setBounds(650, 400, 245, 400);
							JPanel back = new JPanel();
							back.setBackground(mainColor);
							back.setLayout(null);
							JTextField numArea = new JTextField();
							numArea.setBounds(5, 5, 220, 50);
							numArea.setFont(new Font("맑은 고딕", Font.BOLD, 40));
							numArea.setHorizontalAlignment(JTextField.RIGHT);
							back.add(numArea);
							JPanel numKeyP = new JPanel();
							numKeyP.setBounds(5,60,220,295);
							numKeyP.setBackground(mainColor);
							numKeyP.setLayout(new GridLayout(4,3,5,5));
							back.add(numKeyP);
							
							ArrayList<JButton> numKeyArr = new ArrayList<JButton>();
							for(int i=0 ; i<12 ; i++) {
								String [] num = "7,8,9,4,5,6,1,2,3,◀,0,OK".split(",");
								JButton numBtn_i = new JButton(num[i]);
								numBtn_i.setBackground(Color.white);
								numBtn_i.setFont(new Font("맑은 고딕", Font.BOLD, 40));
								numBtn_i.setFocusable(false);
								numKeyP.add(numBtn_i);
								numKeyArr.add(numBtn_i);
								if(i!=9 && i!=11) {
									numBtn_i.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											numArea.setText(numArea.getText()+numBtn_i.getText());
										}
									});
								}
							}
							numKeyArr.get(9).setFont(new Font("맑은 고딕", Font.BOLD, 36));
							numKeyArr.get(11).setFont(new Font("맑은 고딕", Font.BOLD, 25));
							
							key.add(back);
							key.setVisible(true);
							key.setResizable(false);

							numKeyArr.get(9).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(!numArea.getText().equals("")) {
										numArea.setText(numArea.getText().substring(0, numArea.getText().length()-1));
									}
								}
							});
							
							numKeyArr.get(11).addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int qty = Integer.parseInt(numArea.getText());
										key.dispose();
										
										new MenuDAO().stock(menuNo, new MenuDAO().detail(menuNo).getCnt()-qty);
										
										if(new MenuDAO().detail(menuNo).getCnt()<=0) {
											for(String ip : tableIP) {
												new UniSendtoOne(ip, 8888, "#SO"+menuNo).start();
											}
											menuBtn.setBackground(Color.yellow);
											
											try {
												Thread.sleep(100);	// 딜레이 0.1초
											} catch (InterruptedException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
										} else if(before<=0 && new MenuDAO().detail(menuNo).getCnt()>0) { 
											for(String ip : tableIP) {
												new UniSendtoOne(ip, 8888, "#EE"+menuNo).start();
											}
											menuBtn.setBackground(Color.white);
										}
										
									} catch(Exception e1) {
										
									}
									
									menuBtn.setText("<html><body><center>"+new MenuDAO().detail(menuNo).getName()+"<br>재고 : "
											+dfmoney.format(new MenuDAO().detail(menuNo).getCnt())+"</center></body></html>");
									DefaultTableModel model = (DefaultTableModel)salesTable.getModel();
									for(int i=0 ; i<md.size() ; i++) {
										if(new MenuDAO().detail(menuNo).getName().equals(md.get(i).name)) {
											model.setValueAt(new MenuDAO().detail(menuNo).getCnt(), i,2);
										}
									}
								}
							});

							numArea.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										int qty = Integer.parseInt(numArea.getText());
										key.dispose();
										new MenuDAO().stock(menuNo, new MenuDAO().detail(menuNo).getCnt()-qty);
										
										if(new MenuDAO().detail(menuNo).getCnt()<=0) {
											for(String ip : tableIP) {
												new UniSendtoOne(ip, 8888, "#SO"+menuNo).start();
											}
											menuBtn.setBackground(Color.yellow);
											
											try {
												Thread.sleep(100);
											} catch (InterruptedException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
										} else if(before<=0 && new MenuDAO().detail(menuNo).getCnt()>0) { 
											for(String ip : tableIP) {
												new UniSendtoOne(ip, 8888, "#EE"+menuNo).start();
											}
											menuBtn.setBackground(Color.white);
										}
										
									} catch(Exception e1) {
										
									}
									
									menuBtn.setText("<html><body><center>"+new MenuDAO().detail(menuNo).getName()+"<br>재고 : "
											+dfmoney.format(new MenuDAO().detail(menuNo).getCnt())+"</center></body></html>");
									DefaultTableModel model = (DefaultTableModel)salesTable.getModel();
									for(int i=0 ; i<md.size() ; i++) {
										if(new MenuDAO().detail(menuNo).getName().equals(md.get(i).name)) {
											model.setValueAt(new MenuDAO().detail(menuNo).getCnt(), i,2);
										}
									}
								}
							});
						}
					});
				}
				backP.add(menuPanel);
				menuPanel.setVisible(false);
				catArr.add(menuPanel);
			}
			catArr.get(0).setVisible(true);
			
			// 카테고리 버튼
			JPanel categoryP = new JPanel();
			categoryP.setBounds(350, 90, 415, 145);
			categoryP.setLayout(new GridLayout(2, 4, 5, 5));
			categoryP.setBackground(new Color(225,231,251));
			ButtonGroup cbg = new ButtonGroup();
			for(int i=0 ; i<8 ; i++) {
				String [] cat = {"튀김", "마른안주", "탕", "과일", "주류", "음료", "예비1", "예비2"};
				JToggleButton catBtn = new JToggleButton(cat[i]);
				catBtn.setBackground(new Color(30, 38, 80));
				catBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
				catBtn.setForeground(Color.white);
				catBtn.setHorizontalAlignment(JLabel.CENTER);
				catBtn.setBorderPainted(false);
				catBtn.setFocusable(false);
				categoryP.add(catBtn);
				cbg.add(catBtn);
				catBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int c = 0;
						for(int j = 0 ; j<cat.length ; j++) {
							if(cat[j].equals(e.getActionCommand())) {
								c = j;
							}
						}
						for(int j=0 ; j<8 ; j++) {
							catArr.get(j).setVisible(false);
						}
						catArr.get(c).setVisible(true);
					}
				});
				
			}
			backP.add(categoryP);
			
			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
	}
	
	// 통신 리시버
	class UniReceiver extends Thread {
		
		int port;
		String orderString = "";
		
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
					String msg = new String(buf).trim();
					System.out.println(msg);
					
					if (msg.contains("!")) {	// 직원호출
						String tableNumber = msg.substring(1);
						JOptionPane.showMessageDialog(null, "<html><body><center>" + tableNumber + "번 테이블 호출입니다.</center></body></html>");

					} else if (msg.contains("B")) {	// 인원 입장
						int tNum = Integer.parseInt(msg.split(",")[0].substring(1));
						int bNum = Integer.parseInt(msg.split(",")[1].substring(1,2));
						int gNum = Integer.parseInt(msg.split(",")[2].substring(1,2));
						
						tableInfoArr[tNum-1].tableInfo(bNum, gNum);
						
						int num = Integer.parseInt(tableBtnArr.get(tNum-1).getText())-1;
						tableBtnInfoArr.get(num).setText("<html><body>남 " + tableInfoArr[tNum-1].boy_Num + " 여 " + tableInfoArr[tNum-1].girl_Num 
							+ "<br><br>Total : " + dfmoney.format(tableInfoArr[tNum-1].calcTotalPrice()) + "</body></html>");
						
						tableBtnArr.get(num).setBackground(tableInfoArr[tNum-1].tableColor);
						tableBtnArr.get(num).setForeground(tableInfoArr[tNum-1].tableColor);
						
						for(String ip : tableIP) {	// 인원정보 각 테이블 전송
							new UniSendtoTableInfo(ip, 8888).start();
						}
						
						for(MenuDTO md : new MenuDAO().list()) {	// 매진인 메뉴 전송
							if(md.cnt<=0) {
								String soldOutStr = "#SO" + md.id; 
								new UniSendtoOne(tableIP.get(tNum-1), 8888, soldOutStr).start();
								try {
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					
					} else if(msg.contains("K")) {	// 주방에서 메뉴나왔어요
						String tableNumber = msg.substring(1).split(",")[0];
						String orderNumber = msg.substring(1).split(",")[1];
						JOptionPane.showMessageDialog(null,"<html><body><center>"+ tableNumber + "번 테이블 음식 나왔습니다.<br>주문번호 : "
								+ orderNumber + "</center></body></html>");
						
					} else {	// 메뉴 주문인데
						String[] msgArr = msg.split(",");
						int tNum;
						int pNum = 0;
						
						if(msgArr[0].contains("#")) {	// 선물일경우
							tNum = Integer.parseInt(msgArr[0].split("t")[1]);
							pNum = Integer.parseInt(msgArr[0].split("t")[0].substring(1));
							orderString = tNum + "번 테이블이 \n" + pNum+"번 테이블로 선물\n" + timeSdf.format(new Date()) + "\n------------\n";
					
						} else {	// 그냥 주문일경우
							tNum = Integer.parseInt(msg.split(",")[0].substring(1));
							orderString = tNum+"번 테이블\n" + timeSdf.format(new Date()) + "\n------------\n";
						}
						
						int so = 0;
						String soldOut = "#NE";
						for(int i=1 ; i<msgArr.length ; i++) {	// 매진인 메뉴가 있는지 검증
							int id = Integer.parseInt(msgArr[i].split(":")[0]);
							int qty = Integer.parseInt(msgArr[i].split(":")[1]);
							if(new MenuDAO().detail(id).cnt < qty) {
								soldOut += (so==0 ? "" : ", ")+new MenuDAO().detail(id).name;
								so += 1;	// 매진일경우 추가
							} 
						}
						
						if (so==0) {	// 매진이 없을시
							for(int i=1 ; i<msgArr.length ; i++) {
								int id = Integer.parseInt(msgArr[i].split(":")[0]);
								int qty = Integer.parseInt(msgArr[i].split(":")[1]);
								
								for(int j=0 ; j<qty ; j++) {
									tableInfoArr[tNum-1].addOrderMap(id);	// 인포에 메뉴 올림
									new MenuDAO().stock(id, 1);				// DB 재고 차감
									if(new MenuDAO().detail(id).getCnt()==0) {
										for(String ip : tableIP) {
											new UniSendtoOne(ip, 8888, "#SO"+id).start();
										}
								
										try {
											Thread.sleep(100);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
								orderString += new MenuDAO().detail(id).getName() + "    " + qty + "\n";
							}
							
							if(msgArr[0].contains("#")) {	// 선물일경우
								orderString += "T" + pNum;
							} else {	// 그냥 주문일경우
								orderString += "T" + tNum;
							}
							
							new UniSendtoOne(kitchenIP, 8888, orderString).start();
							new UniSendtoOne(tableIP.get(tNum-1), 8888, "#OK").start();
						
						} else {
							new UniSendtoOne(tableIP.get(tNum-1), 8888, soldOut).start();
							JOptionPane.showMessageDialog(null, "<html><body><center>" + tNum + "번 테이블에서 " + soldOut.substring(3)
								+ " 메뉴가 부족하여 주문이 취소됐습니다.<br>재고를 확인해주세요.</center></body></html>");
						}
						
						int num = Integer.parseInt(tableBtnArr.get(tNum-1).getText())-1;
						tableBtnInfoArr.get(num).setText("<html><body>남 " + tableInfoArr[tNum-1].boy_Num + " 여 " + tableInfoArr[tNum-1].girl_Num 
							+ "<br><br>Total : " + dfmoney.format(tableInfoArr[tNum-1].calcTotalPrice()) + "</body></html>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
	// 1인 통신 샌더
	class UniSendtoOne extends Thread {
		
		InetAddress addr;
		int port;
		String msg;
		int toTableNum;
		
		public UniSendtoOne(String addr, int port, String inputOrderListStr) {
			super();
			try {
				this.addr = InetAddress.getByName(addr);
				this.msg = inputOrderListStr;
				this.port = port;
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
						buf,
						buf.length,
						addr,
						port
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
	
	// 테이블 정보 송출
	class UniSendtoTableInfo extends Thread {
		
		InetAddress addr;
		int port;
		String msg;
		int toTableNum;
		
		public UniSendtoTableInfo(String addr, int port) {
			super();
			try {
				
				String msggo = "";
				for (int i=0 ; i<tableInfoArr.length ; i++) {
					msggo += "T"+(i+1)+",B"+tableInfoArr[i].boy_Num+",G"+tableInfoArr[i].girl_Num;
					if(i != tableInfoArr.length-1) {
						msggo += "/";
					}
				}				// T1,B2,G3/T2,B2,G2/T3,B4,G0/.../T28,B0,G0
				
				this.addr = InetAddress.getByName(addr);
				this.msg = msggo;
				this.port = port;
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
						buf,
						buf.length,
						addr,
						port
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
	
}

public class TableOrderMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TableOrder();
	}

}