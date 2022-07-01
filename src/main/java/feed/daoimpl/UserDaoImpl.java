package feed.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import feed.dao.UserDao;
import feed.entity.UserEntity;
import feed.repository.UserRepository;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository lUserRepository;
	
	@Override
	public UserEntity getUserProfile(String pMobileNo) {
		List<UserEntity> lUserEntities = lUserRepository.findByMobile(pMobileNo);
//		System.out.println(lUserEntities);
		if(lUserEntities.size() > 0) {
			return lUserEntities.get(0);
		}
		 
		return null;
	}

	@Override
	public UserEntity saveUser(UserEntity pUserEntity) {
		UserEntity lEntity = lUserRepository.save(pUserEntity);
		return lEntity;
	}

	@Override
	public boolean isUsernameExist(String pUsername) {
		
		List<UserEntity> userList = lUserRepository.findByUserName(pUsername);
		
		if(userList.size() > 0) {
			return false;
		}
		return true;
		
	}
}
