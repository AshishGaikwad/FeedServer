package feed.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
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
	
	

}
