package demo.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SetNumberHandler extends BaseTypeHandler<Set<Integer>> {

    String split_char = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<Integer> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null && parameter.size() > 0) {
            String collect = parameter.stream().map(Number::toString).collect(Collectors.joining(split_char));
            ps.setString(i, collect);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return covert2SetInt(rs.getString(columnName));
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return covert2SetInt(rs.getString(columnIndex));
    }

    @Override
    public Set<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return covert2SetInt(cs.getString(columnIndex));
    }

    private Set<Integer> covert2SetInt(String p) {
        if (StringUtils.isEmpty(p)) {
            return null;
        } else {
            String[] split = p.split(split_char);
            Set<Integer> re = new HashSet<>();
            for (String e : split) {
                re.add(Integer.valueOf(e));
            }
            return re;
        }
    }
}
