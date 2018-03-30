package com.xwbing.shiro;

import com.xwbing.domain.entity.sys.SysAuthority;
import com.xwbing.domain.entity.sys.SysRole;
import com.xwbing.domain.entity.sys.SysUser;
import com.xwbing.service.sys.SysRoleService;
import com.xwbing.service.sys.SysUserService;
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
     * 授权用户权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取验证的对象
        if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        if (primaryPrincipal != null) {
            SysUser user = (SysUser) primaryPrincipal;
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordCaptchaToken captchaToken = (UsernamePasswordCaptchaToken) authenticationToken;
        //1.校验验证码
//        String captcha = captchaToken.getCaptcha();
//        String imgCode = (String) SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.KEY_CAPTCHA);
//        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(imgCode)) {
//            throw new AuthenticationException("验证码错误");
//        }
        //2.查是否有此用户
        SysUser user = sysUserService.getByUserName(captchaToken.getUsername());
        if (user == null) {
            throw new AuthenticationException("账号或密码错误");
        }
        //3.校验密码
        String password = new String(captchaToken.getPassword());
        String sysPassWord = user.getPassword();
        String salt = user.getSalt();
        boolean checkPassWord = sysUserService.checkPassWord(password, sysPassWord, salt);
        if (!checkPassWord) {
            throw new AuthenticationException("账号或密码错误");
        }
        //重新把数据库密码放置到token中
        captchaToken.setPassword(sysPassWord.toCharArray());
        return new SimpleAuthenticationInfo(user, sysPassWord, ByteSource.Util.bytes(salt), getName());
    }
}
