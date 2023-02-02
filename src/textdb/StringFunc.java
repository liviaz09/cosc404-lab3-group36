package textdb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class StringFunc
{
	public static String spaces(int count)
	{
		StringBuffer sp = new StringBuffer(count);
		for(int i = 0; i < count; i++)
			sp.append(" ");
		return sp.toString();
	}          
	
    /**
     * Converts a ResultSet to a string (unlimited rows).
     * @param rst
     * @return
     * @throws SQLException
     */
    public static String resultSetToString(ResultSet rst) throws SQLException
    {
        return resultSetToString(rst,Integer.MAX_VALUE);
    }
    
    /**
     * Converts a ResultSet to a string with a given number of rows displayed.
     * Total rows are determined but only the first few are put into a string.
     * @param rst
     * @param maxrows
     * @return
     * @throws SQLException
     */
    public static String resultSetToString(ResultSet rst, int maxrows) throws SQLException
    {
        StringBuffer buf = new StringBuffer(5000);
        int rowCount = 0;
        ResultSetMetaData meta = rst.getMetaData();
        buf.append("Total columns: " + meta.getColumnCount());
        buf.append("\n");
        buf.append(meta.getColumnName(1));
        for (int j = 2; j <= meta.getColumnCount(); j++)
            buf.append(", " + meta.getColumnName(j));
        buf.append("\n");
        
        while (rst.next()) {
            if (rowCount < maxrows)
            {
                for (int j = 0; j < meta.getColumnCount(); j++) {
                	Object obj = rst.getObject(j + 1);
                    buf.append(obj);                    
                    if (j != meta.getColumnCount() - 1)
                        buf.append(", ");                    
                }
                buf.append("\n");
            }
            rowCount++;
        }            
        buf.append("Total results: " + rowCount);
        return buf.toString();
    }    
}