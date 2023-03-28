package chromeDevTools;

import java.util.Collections;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.fetch.Fetch;
import org.openqa.selenium.devtools.v110.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v110.fetch.model.RequestStage;
import org.openqa.selenium.devtools.v110.network.model.ResourceType;

public class NetworkMocking {
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		ChromeDriver driver = new ChromeDriver(options);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
        
        /*
		devTools.send(Fetch.enable(Optional.empty(Collections.singletonList(requestPattern)), Optional.of(false)));
		
		devTools.addListener(Fetch.requestPaused(), request -> {
			if(request.getRequest().getUrl().contains("=shetty")) {
				String url = request.getRequest().getUrl();
				System.out.println(url);
				url = url.replace("=shetty", "=BadGuy");
				System.out.println(url);
				Fetch.continueRequest(request.getRequestId(), Optional.of(url), 
					Optional.of(request.getRequest().getMethod()), Optional.of(request.getRequest().getMethod()), 
					Optional.empty(), Optional.empty());
			} else
				Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()), 
						Optional.of(request.getRequest().getMethod()), Optional.of(request.getRequest().getMethod()), 
						Optional.empty(), Optional.empty());
			//System.out.println("Response Body: \n" + devTools.send(Fetch.getResponseBody(request.getRequestId())).getBody() + "\n");

		});
		*/
		RequestPattern requestPattern = new RequestPattern(Optional.of(".+"),
		        Optional.of(ResourceType.SCRIPT),
		        Optional.of(RequestStage.RESPONSE));

		
		devTools.send(Fetch.enable(Optional.of(Collections.singletonList(requestPattern)), Optional.of(false)));
		devTools.addListener(Fetch.requestPaused(), request -> {
			System.out.println("N");
			if(request.getRequest().getUrl().contains("=shetty")) {
				String url = request.getRequest().getUrl();
				System.out.println(url);
				url = url.replace("=shetty", "=BadGuy");
				System.out.println(url);
				Fetch.continueRequest(request.getRequestId(), Optional.of(url), 
					Optional.of(request.getRequest().getMethod()), Optional.of(request.getRequest().getMethod()), 
					Optional.empty(), Optional.empty());
			} else
				Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()), 
						Optional.of(request.getRequest().getMethod()), Optional.of(request.getRequest().getMethod()), 
						Optional.empty(), Optional.empty());
		       System.out.println("Response Body: \n" + devTools.send(Fetch.getResponseBody(request.getRequestId())).getBody() + "\n");

		 });
		System.out.println("S");
		driver.get("https://rahulshettyacademy.com/angularAppdemo");
		driver.findElement(By.cssSelector("button[routerLink*='/library']")).click();
		Thread.sleep(10000);
		driver.quit();
	}
}
