# COSC 404 - Database System Implementation<br/>Lab 3 - Implementing a Text Database and JDBC Driver

This lab practices programming with a JDBC driver.

## Your Own Text Database with a JDBC API (30 marks)

In this question, we will build our own text database stored as a text file.  A single record is stored on one line. The fields in each record are separated by tabs
("\t" in Java Strings). The source code consists of some junit tests and the code files in package `textdb`.  The application also has a functioning text menu that prompts and reads in information for the methods to implement. All marking will be based on successfully completing the junit tests.  [Sample output](output.txt) is also available.

The first step is to complete the `TableHandler.java` file that performs the actual methods for search, insert, delete, and update.  Below is the marking for these methods:

- `readAll()` - to read and print out all records (2 marks)
- `findRecord(String key)` - to find a specific record with the specified key and output the record (5 marks)
- `findStartOfRecord(String key)` - may be helpful to find a specific record and return the byte offset into the file of this record from the start of the file (marks are included with findRecord)
- `insertRecord(String record)` - to add a new record to the text file in the proper format. Insert puts the record at the end of the file. (2 marks)
- `deleteRecord(String key)` - to find a specific record with the specified key and remove the record from the text file (3 marks)
- `updateRecord(String key, int col, String value)` - to locate record with key and update the specified column with the new value. (6 marks)

Note that deletes and updates are **EXTREMELY** inefficient as they require re-writing anything in the file after the insertion or deletion point.  This is acceptable for an answer, and you do not need to try to come up with anything more efficient.  However, it should motivate why we store tables in blocks and records in a database for much better performance.

The second component of the assignment is to make a simple JDBC driver for your text database.  Most of the key classes have already been created and default methods produced.  You are only responsible for modifying these methods:

- `TextDBStatement.executeQuery()` - should execute a SELECT style query.  Can be either `SELECT ALL` (read all records) or `SELECT key` (e.g. `SELECT 5`) which would return record with key 5.  (2 marks)
- `TextDBStatement.executeUpdate()` - should execute these commands: (2 marks)
	- `DELETE key` (e.g. `DELETE 5`)
	- `INSERT record` (e.g. `INSERT 33	Lawrence	44	9`).  Note that the fields are already tab-delimited to make it easier to do the insert (can write the record directly to the file by calling appropriate method in TableHandler).
	- `UPDATE key column value` (e.g. `UPDATE 2	2	Change Special Company Export`).  Note that fields are also tab separated for convenience.
- `TextDBResultSet.TextDBResultSet()` - constructor to setup ResultSet (2 marks)
- `TextDBResultSet.next()` - `next()` method advances to next output row in ResultSet (2 marks)
- `TextDBResultSet.getObject()` (both column number and column name versions).  Returns either an Integer or String object depending on if data is int or String.

**2 marks of the 30 are awarded based on comments and code style/indenting.**  The TA will assign marks as follows:

- 0 - code has no comments and is hard to read
- 1 - code has some comments, is mostly indented, and has a readable style
- 2 - code demonstrates professional quality comments and style

### Java File API References

- [Java RandomAccessFile](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/RandomAccessFile.html)
- [RandomAccessFile readFully() method](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/RandomAccessFile.html#readFully(byte%5B%5D))

## Hints
- When using a `RandomAccessFile` make sure you know your current location in the file. If you are not sure, use the `seek()` method to position to a given location. In this assignment, you may need to position to the start `raFile.seek(0)` or the end `raFile.seek(raFile.length())` of the file. To get the current location in the file, use `raFile.getFilePointer()`.
- The `RandomAccessFile` is a text file, so you can read a row as one entire line using `raFile.readLine()`. Note `trim()` may be handy to remove the end-of-line character.
- `StringTokenizer` is useful for dividing up the row represented as a tab-separated string.
- Leave `updateRecord()` to the end. It is the hardest.
- When doing an update or delete, the general strategy is simple:
   * Find and read the record to update/delete.
   * Use `readFully()` to read the rest of the file into a byte array.
   * With update, write the updated record starting at its previous spot. Then write the rest of the buffered file.
   * With delete, write the rest of the buffered file stored in the byte array to where the record being deleted started.
   * In both cases, make sure to set the file length (`raFile.setLength()`) properly. Otherwise, you may have junk characters at the end of your file.
   * Note: This is not efficient, but it does illustrate the motivation for using blocks to avoid this shifting.

## Submission

The lab can be marked immediately by the professor or TA by showing the output of the JUnit tests and by a quick code review.  Otherwise, submit the URL of your GitHub repository on Canvas. **Make sure to commit and push your updates to GitHub.**
