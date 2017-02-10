package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import dto.Player;

public class DataBase implements Data {

	private final String dbUrl;

	private final String dbUser;

	private final String dbPwd;
	
	private final String LOAD_SQL = "SELECT user_name, score FROM user_score WHERE type_id=1 ORDER BY score desc limit 5";
	
	private final String SAVE_SQL = "INSERT INTO user_score(user_name,score) VALUES (?,?)";

	public DataBase(HashMap<String,String> param){
		this.dbUrl = param.get("dbUrl");
		this.dbUser = param.get("dbUser");
		this.dbPwd = param.get("dbPwd");	
		try {
			Class.forName(param.get("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Player> loadData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		List<Player> players = new ArrayList<Player>();
		try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPwd);
			stmt = (PreparedStatement) conn.prepareStatement(LOAD_SQL);
			rs = stmt.executeQuery();
			while(rs.next()){
				players.add(new Player(rs.getString(1),rs.getInt(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
			if(conn!=null)conn.close();
			if(stmt!=null)stmt.close();
			if(rs!=null)rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return players;
	}

	@Override
	public void saveData(Player players) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPwd);
			stmt = (PreparedStatement) conn.prepareStatement(SAVE_SQL);
			stmt.setObject(1, players.getName());
			stmt.setObject(2, players.getScore());
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
			if(conn!=null)conn.close();
			if(stmt!=null)stmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
