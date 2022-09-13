package security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.config.RoleDefine;
import security.mapper.UserInfoMapper;
import security.model.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("security")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    final UserInfoMapper userMapper;

    @GetMapping("get")
    public Object test() {
        List<UserInfo> userInfos = userMapper.selectAll();
        List<User> users = userInfos.stream().map(e -> new User(e.getUsername(), e.getPassword(), e.getEnabled(),
                e.getAccountNonExpired(), e.getCredentialsNonExpired(),
                e.getAccountNonLocked(), e.getAuthorities())).collect(Collectors.toList());
        log.info("res: {}", users);
        return userMapper.selectAll();
    }

    @GetMapping("add")
    public String add() {

        List<UserInfo> list = new ArrayList<>();

        UserInfo p = new UserInfo();
        p.setUsername("a");
        p.setAccountNonExpired(true);
        p.setAccountNonLocked(true);
        p.setCredentialsNonExpired(true);
        p.setDeleted(0);
        p.setEnabled(true);
        p.setPassword("123");
        p.setAuthorities(Collections.singleton(RoleDefine.root));
        list.add(p);
        p = new UserInfo();
        p.setUsername("aasd");
        p.setAccountNonExpired(true);
        p.setAccountNonLocked(true);
        p.setCredentialsNonExpired(true);
        p.setDeleted(0);
        p.setEnabled(true);
        p.setPassword("123");
        list.add(p);
        userMapper.insertAll(list);
        return "test";
    }
}
