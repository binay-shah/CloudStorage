package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

	@Test
	public void testUnauthorizedUsers() throws InterruptedException {
		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		assertEquals("Sign Up", driver.getTitle());
		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginAndLogout() throws InterruptedException {
		String firstName = "binay";
		String lastName = "shah";
		String username= "bishah";
		String password = "12345";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);
		Thread.sleep(1000);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Thread.sleep(1000);
		assertEquals("Home", driver.getTitle());
		Thread.sleep(1000);
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		Thread.sleep(1000);
		assertNotEquals("Home", driver.getTitle());

	}



}
