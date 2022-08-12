package coupleBar;

public class BarMenuItem {
	public String name, category;
	public int price;
	
	public BarMenuItem(String category ,String name, int price) {
		this.category = category;
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "카테고리: "+category+", 이름: " + this.name + ", 가격: " + price;
	}
}
