package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 对WS-Security中Digest式密码的处理Handler.
 * 
 * @author calvin
 */
public class DigestPasswordCallback implements CallbackHandler {

	@Autowired
	private UserManager userManager;

	/**
	 * 根据用户名查出数据库中用户的明文密码,交由框架进行处理并与客户端提交的Digest进行比较.
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		String loginName = pc.getIdentifier();
		User user = userManager.getUserByLoginName(loginName);
		pc.setPassword(user.getPlainPassword());
	}
}
