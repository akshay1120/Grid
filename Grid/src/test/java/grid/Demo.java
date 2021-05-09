package grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Demo 
{
	WebDriver driver; 	   
	String nodeURL; 
	
	@Parameters({"Port"}) 	   
	@BeforeClass 	    
	public void setUp(String Port) throws MalformedURLException 	    
	{            	        
		if(Port.equalsIgnoreCase("4445"))
		{ 	            
			nodeURL = "http://localhost:4444/wd/hub"; 	            
			System.out.println("Chrome Browser Initiated"); 	            
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();             	            
			capabilities.setBrowserName("chrome"); 	            
			capabilities.setPlatform(Platform.WINDOWS); 	            
			driver = new RemoteWebDriver(new URL(nodeURL),capabilities); 	            
			driver.get("file:///C:/Offline%20Website/index.html"); 	            
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		if(Port.equalsIgnoreCase("4446"))
		{ 	            
			nodeURL = "http://localhost:4444/wd/hub"; 	            
			System.out.println("Firefox Browser Initiated"); 	            
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();             	            
			capabilities.setBrowserName("firefox"); 	            
			capabilities.setPlatform(Platform.WINDOWS); 	            
			driver = new RemoteWebDriver(new URL(nodeURL),capabilities); 	            
			driver.get("file:///C:/Offline%20Website/index.html"); 	            
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	@Test(priority=1)
	public void verifyUrl() 
	{ 	
		String expectedUrl = "file:///C:/Offline%20Website/index.html" ; 
		String actualUrl = driver.getCurrentUrl() ;
		Assert.assertEquals(actualUrl, expectedUrl);;
	}
	
	@Test(priority=2)
	public void verifyTitle() 
	{ 	
		String expectedTitle = "JavaByKiran | Log in" ; 
		String actualTitle = driver.getTitle() ;
		
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority=3)
	public void verifyLogo() 
	{ 	
		WebElement logo = driver.findElement(By.xpath("//img"));
		
		if(logo.isDisplayed()==true)
		{
			System.out.println("Logo is displayed");
		}
		else
		{
			System.out.println("Logo is not displayed");
		}
	}
	
	@Test(priority=4)
	public void verifyMainHeading() 
	{ 
		WebElement heading = driver.findElement(By.xpath("//b"));
	
		String actualHeading = heading.getText();
		String expectedHeading = "Java By Kiran" ;
		
		Assert.assertEquals(actualHeading, expectedHeading);
	}
	
	@Test(priority=5)
	public void verifySubHeading() 
	{ 
		WebElement subHeading = driver.findElement(By.xpath("//h4"));
	
		String actualSubHeading = subHeading.getText();
		String expectedSubHeading = "JAVA | SELENIUM | PYTHON" ;
		
		Assert.assertEquals(actualSubHeading, expectedSubHeading);
	}
	
	@Test(priority=6)
	public void verifySignInBoxHeading() 
	{ 
		WebElement signInBoxHeading = driver.findElement(By.xpath("//p"));
	
		String actualSignInBoxHeading = signInBoxHeading.getText();
		String expectedSignInBoxHeading = "Sign in to start your session" ;
	
		Assert.assertEquals(actualSignInBoxHeading, expectedSignInBoxHeading);;
	}
	
	@Test(priority=7)
	public void verifyPlaceholders() 
	{ 	
		List<WebElement> Placeholders =driver.findElements(By.xpath("//input"));
	
		ArrayList <String>actPlaceholderText = new ArrayList <String>();
		
		ArrayList <String>expPlaceholderText = new ArrayList <String>();
		expPlaceholderText.add("Email");
		expPlaceholderText.add("Password");
		
		for (WebElement element : Placeholders)
		{
			actPlaceholderText.add(element.getAttribute("placeholder"));
		}
		Assert.assertEquals(actPlaceholderText, expPlaceholderText);
	}
	
	@Test(priority=8)
	public void verifyValidLogIn() 
	{
		driver.findElement(By.id("email")).sendKeys("kiran@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.xpath("//button")).click();
		
		String expectedTitle = "JavaByKiran | Dashboard" ; 
		String actualTitle = driver.getTitle() ;
		
		Assert.assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.linkText("LOGOUT")).click();
	}
	
	@Test(priority=9)
	public void verifyLogoutSuccessfullyMessage() 
	{	
		String actMessage = driver.findElement(By.xpath("//p[2]")).getText();
		String expMessage = "Logout successfully" ;
		
		Assert.assertEquals(actMessage, expMessage);
	}
	
	@Test(priority=10)
	public void verifyInvalidLogIn() 
	{
		driver.findElement(By.id("email")).sendKeys("akshay@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123456789");
		driver.findElement(By.xpath("//button")).click();
		
		String expectedTitle = "JavaByKiran | Log in" ;  
		String actualTitle = driver.getTitle() ;
		
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority=11)
	public void verifyInvalidLogInErrorMessage() 
	{
		List<WebElement> ErrorMessage =driver.findElements(By.xpath("//div[contains(@id,'error')]"));
		
		ArrayList <String>actErrorMessage = new ArrayList <String>();
		
		ArrayList <String>expErrorMessage = new ArrayList <String>();
		expErrorMessage.add("Please enter email as kiran@gmail.com");
		expErrorMessage.add("Please enter password 123456");
		
		for (WebElement element : ErrorMessage)
		{
			actErrorMessage.add(element.getText());
		}
		Assert.assertEquals(actErrorMessage, expErrorMessage);
	}
	
	@Test(priority=12)
	public void verifyBlankLogInErrorMessage() 
	{
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.xpath("//button")).click();
		
		List<WebElement> ErrorMessage =driver.findElements(By.xpath("//div[contains(@id,'error')]"));
		
		ArrayList <String>actErrorMessage = new ArrayList <String>();
		
		ArrayList <String>expErrorMessage = new ArrayList <String>();
		expErrorMessage.add("Please enter email.");
		expErrorMessage.add("Please enter password.");
		
		for (WebElement element : ErrorMessage)
		{
			actErrorMessage.add(element.getText());
		}
		Assert.assertEquals(actErrorMessage, expErrorMessage);
	}
	
	@Test(priority=13)
	public void verifyRegisterLinkText() 
	{ 	
		WebElement registerLink = driver.findElement(By.partialLinkText("Register"));
	
		String actualRegisterLinkText = registerLink.getText();
		String expectedRegisterLinkText = "Register a new membership" ;
		
		Assert.assertEquals(actualRegisterLinkText, expectedRegisterLinkText);
	}
	
	@Test(priority=14)
	public void verifyRegisterLink() 
	{ 	
		driver.findElement(By.partialLinkText("Register")).click();
	
		String expectedTitle = "JavaByKiran | Registration Page" ; 
		String actualTitle = driver.getTitle() ;
		
		Assert.assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.partialLinkText("membership")).click();
	}
}
