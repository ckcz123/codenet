package codenet.bean;

import java.util.List;

public class DiscussBean {
	private int id;
	private int did;
	private long uid;
	private String content;
	private long timestamp;
	private String username;
	private String avatar;
	
	
	
	public int getId() {return id;}
	public void setId(int id) {this.id=id;}
	public int getDid() {return did;}
	public void setDid(int did) {this.did=did;}
	public long getUid() {return uid;}
	public void setUid(long uid) {this.uid=uid;}
	public String getContent() {return content;}
	public void setContent(String content) {this.content=content;}
	public long getTimestamp() {return timestamp;}
	public void setTimestamp(long timestamp) {this.timestamp=timestamp;}
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
