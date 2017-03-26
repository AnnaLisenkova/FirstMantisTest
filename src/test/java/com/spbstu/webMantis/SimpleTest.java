package com.spbstu.webMantis;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class SimpleTest extends Init {

    public void LogIn(){
        WebElement element1 = driver.findElement(By.id("username"));
        element1.sendKeys("administrator");
        WebElement element2 = driver.findElement(By.id("password"));
        element2.sendKeys("root"+ Keys.ENTER);
    }

    public void PushButtonBeforeCreateTask(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"navbar-container\"]/div[2]/ul/li[1]/div/a[1]"));
        element.click();
    }

    public void PushButtonAfterCreateTask(){
        WebElement element = driver.findElement(By.xpath("//*[@id=\"report_bug_form\"]/div/div[2]/div[2]/input"));
        element.click();
    }

    public String FillingFields(){
        String summary = "JavaMade2";
        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"summary\"]"));
        element1.sendKeys(summary);
        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"description\"]"));
        element2.sendKeys("First task made automatically...");
        return summary;
    }

    public void DeleteTask(int trNumb){
        //Последовательно для созданной задачи выбираем "Редактировать", "Изменить данные", "Удалить", "Удалить задачи"
        WebElement element1 = (driver.findElement(By.xpath("//*[@id=\"buglist\"]/tbody/tr["+trNumb+"]/td[2]/a/i")));
        element1.click();
        WebElement element2 = driver.findElement(By.xpath("//*[@id=\"update_bug_form\"]/div/div[3]/input"));
        element2.click();
        WebElement element3 = driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/div[1]/div/div[2]/div[2]/div/table/tfoot/tr/td/div/div[10]/form/fieldset/input[4]"));
        element3.click();
        WebElement element4 = driver.findElement(By.xpath("//*[@id=\"action-group-div\"]/form/div/div[2]/div[2]/input"));
        element4.click();
    }


    @Test
    public void test2()
    {
        driver.get("http://127.0.0.1/mantisbt");
        LogIn();
        PushButtonBeforeCreateTask();
        String expected = FillingFields();
        PushButtonAfterCreateTask();
        //Check created task
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"buglist\"]/tbody/tr/td[11]"));
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
        if(elements2.size()!=0) {
            Assert.assertFalse(elements2.stream().anyMatch(e -> e.getText().contains(unexpected)));
        }
    }
}
