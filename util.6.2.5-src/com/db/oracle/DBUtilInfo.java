/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.db.oracle;

import com.db.enums.DatabaseType;
import com.lang.ExceptionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public final class DBUtilInfo extends com.db.DBUtilInfo {
	public DBUtilInfo() {
		this.database = "orcl";
		this.uid = "system";
		this.databaseType = DatabaseType.Oracle;
	}

	public int executeProcUpdate(String procedure)
			throws ClassNotFoundException, SQLException {
		return executeProcUpdate(connectionDB(), procedure);
	}

	public int executeProcUpdate(Connection con, String procedure)
			throws ClassNotFoundException, SQLException {
		ExceptionUtil.nullThrowException(con, "数据库链接对象不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(procedure, "存储过程不能为空");

		CallableStatement call = con.prepareCall("call " + procedure);
		call.registerOutParameter(1, 4);
		call.execute();
		int res = call.getInt(1);

		colseDB(con, call);
		return res;
	}
}