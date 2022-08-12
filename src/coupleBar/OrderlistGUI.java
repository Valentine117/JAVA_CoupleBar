package coupleBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class OrderlistGUI extends JFrame{
	
	JTextArea orderedListStrArea = new JTextArea();
	Font orderFont = new Font("HY동녘B", Font.BOLD, 30);
	
	public OrderlistGUI() {
		
		JFrame orderlist_pop = new JFrame("주문내역");
	    orderlist_pop.setSize(1200,600);
 	    orderlist_pop.setLocationRelativeTo(null);
 	    orderlist_pop.setLayout(null);
 	    orderlist_pop.setResizable(false);
 	    
 	   // 추가 2022-04-12
 	    ImageIcon orderimg = new ImageIcon("./img/orderlist.jpg");
 	    
 	    // 프레임안 배경 설정 패널
 	    JPanel orderlist_back = new JPanel() {
 	    	
 	    	@Override
 	    	protected void paintComponent(Graphics g) {
 	    		
 	    		g.drawImage(orderimg.getImage(), 0, 0, null);
 	    		setOpaque(false);
 	    		super.paintComponent(g);
 	    	}
 	    };
 	    
 	    orderlist_back.setBounds(0, 0, 1200, 600);
 	    orderlist_back.setLayout(null);
 	    
 	    JLabel order_label = new JLabel("주문내역");
 	    order_label.setFont(new Font("HY울릉도B", Font.BOLD, 40));
 	    order_label.setForeground(Color.white);
 	    order_label.setBounds(50, 5, 300, 100);
 	    orderlist_back.add(order_label);
 	    
 	    // 주문내역 리스트
 	    JPanel orderlist_panel = new JPanel();
 	    orderlist_panel.setLayout(new BorderLayout());
 	    orderlist_panel.setBounds(50, 100, 750, 380);
 	    
 	    // 상품명, 갯수
 	    JPanel category_panel = new JPanel();
 	    category_panel.setLayout(null);
 	    category_panel.setBounds(0, 0, 750, 40);
 	    category_panel.setBackground(Color.pink);
 	    orderlist_panel.add(category_panel);
 	    
 	    
 	    // 합계
 	    JPanel total_panel = new JPanel();
 	    total_panel.setBounds(50, 490, 750, 50);
 	    total_panel.setLayout(null);
 	    total_panel.setBackground(Color.yellow);
 	    
 	    JLabel total_label = new JLabel("합계");
 	    total_label.setBounds(0, 8, 70, 30);
 	    total_label.setFont(orderFont);
 	    total_panel.add(total_label);
 	    
 	    orderlist_pop.add(total_panel);
 	    
 	    JScrollPane orderlist_scroll = new JScrollPane(orderlist_panel);
 	    orderlist_scroll.setBounds(50, 110, 750, 380);
 	    orderlist_scroll.setBackground(Color.white);
 	    orderlist_scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
 	    orderlist_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
 	    orderlist_scroll.getVerticalScrollBar().setUnitIncrement(16);
 	    
 	    
 	    //추가 2022-04-08 수정 04-12
 	    orderedListStrArea.setBounds(0, 0, 750, 400);
 	    orderedListStrArea.setEditable(false);
 	    
 	    // 04-12
 	    orderlist_panel.add(orderedListStrArea);
 	    orderlist_pop.add(orderlist_scroll, "West");
 	    orderlist_pop.add(orderlist_back);
 	    //여기까지
 	    
 	    
	 	Font clf = new Font("돋움", Font.BOLD, 20);
	 	
	 	// 04-12
	 	RoundedButton clsbtn = new RoundedButton("닫기");
		clsbtn.setBackground(new Color(235, 44, 44));
		clsbtn.setFont(clf);
		clsbtn.setBounds(900, 500, 80, 40);
		clsbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 닫기 창
				orderlist_pop.setVisible(false);
			}
		});
		
		orderlist_back.add(clsbtn);
		
		orderlist_pop.setVisible(true);
		orderlist_pop.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		
		new OrderlistGUI();
		
	}

}
