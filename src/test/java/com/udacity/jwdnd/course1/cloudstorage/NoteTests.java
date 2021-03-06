package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {

    public NoteTests(WebDriver driver){
        this.driver = driver;
        notePage = new NotePage(driver);
    }

    public static WebDriver driver;

    public String baseURL;

    public NotePage notePage;

    public ResultPage resultPage;


    public void testCreateNote() throws InterruptedException {
        String title = "title";
        String description = "description";
        HomePage homePage = new HomePage(driver);
        homePage.clickNotesTab();
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



    public void testDeleteNote() throws InterruptedException {
        String title = "title";
        String description = "description";
        ResultPage resultPage = new ResultPage(driver);
        notePage.deleteNote();
        Thread.sleep(1000);
        resultPage.clickHomeLink();
        Thread.sleep(1000);
        notePage.clickNotesTab(driver);
        Thread.sleep(1000);
        assertThrows(NoSuchElementException.class, () -> {

            driver.findElement(By.className("noteTitle"));
        });
        assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.className("noteDescription"));
        });


    }


}
