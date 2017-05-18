/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.db;

import com.db.enums.DatabaseType;
import com.lang.ExceptionUtil;
import com.reflect.ReflectUtil;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBUtilInfo {
	protected String database;
	protected String uid;
	protected String pwd = "123456";
	protected DatabaseType databaseType;
	protected Connection conn;
	private static Map<Field, Object> fileldsMap;
	private static DBUtil<?> dbUtil = null;

	public DBUtilInfo() {
	}

	public DBUtilInfo(String database, String uid, String pwd,
			DatabaseType databaseType) {
		this.database = database;
		this.uid = uid;
		this.pwd = pwd;
		this.databaseType = databaseType;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}

	public static Connection connectionDB(String database, String uid,
			String pwd, DatabaseType databaseType)
			throws ClassNotFoundException, SQLException {
		ExceptionUtil.nullOrWhiteSpaceThrowException(database, "链接的数据库不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(uid, "链接的数据库的用户名不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(pwd, "链接的数据库的密码不能为空");
		ExceptionUtil.nullThrowException(databaseType, "数据库类型不能为空");
		StringBuilder forName = null;
		StringBuilder connection = null;
		switch ($SWITCH_TABLE$com$db$enums$DatabaseType()[databaseType
				.ordinal()]) {
		case 1:
			forName = new StringBuilder(
					"com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = new StringBuilder(
					"jdbc:sqlserver://localhost:1433;databaseName=");
			break;
		case 2:
			forName = new StringBuilder("oracle.jdbc.driver.OracleDriver");
			connection = new StringBuilder("jdbc:oracle:thin:@127.0.0.1:1521:");
			break;
		case 3:
			forName = new StringBuilder("com.mysql.jdbc.Driver");
			connection = new StringBuilder("jdbc:mysql://localhost:3306/");
			break;
		case 4:
			forName = new StringBuilder("com.ibm.db2.jcc.DB2Driver");
			connection = new StringBuilder("jdbc:db2://127.0.0.1:50000/");
			break;
		case 5:
			forName = new StringBuilder("com.sybase.jdbc.SybDriver");
			connection = new StringBuilder("jdbc:sybase:Tds:localhost:5007/");
			break;
		case 6:
			forName = new StringBuilder("org.postgresql.Driver");
			connection = new StringBuilder("jdbc:postgresql://localhost/");
			break;
		default:
			throw new IllegalArgumentException("the database type not support");
		}

		Class.forName(forName.toString());
		connection.append(database);
		if (databaseType == DatabaseType.MySQL) {
			connection.append("?useUnicode=true&characterEncoding=utf-8");
		}
		Connection con = DriverManager.getConnection(connection.toString(),
				uid, pwd);
		return con;
	}

	public Connection connectionDB() throws ClassNotFoundException,
			SQLException {
		return connectionDB(this.database, this.uid, this.pwd,
				this.databaseType);
	}

	public int executeUpdate(String sql) throws Exception {
		return executeUpdate(connectionDB(), sql);
	}

	public int executeUpdate(Connection con, String sql) throws Exception {
		ExceptionUtil.nullThrowException(con, "数据库链接对象不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(sql, "sql语句不能为空");

		PreparedStatement pre = con.prepareStatement(sql);

		int rowcount = pre.executeUpdate();

		colseDB(con, pre);

		return rowcount;
	}

	public Object uniqueResult(Connection con, String sql)
			throws ClassNotFoundException, SQLException {
		List list = executeQuery(con, sql);
		if ((list == null) || (list.isEmpty()))
			return null;
		Object[] arr = null;
		if (((arr = (Object[]) list.get(0)) == null) || (arr.length == 0))
			return null;
		return arr[0];
	}

	public ArrayList<Object[]> executeQuery(String sql)
			throws ClassNotFoundException, SQLException {
		return executeQuery(connectionDB(), sql);
	}

	public ArrayList<Object[]> executeQuery(Connection con, String sql)
			throws ClassNotFoundException, SQLException {
		ExceptionUtil.nullThrowException(con, "数据库链接对象不能为空");
		ArrayList list = new ArrayList();
		ExceptionUtil.nullOrWhiteSpaceThrowException(sql, "sql语句不能为空");

		Statement st = con.createStatement();

		ResultSet res = st.executeQuery(sql);
		ResultSetMetaData rsmd = null;
		if ((res != null) && ((rsmd = res.getMetaData()) != null)) {
			while (res.next()) {
				Object[] arr = new Object[rsmd.getColumnCount()];
				for (int i = 1; i <= arr.length; arr[(i - 1)] = res
						.getObject(i++))
					;
				list.add(arr);
			}
		}
		colseDB(con, st, res);
		return list;
	}

	public <T> ArrayList<T> executeQuery(Class<? super T> cls, String sql)
			throws Exception {
		return executeQuery(cls, connectionDB(), sql);
	}

	public <T> ArrayList<T> executeQuery(Class<? super T> cls, Connection con,
			String sql) throws Exception {
		dbUtil = new DBUtil(cls);
		ExceptionUtil.nullThrowException(con, "数据库链接对象不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(sql, "sql语句不能为空");

		Statement st = con.createStatement();

		ResultSet res = st.executeQuery(sql);
		ResultSetMetaData rsmd = null;
		ArrayList list = new ArrayList();
		if ((res != null) && ((rsmd = res.getMetaData()) != null)) {
			while (res.next()) {
				Object[] arr = new Object[rsmd.getColumnCount()];
				for (int i = 1; i <= arr.length; arr[(i - 1)] = res
						.getObject(i++))
					;
				list.add(dbUtil.readFileds(cls, arr, fileldsMap));
			}
		}
		colseDB(con, st, res);
		return DBUtil.dispose(dbUtil, fileldsMap, list);
	}

	public void colseDB(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException localSQLException) {
		}
	}

	public void colseDB(Connection con, PreparedStatement pre) {
		try {
			if (pre != null)
				pre.close();
			if (con != null)
				con.close();
		} catch (SQLException localSQLException) {
		}
	}

	public void colseDB(Connection con, Statement st) {
		colseDB(con, st, null);
	}

	public void colseDB(Connection con, Statement st, ResultSet res) {
		try {
			if (res != null)
				res.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException localSQLException) {
		}
	}

	private static class DBUtil<T> extends ReflectUtil<T> {
		public DBUtil(Class<? super T> cls) throws NoSuchFieldException,
				NoSuchMethodException {
			super(cls);
			DBUtilInfo.fileldsMap = this.fileldsMap;
		}

		public Object readFileds(Class<?> cls, Object[] arr,
				Map<Field, Object> fileldsMap) throws Exception {
			return super.readFileds(cls, arr, fileldsMap);
		}

		public static <T> ArrayList<T> dispose(DBUtil<?> dbUtil,
				Map<Field, Object> fileldsMap, ArrayList<T> list) {
			return ReflectUtil.dispose(dbUtil, fileldsMap, list);
		}
	}
}