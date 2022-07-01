package feed.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(value = "users")
public class UserEntity {
	@Id
	@Field(name="_id",targetType = FieldType.OBJECT_ID)
	private String userId;
	
	@Field(name="user_name")
	private String userName;
	
	@Field(name="login_type")
	private String loginType;
	
	@Field(name="full_name")
	private String fullName;
	
	@Field(name="email")
	private String email;
	
	@Field(name="email_verified")
	private boolean emailVerified;
	
	@Field(name="mobile")
	private String mobile;
	
	@Field(name="dob")
	private String dob;
	
	@Field(name="otp")
	private String otp;
	
	@Field(name="status")
	private boolean status;
	
	@Field(name="gender")
	private String gender;

	
	
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", userName=" + userName + ", loginType=" + loginType + ", fullName="
				+ fullName + ", email=" + email + ", emailVerified=" + emailVerified + ", mobile=" + mobile + ", dob="
				+ dob + ", otp=" + otp + ", status=" + status + "]";
	}
	
	
	
	

}
