package codenet.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import codenet.bean.DatasetBean;
import codenet.bean.UserBean;

public class UserJDBC {

	public static UserBean getOrInsert(UserBean user){
		QueryRunner qr = new QueryRunner();
		String sql = "select * from user where id=?";
		List<UserBean> list= null;
		try {
			list = (List<UserBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(UserBean.class), user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()==0){
			sql = "insert into user(id, username, password, avatar, email) values(?,?,?,?,?)";
			try {
				return (UserBean) qr.insert(DBconn.getConn(), sql, new BeanHandler(UserBean.class), user.getId(), user.getUsername(), user.getPassword(), user.getAvatar(), user.getEmail());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			return list.get(0);
		}
		return null;
	}
	
	public static UserBean get(long id){
		QueryRunner qr = new QueryRunner();
		String sql = "select * from user where id=?";
		List<UserBean> list= null;
		try {
			list = (List<UserBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(UserBean.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.size()>0?list.get(0):null;
	}
	
	public static boolean exists(String username) {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from user where username=? or email=?";
		List<UserBean> list= null;
		try {
			list = (List<UserBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(UserBean.class), username, username);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return list.size()>0;
	}
	
	public static UserBean login(String user) {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from user where username=? or email=?";
		List<UserBean> list= null;
		try {
			list = (List<UserBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(UserBean.class), user, user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list==null || list.size()==0)
			return null;
		return list.get(0);
	}
	
}
