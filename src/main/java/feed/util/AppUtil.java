package feed.util;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class AppUtil {
	private Gson gson =new Gson();

	public Gson getGson() {
		return gson;
	}

//	public void setGson(Gson gson) {
//		this.gson = gson;
//	}
	
	
	
}
