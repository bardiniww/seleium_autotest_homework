import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RgsScenarioTest {

    private WebDriver     driver;
    private WebDriverWait wait;

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");      //join driver
        driver = new ChromeDriver();        //create new obj for chrome driver
        driver.manage().window().maximize();        //option to open full window
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);        //max time of waiting for open window
        wait = new WebDriverWait(driver, 20);       //waiter object
    }

    @Test
    public void checkTest() throws InterruptedException {
        //task1
        driver.get("https://www.rgs.ru/");


        //task2
        //finding "menu" link and click
        String menuLinkXpath = "//ol[@class='nav navbar-nav navbar-nav-rgs-menu pull-left']/li/a";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(menuLinkXpath)));
        driver.findElement(By.xpath(menuLinkXpath)).click();


        //task3
        //waiting for open and find by required category
        String concreteCategoryXpath = "//div[@class='grid rgs-main-menu-links']/div[2]/div[2]/ul[@class='collapse animated slideInRight']/li[@class='adv-analytics-navigation-line3-link']/a[contains(text(),'ДМС')]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(concreteCategoryXpath)));
        driver.findElement(By.xpath(concreteCategoryXpath)).click();

//        //if not enough time for code above (not sure)
//        WebElement el = driver.findElement(By.xpath(concreteCategoryXpath));
//        JavascriptExecutor executor1 = (JavascriptExecutor)driver;
//        executor1.executeScript("arguments[0].click();", el);


        //task4
        //verification test
        String targetedElementXpath1 = "//h1[@class='content-document-header']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(targetedElementXpath1)));
        WebElement targetedElement1 = driver.findElement(By.xpath(targetedElementXpath1));
        Assert.assertTrue("Элемент отстутствует на странице", targetedElement1.isDisplayed());


        //task5
        //finding targeted element "Отправить заявку" and click
        String sendApplicationElementXpath = "//a[contains(text(), 'Отправить заявку')]";
        WebElement sendApplicationElement = driver.findElement(By.xpath(sendApplicationElementXpath));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", sendApplicationElement);
        Thread.sleep(2000);


        //task6
        //verification test
        String targetedElementXpath2 = "//h4[@class='modal-title']//b[contains(text(), 'Заявка на добровольное медицинское страхование')]";
        WebElement targetedElement2 = driver.findElement(By.xpath(targetedElementXpath2));
        Assert.assertTrue("Элемент отстутствует на странице", targetedElement2.isDisplayed());


        //task7
        //fill gaps
        //second name
        String secondNameFieldXpath = "//input[contains(@data-bind, 'value:LastName')]";
        WebElement secondNameElement = driver.findElement(By.xpath(secondNameFieldXpath));
        secondNameElement.sendKeys("Тест");

        //first name
        String firstNameFieldXpath = "//input[contains(@data-bind, 'value:FirstName')]";
        WebElement firstNameElement = driver.findElement(By.xpath(firstNameFieldXpath));
        firstNameElement.sendKeys("Тест");

        //middle name
        String middleNameFieldXpath = "//input[contains(@data-bind, 'value:MiddleName')]";
        WebElement middleNameElement = driver.findElement(By.xpath(middleNameFieldXpath));
        middleNameElement.sendKeys("Тест");

        //select
        String selectFieldXpath = "//select[@name='Region']";
        WebElement selectFieldElement = driver.findElement(By.xpath(selectFieldXpath));
        new Select(selectFieldElement).selectByVisibleText("Москва");

        //phone
        String phoneFieldXpath = "//input[contains(@data-bind, 'value: Phone')]";
        WebElement phoneFieldElement = driver.findElement(By.xpath(phoneFieldXpath));
        phoneFieldElement.click();
        phoneFieldElement.sendKeys("9998887766");

        //email
        String emailFieldXpath = "//input[contains(@data-bind, 'value: Email')]";
        WebElement emailFieldElement = driver.findElement(By.xpath(emailFieldXpath));
        emailFieldElement.sendKeys("qwertyqwerty");

        //contact date
        String contactDateFieldXpath = "//input[@name='ContactDate']";
        WebElement contactDateFieldElement = driver.findElement(By.xpath(contactDateFieldXpath));
        contactDateFieldElement.click();
        contactDateFieldElement.sendKeys("05102020");

        //comments
        String commentsFieldXpath = "//textarea[contains(@data-bind, 'value: Comment')]";
        WebElement commentsFieldElement = driver.findElement(By.xpath(commentsFieldXpath));
        commentsFieldElement.sendKeys("somebody once told me the world is gonna roll me");

        //checkbox
        String checkboxFieldXpath = "//input[@class='checkbox']";
        WebElement checkboxFieldElement = driver.findElement(By.xpath(checkboxFieldXpath));
        if ( !checkboxFieldElement.isSelected() )
        {
            checkboxFieldElement.click();
        }


        //task8
        //verification test
        ArrayList<WebElement> inputsList = new ArrayList<>() {{
            add(secondNameElement);
            add(firstNameElement);
            add(middleNameElement);
            add(selectFieldElement);
            add(phoneFieldElement);
            add(emailFieldElement);
            add(contactDateFieldElement);
            add(commentsFieldElement);
        }};

        //assert fields
        for (WebElement temp : inputsList) {
            Assert.assertTrue("Не все поля заполнены" + temp.toString(), !temp.getAttribute("value").equals(""));
        }
        //assert checkbox
        Assert.assertTrue("Checkbox is not selected", checkboxFieldElement.isSelected());


        //task9
        //send application
        String sendFormXpath = "//button[@id='button-m']";
        driver.findElement(By.xpath(sendFormXpath)).click();


        //task10
        //verification test
        String errorXpath = "//span[contains(text(), 'Введите адрес электронной почты')]";
        WebElement errorElement = driver.findElement(By.xpath(errorXpath));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(errorXpath)));
        Assert.assertTrue("Ожидаемой ошибки не возникло", errorElement.isDisplayed());
        Assert.assertEquals("Текст ошибки не соответствует ожидаемому результату",
                "Введите адрес электронной почты", errorElement.getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
