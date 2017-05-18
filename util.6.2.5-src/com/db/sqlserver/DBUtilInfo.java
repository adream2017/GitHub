/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.db.sqlserver;

import com.db.enums.DatabaseType;

public final class DBUtilInfo extends com.db.DBUtilInfo {
	public DBUtilInfo() {
		this.uid = "sa";
		this.databaseType = DatabaseType.SQLServer;
	}
}