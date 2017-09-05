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
			list = (List<UserBean>) qr.query(new DBconn().getConn(), sql, new BeanListHandler(UserBean.class), user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list.size()==0){
			sql = "insert into user(id, username, avatar, email) values(?,?,?,?)";
			try {
				return (UserBean) qr.insert(new DBconn().getConn(), sql, new BeanHandler(UserBean.class), user.getId(), user.getName(), user.getAvatar(), user.getEmail());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			return list.get(0);
		}
		return null;
	}
}
