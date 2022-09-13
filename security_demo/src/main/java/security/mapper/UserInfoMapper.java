package security.mapper;

import org.apache.ibatis.annotations.Mapper;
import security.model.UserInfo;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
