package com.xwbing.shiro;

import com.xwbing.constant.CommonConstant;
import com.xwbing.domain.entity.sys.SysAuthority;
import com.xwbing.domain.entity.sys.SysRole;
import com.xwbing.domain.entity.sys.SysUser;
import com.xwbing.exception.BusinessException;
import com.xwbing.service.sys.SysRoleService;
import com.xwbing.service.sys.SysUserService;
import com.xwbing.util.captcha.CaptchaException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/3/28 14:32
 * 作者: xiangwb
 * 说明:
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取验证的对象
        if (principalCollection == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        SysUser user = (SysUser) principalCollection.fromRealm(getName()).iterator().next();
        if (user != null) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 用户的角色集合
            List<SysRole> sysRoles = sysRoleService.listByUserIdEnable(user.getId(), "Y");
            Set<String> roles = new HashSet<>();
            sysRoles.forEach(sysRole -> roles.add(sysRole.getName()));
            info.setRoles(roles);
            //用户的权限集合
            Set<String> permissions = new HashSet<>();
            List<SysAuthority> sysAuthorities = sysUserService.listAuthorityByIdAndEnable(user.getId(), "Y");
            sysAuthorities.forEach(sysAuthority -> permissions.add(sysAuthority.getUrl()));
            info.setStringPermissions(permissions);
            return info;
        }
        return null;
    }

    /**
     * 验证用户身份
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordCaptchaToken captchaToken = (UsernamePasswordCaptchaToken) authenticationToken;
        SysUser user = sysUserService.getByUserName(captchaToken.getUsername());
        if (user == null) {
            throw new BusinessException("账号错误");
        }
        String sysPassWord = user.getPassword();
        String salt = user.getSalt();
        boolean flag = sysUserService.checkPassWord(String.valueOf(captchaToken.getPassword()), sysPassWord, salt);
        if (!flag) {
            throw new BusinessException("密码错误");
        }
        String captcha = captchaToken.getCaptcha();
        String exitCode = (String) SecurityUtils.getSubject().getSession()
                .getAttribute(CommonConstant.KEY_CAPTCHA);
        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(exitCode)) {
            throw new CaptchaException("验证码错误");
        }
        return new SimpleAuthenticationInfo(user, sysPassWord,
                ByteSource.Util.bytes(salt), getName());
    }
}
