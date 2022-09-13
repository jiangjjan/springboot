package security.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class GrantedAuthorityHandler extends BaseTypeHandler<Set<GrantedAuthority>> {

    String split_char = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<GrantedAuthority> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null && parameter.size() > 0) {
            String collect = parameter.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(split_char));
            ps.setString(i, collect);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return covert2ListGrantedAuthority(rs.getString(columnName));
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return covert2ListGrantedAuthority(rs.getString(columnIndex));
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return covert2ListGrantedAuthority(cs.getString(columnIndex));
    }

    private Set<GrantedAuthority> covert2ListGrantedAuthority(String p) {
        if (StringUtils.isEmpty(p)) {
            return null;
        } else {
            String[] split = p.split(split_char);
            Set<GrantedAuthority> re = new HashSet<>();
            Arrays.stream(split).distinct().map(SimpleGrantedAuthority::new).forEach(re::add);
            return re;
        }
    }
}
