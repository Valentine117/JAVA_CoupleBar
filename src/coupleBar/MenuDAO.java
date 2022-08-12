package coupleBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuDAO {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String sql = null;

	public MenuDAO() {
		
		String url = "jdbc:mariadb://localhost:3306/sales_db";
		String username = "host";
		String password = "1234";
		
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<MenuDTO> list() {
		ArrayList<MenuDTO> res = new ArrayList<MenuDTO>();
		sql = "select * from menu";
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				MenuDTO dto = new MenuDTO();
				dto.id = rs.getInt("id");
				dto.name = rs.getString("name");
				dto.price = rs.getInt("price");
				dto.cnt = rs.getInt("cnt");
				dto.category = rs.getString("category");
				res.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	
	public MenuDTO detail(int id) {
		MenuDTO res = null;
		
		sql = "select * from menu where id = " + id;
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				res = new MenuDTO();
				res.id = rs.getInt("id");
				res.name = rs.getString("name");
				res.price = rs.getInt("price");
				res.cnt = rs.getInt("cnt");
				res.category = rs.getString("category");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	public MenuDTO detailName(String name) {
		MenuDTO res = null;
		
		sql = "select * from menu where name = '" + name + "'";
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				res = new MenuDTO();
				res.id = rs.getInt("id");
				res.name = rs.getString("name");
				res.price = rs.getInt("price");
				res.cnt = rs.getInt("cnt");
				res.category = rs.getString("category");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	public int stock(int id, int cnt) {
		int res = 0;
		
		sql = "update menu set cnt = (select cnt from menu where id = "
				+ id + ") -" + cnt + " where id = " + id;
		
		try {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	public int write(MenuDTO dto) {
		int res = 0;
		
		sql = "insert into menu (id, name, price, cnt, category) values ("
				+dto.id+", '"
				+dto.name+"', "
				+dto.price+", "
				+dto.cnt+", '"
				+dto.category+"')";
		
		try {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	public int modify(MenuDTO dto) {
		int res = 0;
		sql = "update menu set name = '"
				+dto.name+"', price = "
				+dto.price+", cnt = "
				+dto.cnt+", category = '"
				+dto.category+"' where id = "
				+dto.id;
		try {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	public int delete(int id) {
		int res = 0;
		
		sql = "delete from menu where id = " + id;
		
		try {
			res = stmt.executeUpdate(sql);
			// 실행된 갯수
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	
	
	void close() {
		if(rs!=null) try {rs.close();} catch (SQLException e) {}
		if(stmt!=null) try {stmt.close();} catch (SQLException e) {}
		if(con!=null) try {con.close();} catch (SQLException e) {}
	}
	
	
}