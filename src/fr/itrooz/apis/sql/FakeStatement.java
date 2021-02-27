package fr.itrooz.apis.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

public class FakeStatement implements PreparedStatement {
	@Override
	public ResultSet executeQuery() {
		return new FakeResultSet();
	}

	@Override
	public int executeUpdate() {
		return 0;
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) {

	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) {

	}

	@Override
	public void setByte(int parameterIndex, byte x) {

	}

	@Override
	public void setShort(int parameterIndex, short x) {

	}

	@Override
	public void setInt(int parameterIndex, int x) {

	}

	@Override
	public void setLong(int parameterIndex, long x) {

	}

	@Override
	public void setFloat(int parameterIndex, float x) {

	}

	@Override
	public void setDouble(int parameterIndex, double x) {

	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) {

	}

	@Override
	public void setString(int parameterIndex, String x) {

	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) {

	}

	@Override
	public void setDate(int parameterIndex, Date x) {

	}

	@Override
	public void setTime(int parameterIndex, Time x) {

	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) {

	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) {

	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) {

	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) {

	}

	@Override
	public void clearParameters() {

	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) {

	}

	@Override
	public void setObject(int parameterIndex, Object x) {

	}

	@Override
	public boolean execute() {
		return false;
	}

	@Override
	public void addBatch() {

	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) {

	}

	@Override
	public void setRef(int parameterIndex, Ref x) {

	}

	@Override
	public void setBlob(int parameterIndex, Blob x) {

	}

	@Override
	public void setClob(int parameterIndex, Clob x) {

	}

	@Override
	public void setArray(int parameterIndex, Array x) {

	}

	@Override
	public ResultSetMetaData getMetaData() {
		return null;
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) {

	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) {

	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) {

	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) {

	}

	@Override
	public void setURL(int parameterIndex, URL x) {

	}

	@Override
	public ParameterMetaData getParameterMetaData() {
		return null;
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) {

	}

	@Override
	public void setNString(int parameterIndex, String value) {

	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) {

	}

	@Override
	public void setNClob(int parameterIndex, NClob value) {

	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) {

	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) {

	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) {

	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) {

	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) {

	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) {

	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) {

	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) {

	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) {

	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) {

	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) {

	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) {

	}

	@Override
	public void setClob(int parameterIndex, Reader reader) {

	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) {

	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) {

	}

	@Override
	public ResultSet executeQuery(String sql) {
		return new FakeResultSet();
	}

	@Override
	public int executeUpdate(String sql) {
		return 0;
	}

	@Override
	public void close() {

	}

	@Override
	public int getMaxFieldSize() {
		return 0;
	}

	@Override
	public void setMaxFieldSize(int max) {

	}

	@Override
	public int getMaxRows() {
		return 0;
	}

	@Override
	public void setMaxRows(int max) {

	}

	@Override
	public void setEscapeProcessing(boolean enable) {

	}

	@Override
	public int getQueryTimeout() {
		return 0;
	}

	@Override
	public void setQueryTimeout(int seconds) {

	}

	@Override
	public void cancel() {

	}

	@Override
	public SQLWarning getWarnings() {
		return null;
	}

	@Override
	public void clearWarnings() {

	}

	@Override
	public void setCursorName(String name) {

	}

	@Override
	public boolean execute(String sql) {
		return false;
	}

	@Override
	public ResultSet getResultSet() {
		return new FakeResultSet();
	}

	@Override
	public int getUpdateCount() {
		return 0;
	}

	@Override
	public boolean getMoreResults() {
		return false;
	}

	@Override
	public void setFetchDirection(int direction) {

	}

	@Override
	public int getFetchDirection() {
		return ResultSet.FETCH_FORWARD;
	}

	@Override
	public void setFetchSize(int rows) {

	}

	@Override
	public int getFetchSize() {
		return 0;
	}

	@Override
	public int getResultSetConcurrency() {
		return ResultSet.CONCUR_READ_ONLY;
	}

	@Override
	public int getResultSetType() {
		return ResultSet.TYPE_FORWARD_ONLY;
	}

	@Override
	public void addBatch(String sql) {

	}

	@Override
	public void clearBatch() {

	}

	@Override
	public int[] executeBatch() {
		return new int[0];
	}

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public boolean getMoreResults(int current) {
		return false;
	}

	@Override
	public ResultSet getGeneratedKeys() {
		return null;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) {
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) {
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) {
		return 0;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) {
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) {
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames) {
		return false;
	}

	@Override
	public int getResultSetHoldability() {
		return 0;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) {

	}

	@Override
	public boolean isPoolable() {
		return false;
	}

	@Override
	public void closeOnCompletion() {

	}

	@Override
	public boolean isCloseOnCompletion() {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) {
		return false;
	}
}
