package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id="nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void clickNotesTab(){
        WebDriverWait  wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.visibilityOf(this.navNotesTab)).click();
    }


    public void clickCredentialsTab(){
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        js.executeScript("arguments[0].click();", this.navCredentialsTab);
        //WebDriverWait  wait = new WebDriverWait(webDriver, 20);
        //wait.until(ExpectedConditions.visibilityOf(this.navCredentialsTab)).click();
    }

}
