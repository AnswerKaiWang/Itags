package com.lytips.ITags.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lytips.ITags.entity.Msg;

public interface TempMsgRepo {
	@Select("select id, user_id as userId, content, transfer_count as transferCount, "
			+ "comment_count as commentCount, good_count as goodCount,"
			+ " state, create_time as createTime, imgs, store_count as storeCount from t_msg where id = #{msgId}")
	Msg queryById(@Param(value = "msgId") Integer msgId);
}
