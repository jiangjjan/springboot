package security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import security.mapper.UserInfoMapper;
import security.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("security")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    final UserInfoMapper userMapper;

    @PreAuthorize("hasAnyAuthority(T(security.config.RoleDefine).admin.authority)")
    @GetMapping("get")
    public Object test() {

        return userMapper.selectAllUser();

    }

    @PreAuthorize("@cs.check(#name)")
    @GetMapping("role")
    public Object role(String name) {

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        String sessionId = requestAttributes.getSessionId();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        log.info("sessionId {}",sessionId);
        log.info("getRemoteUser {}", request.getRemoteUser());
        return "role";

    }

}
