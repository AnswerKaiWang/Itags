package com.lytips.ITags.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lytips.ITags.entity.Chat;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.query.ChatQuery;

public interface ChatRepo {
	
	List<UserInfo> queryLinkMan(Integer userId);
	
	List<Chat> queryByParams(ChatQuery chatQuery);
	
	List<Chat> queryByParams2(ChatQuery chatQuery);
	
	@Insert("insert into t_chat(user_id, person_user_id, content, create_time, type)"
			+ " values(#{userId}"
			+ " ,#{personUserId}, #{content}, #{createTime}, 0)")
	void insertChat(Chat chat);
	
	@Select("SELECT count(1) from t_chat where person_user_id = #{userId} and"
			+ " user_id = #{personUserId} and type = 0 ")
	Integer queryUnReadCount(@Param(value = "userId") Integer userId, 
			@Param(value = "personUserId") Integer personUserId);
	
	@Update("update t_chat set type = 1  where person_user_id = #{userId} and"
			+ " user_id = #{personUserId}")
	void setIsRead(@Param(value = "userId") Integer userId, 
			@Param(value = "personUserId") Integer personUserId);
	
	
	
}
