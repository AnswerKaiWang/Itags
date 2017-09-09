package com.lytips.ITags.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import com.lytips.ITags.entity.Remark;
import com.lytips.ITags.entity.User;
import com.lytips.ITags.entity.UserCondition;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.entity.UserRelation;
import com.lytips.ITags.query.FollowQuery;
import com.lytips.ITags.vo.PersonAgeVo;
import com.lytips.ITags.vo.VisitVo;

public interface UserRepo {
	/**
	 * 插入登录信息
	 * @param user
	 */
	@Insert("insert into t_user(user_name, user_pwd, create_time, update_time)"
			+ " values(#{userName}, #{userPwd}, #{createTime}, #{updateTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(User user);
	
	/**
	 * 根据用户名查询个人登录信息
	 * @param userName
	 * @return
	 */
	@Select("select id, user_name as userName, user_pwd as userPwd "
			+ "from t_user where user_name = #{userName}")
	User queryUserByUserName(@Param(value = "userName")String userName);
	
	/**
	 * 查询登录信息
	 * @param id
	 * @return
	 */
	@Select("select id, user_name as userName, user_pwd as userPwd "
			+ "from t_user where id = #{id}")
	User queryById(@Param(value = "id")Integer id);
	
	//查询关注或粉丝
	@Select("select user_id from t_user_relation where follow_id = #{userId} and state = 1")
	List<Integer> queryFollowIds(@Param(value = "userId")Integer userId);
	
	/**
	 * 查询个人头像和用户名
	 * @param userId
	 * @return
	 */
	@Select("select user_id as userId, user_name as userName, head from t_user_info where user_id = #{userId}")
	UserInfo queryUserInfoById(@Param(value = "userId") Integer userId);
	
	/**
	 * 更新个人信息
	 * @param userInfo
	 */
	@UpdateProvider(type = UserProvider.class, method = "updateUser")
	void updateUserInfo(UserInfo userInfo);
	
	/**
	 * 查询个人全部信息
	 * @param userId
	 * @return
	 */
	@Select("select id, user_name as userName, user_id as userId, sex, address, follow_count"
			+ " as followCount, followed_count as followedCount, phone, content, head,"
			+ " true_name as trueName, dynamic_count as dynamicCount,"
			+ " create_time as createTime, domain_name as domainName, "
			+ "birthday, email, qq, blood, personBac from t_user_info where user_id = #{userId}")
	UserInfo queryFullUserInfo(@Param(value = "userId") Integer userId);
	
	/**
	 * 插入个人信息
	 * @param userInfo
	 */
	@Insert("insert into t_user_info(user_id, user_name, follow_count, followed_count,"
			+ "dynamic_count, create_time, update_time,head, sex, personBac) values(#{userId}, #{userName}, 0, 0, 0,"
			+ "NOW(), NOW(), #{head}, 0, #{personBac})")
	void insertUserInfo(UserInfo userInfo);
	
	/**
	 * 查询是否关注
	 * @param userId
	 * @param followId
	 * @return
	 */
	@Select("select id from t_user_relation where user_id = #{userId} and follow_id = #{followId} and state = 1")
	Integer isFollow(@Param(value = "userId") Integer userId, @Param(value = "followId") Integer followId);
	
	/**
	 * 添加关注索引
	 * @param userRelation
	 */
	@Insert("insert into t_user_relation(user_id, follow_id, create_time, update_time, state)"
			+ " values(#{userId}, #{followId}, NOW(), NOW(), 1)")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertUserRelation(UserRelation userRelation);
	
	//取消关注
	@Update("update t_user_relation set state = 0 where id = #{id}")
	void cancelFollow(@Param(value = "id") Integer id);
	
	/**
	 * 更新关注数 粉丝数 动态数
	 * @param userCondition
	 */
	@UpdateProvider(type = UserProvider.class, method = "updateUserCondition")
	void updateUserCondition(UserCondition userCondition);
	
	/**
	 * 查询关注
	 * @param userId
	 * @return
	 */
	@Select("select follow_id from t_user_relation where user_id = #{userId} and state = 1")
	@Results({
		@Result(column = "follow_id", property = "userInfo", one=@One
				(select = "com.lytips.ITags.repository.TempUserRepo.queryFullUserInfo")),
		@Result(column = "follow_id", property = "followId"),
	})
	List<UserRelation> queryFollowUsers(@Param(value = "userId") Integer userId);
	
	/**
	 * 查询粉丝
	 * @param userId
	 * @return
	 */
	@Select("select user_id from t_user_relation where follow_id = #{userId} and state = 1")
	@Results({
		@Result(column = "user_id", property = "userInfo", one=@One
				(select = "com.lytips.ITags.repository.TempUserRepo.queryFullUserInfo"))
	})
	List<UserRelation> queryFollowedUsers(@Param(value = "userId") Integer userId);
	
	@Update("update t_user set user_pwd = #{newPwd} where id = #{id}") 
	void updateUserPwd(@Param(value = "id")Integer id, @Param(value = "newPwd") String newPwd);
	
	/**
	 * 查询个人全部信息
	 * @param userName
	 * @return
	 */
	@Select("select id, user_name as userName, user_id as userId, follow_count"
			+ " as followCount, followed_count as followedCount, head"
			+ " , dynamic_count as dynamicCount,sex from t_user_info where user_name like CONCAT('%', #{userName}, '%')")
	List<UserInfo> queryByUserName(@Param(value = "userName") String userName);
	
	/**
	 * 查询备注
	 * @param userId
	 * @param personUserId
	 * @return
	 */
	@Select("select id, user_id as userId, person_user_id as personUserId, remark from"
			+ " t_remark where user_id = #{userId} and person_user_id = #{personUserId}")
	Remark queryRemarkById(@Param(value = "userId") Integer userId, @Param(value = "personUserId") Integer personUserId);
	
	/**
	 * 更新备注信息
	 * @param id
	 * @param remark
	 */
	@Update("update t_remark set remark = #{remark} where id = #{id}")
	void updateRemark(@Param(value = "id") Integer id, @Param(value = "remark") String remark);
	
	/**
	 * 添加备注信息
	 * @param remark
	 */
	@Insert("insert into t_remark(user_id, person_user_id, remark) values(#{userId},"
			+ "#{personUserId}, #{remark})")
	void addRemark(Remark remark);
	
	@Update("update t_user_info set phone = #{phone} where user_id = #{userId}")
	void bindPhone(@Param(value = "userId") Integer userId, @Param(value = "phone")String phone);
	
	@Select("select user_id from t_user_info where phone = #{phone}")
	Integer queryByPhone(@Param(value = "phone")String phone);
	
	@Update("update t_user_info set phone = NULL where user_id = #{userId}")
	void cancelPhoneBind(@Param(value = "userId") Integer userId);
	
	
	//更新个人背景墙
	@Update("update t_user_info set personBac = #{personBac} where user_id = #{userId}")
	void modifyPersonBac(@Param(value = "userId") Integer userId, @Param(value = "personBac")String personBac);
	
	//查询个人相册
	@SelectProvider(type = UserProvider.class, method = "queryUserPics")
	List<String> queryUserPics(Integer userId, String type);
	
	//查询关注或粉丝详情
	@SelectProvider(type = UserProvider.class, method = "queryFollowCount")
	Integer queryFollowCount(FollowQuery followQuery);
	
	//插入访问记录
	@Insert("insert into t_visit(user_id, person_user_id, create_time) values(#{userId},"
			+ " #{personUserId}, CURDATE())")
	void insertVisit(@Param(value = "userId") Integer userId, @Param(value = "personUserId") Integer personUserId);
	
	//查询访问记录
	@Select("SELECT count(*) as count, create_time as createTime FROM t_visit WHERE"
			+ " person_user_id = #{userId} AND SUBSTRING(create_time, 1, 7) "
			+ "= #{date} GROUP BY create_time ")
	List<VisitVo> queryVisitData(@Param(value = "userId") Integer userId,@Param(value = "date") String date);
	
	
	//查询关注或粉丝地址详情
	@SelectProvider(type = UserProvider.class, method = "queryAddress")
	List<String> queryAddress(FollowQuery followQuery);
	
	//查询粉丝或关注年龄分布 连表查询
	@SelectProvider(type = UserProvider.class, method = "queryAgeData")
	PersonAgeVo queryAgeData(FollowQuery followQuery);
	
	//查询所有用户id
	@Select("select user_id from t_user_info")
	List<Integer> queryAllUserId();
	
	//插入年龄分布自定义缓存
	void insertAgeCache(List<PersonAgeVo> list);
	
	//查询粉丝或关注年龄分布 age表缓存查询
	@Select("select user_id as userId, _60, _60y, _70y, _80y, _90y, _00y, unWrite, type from"
			+ " t_age_cache where user_id = #{userId} and type = #{type}")
	PersonAgeVo queryAgeCacheData(FollowQuery followQuery);
	
	//查询age自定义缓存中id
	@Select("select id from t_age_cache where user_id = #{userId} and type = #{type}")
	Integer queryAgeCacheId(@Param(value = "userId") Integer userId, @Param(value = "type") String type);
	
	//更新agecache数据
	@Update("update t_age_cache set _60 = #{_60}, _60y = #{_60y}, _70y = #{_70y},"
 		 + "_80y = #{_80y}, _90y = #{_90y}, _00y = #{_00y}, unWrite = #{unWrite}"
 		+ " where user_id = #{userId} and type = #{type}")
	void updateAgeCache(PersonAgeVo personAgeVo);
	

	
	
	
}
