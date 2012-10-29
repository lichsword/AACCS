/******************************************************************************
 *
 * Copyright 2012 Lichsword Studio, All right reserved.
 *
 * File name   : SqliteTable.java
 * Create time : 2012-10-29
 * Author      : lichsword
 * Description : TODO
 *
 *****************************************************************************/
package org.lichsword.java.jdbc.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqliteTable {

	private String name;

	private ArrayList<SqliteColumn> columns;
	private String[][] values;

	private SqliteDBContext context;

	public SqliteTable(SqliteDBContext context, String tableName) {
		super();
		this.name = tableName;
		this.columns = context.listColumns(tableName);
		this.context = context;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setColumns(ArrayList<SqliteColumn> columns) {
		this.columns = columns;
	}

	public ArrayList<SqliteColumn> getColumns() {
		return columns;
	}

	/**
	 * if row count has changed, will refresh data
	 */
	public void refreshValues() {
		ResultSet resultSet = context.queryAll(name);
		if (null != resultSet) {
			int count = getRowCount(resultSet);
			if (null == values || values.length != count) {
				// has data changed
				setValues(new String[count][columns.size()]);
				resultSet = context.queryAll(name);// query again to reset.
				loadValue(resultSet, getValues());
			}// end if
		}
	}

	/**
	 * 
	 * @param resultSet
	 * @param values
	 */
	private void loadValue(final ResultSet resultSet, final String[][] values) {
		if (null != resultSet) {

			int rowIndex = 0;
			SqliteColumn column;
			try {
				while (resultSet.next()) {
					for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
						column = columns.get(columnIndex);
						values[rowIndex][columnIndex] = getValueString(
								resultSet, column, columnIndex);
					}
					rowIndex++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String getValueString(ResultSet resultSet, SqliteColumn column,
			int columnIndex) {

		String typeName = column.getTypeName();
		if (typeName.equals(SqliteColumn.TYPE_NAME_TEXT)) {
			try {
				return resultSet.getString(column.getName());
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else if (typeName.equals(SqliteColumn.TYPE_NAME_INTEGER)) {
			try {
				int value = resultSet.getInt(columnIndex + 1);
				return "" + value;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			throw new IllegalArgumentException(
					"current version not support type:" + typeName);

		}
	}

	private int getRowCount(ResultSet resultSet) {
		int count = 0;
		if (null != resultSet) {
			try {
				while (resultSet.next()) {
					count++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				count = 0;
			}
		}
		return count;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public String[][] getValues() {
		return values;
	}
}
