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
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import textdb.TableHandler;

/**
 * Tests TableHandler methods.
 */
public class TestTableHandler {

	private static String testFile = "bin/data/Products.txt";
	private static String masterFile = "bin/data/Products_master.txt";
	private static TableHandler table;
	
	private static String baseAnswer = "ProductID	ProductName	SupplierID	CategoryID"
		+"\n17	Alice Mutton	7	6"
		+"\n3	Aniseed Syrup	1	2"
		+"\n18	Carnarvon Tigers	7	8"
		+"\n1	Chai	1	1"
		+"\n2	Chang	1	1"
		+"\n4	Chef Anton's Cajun Seasoning	2	2"
		+"\n5	Chef Anton's Gumbo Mix	2	2"
		+"\n15	Genen Shouyu	6	2"
		+"\n6	Grandma's Boysenberry Spread	3	2"
		+"\n10	Ikura	4	8"
		+"\n13	Konbu	6	8"
		+"\n9	Mishi Kobe Niku	4	6"
		+"\n8	Northwoods Cranberry Sauce	3	2"
		+"\n16	Pavlova	7	3"
		+"\n11	Queso Cabrales	5	4"
		+"\n12	Queso Manchego La Pastora	5	4"
		+"\n19	Teatime Chocolate Biscuits	8	3"
		+"\n14	Tofu	6	7"
		+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";		
	
	/**
	 * Test readAll() method.	    	     
	 */
	@Test
    public void testReadAll(){
		// Copy file for tests
		TestTableHandler.copyfile(masterFile, testFile);
				           
        System.out.println("\nTesting read all: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	String result = table.readAll();            
            String answer = baseAnswer;		            				
            System.out.println(result);
            assertEquals(answer,result);            
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
	
	/**
	 * Test findRecord() method.	    	     
	 */
	@Test
    public void testFindRecord(){
		// Copy file for tests
		TestTableHandler.copyfile(masterFile, testFile);
				           
        System.out.println("\nTesting read key 17: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	String result = table.findRecord("17");            
            String answer = "ProductID	ProductName	SupplierID	CategoryID"
            				+"\n17	Alice Mutton	7	6\n";            						            			
            System.out.println(result);
            assertEquals(answer,result);     
            
            System.out.println("\nTesting read key 17: \n");
            result = table.findRecord("7");            
            answer = "ProductID	ProductName	SupplierID	CategoryID"
            				+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";         						            			
            System.out.println(result);
            assertEquals(answer,result);  
            
            // Record that is not in file
            System.out.println("\nTesting read key 20: \n");  
            result = table.findRecord("20");            
            answer = "ProductID	ProductName	SupplierID	CategoryID\n";         						            			
            System.out.println(result);
            assertEquals(answer,result);     
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }		
	
	/**
	 * Tests delete().
	 */
	@Test
    public void testDelete(){
		// Copy file for tests
		TestTableHandler.copyfile(masterFile, testFile);
				           
        System.out.println("\nTesting delete key 17: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	int count = table.deleteRecord("17");   
        	assertEquals(1, count);
        	  
        	String result = table.readAll();            
            String answer = "ProductID	ProductName	SupplierID	CategoryID"
            				+"\n3	Aniseed Syrup	1	2"
            				+"\n18	Carnarvon Tigers	7	8"
            				+"\n1	Chai	1	1"
            				+"\n2	Chang	1	1"
            				+"\n4	Chef Anton's Cajun Seasoning	2	2"
            				+"\n5	Chef Anton's Gumbo Mix	2	2"
            				+"\n15	Genen Shouyu	6	2"
            				+"\n6	Grandma's Boysenberry Spread	3	2"
            				+"\n10	Ikura	4	8"
            				+"\n13	Konbu	6	8"
            				+"\n9	Mishi Kobe Niku	4	6"
            				+"\n8	Northwoods Cranberry Sauce	3	2"
            				+"\n16	Pavlova	7	3"
            				+"\n11	Queso Cabrales	5	4"
            				+"\n12	Queso Manchego La Pastora	5	4"
            				+"\n19	Teatime Chocolate Biscuits	8	3"
            				+"\n14	Tofu	6	7"
            				+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";		            				
            System.out.println(result);
            assertEquals(answer,result);                
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }  
        
        // Tests deleting record that is not in file
 		TestTableHandler.copyfile(masterFile, testFile);
 				           
         System.out.println("\nTesting delete key 21: \n");      
         try{
         	table = new TableHandler();
         	table.fileCreate(testFile);
         	int count = table.deleteRecord("21");   
         	assertEquals(0, count);
         	  
         	String result = table.readAll();            
             String answer = baseAnswer;		            				
             System.out.println(result);
             assertEquals(answer,result);                      
         }            
         catch (SQLException e){
             System.out.println(e);
             fail(e.toString());
         }   
         
        // Copy file for tests
 		TestTableHandler.copyfile(masterFile, testFile);
 				           
 		System.out.println("\nTesting delete key 10: \n");      
 		try{
 			table = new TableHandler();
 			table.fileCreate(testFile);
 			int count = table.deleteRecord("10");   
 			assertEquals(1, count);

 			String result = table.readAll();            
 			String answer = "ProductID	ProductName	SupplierID	CategoryID"
 					+"\n17	Alice Mutton	7	6"
 					+"\n3	Aniseed Syrup	1	2"
 					+"\n18	Carnarvon Tigers	7	8"
 					+"\n1	Chai	1	1"
 					+"\n2	Chang	1	1"
 					+"\n4	Chef Anton's Cajun Seasoning	2	2"
 					+"\n5	Chef Anton's Gumbo Mix	2	2"
 					+"\n15	Genen Shouyu	6	2"
 					+"\n6	Grandma's Boysenberry Spread	3	2"            				
 					+"\n13	Konbu	6	8"
 					+"\n9	Mishi Kobe Niku	4	6"
 					+"\n8	Northwoods Cranberry Sauce	3	2"
 					+"\n16	Pavlova	7	3"
 					+"\n11	Queso Cabrales	5	4"
 					+"\n12	Queso Manchego La Pastora	5	4"
 					+"\n19	Teatime Chocolate Biscuits	8	3"
 					+"\n14	Tofu	6	7"
 					+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";		            				
 			System.out.println(result);
 			assertEquals(answer,result);                      
 		}            
 		catch (SQLException e){
 			System.out.println(e);
 			fail(e.toString());
 		}     
	 }
	
	
	/**
	 * Tests insert().
	 */
	@Test
    public void testInsert(){
		// Copy file for tests
		TestTableHandler.copyfile(masterFile, testFile);
				           
        System.out.println("\nTesting INSERT 22	Terry	55	2: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	int count = table.insertRecord("22	Terry	55	2");   
        	assertEquals(01, count);
        	  
        	String result = table.readAll();            
            String answer = baseAnswer+"22	Terry	55	2\n";            
            System.out.println(result);
            assertEquals(answer,result);                      
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
	 }
	
	
	/**
	 * Tests update().
	 */
	@Test
    public void testUpdate(){
		// Copy file for tests
		TestTableHandler.copyfile(masterFile, testFile);
				           
        System.out.println("\nTesting UPDATE 8	2	NCS: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	int count = table.updateRecord("8", 2, "NCS");   
        	assertEquals(1, count);
        	  
        	String result = table.readAll();            
            String answer = "ProductID	ProductName	SupplierID	CategoryID"
            				+"\n17	Alice Mutton	7	6"
            				+"\n3	Aniseed Syrup	1	2"
            				+"\n18	Carnarvon Tigers	7	8"
            				+"\n1	Chai	1	1"
            				+"\n2	Chang	1	1"
            				+"\n4	Chef Anton's Cajun Seasoning	2	2"
            				+"\n5	Chef Anton's Gumbo Mix	2	2"
            				+"\n15	Genen Shouyu	6	2"
            				+"\n6	Grandma's Boysenberry Spread	3	2"  
            				+"\n10	Ikura	4	8"
            				+"\n13	Konbu	6	8"
            				+"\n9	Mishi Kobe Niku	4	6"
            				+"\n8	NCS	3	2"
            				+"\n16	Pavlova	7	3"
            				+"\n11	Queso Cabrales	5	4"
            				+"\n12	Queso Manchego La Pastora	5	4"
            				+"\n19	Teatime Chocolate Biscuits	8	3"
            				+"\n14	Tofu	6	7"
            				+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";		            				
            System.out.println(result);
            assertEquals(answer,result);                      
        }            
        catch (SQLException e){
            System.out.println(e);
            fail(e.toString());
        }     
        
        // Copy file for tests
        TestTableHandler.copyfile(masterFile, testFile);

        System.out.println("\nTesting 2	2	Change Special Company Export: \n");      
        try{
        	table = new TableHandler();
        	table.fileCreate(testFile);
        	int count = table.updateRecord("2", 2, "Change Special Company Export");   
        	assertEquals(1, count);

        	String result = table.readAll();            
        	String answer = "ProductID	ProductName	SupplierID	CategoryID"
        			+"\n17	Alice Mutton	7	6"
        			+"\n3	Aniseed Syrup	1	2"
        			+"\n18	Carnarvon Tigers	7	8"
        			+"\n1	Chai	1	1"
        			+"\n2	Change Special Company Export	1	1"
        			+"\n4	Chef Anton's Cajun Seasoning	2	2"
        			+"\n5	Chef Anton's Gumbo Mix	2	2"
        			+"\n15	Genen Shouyu	6	2"
        			+"\n6	Grandma's Boysenberry Spread	3	2"  
        			+"\n10	Ikura	4	8"
        			+"\n13	Konbu	6	8"
        			+"\n9	Mishi Kobe Niku	4	6"
        			+"\n8	Northwoods Cranberry Sauce	3	2"
        			+"\n16	Pavlova	7	3"
        			+"\n11	Queso Cabrales	5	4"
        			+"\n12	Queso Manchego La Pastora	5	4"
        			+"\n19	Teatime Chocolate Biscuits	8	3"
        			+"\n14	Tofu	6	7"
        			+"\n7	Uncle Bob's Organic Dried Pears	3	7\n";		            				
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
