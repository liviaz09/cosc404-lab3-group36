package textdb;

/**
TableHandler.java - A java program for accessing and updating a database table stored as a tab
separated text file.  Each record is stored on a new line. Implement the following methods associated
with a menu item. The run() method will prompt for the input required for execution of these methods

1 - void readAll()
2 - String findRecord(String key )
3 - void insertRecord(String record)
4 - void deleteRecord(String key)
5 - void updateRecord(String key, int col, String value)

Helper method - long findStartOfRecord(String key) to find byte offset from start of file for start of record.
*/

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;


public class TableHandler
{
	private BufferedReader reader;			// Reader from console for user input
	RandomAccessFile raFile;			 	// File to manipulated
	private String columnNames;				// First row of file is a tab separated list of column names
	
	
	public static void main(String[] args)
	{
		TableHandler app = new TableHandler();
		app.init();
		
		try
		{
			app.run();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}

	private void init()
	{	raFile = null;
		reader = new BufferedReader(new InputStreamReader(System.in));	// Set up console reader
	}

	private void run() throws SQLException
	{	// Continually read command from user in a loop and then do the requested query
		String choice;				
		fileCreate();
		printMenu();
		choice = getLine();
		while (!choice.equals("X") )
		{
			if (choice.equals("1"))
			{	// Read in and output entire raFile using API for class RandomAccessFile
				System.out.println(readAll());
			}
			else if (choice.equals("2"))
			{	//Locate and output the record with the following key
				System.out.print("Please enter the key to locate the record: ");
				String key = getLine().trim();		
				System.out.println(findRecord(key) );
			}
			else if (choice.equals("3"))
			{	// Prompt for a record to insert, then append to raFile
				System.out.print("Enter the record:");
				String record = getLine().trim();
				insertRecord(record);
			}
			else if (choice.equals("4"))
			{	// prompt for key of record to delete
				System.out.print("Please enter the key to locate the record to delete: ");
				String key = getLine().trim();
				deleteRecord(key);
			}
			else if (choice.equals("5"))
			{	//read in key value and call update method
				System.out.print("Enter key of record to update: " );
				String key = getLine();
				System.out.print("Enter column number of field to update: ");
				String column = getLine();
				int col = Integer.parseInt(column);
				System.out.print("Enter value of field to update: ");
				String value = getLine();
				updateRecord(key, col, value);
			}
			else if (choice.equals("F"))
			{	// Specify the File to Manipulate			
				fileCreate();
			}
			else
				System.out.println("Invalid input!");

			printMenu();
			choice = getLine();
		}

		try{
				raFile.close();
		}catch(IOException io){System.out.println(io); }
	}

/****************************************************************************************
*	readAll()	reads each record in RandomAccessFile raFile and outputs each record to a String.
*				Each record should be on its own line (add a "\n" to end of each record).
*				Note: You do not have to parse each record.  Just append the whole line (make sure to trim() input).
*				Catch any IOException and re-throw it as a SQLException if any error occurs.
 * @throws IOException
*************************************************************************************/
	public String readAll() throws SQLException
	{
		
		// TODO: Write this method
		StringBuffer buffer = new StringBuffer();
		String record = null;
		String EOL = "\n";

		try{
			raFile.seek(0);
			//record = raFile.readUTF();
			while(raFile.getFilePointer() < raFile.length()) {
				String input = raFile.readLine().trim();
				buffer.append(input+EOL);
			 }
			 record = buffer.toString();
		}catch(IOException e){
			if(e != null){
				throw new SQLException(e.getMessage());
			}
		}

		return record;
	}

/**************************************************************************************
*	findRecord()	takes parameter key holding the key of the record to return
*					Returns a String consisting of the columnNames string an EOL, any record if found, and an EOL.
*					If no record is found, should still return the columnNames string and an EOL.
*					Catch any IOException and re-throw it as a SQLException if any error occurs.
**************************************************************************/
	public String findRecord(String key) throws SQLException
	{
		
		// TODO: Write this method
		try {
			// calling find start of record function to help with the output and return -1 is no output is obtained
			long position = findStartOfRecord(key);

			//chechking new points with the findStartOfRecord()
			if(position > 0){
				//new position
				raFile.seek(position);
				return columnNames + "\n" + raFile.readLine().trim() + "\n";
			}
		} catch (IOException e) {
			throw new SQLException("IO Exception");
		}
		return columnNames + "\n";
		}


/**************************************************************************************
*	findStartOfRecord(String key)	takes parameter key holding the key of the record to find
*					 				returns the cursor position of located record
*
* Helper method to locate a record and find location of cursor
* Catch any IOException and re-throw it as a SQLException if any error occurs.
**************************************************************************/
	private long findStartOfRecord(String key) throws SQLException
	{
		// TODO: Write this method
		try{
			raFile.seek(0);
			// read first line and data before tab
			String realine = raFile.readLine();
			//realine is used to read minimum number of lines to find firstKey
			String firstKey = realine.substring(0,realine.indexOf('\t'));
			// check if key is the right one
			while(!key.equals(firstKey)){
			// if the firstkey is not the right one, move on to the next line in the file
			realine = raFile.readLine();
			// if record not found
			if(realine == null)
				return -1;
				firstKey = realine.substring(0,realine.indexOf('\t'));
			}
			return raFile.getFilePointer()-("\n"+realine+"\n").getBytes().length;
		}catch(IOException a){
			throw new SQLException("IO Exception");
		}
	}

/*************************************************************************************
*	updateRecord(String key, int col, String value)
*		parameters	key - key value of record to update
*					col - column of field to update
*					value - new value for this field of the record
*		Returns the count of how many records were updated (either 0 or 1).
*
* method must find the record with key and update the field. The updated record must
* be put back in the file with all other records maintained.
* Catch any IOException and re-throw it as a SQLException if any error occurs.
************************************************************************************/
	public int updateRecord(String key, int col, String value) throws SQLException
	{
		// TODO: Write this method
		try{
			// we set the pointer to the key of the record and chekc if the record exists
			long pointK = findStartOfRecord(key);
			int newCol = col-1;
			if(pointK > 0){
				// go to the record and divide it in readable and changable items
				raFile.seek(pointK);
				String[] newRec = raFile.readLine().split("\t");
				String oldRec = newRec[newCol];
				String setRec="";
				// value of new record will be changed 
				newRec[newCol] = value;
				//set setRec to the new and updated record
				for(int i=0;i<newRec.length;i++){
					setRec = setRec+newRec[i]+"\t";
				}
				//write the whole file again and add new line
				if(oldRec.length()<value.length()){
					setRec = setRec+"\n";
					String str = raFile.readLine();
					while(str != null){
					setRec = setRec+str.trim()+"\n";
					str = raFile.readLine();
					}
					raFile.seek(pointK);

					//clearing all the data from the file 
					long fileLen = raFile.length()-pointK;
					long count = 0;
					for(count=0;count<=fileLen;count++){
						raFile.writeByte(' ');
					}
					//to remove additional bytes from the file convert string to char and set the pointer and keep a check on the count 
					int c=0;
					char newVal[]= setRec.toCharArray();
					raFile.setLength(pointK+newVal.length);
					raFile.seek(pointK);
					// this loop resets the pointer and rewrites the bytes with new values
					while(c<newVal.length){
						raFile.writeByte(newVal[c]);
						c++;
					}
				}// to compansate for the loss of bytes repeat the same procedure above
				else{
					int k =0;
					byte b=0;
					char newVal[] = setRec.toCharArray();
					//set pointer
					raFile.seek(pointK);
					while(b != 0xA){
						if(k < newVal.length)
						raFile.writeByte(newVal[k]);
						else
						raFile.writeByte(' ');
						k=raFile.readByte();
						// set pointer after getting the current position of the pointer from the file
						raFile.seek(raFile.getFilePointer()-1);
						k++;
					}
				}
				return 1;
			}
		}
	catch(IOException e){
		throw new SQLException("IO Exception");
	}
		return 0;
	}	

/**************************************************************************************
*	deleteRecord()	takes parameter key holding the key of the record to delete
*					the method must maintain validity of entire text file
*
* Returns the count of how many records were deleted (either 0 or 1).
* Locate a record, delete the record, rewrite the rest of file to remove empty
* space of deleted record
* Catch any IOException and re-throw it as a SQLException if any error occurs.
**************************************************************************/
	public int deleteRecord(String key) throws SQLException
	{
		try {
			long position = findStartOfRecord(key);
			// check if the record exists
			if(position > 0){
			// the next record to the recordd being deleted is also found
			raFile.seek(position);
			raFile.readLine();
			// empty string for a new file
			String nfile="\n";
			String stringnow = raFile.readLine();
			//the record is overwritten
			while(stringnow != null){

				nfile+= stringnow.trim()+"\n";
				stringnow = raFile.readLine();
			}
			// keeping only the useful bytes in the file and removing the others
			char newBys[] = nfile.toCharArray();
			raFile.setLength(position-1+newBys.length);
			//Keep a count of newBys 
			int count=0;
			raFile.seek(position-1);
			while(count <newBys.length){
				raFile.writeByte(newBys[count]);
				count+=1;
			}
			return 1;
		}
		} catch (Exception e) {
			// TODO: handle exception
			throw new SQLException("IO Exception");
		}
		return 0;
	}


/**************************************************************************************
*	insertRecord()	Appends records to end of file.
*					the method must maintain validity of entire text file
*
*					Return 1 if record successfully inserted, 0 otherwise.
* Catch any IOException and re-throw it as a SQLException if any error occurs.
 * @throws IOException
**************************************************************************/
public int insertRecord(String record) throws SQLException
{
	// TODO: Write this method
	try {
		if (record==null)
			return 0;
		//go to the end of the file and insert a record at the end of the file
		raFile.seek(raFile.length());
		raFile.writeBytes(record.trim() + "\n");
		return 1;
	} catch (IOException e) {
		// TODO: handle exception
		throw new SQLException("IO Exception");
	}
}



/**********************************************************************************
* The below methods read in a line of input from standard input and create a
* RandomAccessFile to manipulate.  printMenu() prints the menu to enter options
*
* The code works and should not need to be updated.
************************************************************************/
//Input method to read a line from standard input
	private String getLine()
	{	 String inputLine = "";
		  try{
			  inputLine = reader.readLine();
		  }catch(IOException e){
			 System.out.println(e);
			 System.exit(1);
		  }//end catch
	     return inputLine;
	}
	
	private void fileCreate()
	{
		System.out.print("Enter the file name to manipulate:");
		String fileName = getLine();
		try {
			fileCreate(fileName);
		}
		catch (SQLException e)
		{	System.out.println("Error opening file: "+fileName+". "+e); }
	}
	
	//Creates a RandomAccessFile object from text file
	public void fileCreate(String fileName) throws SQLException
	{	
		//Create a RandomAccessFile with read and write privileges
		try{
			raFile = new RandomAccessFile(fileName, "rw" );
			if(raFile.length() <1)
				System.out.println("File has "+raFile.length()+" bytes. Is the file name correct?" );
			// Read the first row of column names
			columnNames = raFile.readLine().trim();
			// Go back to start of file
			raFile.seek(0);
		}
		catch(FileNotFoundException fnf){ throw new SQLException("File not found: "+fileName); }
		catch(IOException io) {throw new SQLException(io); }
	}
	
	public void close() throws SQLException
	{
		if (raFile != null)
		{
			try { 
				raFile.close(); 
			} 
			catch (IOException e) 
			{	throw new SQLException(e); }
		}
	}
	
	private void printMenu()
	{	System.out.println("\n\nSelect one of these options: ");
		System.out.println("  1 - Read and Output All Lines");
		System.out.println("  2 - Return Record with Key");
		System.out.println("  3 - Insert a New Record");
		System.out.println("  4 - Delete Record with Key");
		System.out.println("  5 - Update Record with Key");
		System.out.println("  F - Specify Different Data File");
		System.out.println("  X - Exit application");
		System.out.print("Your choice: ");
	}
}
