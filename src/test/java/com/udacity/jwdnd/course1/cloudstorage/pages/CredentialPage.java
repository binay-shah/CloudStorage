package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id="add-credential-button")
    private WebElement addNewCredentialButton;

    @FindBy(id="credential-url")
    private WebElement urlField;

    @FindBy(id="credential-username")
    private WebElement usernameField;

    @FindBy(id="credential-password")
    private WebElement passwordField;

    @FindBy(id="credential-submit")
    private WebElement submitButton;

    @FindBy(className = "urlCredential")
    private WebElement urlCredentialText;

    @FindBy(className = "usernameCredential")
    private WebElement usernameCredentialText;

    @FindBy(className = "passwordCredential")
    private WebElement passwordCredentialText;

    public WebElement getUrlCredentialText() {
        return urlCredentialText;
    }

    public WebElement getUsernameCredentialText() {
        return usernameCredentialText;
    }

    public WebElement getPasswordCredentialText() {
        return passwordCredentialText;
    }

    @FindBy(className = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(className = "deleteCredentialButton")
    private WebElement deleteCredentialButton;

    private WebDriver webDriver;

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void clickAddNewCredentialButton(){
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.elementToBeClickable(this.addNewCredentialButton)).click();
    }

    public void clickEditCredentialButton(){
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.elementToBeClickable(this.editCredentialButton)).click();
    }

    public void clickDeleteCredentialButton(){
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.elementToBeClickable(this.deleteCredentialButton)).click();
    }

    public void createCredential(String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.visibilityOf(this.urlField)).sendKeys(url);
        wait.until(ExpectedConditions.visibilityOf(this.usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(this.passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(this.submitButton)).click();
    }

    public void editCredential(String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.visibilityOf(this.urlField)).clear();
        wait.until(ExpectedConditions.visibilityOf(this.usernameField)).clear();
        wait.until(ExpectedConditions.visibilityOf(this.passwordField)).clear();
        wait.until(ExpectedConditions.visibilityOf(this.urlField)).sendKeys(url);
        wait.until(ExpectedConditions.visibilityOf(this.usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(this.passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(this.submitButton)).click();
    }

    public Credential getCredential(){
        Credential credential = new Credential();
        credential.setUrl(urlCredentialText.getText());
        credential.setUsername(usernameCredentialText.getText());
        credential.setPassword(passwordCredentialText.getText());
        return credential;
    }





}
