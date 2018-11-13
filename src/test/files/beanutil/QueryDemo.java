package com.rule.test.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class QueryDemo {
	public void GetList() throws IOException, SQLException {
		Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		sqlMapClient.queryForList("select * from user;", 0, 1); // Noncompliant
	}
}
