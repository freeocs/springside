#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.security;

import org.springframework.stereotype.Repository;
import ${package}.entity.security.Resource;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {
}