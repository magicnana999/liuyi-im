package com.creolophus.tomato.service;

import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.tomato.boot.TomatoApiContextValidator;
import com.creolophus.tomato.vo.UserToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author magicnana
 * @date 2019/7/2 上午9:19
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Resource
    private AuthService authService;

    @Resource
    private ApiContextValidator apiContextValidator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserToken userToken = authService.verifyToken(username);
        if(userToken == null) {
            return null;
        } else {
            TomatoApiContextValidator tomatoApiContextValidator = (TomatoApiContextValidator) apiContextValidator;
            tomatoApiContextValidator.setImId(userToken.getImId());
            tomatoApiContextValidator.setImToken(userToken.getImToken());
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return userToken.getUserId() + "";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
