package codenet.bean;


public class UserBean {

	private long id;
	private String username;
	private String password;
	private String avatar;
	private String email;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
		sb.append("password=").append(password).append('\n');
		sb.append("avatar=").append(avatar).append('\n');
		sb.append("email=").append(email).append('\n');
		return sb.toString();
	}
	
	
}
