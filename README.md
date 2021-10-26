# SeleniumProject
	The program reading from user path to directory there will save files
 and path to chromeDriver. 
 Then opening a site (TASE.co.il) and reading data about all companies of TA-125 Index. 
 Saving to txt file and give user an option look for company in list and if it's "in"  so save 1 year chart to directory. 
	Program include 2 classes 
1. Index - include all data of index (9 Strings) and href of index.
2. MainClass include 3 static elements: 
	public static WebDriver driver 
	public static LinkedList <Index>  indexes - a list of TA-125 indexes
	public static String pathToDir - there we will save all files

	function - main 
		setupDriver() - setuping web driver
		moveToTA125() - move to TA-125 page and sort by Turnover
		readDataTA125()- running by web_elements (indexes) and calling a function 'readElement(String elementXPath)' with true xpath. 
		readElement(String elementXPath) - getting elementXPath and reading all necessary data for this webElement. then saving new Index element to list after reading 	
		saveToFile() - saving list of indexes to text file
		
		menu() - after getting a full list and save it to directory starting menu loop
	    0 - quit.
		1- look for company. calling function 'lookingForCompany()'. this function getting String (Company name) from user and calling function 'companyIsIn(theCompanyName)'   
			if company is not one of the indexes - returning null. informing user about this and back to menu-loop. 
			if company is one of the indexes so returning String (link-href) to this index and continuing to this page, open 1year chart, 
			taking time stamp for fileName. taking screenshot and saving to directory as .png file
			informing user about this and back to menu-loop.  
		in any other choice ask a user for new try.
