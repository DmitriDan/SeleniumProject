/**
 * @author DmitriDan
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.google.common.io.Files;

public class MainClass {
	
	public static WebDriver driver = null; 
	public static LinkedList <Index>  indexes = new LinkedList<>();
	public static String pathToDir;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter full path to directory to save all files \n For example:  C:\\Desktop\\theFolder\\  ");
		pathToDir = in.next();
		try {			
			setupDriver();
			moveToTA125();
						
			toSleep(333);			
			
			readDataTA125();		
			
			toSleep(333);
		}   // =====   E of try_out  ======   
		catch (Exception e) {
			System.out.println("LOG ----  Exception___main"  );
			e.printStackTrace();
		}
		
		saveToFile();
		menu();
		driver.close();
	}	
//=================   End of Main  ======================
	//
	public static void setupDriver() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter full path to chromedriver: \n For example:  C:\\Software\\chromedriver_win32\\chromedriver.exe");
		String driverPath = in.next();
		try {
			System.setProperty("webdriver.chrome.driver",driverPath);
		} catch (Exception e) {
			System.out.println("LOG ----  Exception___setupDriver"  );
			e.printStackTrace();
		}			
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(18,TimeUnit.SECONDS);
	}
//--------------------------------------------------------------------------------------------		
	//move to TA-125 page and sort by Turnover
	public static void moveToTA125() {
		String  privacyPolicyPopUp = "/html/body/app-tase/main-tase/privacy-terms/div/div[1]/div[1]/div[2]/button";
		String  ta_125 = "//*[@id=\"trades_panel1\"]/article/div[1]/top-indices/table/tbody/tr[3]/td[1]/a";
		String  moreAboutTA_125 = "//*[@id=\"mainContent\"]/index-lobby/section[1]/div/div/section[2]/button";
		String  indexComponents = "//*[@id=\"more_madad_nav\"]/ul/li[1]/ul/li[4]/a";
		String  marketData = "//*[@id=\"mainContent\"]/index-lobby/index-composition/index-weight/gridview-lib/div/div[2]/div/filter-data/div[1]/div[2]/div/div[1]/div/label[2]";
		String  sortByTurnover = "//*[@id=\"mainContent\"]/index-lobby/index-composition/index-market-data/gridview-lib/div/div[2]/div/div/div[2]/table/thead/tr[2]/td[6]/ul/li[2]/button";
		
		driver.get("http://www.tase.co.il/en");
		//close  privacy policy pop-up
		try { //can be a situation then pop up does not exist
			driver.findElement(By.xpath(privacyPolicyPopUp)).click();	
		}catch (Exception e) {
			System.out.println("LOG ----  Exception___moveToTA125"  );
			e.printStackTrace();
		}
		//find TA-125 and move to page
		driver.findElement(By.xpath(ta_125)).click();
		//More about TA-125
		driver.findElement(By.xpath(moreAboutTA_125)).click();
		//Index Components
		driver.findElement(By.xpath(indexComponents)).click();
		//Market Data
		driver.findElement(By.xpath(marketData)).click();
		toSleep(777);
		// Sort by Turnover
		driver.findElement(By.xpath(sortByTurnover)).click();
	}	
//--------------------------------------------------------------------------------------------	
	//running by web_elements 
	//last page have less elements
	public static void readDataTA125() {
		
		String  nextPage = "//*[@id=\"pageS\"]/pagination-template/ul/li[8]/a";
		//xpath of each index changed by index  (x from 1 to 30 ) except last page
		String basePath="//*[@id=\"mainContent\"]/index-lobby/index-composition/index-market-data/gridview-lib/div/div[2]/div/div/div[2]/table/tbody/tr[";
		String endOfPath = "]";		
		
		int index = 1;    //index to count a pages. TA125 is on 5 pages
        while(true) {
        	toSleep(333);
        	//page 5 include only 10 indexes
			for(int x=1; x<=30;x++) {
				if(index>4 & x>10 )
					return;
				readElement( basePath + x + endOfPath);
			}
			index++;
			driver.findElement(By.xpath(nextPage)).click();
		}  
	}
//--------------------------------------------------------------------------------------------		
	//getting elementXPath and reading all necessary data for this webElement
	//saving new Index element to list after reading 
	public static void readElement(String elementXPath) {
		WebElement element = driver.findElement(By.xpath(elementXPath))	;
		List <String> data = new ArrayList<>();
		data.add( driver.findElement(By.xpath(elementXPath+"/td[1]/a")).getAttribute("href"));
		String[] tmpArray = element.getText().split("\\r?\\n");
		
		// cleaning from extra not relevant data "DL" & "MM"
    	for(int i = 0; i<tmpArray.length-1; i++) {
			if(tmpArray[i].equals("DL") || tmpArray[i].equals("MM") )
				continue;	
			data.add(tmpArray[i]);
		}
		Iterator<String> str = data.iterator();
		while(str.hasNext()) {
			String hrefPath = str.next();
			String name = str.next();        	
			String ar[]=str.next().split(" ");	
			//this data about element is not necessary
			//str.next();
			indexes.add(new  Index( name, hrefPath,  ar));	
			
			//give time to finish indexes.add  
			toSleep(333);			
		}
	}
//--------------------------------------------------------------------------------------------	
	//saving list of indexes to file
	public static void saveToFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(pathToDir+"TA125.txt"));
			bw.write(indexes.toString());
			bw.close();
			System.out.println("Full ,sorted by Turnover, list of indexes is saved to  "+pathToDir+"TA125.txt");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("LOG ----  Exception___saveToFile"  );
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------	
	//after getting a full list and save it to directory starting menu loop
	// 1- look for company.  0 - quit.
	public static void menu() {
		Scanner in = new Scanner(System.in);
		String toDo;
		while(true) {
			System.out.println("Enter: 1  if u wona look for some company and 0 to quit ");
			toDo = in.next();
			if(toDo.equals("1")) {
				lookingForCompany();
			}
			else if(toDo.equals("0")) {
				System.out.println("Good buy!!! " );
				break;
			}
			else
				System.out.println("????????? read again. it is just 2 options :))" );
		}
	}
//--------------------------------------------------------------------------------------------	
	//reading company name from user and checking if it is in list. 
	//if it is -> going to company page , opening page and saving 1 year chart to directory
	//if not print message and return null
	public static void lookingForCompany() {									  
		Scanner in = new Scanner(System.in);
		System.out.println("Enter company name ");
		String theCompanyName = in.nextLine().toUpperCase();
		String elementHref =companyIsIn(theCompanyName);		
		//company is not one of TA-125
		if(elementHref==null) {
			System.out.println("Company " +theCompanyName+ " is not one of TA-125");
			return;
		}
		driver.get(elementHref);
		driver.findElement(By.xpath("//*[@id=\"mainContent\"]/security-lobby/section/div/div/section/button")).click();
		driver.findElement(By.xpath("//*[@id=\"more_madad_nav\"]/ul/li[1]/ul/li[2]/a")).click();
		//scrolling to view a all chart
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,55)");
		
		toSleep(333);
		System.out.println("Company " +theCompanyName+ " is one of TA-125 indexes. Screenshot with 1 year chart will save to directory");
		saveScreen(theCompanyName) ;
		
	}
//--------------------------------------------------------------------------------------------	
	//checking if theCompanyName is in list. 
	//returning link-href if company is one of the TA-125 indexes
	//or null if company is NOT one of the TA-125 indexes
	public static String companyIsIn (String theCompanyName) {
		Index element;
		Iterator<Index> it = indexes.iterator();
		while(it.hasNext()) {
			element = it.next();
			if(element.getName().equals(theCompanyName))
				return element.getHrefPath();
		}		
		return null;
	}
//--------------------------------------------------------------------------------------------	
	//taking time stamp for fileName. taking screenshot and saving to directory as .png file
	public static void saveScreen(String theCompanyName) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");  
		LocalDateTime now = LocalDateTime.now();  
		String timeStamp = dtf.format(now);  
		TakesScreenshot camera = (TakesScreenshot)driver;
		File screenshot = camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(screenshot, new File(pathToDir+theCompanyName+timeStamp+".png"));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("LOG ----  Exception___saveScreen"  );
			e.printStackTrace();
		}
	}
//--------------------------------------------------------------------------------------------	
	//
	public static void toSleep(int time) {
		try {
			Thread.sleep(time);
		}catch (Exception e) {
			System.out.println("LOG ----  Exception___toSleep"  );
			e.printStackTrace();
		}		
	}
//--------------------------------------------------------------------------------------------	
}

