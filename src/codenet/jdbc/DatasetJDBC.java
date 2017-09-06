package codenet.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import codenet.bean.DatasetBean;
import codenet.bean.PreviewBean;


public class DatasetJDBC {

	public static List<DatasetBean> getAll(){/*
		QueryRunner qr = new QueryRunner();
		String sql = "select * from dataset";
		List<DatasetBean> list= null;
		try {
			list = (List<DatasetBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(DatasetBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;*/
		return getAll(null);
	}
	public static List<DatasetBean> getAll(String username){
		QueryRunner qr = new QueryRunner();
		String sql = "select * from dataset";
		if (username!=null && !"".equals(username))
			sql+=" where contributor='"+username+"'";
		List<DatasetBean> list=null;
		try {
			list = (List<DatasetBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(DatasetBean.class));
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
			list = (List<DatasetBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(DatasetBean.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean dowload(int id){
		QueryRunner qr = new QueryRunner();
		String sql = "update dataset set downloads=downloads+1 where id=?";
		try {
			return qr.update(DBconn.getConn(), sql, id)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean rate(int id, int rate) {
		QueryRunner qr = new QueryRunner();
		String sql = "update dataset set rate=rate+?, rateuser=rateuser+1 where id=?";
		try {
			return qr.update(DBconn.getConn(), sql, rate, id)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean delete(int id) {
		QueryRunner qr = new QueryRunner();
		String sql="delete from dataset where id=?";
		try {
			return qr.update(DBconn.getConn(), sql, id)==1;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean insert(String title, String filename, String shortdetail, String detail,
								 String contributor, String reference, String size) {
		QueryRunner qr=new QueryRunner();
		String sql="insert into dataset (title, filename, shortdetail, detail, contributor, reference, size) values (?,?,?,?,?,?,?)";
		try {
			qr.insert(DBconn.getConn(), sql, new BeanHandler(DatasetBean.class), title, filename, shortdetail, detail,
					contributor, reference, size);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static int preview(int id, String username, String title, String content, String time) {
		QueryRunner qr=new QueryRunner();
		String sql="select * from preview where id=? and username=?";
		try {
			List<PreviewBean> list=(List<PreviewBean>)qr.query(DBconn.getConn(), sql, new BeanListHandler(PreviewBean.class), id, username);
			if (list.isEmpty())
				id=0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			id=0;
		}
		if (id==0) {
			sql="insert into preview (username, title, content, time) values (?,?,?,?)";
			try {
				qr.insert(DBconn.getConn(), sql, new BeanHandler(PreviewBean.class), username, title, content, time);
				sql="select * from preview where username=? and time=?";
				List<PreviewBean> list=(List<PreviewBean>)qr.query(DBconn.getConn(), sql, new BeanListHandler(PreviewBean.class), username, time);
				return list.get(0).getId();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		else {
			sql="update preview set title=?, content=?, time=? where id=?";
			try {
				qr.insert(DBconn.getConn(), sql, new BeanHandler(PreviewBean.class), title, content, time, id);
				return id;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

	public static List<PreviewBean> getPreview(int id, String username) {
		QueryRunner qr=new QueryRunner();
		String sql="select * from preview where id=? and username=?";
		try {
			return (List<PreviewBean>)qr.query(DBconn.getConn(), sql, new BeanListHandler(PreviewBean.class), id, username);
		}
		catch (SQLException e) {
			return null;
		}
	}

	public static boolean deletePreview(int id, String username) {
		QueryRunner qr=new QueryRunner();
		String sql="delete from preview where id=? and username=?";
		try {
			qr.query(DBconn.getConn(), sql, new BeanListHandler(PreviewBean.class), id, username);
			return true;
		}
		catch (SQLException e) {
			return false;
		}
	}

}
