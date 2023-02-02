package textdb;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * A JDBC interface for a text database.
 */
public class TextDBDriver implements Driver {

	// Registers TextDBDriver with the DriverManager.
    static {
        try {
            java.sql.DriverManager.registerDriver(new TextDBDriver());           
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Failed to register TextDBDriver: "+e);
        }
    }
    
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		int position = url.indexOf("jdbc:textdb://");
        if (position < 0)
            return false; 	// Not a URL that this driver will handle

        return true;		
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		 if (!acceptsURL(url))
	           return null;
		 		 
		 String fileName = url.substring(14);
		 TextDBConnection con = new TextDBConnection(fileName);
 	    	    	
	     return (java.sql.Connection) con;
	}

	@Override
	public int getMajorVersion() {		
		return 0;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}
