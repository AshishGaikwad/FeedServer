package feed.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		return new ResponseEntity<OTPEntity>(otpService.sendOTP(pOTPEntity), HttpStatus.OK);
	}

	@PostMapping("/otp/verify")
	public ResponseEntity<?> verifyOTP(@RequestBody OTPEntity pOTPEntity) {
		return new ResponseEntity<OTPEntity>(otpService.verifyOTP(pOTPEntity), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveUserDetails(@RequestBody UserEntity pUserEntity, HttpServletRequest request) {

		String pAuthHeader = request.getHeader("Authorization");
		pAuthHeader = pAuthHeader.replaceAll("Bearer ", "");
		String mobile = jwtTokenUtil.getMobileFromToken(pAuthHeader);
		JSONObject response = new JSONObject();
		if (!pUserEntity.getMobile().equalsIgnoreCase(mobile)) {
			response.put("status", false);
			response.put("responseCode", 202);
			response.put("responseMsg", "UNAUTHORIZED ACCESS. INVALID MOBILE NO");
		} else {			
			if (pUserEntity.getUserName() == null || pUserEntity.getUserName().equalsIgnoreCase("")) {
				response.put("status", false);
				response.put("responseCode", 207);
				response.put("responseMsg", "Username should not be null or empty");
			}else if (pUserEntity.getFullName() == null || pUserEntity.getFullName().equalsIgnoreCase("")) {
				response.put("status", false);
				response.put("responseCode", 208);
				response.put("responseMsg", "Fullname should not be null or empty");
			}else if (pUserEntity.getMobile() == null || pUserEntity.getMobile().equalsIgnoreCase("")) {
				response.put("status", false);
				response.put("responseCode", 209);
				response.put("responseMsg", "Mobile no should not be null or empty");
			}else if (pUserEntity.getDob() == null || pUserEntity.getDob().equalsIgnoreCase("")) {
				response.put("status", false);
				response.put("responseCode", 210);
				response.put("responseMsg", "DOB should not be null or empty");
			}else if (pUserEntity.getGender() == null || pUserEntity.getGender().equalsIgnoreCase("")) {
				response.put("status", false);
				response.put("responseCode", 211);
				response.put("responseMsg", "Gender should not be null or empty");
			}   else {

				pUserEntity.setUserName(pUserEntity.getUserName().toLowerCase());

				UserEntity lEntity = null;
				boolean isUsernameAvailable = false;

				synchronized (this) {
					isUsernameAvailable = userDao.isUsernameExist(pUserEntity.getUserName());
					if (isUsernameAvailable) {
						lEntity = userDao.saveUser(pUserEntity);
					}
				}

				if (lEntity != null) {
					response.put("status", true);
					response.put("responseCode", 200);
					response.put("responseMsg", "USER DETAILS SAVED SUCCESSFULLY");
					response.put("body", new JSONObject(appUtil.getGson().toJson(lEntity)));
				} else {

					if (!isUsernameAvailable) {
						response.put("status", false);
						response.put("responseCode", 206);
						response.put("responseMsg", "Username is already exist");
					} else {
						response.put("status", false);
						response.put("responseCode", 204);
						response.put("responseMsg", "FAILED TO ADD OR UPDATE USER");
					}
				}

			}
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{pMobile}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getUserDetails(@PathVariable("pMobile") String pMobile, HttpServletRequest request) {
		String pAuthHeader = request.getHeader("Authorization");
		pAuthHeader = pAuthHeader.replaceAll("Bearer ", "");
		String mobile = jwtTokenUtil.getMobileFromToken(pAuthHeader);
		UserEntity lEntity = userDao.getUserProfile(pMobile);
		JSONObject response = new JSONObject();

		if (mobile.equalsIgnoreCase(pMobile)) {
			if (lEntity != null) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "USER FOUND");
				response.put("body", new JSONObject(appUtil.getGson().toJson(lEntity)));
			} else {
				response.put("status", true);
				response.put("responseCode", 201);
				response.put("responseMsg", "USER NOT FOUND");

			}
		} else {
			response.put("status", false);
			response.put("responseCode", 202);
			response.put("responseMsg", "UNAUTHORIZED ACCESS. INVALID MOBILE NO");
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/availability/{pUsername}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> checkIfUserAvailable(@PathVariable("pUsername") String pUsername) {
		pUsername = pUsername.toLowerCase();

		JSONObject response = new JSONObject();
		if (pUsername.equalsIgnoreCase("")) {
			response.put("status", false);
			response.put("responseCode", 201);
			response.put("responseMsg", "INVALID USERNAME");
		} else {
			boolean uExist = userDao.isUsernameExist(pUsername);
			if (uExist) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "USERNAME IS AVAILABLE");
			} else {
				response.put("status", false);
				response.put("responseCode", 202);
				response.put("responseMsg", "USERNAME IS NOT AVAILABLE");
			}
		}
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

}
