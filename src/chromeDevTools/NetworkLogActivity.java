package chromeDevTools;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.network.Network;
import org.openqa.selenium.devtools.v110.network.model.RequestId;
import org.openqa.selenium.devtools.v110.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NetworkLogActivity {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		ChromeDriver driver = new ChromeDriver(options);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		/*
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		
		devTools.addListener(Network.responseReceived(), response -> {
			Response res = response.getResponse();
			System.out.println(res.getUrl());
			//https://rahulshettyacademy.com/Library/GetBook.php?AuthorName=shetty
			if(Pattern.matches(".+GetBook.php?AuthorName=.+", res.getUrl())) {
				String body = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();
				System.out.println("body " + body + "\n");
			}
		});
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo");
		driver.findElement(By.cssSelector("button[routerLink*='/library']")).click();
		*/
        RequestId[] requestIds = new RequestId[1];
        ArrayList<RequestId> reqs = new ArrayList<RequestId>();
		devTools.createSession();
        devTools.send(Network.enable(Optional.of(1000000), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), request -> {
        	if (request.getRequest().getUrl().contains("GetBook")) 
        		reqs.add(request.getRequestId());
        });
        
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            /*String responseUrl = responseReceived.getResponse().getUrl();
            requestIds[0] = responseReceived.getRequestId();*/
            if (responseReceived.getResponse().getUrl().contains("GetBook")) {
            	/*System.out.println("SI");
                System.out.println("Status: " + responseReceived.getResponse().getStatus());
                System.out.println("Type: " + responseReceived.getType().toJson());
                responseReceived.getResponse().getHeaders().toJson().forEach((k, v) -> System.out.println((k + ":" + v)));
                requestIds[0] = responseReceived.getRequestId();*/
            	System.out.println("responseReceived");
                System.out.println("Url: " + responseReceived.getResponse().getUrl());
                System.out.println("RID: " + responseReceived.getRequestId());
                
            }
 
        });
 
        devTools.addListener(Network.loadingFinished(), response -> {

        	if(reqs.size() > 0 && reqs.get(0).toString().equals(response.getRequestId().toString())) {
        		System.out.println("loading1");
            	System.out.println("Body: ");
            	System.out.println(devTools.send(Network.getResponseBody(response.getRequestId())).getBody().toString());
        	}
        });
        
        driver.get("https://rahulshettyacademy.com/angularAppdemo");
		driver.findElement(By.cssSelector("a[routerLink*='/library']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr")));
		//System.out.println(driver.findElements(By.xpath("//table/tbody/tr")).get(0).getText());
	}

}
