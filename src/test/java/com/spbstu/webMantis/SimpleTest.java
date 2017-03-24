package com.spbstu.webMantis;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class SimpleTest extends Init {

    public void LogIn(){
        WebElement element1 = driver.findElement(By.id("username"));
        element1.sendKeys("administrator");
        WebElement element2 = driver.findElement(By.id("password"));
        element2.sendKeys("root"+ Keys.ENTER);
    }

    public void PushButtonBeforeCreateTask(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"navbar-container\"]/div[2]/ul/li[1]/div/a[1]"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void PushButtonAfterCreateTask(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"report_bug_form\"]/div/div[2]/div[2]/input"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void FillingFields(){
        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"summary\"]"));
        element1.sendKeys("JavaMade");
        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        element2.sendKeys("First task made automatically...");
    }


    @Test
    public void test2()
    {
        driver.get("http://127.0.0.1/mantisbt");
        LogIn();
        PushButtonBeforeCreateTask();
        FillingFields();
        PushButtonAfterCreateTask();
        //Check created task
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"buglist\"]/tbody/tr/td[11]"));
        String expected = "JavaMade";
        Assert.assertTrue(elements.stream().anyMatch(e -> e.getText().contains(expected)));

        Thread th = Thread.currentThread( );
        try {
            th.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}