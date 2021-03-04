package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {

    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    public NotePage notePage;

    public ResultPage resultPage;

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
    public void beforeEach() throws InterruptedException {
        baseURL =  "http://localhost:" + port;
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

        notePage = new NotePage(driver);
        notePage.clickNotesTab(driver);
    }


    @Test
    @Order(1)
    public void testCreateNote() throws InterruptedException {
        String title = "title";
        String description = "description";


        notePage.clickAddNote();
        Thread.sleep(1000);
        notePage.createNote(title, description);
        Thread.sleep(1000);
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickHomeLink();
        Thread.sleep(1000);
        notePage.clickNotesTab(driver);
        Thread.sleep(1000);
        assertEquals(title, notePage.getNote().getNoteTitle());
        assertEquals(description, notePage.getNote().getNoteDescription());
    }


    @Test
    @Order(2)
    public void testEditNote() throws InterruptedException {
        String title = "titlex";
        String description = "descriptionx";
        ResultPage resultPage = new ResultPage(driver);
        notePage.clickEditNoteButton();
        notePage.editNote(title, description);
        Thread.sleep(1000);
        resultPage.clickHomeLink();
        Thread.sleep(1000);
        notePage.clickNotesTab(driver);
        Thread.sleep(1000);
        assertEquals(title, notePage.getNote().getNoteTitle());
        assertEquals(description, notePage.getNote().getNoteDescription());
    }


    @Test
    @Order(3)
    public void testDeleteNote() throws InterruptedException {
        String title = "title";
        String description = "description";

        ResultPage resultPage = new ResultPage(driver);
        notePage.deleteNote();
        Thread.sleep(1000);
        resultPage.clickHomeLink();
        Thread.sleep(1000);
        notePage.clickNotesTab(driver);
        Thread.sleep(3000);
        assertThrows(NoSuchElementException.class, () -> {
            notePage.getNoteTitleText();
        });
        assertThrows(NoSuchElementException.class, () -> {
            notePage.getNoteDescriptionText();
        });


    }


}
