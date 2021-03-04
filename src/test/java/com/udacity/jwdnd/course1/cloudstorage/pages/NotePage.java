package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(css="#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css="#note-title")
    private WebElement noteTitleField;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css="#note-submit")
    private WebElement noteSubmitButton;

    public WebElement getNoteTitleText() {
        return noteTitleText;
    }

    public WebElement getNoteDescriptionText() {
        return noteDescriptionText;
    }

    @FindBy(css="#add-note-button")
    private WebElement addNoteButton;

    @FindBy(className="deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(className="editNoteButton")
    private WebElement editNoteButton;

    @FindBy(className = "noteTitle")
    private WebElement noteTitleText;

    @FindBy(className = "noteDescription")
    private WebElement noteDescriptionText;

    WebDriverWait wait;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 30);
    }

    public void clickNotesTab(WebDriver webDriver){
        wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
    }

    public void clickAddNote(){
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
    }

    public void createNote(String title, String description){
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public void editNote(String title, String description){

        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitleField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(this.noteDescriptionField)).clear();
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public void clickEditNoteButton(){
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
    }



    public void deleteNote(){
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();

    }

    public Note getNote(){
        Note note = new Note();
        note.setNoteTitle(noteTitleText.getText());
        note.setNoteDescription(noteDescriptionText.getText());
        return  note;
    }
}
