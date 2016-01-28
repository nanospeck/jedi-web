import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dto.Constants;

public class JediMain {
	private static final String USERNAME = "nanospeck0";
	static WebDriver driver = new FirefoxDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10);
	public static final Integer NUMBER_OF_JOBS_TO_APPLY = 10;

	public static void main(String[] args) {

		// 1. login to stackoverflow
		login();
		
		// 2. search for java jobs page and open new tabs with job links
		searchJobs();
		
		
		// 3. apply for 1 job
		
		//driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		//pause(7000);
		
//		// 3a. switch to the next tab with job description
//		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.TAB);
//		// check if the apply now button is not ext
//		if(!driver.findElements(By.xpath("//button[contains(.,'apply now')]")).isEmpty()){
//		WebElement applyNowButton = driver.findElement(By.xpath("//button[contains(.,'apply now')]"));
//		applyNowButton.click();
//		}
//		else{
//			// 3b. go to next tab
//			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.TAB);
//			//click apply now
//			driver.switchTo().defaultContent();
//			pause(5000);
		// read the description of the job
		// do stemming and store the description to a map
		// do a mapping and generate a custom cover letter text
		
		//click on the 'apply now' button
//			WebElement element = driver.findElement(By.xpath("//div[@id='careers-wrapper']/button"));
//			element.click();
//			pause(5000);
		// the popup is open
		//fill in my job info
		//click on the popup for page1 to page2
		//clickNext1OnPopup();
		//			
//		}
		
		// write the date and job id to a csv file

		// close the browser

		// driver.quit();

	}
	
	private static ArrayList<String> removeStopWords(String s) {
		//https://javaextreme.wordpress.com/category/java-j2se/java-string/remove-stop-words-from-a-string/
		//change it to a HashSet later
		ArrayList<String> wordsList = new ArrayList<String>();
		s = s.trim().replaceAll("\\s+", " ");
		String[] words = s.split(" ");

		for (String word : words) {
			wordsList.add(word);
		}

		// remove stop words here from the temp list
		for (int i = 0; i < wordsList.size(); i++) {
			// get the item as string
			for (int j = 0; j < Constants.stopWordsofwordnet.length; j++) {
				if (Constants.stopWordsofwordnet[j].contains(wordsList.get(i))) {
					wordsList.remove(i);
				}
			}
		}
		for (String str : wordsList) {
			System.out.print(str + " ");
		}
		return wordsList;
	}

	private static void clickNext1OnPopup() {
		String testUrl = "https://careers.stackoverflow.com/jobs/98468/senior-java-application-architect-bigdata-m-gfk-se?searchTerm=java&offset=20&offersvisasponsorship=true";
		driver.get(testUrl);
		WebElement element = driver.findElement(By.xpath("//div[@id='careers-wrapper']/button"));
		element.click();
		pause(5000);
		
		driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(0));
		List<WebElement> allButt = driver.findElements(By.tagName("button"));
		for (WebElement w : allButt)
		{
			System.out.println(w.getText());
			w.click();
		}
	}
	
	private static String getHtmlFromElement(WebElement el)
	{
		return el.getAttribute("innerHTML");
	}
	
	private static String getTextFromHtml(String html)
	{
		//for testing
		//html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		
		Document doc = Jsoup.parse(html);
		String text = doc.body().text(); // "An example link"
		return text;
	}

	private static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void searchJobs() {
		driver.get("https://careers.stackoverflow.com/jobs?searchTerm=java&offersvisasponsorship=true");
		List<WebElement> list = driver.findElements(By.cssSelector("a"));
		int testnumb = 0;
		for (WebElement element : list) {
			if (testnumb < NUMBER_OF_JOBS_TO_APPLY && isJobType(element.getAttribute("href"))) {
				testnumb++;
				System.out.println(element.getText() + "  :");
				System.out.println(element.getAttribute("href"));
				String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND,
						Keys.RETURN);
				driver.findElement(By.linkText(element.getText())).sendKeys(
						selectLinkOpeninNewTab);
			}

		}
	}

	public static void login() {
		driver.get("https://accounts.google.com/ServiceLogin?passive=1209600&continue=https://accounts.google.com/o/oauth2/auth?scope%3Dprofile%2Bemail%26response_type%3Dcode%26redirect_uri%3Dhttps://stackauth.com/auth/oauth2/google%26state%3D%257B%2522sid%2522:161,%2522st%2522:%25225d570e59be44fd48521fbcad1c8ce470ebf8f124cbcd377ecb3a254884932024%2522,%2522ses%2522:%2522bd6f132ddd64437eb1762e378bd53fd0%2522%257D%26client_id%3D717762328687-p17pldm5fteklla3nplbss3ai9slta0a@developer.gserviceaccount.com%26hl%3Den-GB%26from_login%3D1%26as%3D-408e3cf6bf750fb3&ltmpl=popup&oauth=1&sarp=1&scc=1#identifier");
		WebElement email = driver.findElement(By.id("Email"));
		WebElement nextButton = driver.findElement(By.id("next"));
		// WebElement button =
		// driver.findElement(By.ti("google_oauth openid_large_btn"));
		email.sendKeys(USERNAME);
		nextButton.submit();
		WebElement passwdWaiting = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("Passwd")));
		WebElement pass = driver.findElement(By.id("Passwd"));
		WebElement signInButton = driver.findElement(By.id("signIn"));
		pass.sendKeys(Constants.PASSWORD);
		signInButton.submit();
		WebElement searchTermWaiting = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("searchTerm")));
	}

	public static boolean isJobType(String s) {
		return s != null
				&& s.contains("https://careers.stackoverflow.com/jobs/")
				&& s.contains("10")
				&& !s.contains("tag");
	}
}
