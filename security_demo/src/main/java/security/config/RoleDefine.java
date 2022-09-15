package security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface RoleDefine {

    GrantedAuthority root = new  SimpleGrantedAuthority("ROOT");

    GrantedAuthority admin = new  SimpleGrantedAuthority("ADMIN");

    GrantedAuthority user = new  SimpleGrantedAuthority("USER");

}
