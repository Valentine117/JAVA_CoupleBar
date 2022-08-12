package coupleBar;

public class MenuDTO {

	Integer id;	// int --> Integer = NULL 허용
	String name;
	Integer price;
	Integer cnt;
	String category;
	
	public MenuDTO() {
		// TODO Auto-generated constructor stub
	}

	public MenuDTO(Integer id, String name, Integer price, Integer cnt, String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.cnt = cnt;
		this.category = category;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return id + "," + name + "," + price + "," + cnt + "," + category;
	}

}