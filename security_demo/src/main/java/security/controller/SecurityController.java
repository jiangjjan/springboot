package security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.mapper.UserInfoMapper;
import security.model.UserInfo;

import java.sql.Wrapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("security")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    final UserInfoMapper userMapper;

    @PreAuthorize("hasAnyAuthority(T(security.config.RoleDefine).root.authority)")
    @GetMapping("get")
    public Object test() {

        List<UserInfo> userInfos = userMapper.selectAll();
        return userInfos.stream().map(e -> new User(e.getUsername(), e.getPassword(), e.getEnabled(),
                e.getAccountNonExpired(), e.getCredentialsNonExpired(),
                e.getAccountNonLocked(), e.getAuthorities())).collect(Collectors.toList());
    }

    @PreAuthorize("@cs.check(#name)")
    @GetMapping("role")
    public Object role(String name) {

      return "role";
    }

}
