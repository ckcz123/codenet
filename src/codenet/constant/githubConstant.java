package codenet.constant;

import codenet.utils.EnvironmentProperty;

public class githubConstant {

    public static final long serialVersionUID = 1L;

    public static final String clientId = EnvironmentProperty.readConf("clientId");

    public static final String clientSecret = EnvironmentProperty.readConf("clientSecret");

    public static final String redirectUri = EnvironmentProperty.readConf("redirectUri");

    public static final String githubUri = EnvironmentProperty.readConf("githubUri");;

    public static final String ghUserInfoUri = EnvironmentProperty.readConf("ghUserInfoUri");;
}
