package org.springside.examples.miniservice.ws.user.dto;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.WSResult;
import org.springside.examples.miniservice.ws.WebServiceSupport;

/**
 * CreateUser方法的返回结果类型.
 * 
 * @author calvin
 */
@XmlType(name = "CreateUserResult", namespace = WebServiceSupport.NS)
public class CreateUserResult extends WSResult {

	private Long userId;

	/**
	 * 新建用户的ID.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
