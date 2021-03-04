package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(css="#home-link")
    private WebElement homeLink;

    WebDriverWait wait;

    public ResultPage(WebDriver webDriver) {

        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 100);
    }

    public void clickHomeLink(){

         wait.until(ExpectedConditions.visibilityOf(this.homeLink)).click();
    }






}
