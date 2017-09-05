package codenet.bean;


public class UserBean {

	private int id;
	private String username;
	private String avatar;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((UserBean)obj).id==id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append('\n');
		sb.append("username=").append(username).append('\n');
		sb.append("avatar=").append(avatar).append('\n');
		sb.append("email=").append(email).append('\n');
		return sb.toString();
	}
	
	
}
