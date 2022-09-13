package security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface RoleDefine {

    GrantedAuthority root = new  SimpleGrantedAuthority("root");

    GrantedAuthority admin = new  SimpleGrantedAuthority("admin");

    GrantedAuthority user = new  SimpleGrantedAuthority("user");

}
