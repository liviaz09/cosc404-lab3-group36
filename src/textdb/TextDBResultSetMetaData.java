package textdb;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TextDBResultSetMetaData implements ResultSetMetaData {

	private String[] columnNames;				// Names of each column (should be first row in file)
	
	public TextDBResultSetMetaData(String[] columnNames)
	{	this.columnNames = columnNames; }
	
	@Override
	public int getColumnCount() throws SQLException {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int idx) throws SQLException {
		if (idx < 1 || idx > columnNames.length)
			throw new SQLException("Invalid column index.");
		return columnNames[idx-1];
	}
	
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		
		return null;
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		
		return 0;
	}

	@Override
	public String getColumnLabel(int arg0) throws SQLException {
		
		return null;
	}
	

	@Override
	public int getColumnType(int arg0) throws SQLException {
		
		return 0;
	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		
		return 0;
	}

	@Override
	public int getScale(int arg0) throws SQLException {
		
		return 0;
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public String getTableName(int arg0) throws SQLException {
		
		return null;
	}

	@Override
	public boolean isAutoIncrement(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public int isNullable(int arg0) throws SQLException {
		
		return 0;
	}

	@Override
	public boolean isReadOnly(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isSearchable(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public boolean isWritable(int arg0) throws SQLException {
		
		return false;
	}

}
