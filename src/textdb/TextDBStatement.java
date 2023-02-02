package textdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class TextDBStatement implements Statement {
	
	private TableHandler table;
	private TextDBResultSet results;
	private int updateCount;
	
	public TextDBStatement(TableHandler table)
	{	this.table = table; }
	
	@Override
	public boolean execute(String query) throws SQLException 
	{
		if (query.toLowerCase().trim().startsWith("select")) 
		{
			results = (TextDBResultSet) executeQuery(query);     		  
			return true;
        } 
		else 
		{ 
			updateCount = executeUpdate(query);
			return false;               
        }       		
	}
	
	@Override
	public ResultSet executeQuery(String query) throws SQLException {				
		// TODO: Complete this method.
		return null;	
	}

	@Override
	public int executeUpdate(String query) throws SQLException {
		// TODO: Complete this method.
		return 0;
	}
		
	
	@Override
	public ResultSet getResultSet() throws SQLException {
		return results;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return updateCount;
	}
	
	/*
	 * Do not modify any methods below.
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		
		return null;
	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		

	}

	@Override
	public void cancel() throws SQLException {
		

	}

	@Override
	public void clearBatch() throws SQLException {
		

	}

	@Override
	public void clearWarnings() throws SQLException {
		

	}

	@Override
	public void close() throws SQLException {
		

	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		
		return null;
	}	

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		
		return null;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		
		return 0;
	}
	
	@Override
	public int getResultSetConcurrency() throws SQLException {
		
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		
		return 0;
	}

	

	@Override
	public SQLWarning getWarnings() throws SQLException {
		
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		

	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		

	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		

	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		

	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		

	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		

	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		

	}

	@Override
	public void closeOnCompletion() throws SQLException {
		
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		
		return false;
	}

}
