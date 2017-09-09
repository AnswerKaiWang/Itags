package com.lytips.ITags.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lytips.ITags.business.UserBiz;
import com.lytips.ITags.constant.ItagsConstant;
import com.lytips.ITags.dto.UserDto;
import com.lytips.ITags.entity.Remark;
import com.lytips.ITags.entity.User;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.entity.UserRelation;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.ITags.utils.OssUtils;
import com.lytips.ITags.vo.AddressVo;
import com.lytips.ITags.vo.PersonBacVo;
import com.lytips.ITags.vo.UserVo;
import com.lytips.base.BaseController;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private MsgRepo msgRepo;
	
	//注册账户
	@RequestMapping("registerUser")
	@ResponseBody
	public MessageModel registerUser(UserDto userDto, HttpServletRequest httpServletRequest) {
		String currentCheckMark = (String) httpServletRequest.getSession().getAttribute("KAPTCHA_SESSION_KEY");
		return userBiz.saveOrUpateUser(userDto, currentCheckMark);
	}
	
	//查询用户名是否合法
	@ResponseBody
	@RequestMapping("queryUserNameIsVailable")
	public MessageModel queryUserNameIsVailable(String userName) {
		return userBiz.queryUserNameIsVaiable(userName);
	}
	
	//用户登录操作
	@RequestMapping("userLogin")
	@ResponseBody
	public MessageModel userLogin(User user, HttpServletRequest request) {
		MessageModel messageModel = userBiz.userLogin(user);
		User temp = (User) messageModel.getResult();
		UserInfo userInfo = userRepo.queryFullUserInfo(temp.getId());
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(userInfo, userVo);
		userVo.setNoticeCount(msgRepo.queryNoticeCount(temp.getId()));
		request.getSession().setAttribute("userInfo", userVo);
		request.getSession().setAttribute("userId", userInfo.getUserId());
		return messageModel;
	}
	
	//注销个人账户
	@GetMapping("loginOut")
	@ResponseBody
	public MessageModel loginOut(HttpServletRequest request) {
		request.getSession().setAttribute("userId", null);
		return new MessageModel();
	}
	
	//修改个人头像
	@RequestMapping("modifyHead")
	@ResponseBody
	public MessageModel modifyHead(String img, HttpServletRequest request) {
		MessageModel messageModel = new MessageModel();
		BASE64Decoder decoder = new BASE64Decoder();  
        //Base64解码  
	    byte[] b;
		try {
			b = decoder.decodeBuffer(img.substring(23));
			for(int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			String trueFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
			// 设置存放图片文件的路径
			String url = OssUtils.upLoadByte(trueFileName, b);
		    Integer userId = (Integer) request.getSession().getAttribute("userId");
		    UserInfo userInfo = new UserInfo();
		    userInfo.setUserId(userId);
		    userInfo.setHead(url);
		    userRepo.updateUserInfo(userInfo);
		    UserVo userVo = (UserVo) request.getSession().getAttribute("userInfo");
		    userVo.setHead(url);
		    request.getSession().setAttribute("userInfo", userVo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageModel;  
	}
	
	//更新个人信息
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public MessageModel updateUserInfo(UserInfo userInfo) {
		return userBiz.updateUserInfo(userInfo);
	}
	
	//添加修改关注
	@RequestMapping("saveOrUpdateUserRelation")
	@ResponseBody
	public MessageModel saveOrUpdateUserRelation(UserRelation userRelation) {
		return userBiz.saveOrUpdateUserRelation(userRelation);
	}
	
	//修改密码
	@RequestMapping("modifyPassword")
	@ResponseBody
	public MessageModel modifyPassword(String userName, String oldPwd, String newPwd) {
		return userBiz.modifyPassword(userName, oldPwd, newPwd);
	}
	
	//添加备注信息
	@RequestMapping("addRemark")
	@ResponseBody
	public MessageModel addRemark(Remark remark) {
		return userBiz.addRemark(remark);
	}
	
	//绑定手机号码
	@RequestMapping("bindPhone")
	@ResponseBody
	public MessageModel bindPhone(Integer userId, HttpServletRequest req, Integer verifyCode, String phone) {
		Integer sessionVerifyCode = (Integer) req.getSession().getAttribute(ItagsConstant.BIND_VERIFY_CODE);
		return userBiz.bindPhone(userId, phone, verifyCode, sessionVerifyCode);
		
	}
	
	//通过验证码取消手机绑定
	@RequestMapping("cancelPhoneBind")
	@ResponseBody
	public MessageModel cancelPhoneBind(Integer userId, Integer verifyCode, HttpServletRequest req) {
		Integer sessionVerifyCode = (Integer) req.getSession().getAttribute(ItagsConstant.CANCEL_BIND_VERIFY_CODE);
		return userBiz.cancelPhoneBind(userId, verifyCode, sessionVerifyCode);
	}
	
	//通过验证码修改密码
	@RequestMapping("modifyPwdByVerifyCode")
	@ResponseBody
	public MessageModel modifyPwdByVerifyCode(Integer userId, Integer verifyCode, String userPwd, HttpServletRequest req) {
		Integer sessionVerifyCode = (Integer) req.getSession().getAttribute(ItagsConstant.MODIFY_PWD_VERIFY_CODE);
		return userBiz.modifyPwdByVerifyCode(userId, verifyCode, sessionVerifyCode, userPwd);
	}
	
	//登录中
	@RequestMapping("logining")
	public String logining() {
		return "logining";
	}
	
	//修改个人背景
	@ResponseBody
	@RequestMapping("modifyPersonBac")
	public MessageModel modifyPersonBac(String personBac, Integer userId) {
		return userBiz.modifyPersonBac(personBac, userId);
	}
	
	//查询个人关注粉丝详情
	@RequestMapping("queryPersonAnalyse")
	@ResponseBody
	public Map<String, Object> queryPersonAnalyse(Integer userId) {
		return userBiz.queryPersonAnalyse(userId);
	}
	
	//查询年龄分段
	@RequestMapping("queryAgeAnalyse")
	@ResponseBody
	public Map<String, Object> queryPersonAge(Integer userId) {
		return userBiz.queryAgeAnalyse(userId);
	}
	
	//插入访问记录
	@RequestMapping("insertVisit")
	public void insertVisit(Integer userId, Integer personUserId) {
		userRepo.insertVisit(userId, personUserId);
	}
	
	//查询访主页访问量
	@ResponseBody
	@RequestMapping("queryVisitData") 
	public Map<String, Integer> queryVisitData(Integer userId, String date) {
		return userBiz.queryVisitData(userId, date);
	}
	
	//查询地址分布
	@ResponseBody
	@RequestMapping("queryAddress") 
	public Map<String, List<AddressVo>> queryAddress(Integer userId) {
		return userBiz.queryAddress(userId);
	}
	
	//弹出资料框资料
	@ResponseBody
	@RequestMapping("queryPersonDetail")
	public PersonBacVo queryPersonDetail(Integer personUserId, Integer userId) {
		UserInfo userInfo = userRepo.queryFullUserInfo(personUserId);
		PersonBacVo bacVo = new PersonBacVo();
		BeanUtils.copyProperties(userInfo, bacVo);
		Integer follow = userRepo.isFollow(userId, personUserId);
		bacVo.setFollowId(follow);
		return bacVo;
	}
	
	
}
