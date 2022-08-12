package coupleBar;

public class SalesDTO {
	String date;	// int --> Integer = NULL 허용
	Integer no;
	String TIME;
	String orderlist;
	String approval;
	String card_no;
	Integer total;
	
	public SalesDTO() {
		// TODO Auto-generated constructor stub
	}

	public SalesDTO(String date ,Integer no, String TIME, String orderlist, String approval, String card_no, Integer total) {
		super();
		this.date = date;
		this.no = no;
		this.TIME = TIME;
		this.orderlist = orderlist;
		this.approval = approval;
		this.card_no = card_no;
		this.total = total;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(String orderlist) {
		this.orderlist = orderlist;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return date + "," + no + TIME +","+ orderlist +","+ approval +"," + card_no + "," + total ;
	}
}