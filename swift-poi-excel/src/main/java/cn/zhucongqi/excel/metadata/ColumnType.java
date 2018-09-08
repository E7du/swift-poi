/*
 * Copyright 2018 Jobsz (zcq@zhucongqi.cn)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/
package cn.zhucongqi.excel.metadata;

public final class ColumnType {

	/**
	 * Java String <br/>
	 * sql: varchar, char, enum, set, text, tinytext, mediumtext, longtext
	 */
	public static final Class<?> STRING = java.lang.String.class;

	/**
	 * Java Integer <br/>
	 * sql: int, integer, tinyint, smallint, mediumint
	 */
	public static final Class<?> INTEGER = java.lang.Integer.class;

	/**
	 * Java Byte <br/>
	 */
	public static final Class<?> BYTE = java.lang.Byte.class;

	/**
	 * Java Short <br/>
	 */
	public static final Class<?> SHORT = java.lang.Short.class;

	/**
	 * Java Long <br/>
	 * sql: bigint
	 */
	public static final Class<?> LONG = java.lang.Long.class;

	/**
	 * Java Double <br/>
	 * sql: real, double
	 */
	public static final Class<?> DOUBLE = java.lang.Double.class;

	/**
	 * Java Float <br/>
	 * sql: float
	 */
	public static final Class<?> FLOAT = java.lang.Float.class;

	/**
	 * Java Boolean <br/>
	 * sql: bit
	 */
	public static final Class<?> BOOL = java.lang.Boolean.class;

	/**
	 * Java Date <br/>
	 * java.sql.Date, java.sql.Time, java.sql.Timestamp all extends java.util.Date
	 * so getDate can return the three types data
	 */
	public static final Class<?> DATE = java.util.Date.class;

	/**
	 * Java BigDecimal <br/>
	 * sql: decimal, numeric
	 */
	public static final Class<?> BIGDECIMAL = java.math.BigDecimal.class;

	/**
	 * Java BigInteger <br/>
	 * sql: unsigned bigint
	 */
	public static final Class<?> BIGINTEGER = java.math.BigInteger.class;

	/**
	 * binary, varbinary, tinyblob, blob, mediumblob, longblob <br/>
	 * qjd project: print_info.content varbinary(61800);
	 */
	public static final Class<?> BYTES = byte[].class;

	/**
	 * Sql Date <br/>
	 * sql: date, year
	 */
	public static final Class<?> SQL_DATE = java.sql.Date.class;

	/**
	 * sql: time
	 */
	public static final Class<?> SQL_TIME = java.sql.Time.class;

	/**
	 * sql: timestamp, datetime
	 */
	public static final Class<?> SQL_TIMESTAMP = java.sql.Timestamp.class;
}
