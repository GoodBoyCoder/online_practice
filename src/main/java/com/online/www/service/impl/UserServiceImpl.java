package com.online.www.service.impl;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.config.SecurityProperties;
import com.online.www.mapper.SubjectMapper;
import com.online.www.mapper.UserMapper;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.bo.SignUpBo;
import com.online.www.pojo.po.ExamUser;
import com.online.www.pojo.po.Subject;
import com.online.www.pojo.po.User;
import com.online.www.pojo.vo.ExamRankPage;
import com.online.www.pojo.vo.ExamRankVo;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.pojo.vo.UserVo;
import com.online.www.service.RankService;
import com.online.www.service.UserService;
import com.online.www.util.JwtUtil;
import com.online.www.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private RankService rankService;

    @Override
    public LoginVo login(LoginBo loginBo) {
        //验证用户密码
        User user = baseMapper.getUser(loginBo.getUserName());
        Assert.notNull(user, "用户名不存在");
        Assert.isTrue(user.getPassward().equals(MD5Util.getMD5String(loginBo.getPassword())), "密码错误");

        //签发token
        long expireAt = System.currentTimeMillis() + securityProperties.getExpire();
        return new LoginVo(jwtUtil.creatToken(user.getId(), expireAt), expireAt);
    }

    @Override
    public LoginVo signUp(SignUpBo signUpBo) {
        //验证用户
        User user = baseMapper.getUser(signUpBo.getUserName());
        Assert.isNull(user, "用户名已经存在，个性点换一个吧！");

        User signUpUser = new User();
        signUpUser.setUserName(signUpBo.getUserName());
        signUpUser.setPassward(MD5Util.getMD5String(signUpBo.getPassword()));
        signUpUser.setPic(signUpBo.getPicUrl());

        int insert = baseMapper.insert(signUpUser);
        Assert.isTrue(insert == 1, "系统错误，用户注册失败");

        return login(new LoginBo(signUpUser.getUserName(), signUpBo.getPassword()));
    }

    @Override
    public Boolean checkUserName(String userName) {
        return baseMapper.getUser(userName) == null;
    }

    @Override
    public UserVo getUserInfo(Integer userId) {
        return new UserVo().convertFromUser(userMapper.selectById(userId));
    }

    @Override
    public ExamRankVo getExamRank(Integer userId, Integer subjectId) {
        //检查科目
        Subject subject = subjectMapper.selectById(subjectId);
        Assert.notNull(subject, "所查科目不存在！！！");

        List<ExamUser> examRankList = rankService.getExamRankList(subjectId);
        List<Integer> userIds = examRankList.stream()
                .map(ExamUser::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<User> users = userMapper.selectBatchIds(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        ExamRankVo examRankVo = new ExamRankVo();
        examRankVo.setMyRank(-1);
        //转换examRankPage
        int rank = 1;
        List<ExamRankPage> examRankPages = new LinkedList<>();
        for (ExamUser examUser : examRankList) {
            ExamRankPage examRankPage = new ExamRankPage();
            examRankPage.setRank(rank++);
            examRankPage.setScore(examUser.getTotalScore());
            examRankPage.setPassTime(examUser.getPassTime());
            examRankPage.setUserId(examUser.getUserId());
            examRankPage.setUserName(userMap.get(examUser.getUserId()).getUserName());
            examRankPages.add(examRankPage);

            if (userId.equals(examUser.getUserId())) {
                examRankVo.setMyRank(examRankPage.getRank());
                examRankVo.setMyScore(examRankPage.getScore());
            }
        }
        examRankVo.setExamRankPage(examRankPages);
        examRankVo.setSubjectId(subjectId);
        examRankVo.setSubjectName(subject.getSubjectName());
        return examRankVo;
    }
}
