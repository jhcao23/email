0. Convert the Excel input file from csv to xlsx such as `statement_sample.xlsx`.

	Make sure to add a word `END` to the end of the content in the Excel file.
	
	I will send you a sample link of this sample file.

1. please find `application.properties` file, and change 3 lines
	
	`spring.datasource.url`, `report.location`, `report.jasper-filename`.
	
	`spring.datasource.url` could be any folder as long as its empty.
	
	`report.location` refers to the folder location of your Excel input files.
	
	`report.jasper-filename` refers to the location of `MasterReport.jasper` file in the project folder, 
	you simply only need to change the folder location and don't change the filename! 
	
2. run this command to start up the application: `mvn clean generate-sources install spring-boot:run`.

	You probably will see a google link when you first time run the application, 
	
	then please open the link in chrome browser and login for a proper account. 

3. to shut down the application simply use keyboard `CTRL+C`.

4. Now you can use Jasper Studio to update the layout of `MasterReport.jrxml` file 
	- simply use mouse to drag and play!
	
5. in the `src/main/resources`, there is a file `client_secret.json`, its based on a Google account;
	
	do not spread it and I will let you know how to generate it from Google for yourself later.

6. Remember your Excel filename such as `statement_sample`, then open browser `http://localhost:7788/report/statement_sample`, you will see the generated PDF file names.

## A few tips on Mac terminal:

1. `cd somewhere` go to somewhere directory
2. `cd ..` go to upper level directory
3. `cd ../../..` go to 3 levels up
4. `open .` open current folder in Mac Finder
5. `pwd` show current directory path
6. `cp somewhere/filename .` copy filename from somewhere to current directory
7. `cp -r somedirectory .` copy some directory (whole folder) to current directory
8. `rm -rf something` delete something (either folder or file)
9. `cd ~` go to $HOME directory
	
	