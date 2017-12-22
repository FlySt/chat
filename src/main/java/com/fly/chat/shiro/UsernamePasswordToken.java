package com.fly.chat.shiro;

/**
 * 用户和密码（包含验证码）令牌类
 * @author ThinkGem
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;


	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
                                 boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

}