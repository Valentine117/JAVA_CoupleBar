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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument.Content;

import coupleBar.TableScreenFrame.Counter;
import coupleBar.TableScreenFrame.OrderMenuFrame;
import coupleBar.TableScreenFrame.SendTxtFrame;
import coupleBar.TableScreenFrame.UniSendGiftToKitchen;
import coupleBar.TableScreenFrame.UniSendOk;
import coupleBar.TableScreenFrame.UniSendOrderToKitchen;
import coupleBar.TableScreenFrame.UniSender;



public class TableScreenFrame extends JFrame{
	
	//2022-04-18
	UniReceiver UR;
	
	//2022-04-19
	JLabel total_price;
	Set<Integer> mergeAskerSet = new HashSet<Integer>();
	Set<Integer> mergeAskingSet = new HashSet<Integer>();
	
	//2022-04-14
	JFrame orderlist_pop = null;
	JFrame jf1 = null;
	JFrame giftFrame = null;
	JFrame mergeOk = null;
	soldOutMsgFrame somf = null;
	MessageOrMerge mom = null;
	SendTxtFrame stf = null;
	JFrame[] jFrameArr = new JFrame[12];
	
	//2022-04-15
	JLabel newMenuText = new JLabel("메뉴를 선택 해주세요");
	
	//여기까지
	
	int boyNum=0, girlNum=0, tableNum = 0,newMsgNum =0;
	TableInfo tio;
//	tableInfo tio;
	
	JPanel tableMainPanel;
	OrderMenuFrame omf;
	MessageFrame mf;
	JLabel newMsg;
	JFrame shopping_Popup;
	// 수정
	JButton shopping_okay, shopping_close,gift_Btn, plus_Btn, minus_Btn;
	DecimalFormat dfmoney = new DecimalFormat("#,##0");
	
	Font f1 = new Font("맑은고딕", Font.PLAIN, 15);
	Font f2 = new Font("맑은고딕", Font.BOLD, 15);
	Font orderFont = new Font("HY동녘B", Font.BOLD, 30);
	Font bagfont = new Font("휴먼둥근헤드라인", Font.BOLD, 40);
	LineBorder lb_black = new LineBorder(Color.black);
	LineBorder lb_white = new LineBorder(Color.white);
	
	//수정
	JPanel orderP_3;
	
	//추가 2022-04-08
	boolean chk = true;
	//
	
	//추가 2022-04-06
	JPanel mergeAsked;
	ButtonGroup obg = new ButtonGroup();
	ArrayList<JToggleButton> togbtnList = new ArrayList<JToggleButton>();
	ArrayList<String> tableIPList = new ArrayList<String>();
	int newAskedMergeNum =0;
	String counterAddress = "192.168.20.28";
	//여기까지
	
	//추가 2022-04-12
	Map<String, Integer> orderedListMap = new LinkedHashMap<String, Integer>();
	Map<String, JButton> menuIdButtonMap = new LinkedHashMap<String, JButton>();
	Stack<String> orderedListStrStack = new Stack<String>();
	Stack<Map<String, Integer>> orderedListStack = new Stack<Map<String,Integer>>();
	
	//추가 2022-04-08
	JTextArea orderedListStrArea = new JTextArea();
	String asdf; 
	ArrayList<JButton> tNumArr = new ArrayList<JButton>();
	ArrayList<JLabel> tInfoArr = new ArrayList<JLabel>();
	
	
	Color boyColor = new Color(100, 200, 255);
	Color girlColor = new Color(255, 100, 200);
	Color mixColor = new Color(200, 100, 200);
	Counter counterFrame;
	//여기까지
	//추가 2022-04-07 
	int giftTargetNum;
	
	//수정 2022-04-07 15:58
	String jumon_clear = "";
	String [] lb2 ;
	int menuNo, qty;
	String kitchenIP = "192.168.20.46";
	// 수정완료
	
	TableInfo[] tiArr = new TableInfo[29];
	public void setFirstTableArr() {
		for(int i = 0; i<tiArr.length; i++) {
			TableInfo tt = new TableInfo(i+1, false);
			tiArr[i] = tt;
		}
	}
	
	
	boolean menu_b1_b = true, menu_b2_b = false, menu_b3_b = false, menu_b4_b = false, 
			menu_b5_b = false, menu_b6_b = false;
	
	int fri_cnt = 0, dry_cnt = 0, tang_cnt = 0, fru_cnt = 0, drink_cnt = 0, beverage_cnt = 0;
	public TableScreenFrame(int tableNum) {
		
		super(tableNum + "번 테이블");
		this.tableNum = tableNum;
		
		
		
		//추가 2022-04-08
		setFirstTableArr();
		//여기까지
		
		
		//추가 2022-04-06
		for(int i = 0; i<=29;i++) {
			if(i==11) {
				tableIPList.add("192.168.20.38");
			}
			else if(i==20){
				tableIPList.add("192.168.20.30");
			}
			else if(i==4){
				tableIPList.add("192.168.20.45");
			}
			else {
				tableIPList.add("192.111.11.11");
			}
		}
		//여기까지
		
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//추가 2022-04-06
		mergeAsked = new JPanel();
		mergeAsked.setBounds(300, 50, 1000, 600);
		mergeAsked.setBackground(Color.white);
		mergeAsked.setLayout(null);
		mergeAsked.setVisible(false);
		JLabel mergeText = new JLabel("<요청받은 합석 목록>");
		mergeText.setBounds(0, 0, 1000, 30);
		mergeText.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 20));
		mergeAsked.add(mergeText);

		setBounds(100, 80, 1000, 730);
		setLayout(null);
		
		//여기까지
		setBounds(100, 80, 1000, 730);
		setLayout(null);
		
		// 04-14 수정
		jf1 = new JFrame(tableNum + "번 테이블 인원 정보 입력");
		jf1.setSize(880,600);
		jf1.setLocationRelativeTo(null);
		jf1.getContentPane().setBackground(new Color(244, 238, 248));
		jf1.setLayout(null);
		
		ImageIcon imgman = new ImageIcon("./img/man.png");
		ImageIcon imgwoman = new ImageIcon("./img/woman.png");
		
		JButton man = new JButton(imgman);
		man.setBounds(80, 100, 130, 130);
		man.setBackground(new Color(244, 238, 248));
		// 2022-04-12 추가
		man.setFocusable(false);
		man.setBorderPainted(false);
		jf1.add(man);
		
		JButton woman = new JButton(imgwoman);
		woman.setBounds(80, 300, 130, 130);
		woman.setBackground(new Color(244, 238, 248));
		// 2022-04-12 추가
		woman.setFocusable(false);
		woman.setBorderPainted(false);
		jf1.add(woman);
		
		
		JLabel boyNumText = new JLabel("남성 수: " + boyNum);
		boyNumText.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		boyNumText.setBounds(250, 100, 250, 110);
		jf1.add(boyNumText);
		
		ImageIcon imgupm = new ImageIcon("./img/arrow-upman.png");
		ImageIcon imgdom = new ImageIcon("./img/arrow-downman.png");
		ImageIcon imgupw = new ImageIcon("./img/arrow-up.png");
		ImageIcon imgdow = new ImageIcon("./img/arrow-down.png");
		
		JButton boyPlus = new JButton(imgupm);
		boyPlus.setBounds(500,100,50,50);
		boyPlus.setBackground(Color.white);
		jf1.add(boyPlus);
		boyPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((boyNum+girlNum)<6)boyNum +=1;
				boyNumText.setText("남성 수: " + boyNum);
			}
		});
		
		JButton boyMinus = new JButton(imgdom);
		boyMinus.setBounds(500,160,50,50);
		boyMinus.setBackground(Color.white);
		jf1.add(boyMinus);
		boyMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boyNum>0)boyNum -=1;
				boyNumText.setText("남성 수: " + boyNum);
			}
		});
		
		JLabel girlNumText = new JLabel("여성 수: " + girlNum);
		girlNumText.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		girlNumText.setBounds(250, 300, 250, 110);
		jf1.add(girlNumText);
		
		JButton girlPlus = new JButton(imgupw);
		girlPlus.setBounds(500,300,50,50);
		girlPlus.setBackground(Color.white);
		jf1.add(girlPlus);
		girlPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((boyNum+girlNum)<6)girlNum +=1;
				girlNumText.setText("여성 수: " + girlNum);
			}
		});

		JButton girlMinus = new JButton(imgdow);
		girlMinus.setBounds(500,360,50,50);
		girlMinus.setBackground(Color.white);
		jf1.add(girlMinus);
		girlMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(girlNum>0)girlNum -=1;
				girlNumText.setText("여성 수: " + girlNum);
			}
		});
		
		ImageIcon entry = new ImageIcon("./img/entry.png");
		
		JButton startSeat = new JButton("입장!", entry);
		startSeat.setBounds(620, 190, 200, 150);
		startSeat.setBackground(new Color(250, 235, 255));
		startSeat.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jf1.add(startSeat);
		startSeat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if((boyNum+girlNum)<1) {
					System.out.println("최소 한 명 이상의 인원을 입력하세요");
				}
				else {
				tio = new TableInfo(tableNum, boyNum, girlNum);
				omf = new OrderMenuFrame();
				jf1.dispose();
				//추가 2022-04-08
				new UniSendTableInfoStr(counterAddress,8888,tio).start();
				//여기까지
				
				}
			}
		});
		
		mf = new MessageFrame(tableNum);
		
		jf1.setVisible(true);
		jf1.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	Map<Integer, Integer> orderM = new LinkedHashMap<Integer, Integer>();// 임시 주문내역(Order창을 킬때마다 생성), 주문하기 버튼늘 누르면 TableInfo로 전송
	
	class OrderMenuFrame extends JFrame implements MouseWheelListener {
		int num_T;
		Color backC = new Color(225,231,251);
		Color btnC = Color.white;
		JPanel orderP_2;	// 주문내역 표시패널
		ButtonGroup orderButtonGroup;	// 주문내역 토글버튼그룹
		ArrayList<JToggleButton> orderButtonArr = new ArrayList<JToggleButton>();
			
		Map<JToggleButton, ArrayList> orderBtnM = new LinkedHashMap<JToggleButton, ArrayList>();	// 주문내역 토글버튼 + 라벨 어레이
		ArrayList<JLabel> orderTotArr = new ArrayList<JLabel>();	// 주문내역 금액총합 라벨어레이
		
		// 테이블 오더 총액
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
		
		
		public void addOrderM(int id) {
			if(!orderM.containsKey(id)) {
				orderM.put(id, 1);
				
				System.out.println(orderM);
				
				JToggleButton newMenuBtn = new JToggleButton();
				newMenuBtn.setBounds(0, 30*orderM.size()-30, 498, 30);
				newMenuBtn.setLayout(null);
				newMenuBtn.setBackground(Color.white);
				newMenuBtn.setBorderPainted(false);
				
				ArrayList<JLabel> newMenuArr = new ArrayList<JLabel>();
				for(int i=0 ; i<6 ; i++) {
					String menuName = new MenuDAO().detail(id).getName();
					String menuPrice = dfmoney.format(new MenuDAO().detail(id).getPrice());
					String [] lb1 = {orderM.size()+"", menuName, menuPrice, 1+"", menuPrice, ""};
					
					
					
					int [] lb1w = {0, 40, 238, 318, 378, 458, 498};
					JLabel lb_i = new JLabel(lb1[i]);
					lb_i.setBounds(lb1w[i], 0, lb1w[i+1]-lb1w[i], 30);
					lb_i.setFont(f1);
					lb_i.setHorizontalAlignment(JLabel.CENTER);
					newMenuArr.add(lb_i);
					newMenuBtn.add(lb_i);
				}
				
				orderBtnM.put(newMenuBtn, newMenuArr);
				//기억
				orderButtonArr.add(newMenuBtn);

			} else {
				orderM.put(id, orderM.get(id)+1);
			}
		}
		
		
		public OrderMenuFrame(){
			// 04-14 수정
			super(tio.table_Num + "번 테이블 주문");
			setBounds(200,300,1300, 750);
			setLocationRelativeTo(null);
			
			// 티오더에 배경 추가
			JLabel menu_back = new JLabel();
			menu_back.setBounds(0, 0, 1300, 750);
			menu_back.setIcon(new ImageIcon("Menu_img/배경.jpg"));
			add(menu_back);
			
			// 카테고리 별 이미지 배열에 넣기 
			// 튀김 이미지 배열에넣기
			String[] fried_img = new String[18];
			for (int i = 0; i < 18; i++) {
				fried_img[i] = "Menu_img/fried/튀김_"+ i +".jpg";
			};
			
			// 배열에 마른안주 이미지 넣기
			String[] dry_img = new String[12];
			for (int i = 0; i < 12; i++) {
				dry_img[i] = "Menu_img/dry/마른안주_"+ i +".jpg";
			};
			
			// 배열에 탕 이미지 넣기
			String[] tang_img = new String[12];
			for (int i = 0; i < 12; i++) {
				tang_img[i] = "Menu_img/tang/탕_"+ i +".jpg";
			};
			
			// 배열에 과일 이미지 넣기
			String[] fruit_img = new String[6];
			for (int i = 0; i < 6; i++) {
				fruit_img[i] = "Menu_img/fruit/과일_"+ i +".jpg";
			};
			
			// 배열에 주류 이미지 넣기
			String[] drink_img = new String[12];
			for (int i = 0; i < 12; i++) {
				drink_img[i] = "Menu_img/drink/술_"+ i +".jpg";
			};
			
			// 배열에 음료 이미지 넣기
			String[] beverage_img = new String[6];
			for (int i = 0; i < 6; i++) {
				beverage_img[i] = "Menu_img/beverage/음료_"+ i +".jpg";
			};	
			/// 이미지 배열에 넣기 완료
			
			// ------------------------- 튀김 만들기
			//튀김 화면 패널 3개 만들기
			JPanel menuPan_fried = new JPanel();
			menuPan_fried.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_fried.setLayout(null);
			menuPan_fried.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_fried.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭
			
			JPanel menuPan_fried2 = new JPanel();
			menuPan_fried2.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_fried2.setLayout(null);
			menuPan_fried2.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_fried2.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭
			
			JPanel menuPan_fried3 = new JPanel();
			menuPan_fried3.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_fried3.setLayout(null);
			menuPan_fried3.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_fried3.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭
			
			// 튀김 버튼 누르면 메뉴에 대한 상세 이미지가 나오게 만듦
			for (int i = 0; i < 18; i++) {
				JButton fried_i = new JButton(new ImageIcon(fried_img[i]));
				int fried_id = i+101;
				if (i<6) {
					menuPan_fried.add(fried_i);
				} else if(i>=6 && i <12) {
					menuPan_fried2.add(fried_i);
				} else if(i>=12&&i<18) {
					menuPan_fried3.add(fried_i);
				} else {
					fried_i.setVisible(false);
				}
				fried_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {						
						System.out.println(new MenuDAO().detail(fried_id));	// 메뉴버튼
						addOrderM(fried_id);
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(fried_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(fried_id), fried_i);
			}			
			
			//튀김 화면 패널 위치, 크기 수정 
			menuPan_fried.setSize(950,445);
			menuPan_fried.setLocation(265, 105);
			
			menuPan_fried2.setSize(950,445);
			menuPan_fried2.setLocation(265, 105);
			
			menuPan_fried3.setSize(950,445);
			menuPan_fried3.setLocation(265, 105);
			
			menuPan_fried.setVisible(true);
			// --------------------------- 튀김 끝
			
			// --------------------------- 마른안주 만들기
			// 마른안주 화면 패널 2개 만들기
			JPanel menuPan_dry = new JPanel();
			menuPan_dry.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_dry.setLayout(null);
			menuPan_dry.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_dry.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
			
			JPanel menuPan_dry2 = new JPanel();
			menuPan_dry2.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_dry2.setLayout(null);
			menuPan_dry2.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_dry2.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭
			
			// 마른안주 버튼 누르면 메뉴에 대한 상세표 나오게 만듦
			for (int i = 0; i < 12; i++) {
				JButton dry_i = new JButton(new ImageIcon(dry_img[i]));
				int dry_id = i+201;
				if (i<6) {
					menuPan_dry.add(dry_i);
				} else {
					menuPan_dry2.add(dry_i);
				} 
				if (i>6) {
					dry_i.setVisible(false);
				} 

				dry_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(new MenuDAO().detail(dry_id).id);	// 메뉴버튼
						
						// 종진이형이 만드신 메서드 추가
						addOrderM(dry_id);
						
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(dry_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
						
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(dry_id), dry_i);
			}	
			
			// 배열에 있는 마른안주 이미지를 버튼에 넣어 해당 패널에 추가 
			// 마른안주 화면 패널 위치, 크기 수정 
			menuPan_dry.setSize(950,445);
			menuPan_dry.setLocation(265, 105);
			
			menuPan_dry2.setSize(950,445);
			menuPan_dry2.setLocation(265, 105);
			
			menuPan_dry.setVisible(false);
			menuPan_dry2.setVisible(false);
			// ------------------------------ 마른안주 끝

			// --------------------------- 탕 만들기
			// 탕 화면 패널 2개 만들기
			JPanel menuPan_tang = new JPanel();
			menuPan_tang.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_tang.setLayout(null);
			menuPan_tang.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_tang.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
			
			JPanel menuPan_tang2 = new JPanel();
			menuPan_tang2.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_tang2.setLayout(null);
			menuPan_tang2.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_tang2.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭
			
			
			// 배열에 있는 탕 이미지를 버튼에 넣어 해당 패널에 추가 
			for (int i = 0; i < 12; i++) {
				JButton tang_i = new JButton(new ImageIcon(tang_img[i]));
				int tang_id = i+301;
				if (i<6) {
					menuPan_tang.add(tang_i);
				} else {
					menuPan_tang2.add(tang_i);
				} 
				if (i>9) {
					tang_i.setVisible(false);
				} 

				tang_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						System.out.println(new MenuDAO().detail(tang_id));	// 메뉴버튼
						addOrderM(tang_id);
						
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(tang_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(tang_id), tang_i);
			};	
			
			// 탕 화면 패널 위치, 크기 수정 
			menuPan_tang.setSize(950,445);
			menuPan_tang.setLocation(265, 105);
			
			menuPan_tang2.setSize(950,445);
			menuPan_tang2.setLocation(265, 105);
			
			menuPan_tang.setVisible(false);
			menuPan_tang2.setVisible(false);
			// ------------------------------ 탕 끝

			// --------------------------- 과일 만들기
			// 과일 화면 패널 1개 만들기
			JPanel menuPan_fruit = new JPanel();
			menuPan_fruit.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_fruit.setLayout(null);
			menuPan_fruit.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_fruit.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
				
			// 배열에 있는 과일 이미지를 버튼에 넣어 해당 패널에 추가 			
			for (int i = 0; i < 6; i++) {
				JButton fruit_i = new JButton(new ImageIcon(fruit_img[i]));
				int fruit_id = i+401;
				if (i<5) {
					menuPan_fruit.add(fruit_i);
				} else {
					fruit_i.setVisible(false);
				} 
				
				fruit_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						System.out.println(new MenuDAO().detail(fruit_id));	// 메뉴버튼
						addOrderM(fruit_id);
						
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(fruit_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(fruit_id),  fruit_i);
			};	
			
			// 과일 화면 패널 위치, 크기 수정 
			menuPan_fruit.setSize(950,445);
			menuPan_fruit.setLocation(265, 105);
			
			menuPan_fruit.setVisible(false);
			// ------------------------------ 과일 끝
 	
			
			// --------------------------- 주류 만들기
			// 주류 화면 패널 2개 만들기
			JPanel menuPan_drink = new JPanel();
			menuPan_drink.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_drink.setLayout(null);
			menuPan_drink.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_drink.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
			
			JPanel menuPan_drink2 = new JPanel();
			menuPan_drink2.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_drink2.setLayout(null);
			menuPan_drink2.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_drink2.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
			
			for (int i = 0; i < 12; i++) {
				JButton drink_i = new JButton(new ImageIcon(drink_img[i]));
				int drink_id = i+501;
				if (i<6) {
					menuPan_drink.add(drink_i);
				} else {
					menuPan_drink2.add(drink_i);
				} 
				if (i>9) {
					drink_i.setVisible(false);
				} 
				
				drink_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						System.out.println(new MenuDAO().detail(drink_id));	// 메뉴버튼
						addOrderM(drink_id);
						
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(drink_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(drink_id),  drink_i);
			};	
			
			// 주류 화면 패널 위치, 크기 수정 
			menuPan_drink.setSize(950,445);
			menuPan_drink.setLocation(265, 105);
			
			menuPan_drink2.setSize(950,445);
			menuPan_drink2.setLocation(265, 105);
			
			menuPan_drink.setVisible(false);
			menuPan_drink2.setVisible(false);
			// ------------------------------ 주류 끝
 	
			
			// --------------------------- 음료 만들기
			// 음료 화면 패널 1개 만들기
			JPanel menuPan_beverage = new JPanel();
			menuPan_beverage.setBackground(new Color(255,0,0,0));	// 배경색 투명으로 설정 
			menuPan_beverage.setLayout(null);
			menuPan_beverage.setLayout(new GridLayout());				// 이렇게 하면 일정한 간격
			menuPan_beverage.setLayout(new GridLayout(2,3,28, 46));		// 2행, 3열, 가로폭, 세로폭  	
			
			// 배열에 있는 음료 이미지를 버튼에 넣어 해당 패널에 추가 
			for (int i = 0; i < 6; i++) {
				JButton beverage_i = new JButton(new ImageIcon(beverage_img[i]));
				int beverage_id = i+601;
				if (i<6) {
					menuPan_beverage.add(beverage_i);
				} 
				
				if (i>3){
					beverage_i.setVisible(false);
				} 
				
				beverage_i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(new MenuDAO().detail(beverage_id));	// 메뉴버튼
						addOrderM(beverage_id);
						
						//2022-04-15
						newMenuText.setText(new MenuDAO().detail(beverage_id).getName() + " 메뉴를 담았습니다.");
						newMenuText.setForeground(Color.yellow);
					}
				});
				//2022-04-12
				menuIdButtonMap.put(Integer.toString(beverage_id),  beverage_i);
			};	
			
			// 음료 화면 패널 위치, 크기 수정 
			menuPan_beverage.setSize(950,445);
			menuPan_beverage.setLocation(265, 105);
			
			menuPan_beverage.setVisible(false);
			// ------------------------------ 음료 끝 	
			
			// 티오더 왼쪽 메뉴 카테고리 목록 만들기
			RoundedButton menu_b1 = new RoundedButton("튀김");
			
			menu_b1.setBounds(50,100, 100, 50);
			menu_b1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b1.addActionListener(new ActionListener() {
//				boolean menu_b1_b = false;
				@Override
				public void actionPerformed(ActionEvent e) {
					fri_cnt = 0;
					menuPan_fried.setVisible(true);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(false);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(false);
					menuPan_tang2.setVisible(false);
					
					menuPan_fruit.setVisible(false);
					
					menuPan_drink.setVisible(false);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(false);
					
					menu_b1_b = true;
					menu_b2_b = false;
					menu_b3_b = false;
					menu_b4_b = false;
					menu_b5_b = false;
					menu_b6_b = false;
				}
			});
			RoundedButton menu_b2 = new RoundedButton("마른안주");
			menu_b2.setBounds(50,160, 100, 50);
			menu_b2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dry_cnt = 0;
					menuPan_fried.setVisible(false);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(true);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(false);
					menuPan_tang2.setVisible(false);
					
					menuPan_fruit.setVisible(false);
					
					menuPan_drink.setVisible(false);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(false);
					
					menu_b1_b = false;
					menu_b2_b = true;
					menu_b3_b = false;
					menu_b4_b = false;
					menu_b5_b = false;
					menu_b6_b = false;
				}
			});
			
			RoundedButton menu_b3 = new RoundedButton("탕");
			menu_b3.setBounds(50,220, 100, 50);
			menu_b3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tang_cnt = 0;
					menuPan_fried.setVisible(false);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(false);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(true);
					menuPan_tang2.setVisible(false);
					
					menuPan_fruit.setVisible(false);
					
					menuPan_drink.setVisible(false);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(false);
					
					menu_b1_b = false;
					menu_b2_b = false;
					menu_b3_b = true;
					menu_b4_b = false;
					menu_b5_b = false;
					menu_b6_b = false;
				}
			});
			
			RoundedButton menu_b4 = new RoundedButton("과일");
			menu_b4.setBounds(50,280, 100, 50);
			menu_b4.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fru_cnt = 0;
					menuPan_fried.setVisible(false);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(false);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(false);
					menuPan_tang2.setVisible(false);
					
					menuPan_fruit.setVisible(true);
					
					menuPan_drink.setVisible(false);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(false);
					
					menu_b1_b = false;
					menu_b2_b = false;
					menu_b3_b = false;
					menu_b4_b = true;
					menu_b5_b = false;
					menu_b6_b = false;
				}
			});
			
			RoundedButton menu_b5 = new RoundedButton("주류");
			menu_b5.setBounds(50,340, 100, 50);
			menu_b5.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					drink_cnt = 0;
					menuPan_fried.setVisible(false);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(false);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(false);
					menuPan_tang2.setVisible(false);
					
					menuPan_fruit.setVisible(false);
					
					menuPan_drink.setVisible(true);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(false);
					
					menu_b1_b = false;
					menu_b2_b = false;
					menu_b3_b = false;
					menu_b4_b = false;
					menu_b5_b = true;
					menu_b6_b = false;
				}
			});
			
			RoundedButton menu_b6 = new RoundedButton("음료");
			menu_b6.setBounds(50,400, 100, 50);
			menu_b6.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					beverage_cnt = 0;
					menuPan_fried.setVisible(false);
					menuPan_fried2.setVisible(false);
					menuPan_fried3.setVisible(false);
					
					menuPan_dry.setVisible(false);
					menuPan_dry2.setVisible(false);
					
					menuPan_tang.setVisible(false);
					menuPan_tang2.setVisible(false);
				
					menuPan_fruit.setVisible(false);
					
					menuPan_drink.setVisible(false);
					menuPan_drink2.setVisible(false);
					
					menuPan_beverage.setVisible(true);
					
					menu_b1_b = false;
					menu_b2_b = false;
					menu_b3_b = false;
					menu_b4_b = false;
					menu_b5_b = false;
					menu_b6_b = true;
				}
			});
			
			// 04-13 수정
			// 04-14 수정
			LineBorder line = new LineBorder(Color.black);
			
			RoundedButton menu_b7 = new RoundedButton("주문내역");
			menu_b7.setBounds(50,460, 100, 50);
			menu_b7.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			menu_b7.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// 주문내역 클릭시 새 창이 뜨게
					// 합계
					orderlist_pop = new JFrame("주문내역");
				    orderlist_pop.setSize(1000,600);
			 	    orderlist_pop.setLocationRelativeTo(null);
			 	    orderlist_pop.setLayout(null);
			 	    orderlist_pop.setResizable(false);
			 	    orderlist_pop.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent e) {
							setVisible(true);
							orderlist_pop.setVisible(false);
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
			 	    
			 	    
			 	   // 추가 2022-04-12
			 	    ImageIcon orderimg = new ImageIcon("./img/orderlist.jpg");
			 	    
			 	    JPanel orderlist_poppanel = new JPanel() {
			 	    	
			 	    	@Override
			 	    	protected void paintComponent(Graphics g) {
			 	    		
			 	    		g.drawImage(orderimg.getImage(), 0, 0, null);
			 	    		setOpaque(false);
			 	    		super.paintComponent(g);
			 	    	}
			 	    };
			 	    
			 	    orderlist_poppanel.setBounds(0, 0, 1000, 600);
			 	    orderlist_poppanel.setLayout(null);
			 	    
			 	    JLabel order_label = new JLabel("주문내역");
			 	    order_label.setFont(bagfont);
			 	    order_label.setForeground(Color.white);
			 	    order_label.setBounds(50, 5, 300, 100);
			 	    orderlist_poppanel.add(order_label);
			 	    
			 	    // 주문내역 리스트
			 	    JPanel orderlist_panel = new JPanel();
			 	    orderlist_panel.setLayout(new BorderLayout());
			 	    orderlist_panel.setBounds(50, 130, 750, 350);	// 04-13 크기 수정
			 	    
			 	    // 04-13 추가
			 	   // 카테고리 패널
			 	    JPanel category_panel = new JPanel();
			 	    category_panel.setLayout(null);
			 	    category_panel.setBounds(50, 89, 750, 40);
			 	    category_panel.setBackground(Color.white);
			 	    orderlist_pop.add(category_panel);
			 	    
			 	    // 상품명 패널
			 	    JPanel proprietary_name = new JPanel();
			 	    proprietary_name.setLayout(null);
			 	    proprietary_name.setBounds(5, 5, 350, 30);
			 	    proprietary_name.setBackground(Color.white);
			 	    proprietary_name.setBorder(line);
			 	    category_panel.add(proprietary_name);
			 	    
			 	    Font namefont = new Font("휴먼엑스포", Font.BOLD, 16);
			 	    JLabel cateName = new JLabel("상품명");
			 	    cateName.setBounds(150, 0, 130, 30);
			 	    cateName.setFont(namefont);
			 	    proprietary_name.add(cateName);
			 	    
			 	    // 주문수량 패널
			 	    JPanel orderlist = new JPanel();
			 	    orderlist.setBounds(410, 5, 100, 30);
			 	    orderlist.setBackground(Color.white);
			 	    orderlist.setLayout(null);
			 	    orderlist.setBorder(line);
			 	    category_panel.add(orderlist);
			 	    
			 	    JLabel orderlabel = new JLabel("주문수량");
			 	    orderlabel.setBounds(15, 5, 80, 20);
			 	    orderlabel.setFont(namefont);
			 	    orderlist.add(orderlabel);
		
			 	    
			 	    // 메뉴 당 합계
			 	    JPanel totalMenu = new JPanel();
			 	    totalMenu.setBounds(530, 5, 150, 30);
			 	    totalMenu.setBackground(Color.white);
			 	    totalMenu.setLayout(null);
			 	    totalMenu.setBorder(line);
			 	    category_panel.add(totalMenu);
			 	    
			 	    JLabel totalMlabel = new JLabel("합계");
			 	    totalMlabel.setBounds(60, 5, 100, 20);
			 	    totalMlabel.setFont(namefont);
			 	    totalMenu.add(totalMlabel);
			 	    
//			 	    // 합계
			 	    JPanel total_panel = new JPanel();
			 	    total_panel.setBounds(50, 485, 750, 50);
			 	    total_panel.setLayout(null);
			 	    total_panel.setBackground(Color.white);
			 	    
			 	    JLabel total_label = new JLabel("합계 : ");
			 	    total_label.setBounds(400, 10, 130, 30);
			 	    total_label.setFont(orderFont);
			 	    total_panel.add(total_label);
			 	    
			 	    // 원
			 	   
			 	    String total_money = dfmoney.format(total);
			 	   
			 	    total_price = new JLabel();
			 	    total_price.setText(total + " 원");
			 	    total_price.setBounds(530, 10, 300, 30);
			 	    total_price.setFont(orderFont);
			 	    total_panel.add(total_price);
			 	    
			 	    orderlist_pop.add(total_panel);
			 	    
			 	    JScrollPane orderlist_scroll = new JScrollPane(orderlist_panel);
			 	    orderlist_scroll.setBounds(50, 130, 750, 350);
			 	    orderlist_scroll.setBackground(Color.white);
			 	    orderlist_scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			 	    orderlist_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			 	    orderlist_scroll.getVerticalScrollBar().setUnitIncrement(16);
			 	    // 04-14 수정 끝
			 	    // 04-13 추가 끝
			 	    
			 	    //추가 2022-04-08 수정 04-12
			 	    orderedListStrArea.setBounds(0, 0, 750, 350);
			 	    orderedListStrArea.setEditable(false);
			 	    
			 	    // 04-12
			 	    orderlist_panel.add(orderedListStrArea);
			 	    orderlist_pop.add(orderlist_scroll, "West");
			 	    orderlist_pop.add(orderlist_poppanel);
			 	    //여기까지
			 	    
			 	    
				 	Font clf = new Font("돋움", Font.BOLD, 20);
				 	
				 	// 04-12
				 	RoundedButton clsbtn = new RoundedButton("닫기");
					clsbtn.setBackground(new Color(235, 44, 44));
					clsbtn.setFont(clf);
					clsbtn.setForeground(Color.white);
					clsbtn.setBounds(830, 495, 80, 40);	// 04-18 배치 수정
					clsbtn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// 닫기 창
							setVisible(true);
							orderlist_pop.setVisible(false);
						}
					});
					orderlist_poppanel.add(clsbtn);	// 04-12
			 	   
					setVisible(false);
//					orderlist_pop.setAlwaysOnTop(true);
					orderlist_pop.setVisible(true);
				}
			});
			
			// 2022-04-18 수정하려는 부분
			JFrame call_Alba_Popup = new JFrame();
			call_Alba_Popup.setTitle("직원호출 Frame");	// 04 -12
			call_Alba_Popup.setBounds(0, 0, 1300, 750);	//1300, 750
//			call_Alba_Popup.setLayout(new FlowLayout());
			call_Alba_Popup.setAlwaysOnTop(true);
			
			JPanel call_Alba_Panel = new JPanel();
//			call_Alba_Panel.setBounds(0,0, 500, 300);
			call_Alba_Panel.setBorder(new EmptyBorder(5,5,5,5));
			call_Alba_Panel.setLayout(new BorderLayout(0,0));
			call_Alba_Panel.setLayout(null);
			
			call_Alba_Popup.setContentPane(call_Alba_Panel);
			call_Alba_Popup.setLocationRelativeTo(null);
			call_Alba_Popup.setUndecorated(true);
			call_Alba_Popup.setBackground(new Color(0,0,0,230));	
			
			
			JLabel call = new JLabel("직원을 호출하시겠습니까?");
			call.setBounds(250, 250, 1000, 150);
			call.setFont(new Font("HY동녘B", Font.BOLD, 70));
			call.setForeground(Color.white);
			call_Alba_Panel.add(call);
			
			RoundedButton okayBtn = new RoundedButton("네");
			okayBtn.setFont(new Font("맑은고딕", Font.BOLD, 50));
			okayBtn.setBounds(350, 400, 300, 100);
			okayBtn.setBackground(new Color(255,60,0));
			okayBtn.setVisible(true);
			okayBtn.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					call_Alba_Popup.setVisible(false);
					new Call_Alba(counterAddress, 8888).start();
				}
			});
			
			// 2022-04-18 수정 
			RoundedButton call_alba_closeBtn = new RoundedButton("아니요");
			call_alba_closeBtn.setFont(new Font("맑은고딕", Font.BOLD, 50));
			call_alba_closeBtn.setBounds(700, 400, 300, 100);
			call_alba_closeBtn.setBackground(new Color(255,60,0));
			call_alba_closeBtn.setVisible(true);
			call_alba_closeBtn.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					call_Alba_Popup.setVisible(false);
				}
			});
			
			
			call_Alba_Panel.add(okayBtn);
			call_Alba_Panel.add(call_alba_closeBtn);
			
			
			RoundedButton call_alba = new RoundedButton("직원호출");	// 04-12
			call_alba.setBounds(50, 540, 150, 60);
			call_alba.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			call_alba.setForeground(Color.white);
			call_alba.setBackground(new Color(255,60,0));
			call_alba.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					call_Alba_Popup.setAlwaysOnTop(true);
//					call_Alba_Panel.add(okayBtn);
					call_Alba_Popup.setVisible(true);
				}
			});
			
			// 장바구니 버튼
			RoundedButton shopping = new RoundedButton("장바구니");	// 04-12
			shopping.setBounds(50,610, 150, 60);
			shopping.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			shopping.setForeground(Color.white);
			shopping.setBackground(new Color(255,0,0));
			shopping.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//추가 2022-04-07
					OrderMenuFrame.this.setVisible(false);
					//여기까지
					// 04-14 장바구니 배경 설정
					ImageIcon shopback = new ImageIcon("./img/orderlist.jpg");
					JPanel shopBackpan = new JPanel() {
						
						@Override
						protected void paintComponent(Graphics g) {
							g.drawImage(shopback.getImage(), 0, 0, null);
							setOpaque(false);
							super.paintComponent(g);
						}
					};	// 배경 패널
					shopBackpan.setLayout(null);
					shopBackpan.setBounds(0, 0, 1100, 600);
					shopping_Popup = new JFrame("장바구니");	// 04-12
					shopping_Popup.setSize(1100, 600);	// 04-18 수정
					shopping_Popup.setLocationRelativeTo(null);
					shopping_Popup.getContentPane().setLayout(null);
					shopping_Popup.add(shopBackpan);
					
					// 장바구니 라벨
					
					JLabel inMybag = new JLabel("장바구니");
					inMybag.setBounds(100, 30, 200, 40);
					inMybag.setFont(bagfont);
					inMybag.setForeground(Color.white);
					shopBackpan.add(inMybag);
					
					
					// 주문하기 버튼
	               shopping_okay = new RoundedButton("주문하기");	// 04-12
	               shopping_okay.setFont(new Font("맑은 고딕", Font.BOLD, 20));
	               shopping_okay.setBackground(Color.white);	// 04-15
	               shopping_okay.setBounds(800, 250, 200, 70);	// 04-12
	               shopping_okay.addActionListener(new ActionListener() {
	               
	            	   // 수정
	                  @Override
	                  public void actionPerformed(ActionEvent e) {
	                     System.out.println("주문을 완료하였습니다.");
	                     //수정 2022-04-08
	                     asdf = "";
	                     
	                     //여기까지
	                     for (Entry<Integer, Integer> od : orderM.entrySet()) {
	                    	 asdf += od.getKey();
	                    	 asdf += ":";
	                    	 asdf += od.getValue();
	                    	 asdf += ",";
	                     }
	                     
	                     //2022-04-12 제거
	                     System.out.println(orderedListMap);
	                     	                     
	                     //추가 2022-04-08
	                     new UniSendOrderMapStrToCounter(counterAddress,8888,orderM).start();
	                     //여기까지
	                     
	                     
	                     //2022-04-12 제거
//	                     orderM.clear();
	                     asdf = "";
	                     orderButtonArr = new ArrayList<JToggleButton>();
	                     
	                     System.out.println("변경된 orderM : " + orderM);
	                     System.out.println("변경된 asdf : " + asdf);
	                     
	                     shopping_Popup.dispose();
	                     //추가 2022-04-07 18:00
	                     OrderMenuFrame.this.setVisible(true);
	                     //여기까지
	                     
	                     
	                  }
	               });
		           // 수정완료 14:46
	               // 닫기 버튼
	               shopping_close = new RoundedButton("닫기");	// 04-12
	               shopping_close.setFont(new Font("맑은 고딕", Font.BOLD, 20));
	               shopping_close.setForeground(Color.white);
	               shopping_close.setBackground(new Color(255,50,50));
	               shopping_close.setBounds(855, 350, 100, 70);	// 04-12
	               shopping_close.addActionListener(new ActionListener() {
	                  
	                  @Override
	                  public void actionPerformed(ActionEvent e) {
	                	  // TODO Auto-generated method stub
	                	  //2022-04-08 문제
	                	  asdf = "";
	                	  orderButtonArr = new ArrayList<JToggleButton>();
	                	  //수정
	                	  shopping_Popup.dispose();
	                	  //추가 2022-04-07 18:00
	                	  OrderMenuFrame.this.setVisible(true);
	                	  //여기까지
	                  }
	               });

	               gift_Btn = new RoundedButton("선물하기");	// 04-12
	               gift_Btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
	               gift_Btn.setBackground(Color.white);	// 04-15
	               gift_Btn.setBounds(800, 150, 200, 70);	// 04-12
	               gift_Btn.addActionListener(new ActionListener() {

	            	   @Override
	            	   public void actionPerformed(ActionEvent e) {
	            		   //추가 및 변경 2022-04-07
	            		   giftFrame = new JFrame("선물 해주기!");
	            		   giftFrame.setAlwaysOnTop(true);
	            		   giftFrame.setLayout(null);
	            		   giftFrame.setSize(300,200);	// 04-18수정
	            		   giftFrame.setLocationRelativeTo(null);

	            		   // 04-14
	            		   Font giftxt = new Font("HY견고딕", Font.BOLD, 15);
	            		   JLabel textL = new JLabel("선물할 테이블 번호를 정해주세요");
	            		   textL.setBounds(20, 20, 300, 30);
	            		   textL.setFont(giftxt);
	            		   giftFrame.add(textL);
	            		   
	            		   

	            		   Vector<Integer> tableNumVector = new Vector<Integer>();
	            		   for(int i =1;i<30;i++) {
	            			   int num = Integer.parseInt(tNumArr.get(i-1).getText())-1;
		                       int toTableBoyNum = Integer.parseInt(tInfoArr.get(num).getText().substring(2,3));
		                       int toTableGirlNum = Integer.parseInt(tInfoArr.get(num).getText().substring(6,7));
	            			   if(i != tableNum) {
	            				   if((toTableBoyNum+toTableGirlNum)>0)
	            				   tableNumVector.add(i);
	            			   }
	            		   }
	            		   JComboBox<Integer> box = new JComboBox<Integer>(tableNumVector);
	            		   box.setBounds(100, 55, 60, 20);

	            		   // 04-14 수정
	            		   Font giftff = new Font("휴먼둥근헤드라인", Font.BOLD, 20);
	            		   RoundedButton okBtn = new RoundedButton("선물!");	// 04-12
	            		   okBtn.setBounds(25, 100, 100, 50);
	            		   okBtn.setBackground(new Color(255, 195, 238));
	            		   okBtn.setFont(giftff);
	            		   okBtn.addActionListener(new ActionListener() {
	            			   @Override
	            			   public void actionPerformed(ActionEvent e) {
	            				   
	            				   giftTargetNum = Integer.parseInt(box.getSelectedItem().toString());
	            				   System.out.println("선물을 보냈습니다.");
	            				   System.out.println(orderM);
	            				   //2022-04-11 수정 (없애기 주방이 다 할거임)
	            				   //									new UniSendGiftToKitchen(kitchenIP, 8888, giftTargetNum, orderM).start();
	            				   //여기까지
	            				   //2022-04-12 추가
	            				   new UniSendGiftOrderMapToCounter(counterAddress, 8888, orderM, giftTargetNum).start();
	            				   //여기까지
	            				   //2022-04-12 추가
	            				   //									mapAddtoOrderedListMap(orderM);
	            				   giftFrame.dispose();
	            				   shopping_Popup.dispose();

	            				   //추가 2022-04-07 18:00
	            				   OrderMenuFrame.this.setVisible(true);
	            				   //여기까지
	            			   }
	            		   });

	            		   // 04-14 수정
	            		   RoundedButton cancelBtn = new RoundedButton("취소!");	// 04-12
	            		   cancelBtn.setBounds(150, 100, 100, 50);
	            		   cancelBtn.setBackground(new Color(255, 195, 238));
	            		   cancelBtn.setFont(giftff);
	            		   cancelBtn.addActionListener(new ActionListener() {

	            			   @Override
	            			   public void actionPerformed(ActionEvent e) {
	            				   giftFrame.dispose();
	            			   }
	            		   });



	            		   giftFrame.add(box);
	            		   giftFrame.add(okBtn);
	            		   giftFrame.add(cancelBtn);


	            		   giftFrame.setVisible(true);
	            		   //여기까지
	            	   }
	               });


	               JPanel orderP = new JPanel();
	               orderP.setBounds(100, 90, 500, 370);	// 04-12
	               orderP.setLayout(null);
	               orderP.setBorder(lb_black);
	               
	               if(orderM.isEmpty()) {
	            	   gift_Btn.setEnabled(false);
	            	   shopping_okay.setEnabled(false);
	               }
	               
	               JPanel orderP_1 = new JPanel();
	               orderP_1.setBounds(1, 1, 498, 29);
	               orderP_1.setBackground(Color.black);
	               orderP_1.setLayout(null);
	               for(int i=0 ; i<6 ; i++) {
	            	   String [] lb1 = {"No", "메 뉴 명", "단가", "수량", "금액", "비고"};
	            	   int [] lb1w = {0, 40, 238, 318, 378, 458, 498};
	            	   JLabel lb_i = new JLabel(lb1[i]);
	            	   lb_i.setBounds(lb1w[i], 0, lb1w[i+1]-lb1w[i], 30);
	            	   lb_i.setFont(f1);
	            	   lb_i.setHorizontalAlignment(JLabel.CENTER);
	            	   lb_i.setForeground(Color.yellow);
	            	   lb_i.setBorder(lb_white);
	            	   orderP_1.add(lb_i);
	               }
	               orderP.add(orderP_1);

	               JPanel op2 = new JPanel();
	   		       op2.setBounds(1, 30, 498, 310);
	   			   op2.setBackground(Color.gray);
	   			   op2.setLayout(null);
	   			   orderP.add(op2);
	               
	   			   int high = (orderM.size()<=10 ? 310 : 10+(30*orderM.size()));
	               orderP_2 = new JPanel();
	               orderP_2.setBounds(1, 0, 498, high);
	               orderP_2.setBackground(Color.white);
	               orderP_2.setLayout(null);
	               
	               //2022-04-19
	               if(!orderM.isEmpty()) {
	            	   int totalNum = 0;
		               for(Entry<Integer, Integer> mm : orderM.entrySet()) {
		            	   totalNum+= mm.getValue();
		               }
		               if(totalNum==0) {
		            	   gift_Btn.setEnabled(false);
		            	   shopping_okay.setEnabled(false);
		               }
		               else {
		            	   gift_Btn.setEnabled(true);
		            	   shopping_okay.setEnabled(true);
		               }
	               }
        		   else {
        			   gift_Btn.setEnabled(false);
	            	   shopping_okay.setEnabled(false);
        		   }

	               orderButtonGroup = new ButtonGroup();
	               System.out.println(orderM.size());
	               for(int i=0 ; i<orderM.size() ; i++) {
	            	   JToggleButton btn_i = new JToggleButton();
	            	   btn_i.setBounds(0, 30*i, 498, 30);
	            	   btn_i.setLayout(null);
	            	   btn_i.setBackground(Color.white);
	            	   btn_i.setBorderPainted(false);
	            	   ArrayList<JLabel> menuArr_i = new ArrayList<JLabel>();

	            	   for(int j=0 ; j<6 ; j++) {
	            		   menuNo = (int) orderM.keySet().toArray()[i];
	            		   String menuName = new MenuDAO().detail(menuNo).getName();
	            		   String menuPrice = dfmoney.format(new MenuDAO().detail(menuNo).getPrice());

	            		   qty = orderM.get(menuNo);
	            		   String total = dfmoney.format(new MenuDAO().detail(menuNo).getPrice()*qty);

	            		   String [] lb2 = {i+1+"", menuName, menuPrice+"", qty+"", total, ""};

	            		   int [] lb1w = {0, 40, 238, 318, 378, 458, 498};
	            		   JLabel lb_j = new JLabel(lb2[j]);
	            		   lb_j.setBounds(lb1w[j], 0, lb1w[j+1]-lb1w[j], 30);
	            		   lb_j.setFont(f1);
	            		   lb_j.setHorizontalAlignment(JLabel.CENTER);
	            		   menuArr_i.add(lb_j);
	            		   btn_i.add(lb_j);
	            	   }
	            	   // 수정 14:46
	            	   orderButtonArr.add(btn_i);

	            	   orderBtnM.put(btn_i, menuArr_i);

	            	   orderP_2.add(btn_i);
	            	   orderButtonGroup.add(btn_i);
	            	   //2022-04-12 추가
	            	   btn_i.setSelected(true);

	               }

	               op2.add(orderP_2);

	               orderTotArr = new ArrayList<JLabel>();

	               orderP_3 = new JPanel();
	               orderP_3.setBounds(1, 339, 498, 29);
	               orderP_3.setBackground(Color.black);
	               orderP_3.setLayout(null);
	               for(int i=0 ; i<4 ; i++) {
	            	   String [] lb1 = {"합  계", calcTotalQty()+"", dfmoney.format(calcTotalPrice()), ""};
	            	   int [] lb1w = {0, 318, 378, 458, 498};
	            	   JLabel lb_i = new JLabel(lb1[i]);
	            	   lb_i.setBounds(lb1w[i], 0, lb1w[i+1]-lb1w[i], 30);
	            	   lb_i.setFont(f1);
	            	   lb_i.setHorizontalAlignment(JLabel.CENTER);
	            	   lb_i.setForeground(Color.yellow);
	            	   lb_i.setBorder(lb_white);
	            	   orderTotArr.add(lb_i);
	            	   orderP_3.add(lb_i);
	               }
	               orderP.add(orderP_3);
	               
	               

	               // 04-14 수정
	               shopBackpan.add(orderP);

	               // 수정 + 버튼
	               plus_Btn = new RoundedButton("+");	// 04-12
	               plus_Btn.setFont(new Font("맑은 고딕", Font.BOLD, 30));	// 04-15
	               plus_Btn.setForeground(Color.white);
	               plus_Btn.setBackground(new Color(255,50,50));
	               plus_Btn.setBounds(650, 180, 80, 70);
	               plus_Btn.addActionListener(new ActionListener() {
	            	   @Override
	            	   public void actionPerformed(ActionEvent e) {
	            		   System.out.println("orderButtonArr.size(): "+orderButtonArr.size());
	            		   for(JToggleButton jtb : orderButtonArr) {
	            			   if(jtb.isSelected() == true) {
	            				   ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
	            				   int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
	            				   System.out.println(id);
	            				   orderM.put(id, orderM.get(id)+1);
	            				   menuArr.get(3).setText(orderM.get(id)+"");
	            				   menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice()));
	            				   menuArr.get(5).setText("");
	            				   orderTotArr.get(1).setText(calcTotalQty()+"");
	            				   orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));	            	               
	            			   }
	            		   }
	            		   
	            		   if(!orderM.isEmpty()) {
        	            	   int totalNum = 0;
        		               for(Entry<Integer, Integer> mm : orderM.entrySet()) {
        		            	   totalNum+= mm.getValue();
        		               }
        		               if(totalNum==0) {
        		            	   gift_Btn.setEnabled(false);
        		            	   shopping_okay.setEnabled(false);
        		               }
        		               else {
        		            	   gift_Btn.setEnabled(true);
        		            	   shopping_okay.setEnabled(true);
        		               }
        	               }
	            		   else {
	            			   gift_Btn.setEnabled(false);
    		            	   shopping_okay.setEnabled(false);
	            		   }
	            		   
	            		   System.out.println("수량+1");	
	            		   qty =+ 1;
	            	   }
	               });

	               minus_Btn = new RoundedButton("-");	// 04-12
	               minus_Btn.setFont(new Font("맑은 고딕", Font.BOLD, 30));	// 04-15
	               minus_Btn.setForeground(Color.white);
	               minus_Btn.setBackground(new Color(255,50,50));
	               minus_Btn.setBounds(650, 300, 80, 70);
	               minus_Btn.addActionListener(new ActionListener() {

	            	   @Override
	            	   public void actionPerformed(ActionEvent e) {
	            		   for(JToggleButton jtb : orderButtonArr) {
	            			   if(jtb.isSelected() == true) {
	            				   ArrayList<JLabel> menuArr = orderBtnM.get(jtb);
	            				   int id = new MenuDAO().detailName(menuArr.get(1).getText()).getId();
	            				   if(orderM.get(id)>=1) {
	            					   orderM.put(id, orderM.get(id)-1);
	            					   menuArr.get(3).setText(orderM.get(id)+"");
	            					   menuArr.get(4).setText(dfmoney.format(orderM.get(id)*new MenuDAO().detail(id).getPrice()));
	            					   orderTotArr.get(1).setText(calcTotalQty()+"");
	            					   orderTotArr.get(2).setText(dfmoney.format(calcTotalPrice()));
	            				   } 
	            			   }
	            		   }
	            		   

	    	               if(!orderM.isEmpty()) {
	    	            	   int totalNum = 0;
	    		               for(Entry<Integer, Integer> mm : orderM.entrySet()) {
	    		            	   totalNum+= mm.getValue();
	    		               }
	    		               if(totalNum==0) {
	    		            	   gift_Btn.setEnabled(false);
	    		            	   shopping_okay.setEnabled(false);
	    		               }
	    		               else {
	    		            	   gift_Btn.setEnabled(true);
	    		            	   shopping_okay.setEnabled(true);
	    		               }
	    	               }
	            		   qty =- 1;

	            		   System.out.println("수량-1");
	            	   }
	               });


	               //수정 2022-04-08
	               // 04-14수정
	               shopBackpan.add(shopping_close);
	               shopBackpan.add(shopping_okay);
	               shopBackpan.add(gift_Btn);
	               shopBackpan.add(plus_Btn);
	               shopBackpan.add(minus_Btn);
	               //수정완료
	               shopping_Popup.setAlwaysOnTop(true);
	               shopping_Popup.setVisible(true);
	               shopping_Popup.addMouseWheelListener(OrderMenuFrame.this);
				}
			});


			RoundedButton next = new RoundedButton("다음");	// 04-12
			next.setBounds(1160, 600, 80, 50);
			next.setBackground(Color.white);
			next.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (menu_b1_b == true) {
						if (fri_cnt == 2) {
							fri_cnt = 2;
							return;
						} else {
							fri_cnt += 1;
							if (fri_cnt == 0) {
								menuPan_fried.setVisible(true);
								menuPan_fried2.setVisible(false);
								menuPan_fried3.setVisible(false);
							} else if(fri_cnt==1) {
								menuPan_fried.setVisible(false);
								menuPan_fried2.setVisible(true);
								menuPan_fried3.setVisible(false);
							} else {
								menuPan_fried.setVisible(false);
								menuPan_fried2.setVisible(false);
								menuPan_fried3.setVisible(true);
							}
						}
					}
					
					if (menu_b2_b == true) {
						if (dry_cnt==1) {
							dry_cnt = 1;
						} else {
							dry_cnt += 1;
							if (dry_cnt == 0) {
								menuPan_dry.setVisible(true);
								menuPan_dry2.setVisible(false);
							} else if(dry_cnt==1) {
								menuPan_dry.setVisible(false);
								menuPan_dry2.setVisible(true);
							}
						}
					}
					
					if (menu_b3_b == true) {
						if (tang_cnt==1) {
							tang_cnt = 1;
						} else {
							tang_cnt += 1;
							if (tang_cnt == 0) {
								menuPan_tang.setVisible(true);
								menuPan_tang2.setVisible(false);
							} else if(tang_cnt==1) {
								menuPan_tang.setVisible(false);
								menuPan_tang2.setVisible(true);
							}
						}
					}
					
					if (menu_b5_b == true) {
						if (drink_cnt==1) {
							drink_cnt = 1;
						} else {
							drink_cnt += 1;
							if (drink_cnt == 0) {
								menuPan_drink.setVisible(true);
								menuPan_drink2.setVisible(false);
							} else if(drink_cnt==1) {
								menuPan_drink.setVisible(false);
								menuPan_drink2.setVisible(true);
							}
						}
					}
					
				}
			});
			
			RoundedButton before = new RoundedButton("이전");	// 04-12
			before.setBounds(1070, 600, 80, 50);	// 04-12
			before.setBackground(Color.white);
			before.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (menu_b1_b == true) {
						if (fri_cnt==0) {
							fri_cnt = 0;
						} else {
							fri_cnt -= 1;
							
							if (fri_cnt == 0) {
								menuPan_fried.setVisible(true);
								menuPan_fried2.setVisible(false);
								menuPan_fried3.setVisible(false);
							} else if(fri_cnt==1) {
								menuPan_fried.setVisible(false);
								menuPan_fried2.setVisible(true);
								menuPan_fried3.setVisible(false);
							} else {
								menuPan_fried.setVisible(false);
								menuPan_fried2.setVisible(false);
								menuPan_fried3.setVisible(true);
							}
						}
						
						
					}
					
					if (menu_b2_b == true) {
						if (dry_cnt==0) {
							dry_cnt = 0;
						} else {
							dry_cnt -= 1;
							
							if (dry_cnt == 0) {
								menuPan_dry.setVisible(true);
								menuPan_dry2.setVisible(false);
							} else if(dry_cnt==1) {
								menuPan_dry.setVisible(false);
								menuPan_dry2.setVisible(true);
							}
						} 
					}
					
					if (menu_b3_b == true) {
						if (tang_cnt==0) {
							tang_cnt = 0;
						} else {
							tang_cnt -= 1;
							if (tang_cnt == 0) {
								menuPan_tang.setVisible(true);
								menuPan_tang2.setVisible(false);
							} else if(tang_cnt==1) {
								menuPan_tang.setVisible(false);
								menuPan_tang2.setVisible(true);
							}
						}
					}
					
					if (menu_b4_b == true) {
						menuPan_fruit.setVisible(true);
					}
					
					if (menu_b5_b == true) {
						if (drink_cnt==0) {
							drink_cnt = 0;
						} else {
							drink_cnt -= 1;
							
							if (drink_cnt == 0) {
								menuPan_drink.setVisible(true);
								menuPan_drink2.setVisible(false);
							} else if(drink_cnt==1) {
								menuPan_drink.setVisible(false);
								menuPan_drink2.setVisible(true);
							}
						}
					}
					
					if (menu_b6_b == true) {
						menuPan_beverage.setVisible(true);
					}
				}
			});
			
			
			JLabel peopleInfo = new JLabel("["+tio.table_Num+"번 테이블] 남성 수: " + tio.boy_Num +", 여성 수: " + tio.girl_Num);
			peopleInfo.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 30));
			peopleInfo.setForeground(Color.white);
			peopleInfo.setBounds(50, 20, 600, 80);
			
			
			RoundedButton tableMessage = new RoundedButton("쪽지함");	// 04-12
			tableMessage.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 20));
			tableMessage.setBounds(500, 650, 100, 50);	// 04-12
			tableMessage.setBackground(Color.white);	// 04-12
			tableMessage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 04-14 수정
					omf.setVisible(false);
					mf.setVisible(true);
					newMsgNum = 0;
					newMsg.setForeground(Color.white);
					// 추가 및 수정
					newAskedMergeNum = 0;
					newMsg.setText("새 합석요청: "+newAskedMergeNum+", 새 쪽지: " + newMsgNum);
				}
			});
			RoundedButton tablePosition = new RoundedButton("짝 둘러보기");	// 04-12
			tablePosition.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 20));
			tablePosition.setBounds(650, 650, 150, 50);	// 04-12
			tablePosition.setBackground(Color.white);	// 04-12
			
			tablePosition.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//추가
					if(chk) {
					counterFrame = new Counter();
					new UniSendTableInfoStr(counterAddress, 8888, tio).start();
					chk = false;
					tNumArr.get(tableNum-1).setEnabled(false);
					}
					else {
						counterFrame.setVisible(true);
					}
					//2022-04-08
					int num = Integer.parseInt(tNumArr.get(tableNum-1).getText())-1;
					tInfoArr.get(num).setText("남 " + tio.boy_Num + " 여 " + tio.girl_Num);
					//여기까지
					omf.setVisible(false);
				}
			});
			//여기 까지
			
			newMsg = new JLabel();
			newMsg.setBounds(450, 570, 500, 100);	// 04-12
			//추가 및 수정
			newMsg.setText("새 합석요청: "+newAskedMergeNum+", 새 쪽지: " + newMsgNum);
			//여기까지
			newMsg.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 30));
			newMsg.setForeground(Color.white);
			menu_back.add(newMsg);
			
			// !!! 수정 2022-04-15 17:14
			JLabel TimeLabel = new JLabel();
			TimeLabel.setBounds(50, 35, 400, 50);
			TimeLabel.setFont(new Font("휴먼둥근헤드라인",Font.BOLD,30));
			TimeLabel.setForeground(Color.white);
			menu_back.add(TimeLabel);
			
			Timer th = new Timer(TimeLabel);
			th.start();
			// !!! 수정 2022-04-11 00:38 여기까지
				
			//2022-04-15
			newMenuText.setFont(new Font("휴먼둥근헤드라인", Font.ITALIC, 30));
			newMenuText.setForeground(Color.white);
			newMenuText.setBounds(600, 30, 700, 100);
			menu_back.add(newMenuText);
			
			
			menu_back.add(menu_b1);
			menu_back.add(menu_b2);
			menu_back.add(menu_b3);
			menu_back.add(menu_b4);
			menu_back.add(menu_b5);
			menu_back.add(menu_b6);
			menu_back.add(menu_b7);
			menu_back.add(call_alba);
			menu_back.add(shopping);
			
			menu_back.add(menuPan_fried);
			menu_back.add(menuPan_fried2);
			menu_back.add(menuPan_fried3);
			
			menu_back.add(menuPan_dry);
			menu_back.add(menuPan_dry2);
			
			menu_back.add(menuPan_tang);
			menu_back.add(menuPan_tang2);
			
			menu_back.add(menuPan_fruit);
			
			menu_back.add(menuPan_drink);
			menu_back.add(menuPan_drink2);
			
			menu_back.add(menuPan_beverage);
			menu_back.add(next);
			menu_back.add(before);
			menu_back.add(tableMessage);
			menu_back.add(tablePosition);
			//menu_back.add(tablePosition);
			
			UR = new UniReceiver(8888);
			UR.start();
			
			setLayout(null);
			
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if(e.getX()>110 && e.getX()<605 && e.getY()>150 && e.getY()<460) {
				int setY = (orderP_2.getBounds().y)+(-30)*e.getWheelRotation();
				if(setY>0) {
					setY = 0;
				}
				if(setY<310-orderP_2.getSize().height) {
					setY = 310-orderP_2.getSize().height; //size 610 sety 최소 -310 최대 0
				}
				orderP_2.setBounds(1, setY, 498, orderP_2.getSize().height);
			}
		}
	}

	// 04 12 쪽지함 스크롤 바 추가 
	JTextArea slta= new JTextArea("<보낸 쪽지 목록>");
	JTextArea rlta= new JTextArea("<받은 쪽지 목록>");

	JPanel jp = new JPanel();	// 받쪽
	JPanel jp2 = new JPanel();	// 보쪽

	JScrollPane jrlta = new JScrollPane(jp);
	JScrollPane jslta = new JScrollPane(jp2);

	ImageIcon send;
	ImageIcon message;

	class MessageFrame extends JFrame{

		int sendTableNum = -1, receiveTableNum = -1;
		String sendLog = "", receiveLog = "";

		public MessageFrame(int sendTableNum){

			super(sendTableNum + "번 테이블의 쪽지 함");
			setBounds(300, 200, 1370, 700);
			setLayout(null);
			
			
			Container con = getContentPane();
			
			con.setBackground(Color.gray);


			Font ff = new Font("맑은 고딕", Font.BOLD, 16);

			RoundedButton receiveListBtn = new RoundedButton("받은 쪽지함");
			receiveListBtn.setBounds(50, 50, 200, 100);
			receiveListBtn.setFont(ff);
			add(receiveListBtn);
			receiveListBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					rlta.append(receiveLog);
					newMsgNum = 0;
					newMsg.setForeground(Color.white);
					//추가 2022-04-06
					newMsg.setText("새 합석요청: "+newAskedMergeNum+", 새 쪽지: " + newMsgNum);
					jslta.setVisible(false);
					// 04-07
					mergeAsked.setVisible(false);
					//여기까지
					jrlta.setVisible(true);
				}
			});

			jp.setBounds(300, 50, 1000, 600);
			jp.setBackground(Color.white);
			jp.add(rlta, "Center");
			jp.setLayout(new FlowLayout(FlowLayout.LEFT));
			jrlta.setBounds(300, 50, 1000, 600);
			jrlta.getVerticalScrollBar().setUnitIncrement(16);
			jrlta.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//				rlta.setLineWrap(true);
			rlta.setFont(new Font("굴림", Font.TYPE1_FONT, 20));
			rlta.setEditable(false);
			add(jrlta);

			send = new ImageIcon("./img/send.png");

			RoundedButton sendListBtn = new RoundedButton("보낸 쪽지함");
			sendListBtn.setBounds(50, 180, 200, 100);
			sendListBtn.setFont(ff);
			add(sendListBtn);
			sendListBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					slta.append(receiveLog);
					jrlta.setVisible(false);
					//추가 2022-04-06
					mergeAsked.setVisible(false);
					//여기까지
					jslta.setVisible(true);
				}
			});

			jp2.setBounds(300, 50, 1000, 600);
			jp2.setBackground(Color.white);
			jp2.add(slta, "Center");
			jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
			jslta.setBounds(300, 50, 1000, 600);
			jslta.getVerticalScrollBar().setUnitIncrement(16);
			jslta.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//				slta.setLineWrap(true);
			slta.setFont(new Font("굴림", Font.TYPE1_FONT, 20));
			slta.setEditable(false);
			add(jslta);
			// 여기까지 04-12
			// 04-12 이미지 추가

			//추가 2022-04-06
			// 2022-04-12 수정
			RoundedButton mergeAskedCheckBtn = new RoundedButton("합석 요청함");
			mergeAskedCheckBtn.setBounds(50, 310, 200, 100);
			mergeAskedCheckBtn.setFont(ff);
			add(mergeAskedCheckBtn);
			mergeAskedCheckBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jrlta.setVisible(false);
					jslta.setVisible(false);
					mergeAsked.setVisible(true);
				}
			});

			add(mergeAsked);
			//여기까지 04 - 12



			RoundedButton exit = new RoundedButton("나가기");
			exit.setBounds(50, 550, 200, 100);
			exit.setFont(ff);
			add(exit);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 04-14 수정
					omf.setVisible(true);
					mf.dispose();
					newMsgNum = 0;
					newMsg.setForeground(Color.white);
					//추가 및 수정
					newMsg.setText("새 합석요청: "+newAskedMergeNum+", 새 쪽지: " + newMsgNum);
					//여기까지
				}
			});

			setVisible(false);
			//			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	// 쪽지함 수정 04-12
	
	LineBorder lb_gray = new LineBorder(Color.gray);
	
	class Counter extends JFrame {
		//삭제 2022-04-08
//		ArrayList<JButton> tNumArr = new ArrayList<JButton>();
		//여기까지
		Color btnColor = Color.white;
		Color wallColor = new Color(225,231,251);
		
		public Counter() {
			
			// 04-14
			super("짝 찾기");
			setBounds(300,200,1316, 859);
			setLocationRelativeTo(null);
			Container con = getContentPane();
			con.setBackground(Color.white);
			setLayout(null);
						
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
				wallArr.get(i).setBackground(wallColor);
				add(wallArr.get(i));
			}
			
			
			JPanel jp1 = new JPanel();
			jp1.setBounds(50, 50, 940, 120);
			jp1.setBackground(wallColor);
			jp1.setLayout(new GridLayout(1, 8, 20, 0));
			for(int i=8 ; i>0 ; i--) {
				JButton table_i = new JButton(i+"");
				table_i.setBackground(btnColor);
				jp1.add(table_i);
				tNumArr.add(table_i);
			}
			add(jp1);
			
			JPanel jp2 = new JPanel();
			jp2.setBounds(50, 240, 120, 340);
			jp2.setBackground(wallColor);
			jp2.setLayout(new GridLayout(3, 1, 0, 20));
			for(int i=9 ; i<=11 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setBackground(btnColor);
				jp2.add(table_i);
				tNumArr.add(table_i);
			}
			add(jp2);
			
			JPanel jp3 = new JPanel();
			jp3.setBounds(50, 650, 940, 120);
			jp3.setBackground(wallColor);
			jp3.setLayout(new GridLayout(1, 8, 20, 0));
			for(int i=12 ; i<=19 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setBackground(btnColor);
				jp3.add(table_i);
				tNumArr.add(table_i);
			}
			add(jp3);
			
			JPanel jp4 = new JPanel();
			jp4.setBounds(290, 420, 580, 120);
			jp4.setBackground(wallColor);
			jp4.setLayout(new GridLayout(1, 5, 20, 0));
			for(int i=24 ; i>=20 ; i--) {
				JButton table_i = new JButton(i+"");
				table_i.setBackground(btnColor);
				jp4.add(table_i);
				tNumArr.add(table_i);
			}
			add(jp4);

			JPanel jp5 = new JPanel();
			jp5.setBounds(290, 280, 580, 120);
			jp5.setBackground(wallColor);
			jp5.setLayout(new GridLayout(1, 5, 20, 0));
			for(int i=25 ; i<=29 ; i++) {
				JButton table_i = new JButton(i+"");
				table_i.setBackground(btnColor);
				jp5.add(table_i);
				tNumArr.add(table_i);
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
			exit.setBorder(lb_gray);
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					omf.setVisible(true);
				}

			});

			exitPanel.add(exit_label);
			exitPanel.add(exit);
			add(exitPanel);
			setVisible(true);
			// 04-14수정완
			
			//추가 2022-04-08
			for(int i=0 ; i<tNumArr.size() ; i++) {
				String num = tNumArr.get(i).getText();
				tNumArr.get(i).setBackground(btnColor);
				tNumArr.get(i).setForeground(btnColor);
				tNumArr.get(i).setBorder(new LineBorder(new Color(225,231,251)));
				tNumArr.get(i).setLayout(null);
				JLabel jlTNum_i = new JLabel(num);
				jlTNum_i.setBounds(8, 8, 20, 20);
				jlTNum_i.setFont(f2);
				JLabel jlBtn_i = new JLabel("남 " + tiArr[i].boy_Num + " 여 " + tiArr[i].girl_Num );
				jlBtn_i.setBounds(8, tNumArr.get(i).getHeight()-55, 100, 50);
				tInfoArr.add(jlBtn_i);
				tNumArr.get(i).add(jlTNum_i);
				tNumArr.get(i).add(jlBtn_i);
			}
			//여기까지
			
//			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			// 테이블버튼
			for(int i=0 ; i<tNumArr.size(); i++) {
				tNumArr.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int num_T = Integer.parseInt(e.getActionCommand());
						mom = new MessageOrMerge(num_T);
					}
				});
			}
			
		}
	}
	
	class MessageOrMerge extends JFrame{
		public MessageOrMerge(int toTableNum){
			super(toTableNum+ "번에게 쪽지 or 합석 요청");
			setSize(600, 300);
			setLocationRelativeTo(null);
			getContentPane().setBackground(new Color(230, 236, 255));
			setLayout(null);
			
			Font ff = new Font("HYHeadLine", Font.BOLD, 20);
			JLabel targetTable = new JLabel();
			targetTable.setText(toTableNum + "번 테이블에게");
			targetTable.setFont(ff);
			targetTable.setBounds(230, 50, 200, 50);
			add(targetTable);
			
			Font fs = new Font("HY울릉도B", Font.BOLD, 15);
			JButton sendMessage = new JButton("쪽지 보내기", send);
			sendMessage.setBounds(60,100,200,100);
			sendMessage.setFont(fs);
			sendMessage.setBackground(new Color(255, 230, 249));
			add(sendMessage);
			sendMessage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					stf = new SendTxtFrame(toTableNum);
					dispose();
				}
			});
			
			ImageIcon with = new ImageIcon("./img/with.png");

			JButton letsMergeTable = new JButton("합석 요청하기", with);
			
			int num = Integer.parseInt(tNumArr.get(toTableNum-1).getText())-1;
			int toTableBoyNum = Integer.parseInt(tInfoArr.get(num).getText().substring(2,3));
			int toTableGirlNum = Integer.parseInt(tInfoArr.get(num).getText().substring(6,7));
			if((toTableBoyNum + toTableGirlNum + tio.boy_Num + tio.girl_Num)>6) {
				letsMergeTable.setEnabled(false);
			}
			else {
				letsMergeTable.setEnabled(true);
			}
			
			letsMergeTable.setBounds(310,100,200,100);
			letsMergeTable.setFont(fs);
			letsMergeTable.setBackground(new Color(255, 244, 230));
			if(!solo) {
				letsMergeTable.setEnabled(false);
			}
			letsMergeTable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//수정 2022-04-08
					int num = Integer.parseInt(tNumArr.get(toTableNum-1).getText())-1;
					System.out.println(tInfoArr.get(num).getText());
                    int toTableBoyNum = Integer.parseInt(tInfoArr.get(num).getText().substring(2,3));
                    int toTableGirlNum = Integer.parseInt(tInfoArr.get(num).getText().substring(6,7));
					// TODO Auto-generated method stub
					if((tio.boy_Num + tio.girl_Num + toTableBoyNum + toTableGirlNum)<7) {
						dispose();
						
						new UniAskMerge(tableIPList.get(toTableNum), 8888, toTableNum).start();
						mergeAskingSet.add(toTableNum);
						
						JDialog okDialog = new JDialog(MessageOrMerge.this, "합석 요청 완료", true);
						okDialog.setSize(250,100);
						okDialog.setLocationRelativeTo(null);
						okDialog.setLayout(new FlowLayout());
			
						okDialog.add(new JLabel(toTableNum+"번 테이블에게 합석을 요청했습니다!"));
					    JButton ok = new JButton("확인");
					    okDialog.add(ok);
					    ok.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								okDialog.dispose();
							}
						});
					    okDialog.setVisible(true);
					}
					else {
						System.out.println("인원 수 초과입니다.");
					}
				    //여기까지
				}
			});
			add(letsMergeTable);
			
			setVisible(true);
//			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	//추가 2022-04-06
		class UniSendOk extends Thread{
				
			InetAddress addr;
			int port;
			String msg;
			int toTableNum;
			
			public UniSendOk(String addr, int port, int toTableNum) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.toTableNum = toTableNum;
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
					msg = "[<<축하>> "+tableNum+"번 테이블에서 합석을 수락했습니다!]";
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
		//여기까지
		
		class UniSendSorry extends Thread{
			
			InetAddress addr;
			int port;
			String msg;
			int toTableNum;
			
			public UniSendSorry(String addr, int port, int toTableNum) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.toTableNum = toTableNum;
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
					msg = "[ "+tableNum+"번 테이블에서 합석을 거절했습니다]";
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
		
		//추가 2022-04-19
		class UniSendCancelAsking extends Thread{
			
			InetAddress addr;
			int port;
			String msg;
			int toTableNum;
			
			public UniSendCancelAsking(String addr, int port, int toTableNum) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.toTableNum = toTableNum;
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
					msg = "#CMT"+tableNum;
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

			
		
		//추가 2022-04-06
		class UniAskMerge extends Thread{
			
			InetAddress addr;
			int port;
			String msg;
			int toTableNum;
			
			public UniAskMerge(String addr, int port, int toTableNum) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.toTableNum = toTableNum;
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
					msg = "!["+tableNum +"번 테이블이 합석을 요청하였습니다! 합석을 수락하려면 메세지 클릭!]";
					byte [] buf = msg.getBytes();
					DatagramPacket data = new DatagramPacket(
							buf, //버퍼
							buf.length, //버퍼길이
							addr, //주소(InetAddress 객체)
							port//int 포트번호
							);
					if(solo) {
						socket.send(data);
					}
					else {
						System.out.println("이미 합석 예정입니다.");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					socket.close();
				}
			}
		}
		//여기까지
		
		class UniSender extends Thread{
			
			InetAddress addr;
			int port;
			String msg;
			int toTableNum;
			
			public UniSender(String addr, int port, String msg, int toTableNum) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.msg = msg;
					this.toTableNum = toTableNum;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				DatagramSocket socket = null;
				if(tableNum!=toTableNum) {
					try {
						socket = new DatagramSocket();
						if(!msg.equals("")) {
							slta.append("\n"+ toTableNum+"번 테이블에게: "+msg);
							slta.setCaretPosition(slta.getDocument().getLength());
							msg = "["+tableNum +"번 테이블]"+msg;
							byte [] buf = msg.getBytes();
							DatagramPacket data = new DatagramPacket(
									buf, //버퍼
									buf.length, //버퍼길이
									addr, //주소(InetAddress 객체)
									port//int 포트번호
									);
							socket.send(data);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						socket.close();
					}
				}
			}
		}
		
		class SendTxtFrame extends JFrame{
			public SendTxtFrame(int toTableNum) {
				super(toTableNum + "번 테이블에게 쪽지 보내기");
				getContentPane().setBackground(new Color(230, 236, 255));
				setSize(400, 200);
				setLocationRelativeTo(null);
				setLayout(null);
				
				Font ff = new Font("한컴 소망 B", Font.PLAIN, 20);
				JTextField tf = new JTextField("메세지를 입력하세요");
				tf.setFont(ff);
				tf.requestFocus();
				tf.selectAll();
				tf.setBounds(50, 50, 300, 30);
				
				ImageIcon go = new ImageIcon("./img/go.png");
				Font fc = new Font("한컴 소망 B", Font.BOLD, 14);
				
				JButton confirm = new JButton("전송", go);
				confirm.setBounds(150, 100, 100, 50);
				confirm.setBackground(new Color(255, 230, 243));
				confirm.setFont(fc);
				confirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new UniSender(tableIPList.get(toTableNum), 8888, tf.getText(), toTableNum).start();
						dispose();
						JDialog okDialog = new JDialog(SendTxtFrame.this, "쪽지 전송 완료", true);
						okDialog.setSize(250,100);
						okDialog.setLocationRelativeTo(null);
						okDialog.setLayout(new FlowLayout());
			
						okDialog.add(new JLabel(toTableNum+"번 테이블에게 쪽지를 전송했습니다!"));
					    JButton ok = new JButton("확인");
					    okDialog.add(ok);
					    
					    ok.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								okDialog.dispose();
							}
						});
					    okDialog.setVisible(true);
						
					}
				});
				add(confirm);
				add(tf);
				
				setVisible(true);
			}
		}
		boolean firstStart = true;
		
		//추가 및 변경 2022-04-06
		int toggleNum = 0;
		//여기까지
		
		//2022-04-14
		boolean isRunning = true;
		
		boolean solo = true;
		
		class UniReceiver extends Thread{
			int port;
			//추가 및 변경
			int senderTableNum =0;
			//여기까지

			public UniReceiver(int port) {
				super();
				this.port = port;
			}
			
			@Override
			public void run() {
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket(port);
					while(isRunning) {
						byte [] buf = new byte[1024];
						DatagramPacket dp = new DatagramPacket(buf, buf.length);
						socket.receive(dp);
						String msg = new String(buf).trim();
						
						System.out.println("msg: " + msg);
						
						//2022-04-19 추가
                        if(msg.contains("#CMT")) {
                            msg = msg.replaceAll("#CMT", "");
                            msg = msg.trim();
                            System.out.println(togbtnList.toString());
                            for(JToggleButton jb : togbtnList) {
                            	if(jb.getText().contains(msg)) {
                            		jb.setEnabled(false);
                            		mergeOk.dispose();
                            	}
                            }
                            continue;
                        }
						
						
						//2022-04-12 추가
						if(msg.contains("# ")) {
							System.out.println(msg);
						}
						
						//여기까지
						
						//2022-04-12 추가
						if(msg.contains("#SO")) {
							msg = msg.replaceAll("#SO", "");
							menuIdButtonMap.get(msg).setEnabled(false);
							continue;
						}
						
						//2022-04-13 추가
                        if(msg.contains("#EE")) {
                            msg = msg.replaceAll("#EE", "");
                            menuIdButtonMap.get(msg).setEnabled(true);
                            continue;
                        }
                        
                        //2022-04-14 추가
                        if(msg.contains("@EN")) {
                        	msg = msg.replaceAll("@EN,", "");
                        	String [] msgArr = msg.split(",");
                        	boyNum = Integer.parseInt(msgArr[0].substring(1));
                        	girlNum = Integer.parseInt(msgArr[1].substring(1));
                    		
                    		tio = new TableInfo(tableNum, boyNum, girlNum);
            				omf = new OrderMenuFrame();
            				jf1.dispose();
                        	
                        	continue;
                        }
                        
                        //2022-04-14 추가
                        if(msg.contains("@CP")) {
                        	
                        	//2022-04-14
                    		jFrameArr[0] = TableScreenFrame.this;
                    		jFrameArr[1] =omf;
                    		jFrameArr[2] = mf;
                    		jFrameArr[3] = counterFrame;
                    		jFrameArr[4] = mom;
                    		jFrameArr[5] = stf;
                    		jFrameArr[6] = somf;
                    		jFrameArr[7] = jf1;
                    		jFrameArr[8] = orderlist_pop;
                    		jFrameArr[9] = shopping_Popup;
                    		jFrameArr[10] = giftFrame;
                    		jFrameArr[11] = mergeOk;
                    		
                    		for(JFrame jf : jFrameArr) {
                    			if(jf!=null) {
                    				System.out.println(jf.getName());
                    				jf.dispose();
                    			}
                    		}
                        	isRunning =false;
                        	continue;
                        }
                        
                        //2022-04-13 추가
                        if(msg.contains("#EE")) {
                        	msg = msg.replaceAll("#EE", "");
                        	menuIdButtonMap.get(msg).setEnabled(true);
                        	continue;
                        }
						
						if(msg.contains("#OD")) {
							msg = msg.replaceAll("#OD", "");
							Map<Integer,Integer> inputIdAndNumber = new LinkedHashMap<Integer, Integer>();
							if(msg.contains(",")) {
								String[] IdNumArr = msg.split(",");
								for(String s : IdNumArr) {
									String[] idNum = s.split(":");
									inputIdAndNumber.put(Integer.parseInt(idNum[0]), Integer.parseInt(idNum[1]));
								}
								mapAddtoOrderedListMap(inputIdAndNumber);
							}
							else {
								String[] idNum = msg.split(":");
								inputIdAndNumber.put(Integer.parseInt(idNum[0]), Integer.parseInt(idNum[1]));
								mapAddtoOrderedListMap(inputIdAndNumber);
							}
							continue;
						}
						//여기까지
//						int num = Integer.parseInt(tNumArr.get(tNum-1).getText())-1;
						//추가 및 변경 2022-04-08
						if(msg.contains("B") && msg.contains("G")) {
							
							for (int i=0 ; i<tNumArr.size() ; i++ ) {
		                        String [] strArr = msg.split("/");
		                        int tNum = Integer.parseInt(strArr[i].split(",")[0].substring(1));
		                        int bNum = Integer.parseInt(strArr[i].split(",")[1].substring(1));
		                        int gNum = Integer.parseInt(strArr[i].split(",")[2].substring(1));
		                        
		                        if(tNum == tableNum) {
		                        	System.out.println("갱신됨");
		                        	tio.boy_Num = bNum;
		                        	tio.girl_Num = gNum;
		                        }
		                        	
		                        int num = Integer.parseInt(tNumArr.get(tNum-1).getText())-1;
		                        tInfoArr.get(num).setText("남 " + bNum + " 여 " + gNum);
	
		                        if((bNum + gNum) == 0) {
		                        	tNumArr.get(num).setBackground(Color.white);
		                            tNumArr.get(num).setForeground(Color.white);
		                            tNumArr.get(num).setEnabled(false);
		                        } else if(gNum == 0) {
		                            tNumArr.get(num).setBackground(boyColor);
		                            tNumArr.get(num).setForeground(boyColor);
		                            tNumArr.get(num).setEnabled(true);
		                        } else if(bNum == 0) {
		                        	tNumArr.get(num).setBackground(girlColor);
		                            tNumArr.get(num).setForeground(girlColor);
		                            tNumArr.get(num).setEnabled(true);
		                        } else {
		                            tNumArr.get(num).setBackground(mixColor);
		                            tNumArr.get(num).setForeground(mixColor);
		                            tNumArr.get(num).setEnabled(true);
		                        }
		                        
		                        if((bNum+gNum)>0) {
		                        	tNumArr.get(num).setEnabled(true);
		                        	if(tNum == (tableNum))tNumArr.get(num).setEnabled(false);
		                        	System.out.println("tNum: " + tNum);
		                        	System.out.println("num: " + num);
		                        	System.out.println("tableNum: " + tableNum);
		                        }
		                        else{
		                        	tNumArr.get(num).setEnabled(false);
		                        }
			                }

							String [] strArr2 = msg.split("/");
							
							if(strArr2.length<tableNum)continue;
							
	                        int tNum2 = Integer.parseInt(strArr2[tableNum-1].split(",")[0].substring(1));
	                        int bNum2 = Integer.parseInt(strArr2[tableNum-1].split(",")[1].substring(1));
	                        int gNum2 = Integer.parseInt(strArr2[tableNum-1].split(",")[2].substring(1));
	                        
	                        if(tNum2 == tableNum) {
	                        	if(tio.boy_Num!=bNum2||tio.girl_Num!=gNum2) {
		                        	tio.boy_Num = bNum2;
		                        	tio.girl_Num = gNum2;
		                        	if(tio.boy_Num!=0 && tio.girl_Num!=0) {
		                        		new UniSendTableInfoStr(counterAddress, 8888, tio).start();
		                        	}
	                        	}
	                        }
							
							continue;
						}
						
						if(msg.contains("번 테이블\n")){
							msg = msg.replaceAll("번 테이블\n", "번 테이블(카운터 추가조정)\n");
							//2022-04-12
//							orderedListStrArea.append("\n"+msg);
							continue;
						}
						//여기까지
						
						//추가 변경 2022-04-12
						if(msg.contains("#OK")) {
							mapAddtoOrderedListMap(orderM);
							continue;
						}
						
						if(msg.contains("#NE")) {
							msg = msg.replaceAll("#NE","");
							
							//2022-04-14
							somf = new soldOutMsgFrame(msg);
							orderM.clear();
							continue;
						}
						//여기까지
						
						if(solo) {
							if(!mf.isVisible()) {
								if(msg.substring(0, 2).equals("![")){
									newAskedMergeNum +=1;
								}
								else {
									newMsgNum +=1;
								}
								newMsg.setForeground(Color.yellow);
								newMsg.setText("새 합석요청: "+newAskedMergeNum+", 새 쪽지: " + newMsgNum);
							}
						}
						
						//여기까지
						//추가 및 변경 2022-04-06
						if(msg.substring(0, 2).equals("![")) {
							System.out.println("123");
							JToggleButton btn_i = new JToggleButton(msg);
							btn_i.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 20));
							btn_i.setBounds(0, 30*(toggleNum+1), 1000, 30);
							btn_i.setLayout(null);
							btn_i.setBackground(Color.white);
							btn_i.setBorderPainted(false);
							
							//2022-04-19 합석 수정
							for(int i =1; i<30; i++) {
								if(btn_i.getText().contains(Integer.toString(i))){
									senderTableNum = i;
								}
							}
							if(!solo) {
								System.out.println("이미 합석 예정이십니다.");
								new UniSendSorry(tableIPList.get(senderTableNum),8888,senderTableNum).start();
								continue;
							}
							mergeAskerSet.add(senderTableNum);
							
							btn_i.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									// 04-14
									mergeOk = new JFrame("수락 여부");
									mergeOk.setSize(500, 230);	// 04-18 수정
									mergeOk.setLocationRelativeTo(null);
									mergeOk.getContentPane().setBackground(new Color(230, 236, 255));
									mergeOk.setLayout(null);
									
									Font ff = new Font("HYHeadLine", Font.BOLD, 20);
									JLabel confirmMsg = new JLabel();
									confirmMsg.setText("합석 요청을 수락하시겠습니까?");
									confirmMsg.setFont(ff);
									confirmMsg.setBounds(110, 20, 400, 50);	// 04-18
									mergeOk.add(confirmMsg);
									
									for(int i =1; i<30; i++) {
										if(btn_i.getText().contains(Integer.toString(i))){
											senderTableNum = i;
										}
									}
									
									ImageIcon good_img = new ImageIcon("./img/good.png");
									Font fs = new Font("HY울릉도B", Font.BOLD, 15);
									JButton OKbtn = new JButton("합석 수락하기", good_img);
									OKbtn.setBounds(30,80,200,50);
									OKbtn.setFont(fs);
									OKbtn.setBackground(new Color(255, 230, 249));
									mergeOk.add(OKbtn);
									
									OKbtn.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											
											new UniSendOk(tableIPList.get(senderTableNum),8888,senderTableNum).start();
											for(int cancelTableNum : mergeAskerSet) {
												new UniSendCancelAsking(tableIPList.get(cancelTableNum), 8888, cancelTableNum).start();
											}
											
											mergeOk.dispose();
											for(JToggleButton jtg : togbtnList) {
												jtg.setEnabled(false);
											}
											JDialog okDialog = new JDialog(mergeOk,"합석 수락 완료");
											okDialog.setSize(200,100);	// 04-14
											okDialog.setLocationRelativeTo(null);
											okDialog.setLayout(new FlowLayout());
											
											okDialog.add(new JLabel("합석을 수락했습니다!"));
										    JButton ok = new JButton("확인");
										    ok.setBackground(Color.white);
										    okDialog.add(ok);
										    ok.addActionListener(new ActionListener() {
												@Override
												public void actionPerformed(ActionEvent e) {
													okDialog.dispose();
												}
											});
										    solo = false;
										    okDialog.setVisible(true);
										    
										    if(!mergeAskerSet.isEmpty()) {
											    for(int sorryTableNum : mergeAskerSet) {
											    	if(sorryTableNum!=senderTableNum)
											    	new UniSendSorry(tableIPList.get(sorryTableNum), 8888, sorryTableNum).start();
											    }
										    }
										}
									});
									
									ImageIcon bad_img = new ImageIcon("./img/bad.png");
									JButton Sorrybtn = new JButton("합석 거절하기",bad_img);
									Sorrybtn.setBounds(250,80,200,50);
									Sorrybtn.setFont(fs);
									Sorrybtn.setBackground(new Color(255, 230, 249));
									mergeOk.add(Sorrybtn);
									Sorrybtn.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											new UniSendSorry(tableIPList.get(senderTableNum), 8888, senderTableNum).start();
											mergeOk.dispose();
											mergeAskerSet.remove(senderTableNum);
											Sorrybtn.setEnabled(false);
										}
									});
									
									//04-18 닫기 버튼
									JButton cls_btn = new JButton("닫기");
									cls_btn.setBounds(190, 150, 100, 30);
									cls_btn.setBackground(Color.white);
									cls_btn.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											mergeOk.dispose();
											
										}
									});
									
									mergeOk.add(cls_btn);
									// 수정 완 04-18
									
								    mergeOk.setVisible(true);
								}
							});
							togbtnList.add(btn_i);
							mergeAsked.add(togbtnList.get(toggleNum));
							obg.add(btn_i);
							mergeAsked.repaint();
							toggleNum += 1;
						}
						else {
							rlta.append("\n"+msg);
							rlta.setCaretPosition(rlta.getDocument().getLength());
							if(msg.contains("[<<축하>>")) {
								solo = false;
								for(JToggleButton jtbtn : togbtnList) {
									jtbtn.setEnabled(false);
								}
								if(!mergeAskingSet.isEmpty()) {
									for(Integer cancelTableNum : mergeAskingSet) {
										new UniSendCancelAsking(tableIPList.get(cancelTableNum), 8888, cancelTableNum).start();
									}
								}
							}
						}
						//여기까지
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					socket.close();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		}
		
		class newMessage extends JDialog{
			public newMessage() {
				setBounds(750, 430, 350, 120);
				setLayout(new FlowLayout());
				JLabel call = new JLabel("메세지를 받았습니다!");
				call.setFont(new Font("맑은 고딕", Font.BOLD, 30));
				add(call);
				
				JButton okayBtn = new JButton("메세지 보러가기"); 
				okayBtn.addActionListener(new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						mf.setVisible(true);
					}
				});
				add(okayBtn);
				setVisible(true);
			}
		}
		
		class UniSendTableInfoStr extends Thread{
			InetAddress addr;
			int port;
			String tableInfostr;
			
			public UniSendTableInfoStr(String addr, int port, TableInfo thisTableInfo) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
					this.port = port;
					this.tableInfostr = thisTableInfo.showTableBoyGitlString();
					System.out.println(tableInfostr);
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
					byte [] buf = tableInfostr.getBytes();
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
		
		class UniSendSoldOutToKitchen extends Thread{
			InetAddress addr;
			int port;
			String msg;
			
			public UniSendSoldOutToKitchen(String addr, int port, String msg) {
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
		
		//2022-04-08 수정
		class UniSendOrderMapStrToCounter extends Thread{
			InetAddress addr;
			int port;
			String tableInfostr = "T"+tableNum +",";
			
			public UniSendOrderMapStrToCounter(String addr, int port, Map<Integer, Integer> lhm) {
				super();
				System.out.println(lhm);
				for(Entry<Integer, Integer> od : lhm.entrySet()) {
					tableInfostr += od.getKey() +":"+od.getValue() + ",";
				}
				tableInfostr = tableInfostr.substring(0, tableInfostr.length()-1);
				System.out.println(tableInfostr);
				try {
					this.addr = InetAddress.getByName(addr);
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
					byte [] buf = tableInfostr.getBytes();
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
		//여기까지
		
		//추가 2022-04-12
		class UniSendGiftOrderMapToCounter extends Thread{
			InetAddress addr;
			int port;
			String tableInfostr;
			
			public UniSendGiftOrderMapToCounter(String addr, int port, Map<Integer, Integer> lhm, int targetTable) {
				super();
				
				tableInfostr = "#"+targetTable+"t"+tableNum +",";
				
				for(Entry<Integer, Integer> od : lhm.entrySet()) {
					tableInfostr += od.getKey() +":"+od.getValue() + ",";
				}
				tableInfostr = tableInfostr.substring(0, tableInfostr.length()-1);
				try {
					this.addr = InetAddress.getByName(addr);
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
					byte [] buf = tableInfostr.getBytes();
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
		//여기까지
		
		//추가 2022-04-07
		class UniSendGiftToKitchen extends Thread{
			InetAddress addr;
			int port;
			String giftInforStr="";
			
			public UniSendGiftToKitchen(String addr, int port,int gifttargetTable, Map<Integer, Integer> lhm) {
				super();
				
				giftInforStr = tableNum+"번 테이블이\n"+gifttargetTable+"번 테이블에게 선물!\n------------\n";
				for (Entry<Integer, Integer> od : lhm.entrySet()) {
					
					System.out.println(new MenuDAO().detail(od.getKey()).getName() + "    " + (od.getValue()));
					giftInforStr += new MenuDAO().detail(od.getKey()).getName() + "    " + (od.getValue()) + "\n";
					
				}
				
				//2022-04-12
//				orderedListStrArea.append(giftInforStr);
				//제거
				
				System.out.println(giftInforStr);
				try {
					this.addr = InetAddress.getByName(addr);
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
					byte [] buf = giftInforStr.getBytes();
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
		
		class UniSendOrderToKitchen extends Thread{
			InetAddress addr;
			int port;
			String OrderInforStr="";
			//추가 2022-04-08
			SimpleDateFormat format2 = new SimpleDateFormat ( "HH:mm:ss");
		    String format_time2 = format2.format (System.currentTimeMillis());
			
			public UniSendOrderToKitchen(String addr, int port, Map<Integer, Integer> lhm) {
				super();
				OrderInforStr = tableNum+"번 테이블\n"+format_time2+"\n------------\n";
				if(!orderedListStrArea.getText().contains("테이블")) {
					//2022-04-12 제거
//					orderedListStrArea.append(tableNum+"번 테이블\n"+format_time2+"\n------------\n");
				}
				else {
					//2022-04-12 제거
//					orderedListStrArea.append("--------------------\n추가주문 내역\n"+format_time2+"\n--------------------\n");
				}
				//여기까지
				
				//2022-04-12 제거
//				for (Entry<Integer, Integer> od : lhm.entrySet()) {
//					OrderInforStr += new MenuDAO().detail(od.getKey()).getName() + "    " + (od.getValue()) + "\n";
//					//2022-04-08 추가
//					orderedListStrArea.append(new MenuDAO().detail(od.getKey()).getName() + "    " + (od.getValue()) + "\n");
//					//여기까지
//				}
				
				try {
					this.addr = InetAddress.getByName(addr);
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
					byte [] buf = OrderInforStr.getBytes();
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
		//여기까지
		
		//2022-04-08
		class Call_Alba extends Thread{
			InetAddress addr;
			int port;
			
			public Call_Alba(String addr, int port) {
				super();
				try {
					this.addr = InetAddress.getByName(addr);
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
					byte [] buf = ("!"+tableNum).getBytes();
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
		//여기까지
	
		// !!! 수정 2022-04-11 00:38 여기까지
		class Timer extends Thread{
			JLabel timerLabel;
			
			public Timer(JLabel Label) {
				timerLabel = Label;
			}
			
			@Override
			public void run() {
				Calendar time = Calendar.getInstance();
				int hour = time.get(Calendar.HOUR_OF_DAY);
				int minute = time.get(Calendar.MINUTE);
				int second = time.get(Calendar.SECOND);
				
				//2022-04-15
				int yellowSecond = 0;
				boolean isYellow = false;
				
				while(true) {
					timerLabel.setText("현재시간: "+(Integer.toString(hour))+"시"+(Integer.toString(minute))+"분");
					second++;
					if (second==60) {
						second = 0;
						minute++;
					}
					if (minute==60) {
						minute=0;
						hour++;
					}
					
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						return;
					}
					
					//2022-04-15
					if(newMenuText.getForeground()==Color.yellow) {
						if(!isYellow) {
						yellowSecond = second;
						isYellow = true;
						}
						if(second == (yellowSecond+1)%60) {
							newMenuText.setText("메뉴를 선택해주세요");
							newMenuText.setForeground(Color.white);
							isYellow = false;
						}
					}
				}
				
				
			}
		}
		// !!! 수정 2022-04-11 00:38 여기까지
		// 04-14 수정
		class soldOutMsgFrame extends JFrame{
			public soldOutMsgFrame(String menuName){
				super("SOLDOUT");
				setSize(300,160);
				setLocationRelativeTo(null);
				setLayout(null);
				Font f1 = new Font("한컴 윤고딕 250", Font.BOLD, 13);
				JPanel soldOut_P = new JPanel();
				soldOut_P.setLayout(null);
				soldOut_P.setBounds(0, 0, 500, 400);
				soldOut_P.setBackground(Color.white);
	         
				JLabel soldOut_L = new JLabel("<html>"+menuName+"은(는)<br/> 재고가 부족하여 주문 취소됩니다. <br/>직원을 호출하여 문의하세요.</html>");
				soldOut_L.setFont(f1);
				soldOut_L.setBounds(10, 10, 500, 50);
				RoundedButton soldOut_C = new RoundedButton("닫기");
				soldOut_C.setFont(f1);
				soldOut_C.setForeground(Color.white);
				soldOut_C.setBackground(Color.RED);

				soldOut_C.setBounds(10, 70, 100, 40);
				soldOut_C.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						soldOutMsgFrame.this.dispose();
					}
				});
	        
				JDialog call_Alba_Popup = new JDialog();
				call_Alba_Popup.setTitle("직원호출");
				call_Alba_Popup.setSize(300,120);
				call_Alba_Popup.setLocationRelativeTo(null);
				call_Alba_Popup.setLayout(new FlowLayout());
			
				JLabel call = new JLabel("직원을 호출하셨습니다");
				call.setFont(new Font("맑은 고딕", Font.BOLD, 25));
				call_Alba_Popup.add(call);
			
				RoundedButton okayBtn = new RoundedButton("확인"); 
				okayBtn.setForeground(Color.white);
				okayBtn.setBackground(Color.RED);
				okayBtn.addActionListener(new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						call_Alba_Popup.setVisible(false);	
					}
				});
//	         
				RoundedButton soldOut_C1 = new RoundedButton("직원호출");
				soldOut_C1.setFont(f1);
				soldOut_C1.setForeground(Color.white);
	         	soldOut_C1.setBackground(Color.red);

	         	soldOut_C1.setBounds(170, 70, 100, 40);
	         	soldOut_C1.addActionListener(new ActionListener() {
	         		@Override
	         		public void actionPerformed(ActionEvent e) {
	         			soldOutMsgFrame.this.dispose();
	         			call_Alba_Popup.setAlwaysOnTop(true);
	         			call_Alba_Popup.add(okayBtn);
	         			call_Alba_Popup.setVisible(true);
						new Call_Alba(counterAddress, 8888).start();
	         		}
	         	});

	         	soldOut_P.add(soldOut_C);
	         	soldOut_P.add(soldOut_C1);
	         	soldOut_P.add(soldOut_L);
	         
	         	add(soldOut_P);

	         	setAlwaysOnTop(true);
	         	setVisible(true);
			}
		}
		// 04-14 수정 끝
		// 04-12
		int total = 0;
		
	//2022-04-12 추가
	public void mapAddtoOrderedListMap(Map<Integer, Integer> inputMap) {
		String orderedListText= "\t" + tio.table_Num+"번 테이블 주문내역\n-----------------------------------------------------------------------------------------------------------------------------\n";
		String menuName="";
		int menuNum, price;
		
		for (Entry<Integer, Integer> od : inputMap.entrySet()) {
			DecimalFormat dfmoney = new DecimalFormat("#,##0");
			menuName = new MenuDAO().detail(od.getKey()).getName();
			menuNum = od.getValue();
			
			price = new MenuDAO().detail(od.getKey()).getPrice();
			
			if(!orderedListMap.containsKey(menuName)){
				orderedListMap.put(menuName,menuNum);
			}
			else {
				orderedListMap.put(menuName,orderedListMap.get(menuName)+menuNum);
				if(orderedListMap.get(menuName)==0) orderedListMap.remove(menuName);
			}
        }
		
		
		String tab;
		total =0;
		for(Entry<String, Integer> od : orderedListMap.entrySet()) {
			if(od.getKey().length()>6)tab = "\t\t";
			else tab = "\t\t\t"; 
			 
			if(od.getKey().contains("반건조")||od.getKey().contains("예거"))tab = "\t\t\t";
			
			total += (new MenuDAO().detailName(od.getKey()).getPrice())*od.getValue();
			orderedListText += "\t" + od.getKey() + tab + od.getValue() + "\t" + dfmoney.format((new MenuDAO().detailName(od.getKey()).getPrice())*od.getValue()) + " 원\n";
		}
		if(total_price!=null) {
			total_price.setText(total + " 원");
		}
//		orderedListText +="----------------------------------------------------------------------------------\n\t\t총액\t" + dfmoney.format(total) + " 원";
		
		Font order_font = new Font("HY동녘B", Font.BOLD, 17);
		orderedListStrArea.setFont(order_font);
		orderedListStrArea.setText(orderedListText);
		orderM.clear();
	}
	
	//여기까지
	public static void main(String[] args) {
		new TableScreenFrame(4);
	}
}