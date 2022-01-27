package demo.mybatis.typehandler;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 与页面vo共用一个注解
 * @author jianjiang
 */
public class JsonEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Map<String, E> key2value = new HashMap<>();
    private final Map<E, String> value2key = new HashMap<>();

    public JsonEnumTypeHandler(Class<E> type) throws IllegalAccessException {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        E[] enumConstants = type.getEnumConstants();
        Field[] declaredFields = type.getDeclaredFields();
        Field jsonValue = null;
        int count = 0;
        for (Field e : declaredFields) {
            if (e.isAnnotationPresent(JsonValue.class)) {
                jsonValue = e;
                count++;
            }
        }

        if (count > 1) {
            throw new IllegalArgumentException("exist more than one  Annotation JsonValue" + type.getName());
        } else if (count == 1) {
            for (E e : enumConstants) {
                if (!jsonValue.isAccessible())
                    jsonValue.setAccessible(true);
                key2value.put(String.valueOf(jsonValue.get(e)), e);
                value2key.put(e, String.valueOf(jsonValue.get(e)));
            }
        } else { //如果没有,按照name进行设置
            for (E e : enumConstants) {
                key2value.put(e.name(), e);
                value2key.put(e, e.name());
            }
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, value2key.get(parameter));
        } else {
            ps.setObject(i, value2key.get(parameter), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : key2value.get(s);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : key2value.get(s);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : key2value.get(s);
    }
}