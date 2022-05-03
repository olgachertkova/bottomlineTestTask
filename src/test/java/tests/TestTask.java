package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;

import java.util.List;

public class TestTask extends TestBase{
    MainPage mainPage;

    String bookName = "the Lost World by Arthur Conan Doyle";

    @BeforeMethod
    public void initPage(){
        mainPage = PageFactory.initElements(driver, MainPage.class);
    }

    @Test
    public void searchBookByNameTest(){
        mainPage.selectInDropDown("Books");
        mainPage.fillSearchField(bookName);
        mainPage.clickOnSearchButton();
        int num = mainPage.getBooksNumberFromResults();
        logger.info("Number of books in search result is " + num);
    }

    @Test
    public void searchBookByNameAndLanguageTest(){
        mainPage.selectInDropDown("Books");
        mainPage.fillSearchField(bookName);
        mainPage.clickOnSearchButton();
        mainPage.setLanguageFilter("English");
        int num = mainPage.getBooksNumberFromResults();
        logger.info("Number of English books in search result is " + num);

    }

    @Test
    public void searchBookWithLongestNameIncludesTheFullValueTest(){
        mainPage.selectInDropDown("Books");
        mainPage.fillSearchField(bookName);
        mainPage.clickOnSearchButton();
        List<String> bookNamesList = mainPage.getAllBookNames();
        logger.info("List of books: " + bookNamesList);
        String book = mainPage.getBookName(bookNamesList);
        logger.info("Book includes the full value used for search and has the longest name: " + book);
        Assert.assertTrue(mainPage.lengthNameValidation(book, 70), "Name of found result’s item is longer than 70 characters");
    }
}
