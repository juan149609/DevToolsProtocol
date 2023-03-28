package chromeDevTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.emulation.Emulation;

public class setGeoLocation {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		ChromeDriver driver = new ChromeDriver(options);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("latitude", 30);
		param.put("longitude", -97);
		param.put("accuracy", 1);
		
		//driver.executeCdpCommand("Emulation.setGeolocationOverride", param);
		//devTools.send(Emulation.setGeolocationOverride(Optional.of(30), Optional.of(-97), Optional.of(1)));
        driver.get("https://oldnavy.gap.com/stores");
		//driver.get("https://mycurrentlocation.net/");
		//driver.quit();
	}

}
