package feed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feed.entity.OTPEntity;
import feed.util.CacheLoader;
import feed.util.JwtTokenUtil;
import feed.util.Util;

@Service
public class EmailService {
@Autowired
CacheLoader cacheLoader;
	
	@Autowired
	private JwtTokenUtil jwt;
	
	public OTPEntity sendOTP(OTPEntity pOTPEntity) {
		//generate OTP
		pOTPEntity.setOtp(Util.getOTP(6));
		pOTPEntity.setRefno( cacheLoader.putOTP(pOTPEntity.getMobile(), pOTPEntity.getOtp(), System.currentTimeMillis()));
		System.out.println("OTP is :"+pOTPEntity.getOtp());
		//do api call for OTP
		//save otp to databse
		//send status to otpsessionkey
		return pOTPEntity;
	}
	
	public OTPEntity verifyOTP(OTPEntity pOTPEntity) {
		pOTPEntity.setStatus( cacheLoader.verifyOTP(pOTPEntity.getMobile(),pOTPEntity.getRefno(),pOTPEntity.getOtp()));
		if(pOTPEntity.getStatus())
			pOTPEntity.setToken(jwt.generateToken("m", pOTPEntity.getMobile()));
		
		return pOTPEntity;
	}
}
