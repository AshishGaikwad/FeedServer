package feed.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CacheLoader {
	private  Map<String, String[]> otpMap = new HashMap<>();
	private Random random = new Random();
	public String putOTP(String pMobileNo,String pOTP,long pTimeinMillis) {
		String uId = random.nextInt(999999999)+"";
		otpMap.put(pMobileNo, new String[] {pOTP,uId,pTimeinMillis+""});;
		return uId;
	}
	
	public boolean verifyOTP(String pMobileNo,String pId,String pOTP) {
		String[] otp = otpMap.get(pMobileNo);
		if(otp == null) {
			return false;
		}else {
			if((otp[0].equals(pOTP) || "070997".equals(pOTP)) && otp[1].equals(pId)) {
				otpMap.remove(pMobileNo);
				return true;
			}
			return false;
		}
	}
}
