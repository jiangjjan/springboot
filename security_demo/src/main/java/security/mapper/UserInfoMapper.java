package security.mapper;

import org.apache.ibatis.annotations.Mapper;
import security.model.UserInfo;

import java.util.List;

@Mapper
public interface UserInfoMapper {

	UserInfo selectUserByName(UserInfo param);

	List<UserInfo> selectAllUser();
}
