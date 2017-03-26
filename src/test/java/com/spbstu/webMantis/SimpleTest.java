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
        element1.sendKeys("JavaMade2");
        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        element2.sendKeys("First task made automatically...");
    }

    public void DeleteTask(int trNumb){
        //Последовательно для созданной задачи выбираем "Редактировать", "Изменить данные", "Удалить", "Удалить задачи"
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id=\"buglist\"]/tbody/tr["+trNumb+"]/td[2]/a/i")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id=\"update_bug_form\"]/div/div[3]/input")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/div[1]/div/div[2]/div[2]/div/table/tfoot/tr/td/div/div[10]/form/fieldset/input[4]")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id=\"action-group-div\"]/form/div/div[2]/div[2]/input")));
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
        String expected = "JavaMade2";
        Assert.assertTrue(elements.stream().anyMatch(e -> e.getText().contains(expected)));

        //Find the number of created task
        int seqNumb = 1;
        for (int i = 0;i < elements.size();i++){
            if(elements.get(i).getText().equals(expected)){
                seqNumb = i+1;
                break;
            }
        }

        DeleteTask(seqNumb);

        //Check deleted task
        List<WebElement> elements2 = driver.findElements(By.xpath("//*[@id=\"buglist\"]/tbody/tr/td[11]"));
        String unexpected = "JavaMade2";
        Assert.assertFalse(elements2.stream().anyMatch(e -> e.getText().contains(unexpected)));
    }

}
