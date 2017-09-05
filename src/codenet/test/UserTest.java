package codenet.test;

import org.junit.Test;

import codenet.bean.UserBean;
import codenet.jdbc.UserJDBC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserTest {

	@Test
	public void testUser(){
		String userInfo = "{\"login\":\"w2qiao\",\"id\":7792075,\"avatar_url\":\"https://avatars0.githubusercontent.com/u/7792075?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/w2qiao\",\"html_url\":\"https://github.com/w2qiao\",\"followers_url\":\"https://api.github.com/users/w2qiao/followers\",\"following_url\":\"https://api.github.com/users/w2qiao/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/w2qiao/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/w2qiao/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/w2qiao/subscriptions\",\"organizations_url\":\"https://api.github.com/users/w2qiao/orgs\",\"repos_url\":\"https://api.github.com/users/w2qiao/repos\",\"events_url\":\"https://api.github.com/users/w2qiao/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/w2qiao/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":null,\"company\":null,\"blog\":\"\",\"location\":null,\"email\":null,\"hireable\":null,\"bio\":null,\"public_repos\":4,\"public_gists\":0,\"followers\":5,\"following\":4,\"created_at\":\"2014-06-04T09:34:39Z\",\"updated_at\":\"2017-05-10T12:43:09Z\"}";
		userInfo = userInfo.replace("login", "username");
		userInfo = userInfo.replace("avatar_url", "avatar");
		Gson builder = new GsonBuilder().create();
		UserBean user = builder.fromJson(userInfo, UserBean.class);
		System.out.println(user);
		UserJDBC.getOrInsert(user);
	}
}
