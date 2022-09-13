package security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import security.config.GrantedAuthorityHandler;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Table;
import java.util.Set;

@Table(name="user")
@Data
public class UserInfo{

    @KeySql(useGeneratedKeys = true)
    Long id;
    String username;

    String password;
    Boolean accountNonExpired;
    Boolean accountNonLocked;
    Boolean credentialsNonExpired;
    Boolean enabled;

    @ColumnType(typeHandler = GrantedAuthorityHandler.class)
    Set<GrantedAuthority> authorities;

    /**
     * 0 未删除
     */
    long deleted;

}
