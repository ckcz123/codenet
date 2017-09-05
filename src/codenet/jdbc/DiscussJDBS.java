package codenet.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import codenet.bean.CommentBean;
import codenet.bean.DiscussBean;

public class DiscussJDBS {

	public static List<DiscussBean> getDiscuss(int id) {
		QueryRunner qr = new QueryRunner();
		List<DiscussBean> discusses = null;
		try {
			String sql="select discuss.id, did, uid, content, timestamp, username, avatar "
					+ "from discuss, user where discuss.uid = user.id and did=?";
			discusses = (List<DiscussBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(DiscussBean.class), id);
		}
		catch (Exception e) {e.printStackTrace();}
		return discusses;
	}
	
	public static List<CommentBean> getComments(int id) {
		QueryRunner qr = new QueryRunner();
		List<CommentBean> comments = null;
		try {
			String sql="select cid, comment.id, uid, content, timestamp, username, avatar "
					+ "from comment, user where comment.uid = user.id and comment.id=?";
			
		comments = (List<CommentBean>) qr.query(DBconn.getConn(), sql, new BeanListHandler(CommentBean.class), id);
		}
		catch (Exception e) {e.printStackTrace();}
		return comments;
	}
	
	public static boolean post(long uid, int did, int id, String content) {
		QueryRunner qr = new QueryRunner();
		try {
			if (id>0) {
				String sql = "insert into comment (id, uid, content, timestamp) values (?, ?, ?, ?)";
				qr.insert(DBconn.getConn(), sql, new BeanHandler(CommentBean.class), id, uid, content, System.currentTimeMillis());
			}
			else {
				String sql = "insert into discuss (did, uid, content, timestamp) values (?, ?, ?, ?)";
				qr.insert(DBconn.getConn(), sql, new BeanHandler(DiscussBean.class), did, uid, content, System.currentTimeMillis());
			}
		}
		catch (Exception e) {e.printStackTrace(); return false;}
		return true;
	}
	
}
