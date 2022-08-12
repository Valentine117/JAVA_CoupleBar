package coupleBar;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TableInfo {
	public int table_Num;
	public int boy_Num=0, girl_Num=0;
	public boolean isSeated;
	public int totalPrice;
	public int getMoney=0;
	public Date entrance;
	public Map<Integer, Integer> OrderMap = new LinkedHashMap<Integer, Integer>();
	Color tableColor = Color.white;
	
	
	// 티오더용 Info
	public TableInfo(int table_Num, int boy_Num, int girl_Num) {
		this.table_Num = table_Num;
		this.boy_Num = boy_Num;
		this.girl_Num = girl_Num;
		this.isSeated = false;
	}
	
	public String showTableBoyGitlString() {
		return "T"+table_Num+","
				+"B"+boy_Num+","
				+"G"+girl_Num;
	}

	public String showOrderMapString() {
		String ss ="";
		for(Entry<Integer, Integer> od : OrderMap.entrySet()) {
			ss += od.getKey() +":"+od.getValue() + ",";
		}
		ss = ss.substring(0, ss.length()-1);
		return ss;
	}
	
	
	// 포스용 Info
	public TableInfo(int table_Num, boolean isSeated) {
		this.table_Num = table_Num;
		this.isSeated = isSeated;
	}
	
	public void tableInfo(int boy_Num, int girl_Num) {
		this.boy_Num = boy_Num;
		this.girl_Num = girl_Num;
		
		if(!isSeated) {
			entrance = new Date();
		}
		
		isSeated = true;
		
		if(girl_Num == 0) {
			tableColor = new Color(100, 200, 255); 
		} else if(boy_Num == 0) {
			tableColor = new Color(255, 100, 200);
		} else {
			tableColor = new Color(200, 100, 200);
		}
		

	}
	
	public void addOrderMap(int id) {	// id메뉴 하나 추가
		if(!OrderMap.containsKey(id)) {
			OrderMap.put(id, 1);
		}
		else {
			OrderMap.put(id,OrderMap.get(id)+1);
		}
	}
	
	public int calcTotalPrice() {	// 테이블 총액
		totalPrice = 0;
		for (Entry<Integer, Integer> od : OrderMap.entrySet()) {
			int menuPrice = new MenuDAO().detail(od.getKey()).getPrice();
			totalPrice += menuPrice * od.getValue();
		}
		return totalPrice;
	}

	public void leave() {
		boy_Num = 0;
		girl_Num = 0;
		getMoney = 0;
		OrderMap.clear();
		tableColor = Color.white;
		isSeated = false;
	}
	
	@Override
	public String toString() {
		return "TableInfo  " + table_Num + "번 테이블  남자 " + boy_Num + ", 여자 " + girl_Num;
	}

}