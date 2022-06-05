package feed.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import feed.util.JwtTokenUtil;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private JwtTokenUtil tokenUtil;
	@Override
	public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
		
		try {
			String pMobile = tokenUtil.getMobileFromToken(token);
			
			if(pMobile !=null &&  !pMobile.equalsIgnoreCase(""))
				return new User(pMobile, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",new ArrayList<>());
			throw new UsernameNotFoundException("User not found with pMobile: " + pMobile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UsernameNotFoundException("User not found with pMobile: " );
		}
		
		
		
	}

}