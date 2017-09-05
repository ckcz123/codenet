package codenet.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import codenet.bean.DatasetBean;


public class DatasetJDBC {

	public static List<DatasetBean> getAll(){
		QueryRunner qr = new QueryRunner();
		String sql = "select * from dataset";
		List<DatasetBean> list= null;
		try {
			list = (List<DatasetBean>) qr.query(new DBconn().getConn(), sql, new BeanListHandler(DatasetBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static List<DatasetBean> get(int id){
		QueryRunner qr = new QueryRunner();
		String sql = "select * from dataset where id=?";
		List<DatasetBean> list= null;
		try {
			list = (List<DatasetBean>) qr.query(new DBconn().getConn(), sql, new BeanListHandler(DatasetBean.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean dowload(int id){
		QueryRunner qr = new QueryRunner();
		String sql = "update dataset set downloads=downloads+1 where id=?";
		try {
			return qr.update(new DBconn().getConn(), sql, id)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
