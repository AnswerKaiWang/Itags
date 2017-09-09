package com.lytips.ITags.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lytips.ITags.dto.UserDto;
import com.lytips.ITags.entity.Remark;
import com.lytips.ITags.entity.User;
import com.lytips.ITags.entity.UserCondition;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.entity.UserRelation;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.query.FollowQuery;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.ITags.utils.AssertUtil;
import com.lytips.ITags.utils.ITagsUtils;
import com.lytips.ITags.utils.Md5Util;
import com.lytips.ITags.utils.UserBase64;
import com.lytips.ITags.vo.AddressVo;
import com.lytips.ITags.vo.FollowVo;
import com.lytips.ITags.vo.PersonAgeVo;
import com.lytips.ITags.vo.VisitVo;

@Service
public class UserBiz {
	@Autowired UserRepo userRepo;

	public MessageModel saveOrUpateUser(UserDto userDto, String currentCheckMark) {
		if(userDto.getId() == null) {
			userDto.setCreateTime(new Date());
			userDto.setUpdateTime(new Date());
			checkParam(userDto.getUserName(), userDto.getUserPwd(), userDto.getConfirmPassword(), userDto.getCheckMark());
			AssertUtil.isTrue(!currentCheckMark.equals(userDto.getCheckMark()), "验证码输入不正确！");
			AssertUtil.isTrue(0 == queryUserNameIsVaiable(userDto.getUserName()).getResultCode(), "用户名已存在！");
			userDto.setUserPwd(Md5Util.encode(userDto.getUserPwd()));
			UserInfo userInfo = new UserInfo();
			userRepo.insert(userDto);
			userInfo.setHead("/ITags/images/defaultHead.png");
			userInfo.setUserId(userDto.getId());
			userInfo.setUserName(userDto.getUserName());
			userRepo.insertUserInfo(userInfo);
		}
		return new MessageModel();
	}

	private void checkParam(String userName, String userPwd, String confrimPassword, String checkMark) {
		AssertUtil.isTrue(StringUtils.isEmpty(userName), "用户名不能为空！");
		AssertUtil.isTrue(StringUtils.isEmpty(userPwd), "密码不能为空！");
		AssertUtil.isTrue(StringUtils.isEmpty(confrimPassword), "确认密码不能为空！");
		AssertUtil.isTrue(StringUtils.isEmpty(checkMark), "验证码不能为空！");
		AssertUtil.isTrue(!userPwd.equals(confrimPassword), "两次密码输入不一致！");
	}
	
	public MessageModel queryUserNameIsVaiable(String userName) {
		User user = userRepo.queryUserByUserName(userName);
		MessageModel messageModel = new MessageModel();
		if(null != user) {
			messageModel.setMsg("此用户名已存在，请更换用户名后再试！");
			messageModel.setResultCode(0);
		}
		return messageModel;
	}

	public MessageModel userLogin(User user) {
		User temp = userRepo.queryUserByUserName(user.getUserName());
		MessageModel messageModel = new MessageModel();
		AssertUtil.isTrue(StringUtils.isEmpty(user.getUserName()), "用户名不能为空！");
		AssertUtil.isTrue(StringUtils.isEmpty(user.getUserPwd()), "密码不能为空！");
		AssertUtil.isTrue(null == temp, "此用户名不存在！");
		AssertUtil.isTrue(!Md5Util.encode(user.getUserPwd()).equals(temp.getUserPwd()), "用户名或密码错误！");
		temp.setUserName(UserBase64.encode(user.getUserName()));
		temp.setUserPwd(UserBase64.encode(user.getUserPwd()));
		messageModel.setResult(temp);
		return messageModel;
	}

	public User queryById(Integer id) {
		return userRepo.queryById(id);
	}

	public MessageModel updateUserInfo(UserInfo userInfo) {
		userRepo.updateUserInfo(userInfo);
		return new MessageModel();
	}

	public MessageModel saveOrUpdateUserRelation(UserRelation userRelation) {
		MessageModel messageModel = new MessageModel();
		UserCondition userCondition;
		if(userRelation.getId() == null) {
			userRepo.insertUserRelation(userRelation);
			messageModel.setResult(userRelation.getId());
			userCondition = new UserCondition(userRelation.getUserId(), true, null, null);
			userRepo.updateUserCondition(userCondition);
			userCondition = new UserCondition(userRelation.getFollowId(), null, true, null);
			userRepo.updateUserCondition(userCondition);
		} else {
			userRepo.cancelFollow(userRelation.getId());
			userCondition = new UserCondition(userRelation.getUserId(), false, null, null);
			userRepo.updateUserCondition(userCondition);
			userCondition = new UserCondition(userRelation.getFollowId(), null, false, null);
			userRepo.updateUserCondition(userCondition);
		}
		return messageModel;
	}

	public MessageModel modifyPassword(String userName, String oldPwd, String newPwd) {
		AssertUtil.isTrue(StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(userName), "所填项不能为空！");
		User user = userRepo.queryUserByUserName(userName);
		AssertUtil.isTrue(null == user, "此用户不存在！");
		AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原密码或账号不正确！");
		userRepo.updateUserPwd(user.getId(), Md5Util.encode(newPwd));
		return new MessageModel();
	}

	public MessageModel addRemark(Remark remark) {
		Remark temp = userRepo.queryRemarkById(remark.getUserId(), remark.getPersonUserId());
		if(temp != null) {
			userRepo.updateRemark(temp.getId(), remark.getRemark());
		} else {
			userRepo.addRemark(remark);
		}
		return new MessageModel();
	}

	public MessageModel bindPhone(Integer userId, String phone, Integer verifyCode, Integer sessionVerifyCode) {
		AssertUtil.isTrue(StringUtils.isEmpty(phone), "手机号码不能为空！");
		AssertUtil.isTrue(null != userRepo.queryByPhone(phone), "该手机号已被绑定！");
		AssertUtil.isTrue(null == verifyCode, "验证码不能为空！");
		AssertUtil.isTrue(null == sessionVerifyCode, "验证码已过期，请重新获取！");
		AssertUtil.isTrue(!sessionVerifyCode.equals(verifyCode) , "验证码不正确！");
		userRepo.bindPhone(userId, phone);
		return new MessageModel();
	}

	public MessageModel cancelPhoneBind(Integer userId, Integer verifyCode, Integer sessionVerifyCode) {
		AssertUtil.isTrue(null == sessionVerifyCode, "验证码已过期，请重新获取！");
		AssertUtil.isTrue(!sessionVerifyCode.equals(verifyCode) , "验证码不正确！");
		userRepo.cancelPhoneBind(userId);
		return new MessageModel();
	}

	public MessageModel modifyPwdByVerifyCode(Integer userId, Integer verifyCode, Integer sessionVerifyCode, String userPwd) {
		AssertUtil.isTrue(null == sessionVerifyCode, "验证码已过期，请重新获取！");
		AssertUtil.isTrue(!sessionVerifyCode.equals(verifyCode) , "验证码不正确！");
		AssertUtil.isTrue(StringUtils.isEmpty(userPwd), "密码不能为空");
		userRepo.updateUserPwd(userId, Md5Util.encode(userPwd));
		return new MessageModel();
	}

	public MessageModel modifyPersonBac(String personBac, Integer userId) {
		userRepo.modifyPersonBac(userId, personBac);
		return new MessageModel();
	}

	public Map<String, Object> queryPersonAnalyse(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FollowVo follow = new FollowVo(userRepo.queryFollowCount(new FollowQuery(userId, 0, "follow")), userRepo.queryFollowCount(new FollowQuery(userId, 1, "follow")));
		map.put("follow", follow);
		FollowVo followed = new FollowVo(userRepo.queryFollowCount(new FollowQuery(userId, 0, "followed")), userRepo.queryFollowCount(new FollowQuery(userId, 1, "followed")));
		map.put("followed", followed);
		return map;
	}
	
	public Map<String, Object> queryAgeAnalyse(Integer userId) {
		FollowQuery followQuery = new FollowQuery(userId, null, "follow");
		PersonAgeVo followAge = processAge(followQuery);
		followQuery.setType("followed");
		PersonAgeVo followedAge = processAge(followQuery);
		Map<String, Object> map = new HashMap<>();
		map.put("followAge", followAge);
		map.put("followedAge", followedAge);
		return map;
	}
	
	public PersonAgeVo processAge(FollowQuery followQuery) {
		PersonAgeVo ageVo = userRepo.queryAgeCacheData(followQuery);
		if(ageVo == null) {
			List<PersonAgeVo> list = new ArrayList<>();
			ageVo = userRepo.queryAgeData(followQuery);
			list.add(ageVo);
			userRepo.insertAgeCache(list);
		}
		return ageVo;
	}

	public Map<String, Integer> queryVisitData(Integer userId, String date) {
		List<VisitVo> list = userRepo.queryVisitData(userId, date);
		Map<String, Integer> map = new HashMap<>();
		if(ITagsUtils.isNotEmptyList(list)) {
			for(VisitVo ex:list) {
				map.put((ex.getCreateTime()).substring(8), ex.getCount());
			}
		}
		return map;
	}
	
	public Map<String, List<AddressVo>> queryAddress(Integer userId) {
		FollowQuery followQuery = new FollowQuery(userId, null, "follow");
		Map<String, List<AddressVo>> map = new HashMap<>();
		map.put("follow", getMapAddress(followQuery));
		followQuery.setType("followed");
		map.put("followed", getMapAddress(followQuery));
		
		return map;
	}
	
	public List<AddressVo> getMapAddress(FollowQuery followQuery) {
		List<String> list = userRepo.queryAddress(followQuery);
		Map<String, Integer> map = new HashMap<>();
		List<AddressVo> addressList = new ArrayList<>();
		String province;
		AddressVo addressVo = null;
		if(ITagsUtils.isNotEmptyList(list)) {
			for(String ex:list) {
				province = getProvince(ex);
				if(map.containsKey(province)) {
					Integer index = map.get(province);
					addressList.get(index).upCount();;
				} else {
					map.put(province, addressList.size());
					addressVo = new AddressVo(province, 1);
					addressList.add(addressVo);
				}
			}
		}
		return addressList;
	}
	
	public String getProvince(String address) {
		String province = address.split(" ")[0].substring(0, 2);
		if(province.equals("黑龙"))
			province += "江";
		if(province.equals("内蒙"))
			province += "古";
		return province;
	}
}
