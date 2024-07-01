package asia.serverchillrain.school.server.service.impl;

import asia.serverchillrain.aspect.BaseService;
import asia.serverchillrain.cache.core.AutoExpiredMap;
import asia.serverchillrain.school.server.entity.Constant;
import asia.serverchillrain.school.server.entity.bean.User;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;
import asia.serverchillrain.school.server.entity.enums.UserStatus;
import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;
import asia.serverchillrain.school.server.entity.vo.UserVo;
//import asia.serverchillrain.school.server.mappers.UserMapper;
import asia.serverchillrain.school.server.service.UserService;
//import asia.serverchillrain.school.server.table.UserTable;
import asia.serverchillrain.school.server.utils.CodingUtil;
import asia.serverchillrain.school.server.utils.IPUtil;
import asia.serverchillrain.school.server.utils.JsonUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * &#064;auther  2024 01 28
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private AutoExpiredMap cache;

//    @Resource
//    private UserMapper userMapper;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public String register(String email, String password, String emailCode) throws MonitoringPlatformException {
        if(cache.get(email) != null){
            throw new MonitoringPlatformException("注册名已存在！", ResponseCodeEnum.CODE_200);
        }
        //封装User
        User user = new User();
        user.setEmail(email);
        user.setId(new Random().nextInt(999999));
        user.setPassword(CodingUtil.encodeMD5(password));
        user.setStatus(UserStatus.NORMAL.getCode());
        cache.put(Constant.REGISTERED + email,JsonUtil.object2Json(user));
        cache.save();
//        QueryWrapper<User> query = new QueryWrapper<>();
//        query.eq("email", user.getEmail());
//        User tryRegisterUser = userMapper.selectOne(query);
//        if(tryRegisterUser != null){
//            throw new MonitoringPlatformException("注册名已存在！", ResponseCodeEnum.CODE_200);
//        }
//        user.setId(new Random().nextInt(999999));
//        user.setEmail(user.getEmail());
//        user.setPassword(CodingUtil.encodeMD5(user.getPassword()));
//        userMapper.insert(user);
        logger.info("注册信息---> {}", user);
        return "注册成功！";
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void registerAdmin(User adminUser) {
//        QueryWrapper<User> query = new QueryWrapper<>();
//        query.eq("email", adminUser.getEmail());
//        User tryRegisterUser = userMapper.selectOne(query);
//        if(tryRegisterUser != null){
//            logger.info("最高管理员账户已存在！");
//        }else{
//            adminUser.setId(new Random().nextInt(999999));
//            userMapper.insert(adminUser);
//            logger.info("管理员未注册，已自动注册。 管理员注册信息---> " + adminUser);
//        }
        if(cache.get(Constant.REGISTERED + adminUser.getEmail()) != null){
            logger.info("最高管理员账户已存在！");
        }else {
            adminUser.setPassword(CodingUtil.encodeMD5(adminUser.getPassword()));
            cache.put(Constant.REGISTERED + adminUser.getEmail(), JsonUtil.object2Json(adminUser));
            cache.save();
            logger.info("最高管理员账户已注册！");
        }
    }

    @Override
    public String login(HttpServletRequest request, String email, String password) throws MonitoringPlatformException {
        String ip = IPUtil.getIp(request);
        String userJson = cache.getStr(ip + "-" + email);
        UserVo user;
        //cache中有，直接登陆并重置自动登录时间
        if(userJson != null){
            //重新设置过期和过期时间
            logger.info("------>从缓存中登录");
//            MemoryData data = JsonUtil.json2Object(userJson, MemoryData.class);
            user = JsonUtil.json2Object(userJson, UserVo.class);
            if(user.getEmail() == null || user.getStatus() == null){
                cache.remove(ip + "-" + email);
                throw new MonitoringPlatformException("错误的缓存信息，已刷新，请重新登陆！", ResponseCodeEnum.CODE_200);
            }
        }else {//正常登录逻辑
            logger.info("------>从数据表中登录");
            User nowUser = JsonUtil.json2Object((String)cache.get(Constant.REGISTERED + email), User.class);
            if(nowUser == null){
                throw new MonitoringPlatformException("用户不存在！", ResponseCodeEnum.CODE_500);
            }
            if(!CodingUtil.encodeMD5(password).equals(nowUser.getPassword())){//密码错误
                throw new MonitoringPlatformException("账号或密码错误！", ResponseCodeEnum.CODE_500);
            }
            if(nowUser.getStatus() == UserStatus.BAN.getCode()){
                throw new MonitoringPlatformException("用户已封禁！", ResponseCodeEnum.CODE_500);
            }
            user = new UserVo(nowUser.getStatus(),  nowUser.getEmail());
        }
//        else{//没有，走正常登录逻辑
//            logger.info("------>从MySQL中登录");
//            QueryWrapper<User> query = new QueryWrapper<>();
//            query.eq(UserTable.EMAIL, email)
//                    .eq(UserTable.PASSWORD, password);
//            User one = userMapper.selectOne(query);
//            if(one == null){
//                throw new MonitoringPlatformException("账号或密码错误！", ResponseCodeEnum.CODE_200);
//            }
//            if(one.getStatus() == UserStatus.BAN.getCode()){
//                throw new MonitoringPlatformException("用户已封禁！", ResponseCodeEnum.CODE_200);
//            }
//            user = new UserVo(one.getStatus(), one.getEmail());
//        }
        request.getSession().setAttribute(Constant.LOG_USER, user);
//        request.getSession().setAttribute(Constant.LOG_USER + ip, user);
        logger.info(Constant.LOG_USER + ip);
        cache.put(ip, JsonUtil.object2Json(user));
//        cache.put(ip + "-" + email, JsonUtil.object2Json(user));
        cache.expired(7 * 24 * 60 * 60);
        cache.save();
        return "已登录！";
    }

    @Override
    public String logout(HttpServletRequest request) throws MonitoringPlatformException {
        String ip = IPUtil.getIp(request);
//        UserVo uservo = (UserVo) request.getSession().getAttribute(Constant.LOG_USER);
        String jsonStr = (String)cache.get(ip);
        UserVo uservo = JsonUtil.json2Object(jsonStr, UserVo.class);
//        UserVo uservo = (UserVo) request.getSession().getAttribute(Constant.LOG_USER + IPUtil.getIp(request));
        if(uservo == null){
            throw new MonitoringPlatformException("你没有登陆！", ResponseCodeEnum.CODE_600);
        }
        request.getSession().removeAttribute(Constant.LOG_USER);
//        request.getSession().removeAttribute(Constant.LOG_USER + IPUtil.getIp(request));
//        cache.remove(ip + "-" + uservo.getEmail());
        cache.remove(ip);
        logger.info("用户登出---> " + uservo.getEmail());
        return "已退出登录！";
    }

    @Override
    public String link(HttpServletRequest request) {
        String ip = IPUtil.getIp(request);
        User user = JsonUtil.json2Object((String)cache.get(ip), User.class);
        if(user != null){
            request.getSession().setAttribute(Constant.LOG_USER, new UserVo(user.getStatus(), user.getEmail()));
            cache.put(ip, JsonUtil.object2Json(user));
            cache.expired(7 * 24 * 60 * 60);
            cache.save();
        }
        return "已连接到Server!";
    }
}
