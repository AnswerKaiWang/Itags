package com.lytips.ITags.repository;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import com.lytips.ITags.entity.UserCondition;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.query.FollowQuery;

public class UserProvider {
	public String updateUser(final UserInfo userInfo) {
		return new SQL() {
			{
				UPDATE("t_user_info");
				if (userInfo.getSex() != null){
					SET("sex = #{sex}");
				}
				if (StringUtils.isNotBlank(userInfo.getAddress())){
					SET("address = #{address}");
				}
				if (StringUtils.isNotBlank(userInfo.getPhone())){
					SET("phone = #{phone}");
				}
				if (StringUtils.isNotBlank(userInfo.getHead())){
					SET("head = #{head}");
				}
				if (StringUtils.isNotBlank(userInfo.getContent())){
					SET("content = #{content}");
				}
				if (StringUtils.isNotBlank(userInfo.getUserName())){
					SET("user_name = #{userName}");
				}
				if (StringUtils.isNotBlank(userInfo.getDomainName())){
					SET("domain_name = #{domainName}");
				}
				if (StringUtils.isNotBlank(userInfo.getBirthday())){
					SET("birthday = #{birthday}");
				}
				if (StringUtils.isNotBlank(userInfo.getEmail())){
					SET("email = #{email}");
				}
				if (StringUtils.isNotBlank(userInfo.getBlood())){
					SET("blood = #{blood}");
				}
				if (StringUtils.isNotBlank(userInfo.getQq())){
					SET("qq = #{qq}");
				}
				if (StringUtils.isNotBlank(userInfo.getTrueName())){
					SET("true_name = #{trueName}");
				}
				WHERE("user_id = #{userId}");
			}
		}.toString();
	}
	
	public String updateUserCondition(final UserCondition userCondition) {
		return new SQL() {
			{
				UPDATE("t_user_info");
				if(null != userCondition.getUpDynamicCount()) {
					if(userCondition.getUpDynamicCount()) {
						SET("dynamic_count = dynamic_count + 1");
					}else {
						SET("dynamic_count = dynamic_count - 1");
					}
				}
				if(null != userCondition.getUpFollowCount()) {
					if(userCondition.getUpFollowCount()) {
						SET("follow_count = follow_count + 1");
					}else {
						SET("follow_count = follow_count - 1");
					}
				}
				if(null != userCondition.getUpFollowedCount()) {
					if(userCondition.getUpFollowedCount()) {
						SET("followed_count = followed_count + 1");
					}else {
						SET("followed_count = followed_count - 1");
					}
				}
				WHERE(" user_id = #{userId}");
			}
		}.toString();
	}	
	
	public String queryUserPics(Integer userId, String type) {
		StringBuffer sb = new StringBuffer("select imgs from t_msg where state = 1 and user_id = ");
		sb.append(userId);
		if(type.equals("other")) {
			sb.append(" and visibility = 1");
		}
		return sb.toString();
	}
	
	public String queryFollowCount(FollowQuery followQuery) {
		StringBuffer sb = new StringBuffer("SELECT count(1) as count from t_user_relation a ,t_user_info b where");
			sb.append(" a.state = 1");
		if(followQuery.getType().equals("follow")) {
			sb.append(" and a.user_id = ").append(followQuery.getUserId())
				.append(" and a.follow_id = b.user_id");
		} else {
			sb.append(" and a.follow_id = ").append(followQuery.getUserId())
				.append(" and a.user_id = b.user_id");
		}
		if(followQuery.getSex() != null) {
			sb.append(" and b.sex = ").append(followQuery.getSex());
		}
		String ageStr = followQuery.getAgeStr();
		if(StringUtils.isNoneEmpty(ageStr)) {
			switch (ageStr) {
			case "60前":
				sb.append(" and b.birthday < 1960");
				break;
			case "60后":
				sb.append(" and b.birthday < 1970 and b.birthday >= 1960");
				break;
			case "70后":
				sb.append(" and b.birthday < 1980 and b.birthday >= 1970");
				break;
			case "80后":
				sb.append(" and b.birthday < 1990 and b.birthday >= 1980");
				break;
			case "90后":
				sb.append(" and b.birthday < 2000 and b.birthday >= 1990");
				break;
			case "00后":
				sb.append(" and 2000 <= b.birthday");
				break;
			case "未填写":
				sb.append(" and ISNULL(b.birthday)");
				break;
			}
		}
		return sb.toString();
	}
	
	public String queryAddress(FollowQuery followQuery) {
		StringBuffer sb = new StringBuffer("SELECT address from t_user_relation a ,t_user_info b where");
		sb.append(" a.state = 1 and address IS NOT NULL");
		if(followQuery.getType().equals("follow")) {
			sb.append(" and a.user_id = ").append(followQuery.getUserId())
				.append(" and a.follow_id = b.user_id");
		} else {
			sb.append(" and a.follow_id = ").append(followQuery.getUserId())
				.append(" and a.user_id = b.user_id");
		}
		return sb.toString();
	}
	
	public String queryAgeData(FollowQuery followQuery) {
		StringBuffer sb = new StringBuffer("MAX( CASE WHEN t_age.ageStr = '_60y' THEN "
			+ "t_age.count else 0 END) as _60y, MAX( CASE WHEN t_age.ageStr = '_60' THEN "
			+ "t_age.count else 0 END) as _60, MAX( CASE WHEN t_age.ageStr = '_70y' THEN "
			+ "t_age.count else 0 END) as _70y, MAX( CASE WHEN t_age.ageStr = '_80y' THEN "
			+ "t_age.count else 0 END) as _80y, MAX( CASE WHEN t_age.ageStr = '_90y' THEN "
			+ "t_age.count else 0 END) as _90y, MAX( CASE WHEN t_age.ageStr = '_00y' THEN "
			+ "t_age.count else 0 END) as _00y, MAX( CASE WHEN t_age.ageStr = '未填写' THEN "
			+ "t_age.count else 0 END) as 'unWrite' FROM(SELECT ageStr, COUNT(1) AS count FROM "
			+ "(SELECT(CASE WHEN b.birthday < 1960 THEN '_60' WHEN b.birthday >= 1960 "
			+ "AND b.birthday < 1970 THEN'_60y' WHEN b.birthday >= 1970 AND "
			+ "b.birthday < 1980 THEN '_70y' WHEN b.birthday >= 1980 AND "
			+ "b.birthday < 1990 THEN '_80y' WHEN b.birthday >= 1990 AND "
			+ "b.birthday < 2000 THEN '_90y' WHEN b.birthday >= 2000 THEN '_00y' "
			+ "ELSE '未填写' END) ageStr FROM t_user_relation a,t_user_info b where a.state = 1");
		StringBuffer str;
		if(followQuery.getType().equals("follow")) {
			str = new StringBuffer("select 'follow' as type, '" + followQuery.getUserId() + "' as userId,");
			str.append(sb);
			str.append(" and a.user_id = ").append(followQuery.getUserId())
				.append(" and a.follow_id = b.user_id");
		} else {
			str = new StringBuffer("select 'followed' as type, '" + followQuery.getUserId() + "' as userId,");
			str.append(sb);
			str.append(" and a.follow_id = ").append(followQuery.getUserId())
				.append(" and a.user_id = b.user_id");
		}
		str.append(") age_temp GROUP BY age_temp.ageStr) t_age");
		return str.toString();
	}
	
}
