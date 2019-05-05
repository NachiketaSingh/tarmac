package hcl;

import org.testng.annotations.Test;

import tarmac.hcl.Constants;
import tarmac.hcl.getResources;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class HCLnTest {
	
	
	WebDriver driver;
	WebDriverWait wait;
	List<String> SAPId;
	
@Test
  public void updateTarmac() {	  
	  //********************************Login Step*******************************************
		      driver.get(Constants.TARMAC_URL);     
		      driver.manage().window().maximize();    
      
    		  getObject("LoginPage.UserName").sendKeys(Constants.USERNAME);  
    		  getObject("LoginPage.Next").click();      
    		  getObject("LoginPage.Password").sendKeys(Constants.PASSWORD);	  
    		  getObject("LoginPage.Signin").click();
    		  getObject("LoginPage.Yes").click(); 
    	//******************************End of Login Step************************************	  
    		  
    		  
    		  getObject("HomePage.RaiseNew").sendKeys(Constants.APPNAME);		  		  
    		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ui-id-1']//div[text()='"+Constants.APPNAME+"']")));
    		  driver.findElement(By.xpath("//*[@id='ui-id-1']//div[text()='"+Constants.APPNAME+"']")).click();
    		  getObject("RequestPage.HCLInventory").click();
    		  driver.switchTo().alert().accept();
    		  
    		  for (int j=0; j<SAPId.size(); j++) {
    			   searchAndAddEmp(SAPId.get(j));
    		  }
    		    		      		  
    		  Select pack= new Select(getObject("RequestPage.Package"));    		 
    		  pack.selectByVisibleText(Constants.PACKAGENAME);
    		  getObject("RequestPage.Justification").sendKeys("Needed for project work");
    		  getObject("RequestPage.Submit").click();    		  
    		  driver.switchTo().alert().accept();
    		  driver.manage().timeouts().implicitlyWait(Constants.PAGE_TIMEOUT, TimeUnit.SECONDS);
    		  driver.switchTo().alert().accept();
  }
 
    
  
  @BeforeTest
  public void StartProcess() {
	  
	  System.out.println( "Tarmac Update Starts- All resources loading start!" );
      getResources.loadResources();
      getResources.loadExcel();
      SAPId= getResources.getSAPIdwithAppName(Constants.APPNAME);
      System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
      driver = new ChromeDriver();
      wait = new WebDriverWait(driver, 10);
      System.out.println( "Tarmac Update Starts- All resources loading completed!" );
     
  }
  
  @AfterTest
  public void afterClass() {
	    
	  getResources.closeExcel();
      driver.close();
      driver.quit();
      System.out.println("Tarmac update process completed");
  }
  
  
  public WebElement getObject(String objName) {
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getResources.getLocator(objName))));
	 return  driver.findElement(By.xpath(getResources.getLocator(objName)));
	  
	  
  }
  
  public void searchAndAddEmp(String EmpId) {
	  getObject("RequestPage.SearchEmployee").sendKeys(EmpId);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='ui-menu-item']/div[contains(text(),'"+EmpId+"')]")));
	  driver.findElement(By.xpath("//li[@class='ui-menu-item']/div[contains(text(),'"+EmpId+"')]")).click();	  
	  getObject("RequestPage.AddUser").click();
	  	  
  }
  

}
