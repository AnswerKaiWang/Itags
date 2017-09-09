package com.lytips.ITags.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lytips.ITags.entity.UserInfo;

public interface TempUserRepo {
	//查询个人全部信息
	@Select("select id, user_name as userName, user_id as userId, sex, address, follow_count"
			+ " as followCount, followed_count as followedCount, phone, content, head,"
			+ " true_name as trueName, dynamic_count as dynamicCount,"
			+ " create_time as createTime, domain_name as domainName, "
			+ "birthday, email, qq, blood from t_user_info where user_id = #{userId}")
	UserInfo queryFullUserInfo(@Param(value = "userId") Integer userId);
}
