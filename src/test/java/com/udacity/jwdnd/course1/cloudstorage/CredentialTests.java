package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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

public class CredentialTests {

    public CredentialTests(WebDriver driver){
        this.driver = driver;
        credentialPage = new CredentialPage(driver);
    }

    public static WebDriver driver;

    public String baseURL;

    public CredentialPage credentialPage;

    public ResultPage resultPage;




    @Test
    @Order(1)
    public void testCreateCredential() throws InterruptedException {
        String url = "www.google.com";
        String username = "google";
        String password = "password";

        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialsTab();
        credentialPage = new CredentialPage(driver);

        credentialPage.clickAddNewCredentialButton();
        credentialPage.createCredential(url, username, password);

        resultPage = new ResultPage(driver);
        Thread.sleep(1000);
        resultPage.clickHomeLink();
        Thread.sleep(2000);
        homePage.clickCredentialsTab();
        Thread.sleep(2000);
        Credential credential = credentialPage.getCredential();
        assertEquals(url, credential.getUrl());
        assertEquals(username, credential.getUsername());
        assertNotEquals(password, credential.getPassword());
    }

    @Test
    @Order(2)
  public void testEditCredential() throws InterruptedException {
        String url = "www.google.comXX";
        String username = "googleXX";
        String password = "passwordXX";
        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialsTab();
        credentialPage = new CredentialPage(driver);
        Thread.sleep(2000);
        homePage.clickCredentialsTab();

        credentialPage.clickEditCredentialButton();
        credentialPage.editCredential(url, username, password);
        Thread.sleep(1000);
        resultPage = new ResultPage(driver);
        Thread.sleep(1000);
        resultPage.clickHomeLink();
        Thread.sleep(3000);
        homePage.clickCredentialsTab();

        Credential credential = credentialPage.getCredential();
        Thread.sleep(3000);
        assertEquals(url, credential.getUrl());
        assertEquals(username, credential.getUsername());
        assertNotEquals(password, credential.getPassword());
  }

  @Test
  @Order(3)
  public void testDeleteCredential() throws InterruptedException {
      HomePage homePage = new HomePage(driver);
      homePage.clickCredentialsTab();
      credentialPage = new CredentialPage(driver);
      Thread.sleep(2000);
      homePage.clickCredentialsTab();
      credentialPage.clickDeleteCredentialButton();

      assertThrows(NoSuchElementException.class, () -> {
          driver.findElement(By.className("urlCredential"));
      });
      assertThrows(NoSuchElementException.class, () -> {
          driver.findElement(By.className("usernameCredentialText"));
      });
      assertThrows(NoSuchElementException.class, () -> {
          driver.findElement(By.className("passwordCredential"));
      });

  }


}
