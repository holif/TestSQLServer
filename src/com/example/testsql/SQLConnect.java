package com.example.testsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class SQLConnect {
	private Connection con = null;
	private Statement stmt=null;
	private String ipString;
	private String keyString;
	private String result[] =new String[4];
	SQLConnect(String ip,String key) {	
		ipString = ip;		
		keyString = key;	
	}
	public boolean CreateLink(){
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager
					.getConnection(
							"jdbc:jtds:sqlserver://"+ipString+":1433/tempdb;charset=utf8",
							"sa", keyString);
			stmt = con.createStatement();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public String[] select(){
		try{
			String sqlString = "select * from testdb;";			
			ResultSet rs = stmt.executeQuery(sqlString);
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
				result[3] = rs.getString(4);
			}
			rs.close();
		} catch (Exception e) {
		}
		return result;
	}
	public boolean ins_upd_del() {
		String sql = "insert into testdb(name,pswd,mail) values('zhangsan','123456','zhangsan@123.com');";
		try {
			int re = stmt.executeUpdate(sql);
			if (re > 0) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}	
	public void close() {
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
		}
	}
}
