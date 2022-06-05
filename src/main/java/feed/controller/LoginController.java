package feed.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import feed.dao.UserDao;
import feed.entity.OTPEntity;
import feed.entity.UserEntity;
import feed.service.OTPService;
import feed.util.AppUtil;
import feed.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private OTPService otpService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@Autowired
	private AppUtil appUtil;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/login")
	public String login() {
		System.out.println("inside login");
		return "ashish";
	}
	
	
	@PostMapping("/otp/send")
	public ResponseEntity<?> sendOTP(@RequestBody OTPEntity pOTPEntity) {
		return new ResponseEntity<OTPEntity>(otpService.sendOTP(pOTPEntity),HttpStatus.OK);
	}

	@PostMapping("/otp/verify")
	public ResponseEntity<?> verifyOTP(@RequestBody OTPEntity pOTPEntity) {
		return new ResponseEntity<OTPEntity>(otpService.verifyOTP(pOTPEntity),HttpStatus.OK);
	}

	
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveUserDetails(@RequestBody UserEntity pUserEntity,HttpServletRequest request){
		String pAuthHeader = request.getHeader("Authorization");
		pAuthHeader =pAuthHeader.replaceAll("Bearer ", "");
		String mobile = jwtTokenUtil.getMobileFromToken(pAuthHeader);
		JSONObject response = new JSONObject();
		
		if(!pUserEntity.getMobile().equalsIgnoreCase(mobile)) {
			response.put("status", false);
			response.put("responseCode", 202);
			response.put("responseMsg", "UNAUTHORIZED ACCESS. INVALID MOBILE NO");
		}else {
			
			UserEntity lEntity = userDao.saveUser(pUserEntity);
			
			
			if(lEntity != null) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "USER PERSISTED");
				response.put("body", new JSONObject( appUtil.getGson().toJson(lEntity)));
			}else {
				response.put("status", false);
				response.put("responseCode", 204);
				response.put("responseMsg", "FAILED TO ADD OR UPDATE USER");
			}
			
			
		}
		
		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/user/{pMobile}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getUserDetails(@PathVariable("pMobile") String pMobile,HttpServletRequest request){
		String pAuthHeader = request.getHeader("Authorization");
		pAuthHeader =pAuthHeader.replaceAll("Bearer ", "");
		String mobile = jwtTokenUtil.getMobileFromToken(pAuthHeader);
		UserEntity lEntity = userDao.getUserProfile(pMobile);
		JSONObject response = new JSONObject();
		
		if(mobile.equalsIgnoreCase(pMobile)) {
			if(lEntity != null) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "USER FOUND");
				response.put("body", new JSONObject( appUtil.getGson().toJson(lEntity)));
			}else {
				response.put("status", false);
				response.put("responseCode", 201);
				response.put("responseMsg", "USER NOT FOUND");
				
			}
		}else {
			response.put("status", false);
			response.put("responseCode", 202);
			response.put("responseMsg", "UNAUTHORIZED ACCESS. INVALID MOBILE NO");
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

}
