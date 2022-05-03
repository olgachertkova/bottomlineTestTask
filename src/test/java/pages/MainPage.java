package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPage extends PageBase {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchField;
    @FindBy(id = "nav-search-submit-button")
    WebElement searchButton;
    @FindBy(id = "searchDropdownBox")
    WebElement searchDropdownBox;
    @FindBy(className = "s-pagination-next")
    WebElement nextPageButton;
    @FindBy(css = ".s-card-container h2")
    WebElement anyResult;


    public void fillSearchField(String bookName) {
        inputText(searchField, bookName);
    }

    public void clickOnSearchButton() {
        searchButton.click();
    }

    public void selectOnMenu(String option) {
        WebElement menuItem = driver.findElement(By.xpath("//div[@id=\"nav-xshop\"]//a[text() ='" + option + "']"));
        click(menuItem);
    }

    public int getBooksNumberFromResults() {
        String text = driver.findElement(By.xpath("//div[@cel_widget_id=\"UPPER-RESULT_INFO_BAR-0\"]//div[@class=\"a-section a-spacing-small a-spacing-top-small\"]//span[1]")).getText();
        return Integer.parseInt(text.split(" ")[2]);
    }

    public void selectInDropDown(String text) {
        selectInDropDownByVisibleText(searchDropdownBox, text);
    }

    public void setLanguageFilter(String lang) {
        WebElement filterName = driver.findElement(By.xpath("//div[@id=\"s-refinements\"]//a[contains(.,'" + lang + "')]"));
        checkIn(filterName);
    }

    public List<String> getAllBookNames() {
        waitUntilElementVisible(anyResult, 60);
        List<String> bookNamesList = new ArrayList<>();
        List<WebElement> bookList;
        if (!nextPageButton.isDisplayed()) {
            bookList = driver.findElements(By.cssSelector(".s-card-container h2"));
            for (WebElement element : bookList) {
                String text = element.getText();
                bookNamesList.add(text);
            }
        }
        else
        { int numOfPages = getNumberOfPages();
            bookList = driver.findElements(By.cssSelector(".s-card-container h2"));
            for (WebElement element : bookList) {
                bookNamesList.add(element.getText());
            }
            for (int i = 2; i < numOfPages-1; i ++)  {
                click(nextPageButton);
                waitUntilElementVisible(anyResult, 60);
                bookList = driver.findElements(By.cssSelector(".s-card-container h2"));
                for (WebElement element : bookList) {
                    bookNamesList.add(element.getText());
                }
            }
        }

        return bookNamesList;
    }

    private int getNumberOfPages() {
        List<WebElement> pagination = driver.findElements(By.className("s-pagination-item"));
        int size = pagination.size();
        WebElement lastElement = pagination.get(size-2);
        return Integer.parseInt(lastElement.getText());
    }

    public String getBookName(List<String> bookNamesList) {
        String textMath = "the lost world by arthur conan doyle";
        List<String> booksToRemove = new ArrayList<>();

        for (String text : bookNamesList) {
            if (!text.toLowerCase(Locale.ROOT).contains(textMath)) {
                booksToRemove.add(text);
            }
        }
        bookNamesList.removeAll(booksToRemove);
//        System.out.println(bookNamesList);
        int maxLenName = 0;
        String bookWithMaxName = "";
        for (String currentBookName : bookNamesList){
            if(currentBookName.length() >= maxLenName){
                maxLenName = currentBookName.length();
                bookWithMaxName = currentBookName;
            }
        }
        return bookWithMaxName;
    }

    public boolean lengthNameValidation(String book, int i) {
        return book.length() <= i;
    }
}
