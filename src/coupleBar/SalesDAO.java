package coupleBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalesDAO {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String sql = null;

	public SalesDAO() {
		
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
	
	// %%% 2022-04-13 01:16
	public ArrayList<SalesDTO> list(String date) {
		ArrayList<SalesDTO> res = new ArrayList<SalesDTO>();
		sql = "select date, total from sales_total where date like '" + date +"'";
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				SalesDTO dto = new SalesDTO();
				dto.date = rs.getString("date");
//				dto.no = rs.getInt("no");
//				dto.TIME = rs.getString("TIME");
//				dto.orderlist = rs.getString("orderlist");
//				dto.approval = rs.getString("approval");
//				dto.card_no = rs.getString("card_no");
				dto.total = rs.getInt("total");
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
	// %%% 2022-04-13 01:16 끝
//	public ArrayList<SalesDTO> list(String date) {
//		ArrayList<SalesDTO> res = new ArrayList<SalesDTO>();
//		sql = "select * from sales_total where date like '" + date +"'";
//		
//		try {
//			rs = stmt.executeQuery(sql);
//			while(rs.next()) {
//				SalesDTO dto = new SalesDTO();
//				dto.date = rs.getString("date");
//				dto.no = rs.getInt("no");
//				dto.TIME = rs.getString("TIME");
//				dto.orderlist = rs.getString("orderlist");
//				dto.approval = rs.getString("approval");
//				dto.card_no = rs.getString("card_no");
//				dto.total = rs.getInt("total");
//				res.add(dto);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			close();
//		}
//		
//		return res;
//	}
	
	public ArrayList<SalesDTO> sale_list(String date) {
		ArrayList<SalesDTO> res = new ArrayList<SalesDTO>();
		sql = "select date, total from sales_total where date like '" + date +"'";
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				SalesDTO dto = new SalesDTO();
				dto.date = rs.getString("date");				
				dto.total = rs.getInt("total");
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
	
	
	public SalesDTO detail(String date, int no) {
		SalesDTO res = null;
		
		sql = "select * from sales_total where date like '" + date +"' and no = " + no;
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				res = new SalesDTO();
				res.date = rs.getString("date");
				res.no = rs.getInt("no");
				res.TIME = rs.getString("TIME");
				res.orderlist = rs.getString("orderlist");
				res.approval = rs.getString("approval");
				res.card_no = rs.getString("card_no");
				res.total = rs.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	//2022-04-13 추가
	public String showMonthlySales (int inputYearNum, int inputMonthNum) {
		SalesDTO res = null;
		String monthSalesTotal = "매출이 없습니다.";
		
		sql = "select sum(total)"
				+ " from sales_total"
				+ " where year(date) = " + inputYearNum
				+ " and month(date) = " + inputMonthNum;
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				res = new SalesDTO();
				monthSalesTotal = rs.getString("sum(total)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return monthSalesTotal;
	}
	
	//여기까지
	
	public SalesDTO orderList(String orderlist) {
		SalesDTO res = null;
		
		sql = "select * from sales_total where orderlist = '" + orderlist + "'";
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				res = new SalesDTO();
				res.date = rs.getString("date");
				res.no = rs.getInt("no");
				res.TIME = rs.getString("TIME");
				res.orderlist = rs.getString("orderlist");
				res.approval = rs.getString("approval");
				res.card_no = rs.getString("card_no");
				res.total = rs.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	
	public int write(SalesDTO dto) {
		int res = 0;
		
		sql = "insert into sales (date, no, TIME, orderlist, approval, card_no, total) values ("
				+dto.date+", '"
				+dto.no+"', "
				+dto.TIME+", "
				+dto.orderlist+", '"
				+dto.approval+", '"
				+dto.card_no+", '"
				+dto.total+"')";
		
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
	
	public int modify(SalesDTO dto) {
		int res = 0;
		sql = "update sales_total set date = '"
				+dto.date+"', no = "
				+dto.no+", TIME = "
				+dto.TIME+", orderlist = '"
				+dto.orderlist+", approval = '"
				+dto.approval+", card_no = '"
				+dto.card_no+", total = '"
				+dto.total+"' where date = "
				+dto.date;
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
	
	public int delete(String date) {
		int res = 0;
		
		sql = "delete from sales_total where date = " + date;
		
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
	
	public String dailySales (String date) {
        SalesDTO res = null;
        String dailySales = "매출이 없습니다.";

        sql = "select sum(total) from sales_total where date = '" + date + "'";

        try {
            rs = stmt.executeQuery(sql);

            if(rs.next()) {
                res = new SalesDTO();
                dailySales = rs.getString("sum(total)");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close();
        }

        return dailySales;
    }

// SalesDAO에 추가
	
	
	void close() {
		if(rs!=null) try {rs.close();} catch (SQLException e) {}
		if(stmt!=null) try {stmt.close();} catch (SQLException e) {}
		if(con!=null) try {con.close();} catch (SQLException e) {}
	}
	
}