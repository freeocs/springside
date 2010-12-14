package org.springside.examples.miniservice.dao.account;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;

@Component
public class AccountMyBatisDao extends SqlSessionDaoSupport {

	public List<Department> getDepartmentList() {
		return getSqlSession().selectList("Account.getDepartmentList");
	}

	public Department getDepartmentDetail(Long id) {
		return (Department) getSqlSession().selectOne("Account.getDepartmentDetail", id);
	}

	public Long saveUser(User user) {
		getSqlSession().insert("Account.saveUser", user);
		return user.getId();
	}

	public Long countByLoginNamePassword(String loginName, String password) {
		return 0L;
	}

}
