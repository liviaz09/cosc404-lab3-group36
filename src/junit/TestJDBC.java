package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import textdb.StringFunc;
import textdb.TextDBConnection;
import textdb.TextDBStatement;

/**
 * Tests JDBC classes.
 */
public class TestJDBC 
{
	private static String testFile = "bin/data/Products.txt";
	private static String masterFile = "bin/data/Products_master.txt";
	private static String url = "jdbc:textdb://"+testFile;
	private static TextDBConnection con;
	private static TextDBStatement stmt;
	
	@BeforeAll
	public static void init() throws Exception {
		// Create new instance of driver and make connection
		System.out.println("Registering driver.");
		try
		{
			Class.forName("textdb.TextDBDriver");
		}
		catch (Exception e)
		{	System.out.println(e); }
					
		System.out.println("Getting connection:  "+url);
		con = (TextDBConnection) DriverManager.getConnection(url);
		System.out.println("Connection successful for "+ url);

		System.out.println("Creating statement.");
		stmt = (TextDBStatement) con.createStatement();		
	}

	@AfterAll
	public static void end() throws Exception {
        if (con != null)
            con.close();
	}
    	     
	/**
	 * Tests SELECT ALL for executeQuery().  If this works, get marks for executeQuery(), TextDBResultSet(), next(), and getObject() as all are tested here.
	 */
	@Test
    public void testExecuteQuerySelectAll(){
		// Copy file for tests
		TestJDBC.copyfile(masterFile, testFile);
		
        String sql = "SELECT ALL";                                          
        System.out.println("\nTesting query: \n"+sql);      
        try{
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\nTotal results: 19";                        
            System.out.println(result);
            assertEquals(answer,result);            
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }

	/**
	 * Tests executeQuery() with various SELECT key.
	 */
	@Test
    public void testExecuteQuerySelectKey(){
		// Copy file for tests
		TestJDBC.copyfile(masterFile, testFile);
				
        String sql = "SELECT 17";                                          
        System.out.println("\nTesting query: \n"+sql);      
        try{
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"            				
            				+"\nTotal results: 1";                        
            System.out.println(result);
            assertEquals(answer,result);            
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
        
        sql = "SELECT 7";                                          
        System.out.println("\nTesting query: \n"+sql);      
        try{
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"           				
            				+"\nTotal results: 1";                        
            System.out.println(result);
            assertEquals(answer,result);            
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
        
        sql = "SELECT 20";                                          
        System.out.println("\nTesting query: \n"+sql);      
        try{
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"           			
            				+"\nTotal results: 0";                        
            System.out.println(result);
            assertEquals(answer,result);            
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
	
	/**
	 * Tests executeUpdate() with DELETE.
	 */
	@Test
    public void testExecuteUpdateDelete(){
		// Copy file for tests
		TestJDBC.copyfile(masterFile, testFile);
				
        String sql = "DELETE 17";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\nTotal results: 18";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }   
        
        // Key 21 is not present
        sql = "DELETE 21";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(0, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\nTotal results: 18";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }   
        
        // Delete middle of file
        sql = "DELETE 10";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\nTotal results: 17";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
	
	
	/**
	 * Tests executeUpdate() with INSERT.
	 */
	@Test
    public void testExecuteUpdateInsert(){
		// Copy file for tests
		TestJDBC.copyfile(masterFile, testFile);
				
        String sql = "INSERT 22	Terry	55	2";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\n22, Terry, 55, 2"
            				+"\nTotal results: 20";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }   
        
        sql = "INSERT 33	Lawrence	44	9";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"            				
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, Northwoods Cranberry Sauce, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"
            				+"\n22, Terry, 55, 2"
            				+"\n33, Lawrence, 44, 9"
            				+"\nTotal results: 21";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
			
	/**
	 * Tests executeUpdate() with UPDATE.
	 */
	@Test
    public void testExecuteUpdateWithUpdate(){
		// Copy file for tests
		TestJDBC.copyfile(masterFile, testFile);
		
        String sql = "UPDATE 8	2	NCS";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Chang, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, NCS, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"            				
            				+"\nTotal results: 19";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }  
        
        sql = "UPDATE 2	2	Change Special Company Export";                                          
        System.out.println("\nTesting: \n"+sql);      
        try{
            int count = stmt.executeUpdate(sql);
            assertEquals(1, count);
            
            // Check if actually deleted and file is correct
            sql = "SELECT ALL";
            ResultSet rst = stmt.executeQuery(sql);            
            String result = StringFunc.resultSetToString(rst);
            String answer = "Total columns: 4"
            				+"\nProductID, ProductName, SupplierID, CategoryID"
            				+"\n17, Alice Mutton, 7, 6"
            				+"\n3, Aniseed Syrup, 1, 2"
            				+"\n18, Carnarvon Tigers, 7, 8"
            				+"\n1, Chai, 1, 1"
            				+"\n2, Change Special Company Export, 1, 1"
            				+"\n4, Chef Anton's Cajun Seasoning, 2, 2"
            				+"\n5, Chef Anton's Gumbo Mix, 2, 2"
            				+"\n15, Genen Shouyu, 6, 2"
            				+"\n6, Grandma's Boysenberry Spread, 3, 2"
            				+"\n10, Ikura, 4, 8"
            				+"\n13, Konbu, 6, 8"
            				+"\n9, Mishi Kobe Niku, 4, 6"
            				+"\n8, NCS, 3, 2"
            				+"\n16, Pavlova, 7, 3"
            				+"\n11, Queso Cabrales, 5, 4"
            				+"\n12, Queso Manchego La Pastora, 5, 4"
            				+"\n19, Teatime Chocolate Biscuits, 8, 3"
            				+"\n14, Tofu, 6, 7"
            				+"\n7, Uncle Bob's Organic Dried Pears, 3, 7"            				
            				+"\nTotal results: 19";                        
            System.out.println(result);
            assertEquals(answer,result);                                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
			
	
	/**
	 * Copies one file to another.
	 * 
	 * @param srFile
	 * 		source file name
	 * @param dtFile
	 * 		destination file name
	 */
    private static void copyfile(String srFile, String dtFile){
		try{
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);
			
			//For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			// System.out.println("File copied.");
		}
		catch(FileNotFoundException ex){
			System.out.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		}
		catch(IOException e){
			System.out.println(e.getMessage());			
		}
	}
}
