package com.lytips.ITags.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import com.lytips.ITags.entity.ExtraMsg;
import com.lytips.ITags.entity.Msg;
import com.lytips.ITags.entity.MsgCondition;
import com.lytips.ITags.entity.MsgIndex;
import com.lytips.ITags.entity.MsgRelation;
import com.lytips.ITags.query.MsgQuery;
import com.lytips.ITags.query.StateQuery;

public interface MsgRepo {
	//插入新消息
	@Insert("insert into t_msg(user_id, content, transfer_count, comment_count, good_count, state"
			+ ", create_time, update_time, imgs, store_count, visibility) values(#{userId}, #{content}, #{transferCount},"
			+ "#{commentCount}, #{goodCount}, #{state}, #{createTime}, #{updateTime}, #{imgs}, #{storeCount}, #{visibility})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertMsg(Msg msg);
	
	//插入消息索引 方便后续根据userId查询最新消息
	@Insert("insert into t_msg_index(user_id, author_id, msg_id, publish_time, state)"
			+ " values(#{userId}, #{authorId}, #{msgId}, #{publishTime}, 1)")
	void insertMsgIndex(MsgIndex msgIndex);
		
	//feed拉消息 一对一 消息索引内部根据id查询对应的具体消息和用户信息
	@Select("select * from t_msg_index where user_id = #{userId} and state = 1 order by publish_time desc")
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "user_id", property = "userId"),
		@Result(column = "author_id", property = "authorId"),
		@Result(column = "msg_id", property = "msgId"),
		@Result(column = "msg_id", property = "msg",one=@One(
				select = "com.lytips.ITags.repository.TempMsgRepo.queryById")),
		@Result(column = "author_id", property = "author",one=@One(
				select = "com.lytips.ITags.repository.UserRepo.queryUserInfoById")),
	})
	List<MsgIndex> queryMsgIndexsByUserId(MsgQuery msgQuery);
	

	List<Msg> queryPersonMsg(MsgQuery msgQuery);
	
	//根据id查询消息
	@Select("select id, user_id as userId, content, transfer_count as transferCount, "
			+ "comment_count as commentCount, good_count as goodCount,"
			+ " state, create_time as createTime, imgs, store_count as storeCount from t_msg where id = #{msgId}")
	Msg queryById(@Param(value = "msgId") Integer msgId);
	
	//根据id查询 回复 评论 信息
	@Select("select id, msg_id as msgId, content from t_extra_msg where id = #{id}")
	ExtraMsg queryExtraMsgById(@Param(value = "id") Integer id);
	
	//插入评论 回复消息
	@Insert("insert into t_extra_msg(msg_id, content) values(#{msgId}, #{content})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertExtraMsg(ExtraMsg extraMsg);
	
	//插入消息索引，可根据消息id查询对应的评论回复
	@Insert("insert into t_msg_msg_relation(user_id, msg_id, reference_user_id, reference_msg_id,"
			+ "type, state, create_time, update_time,is_read) values(#{userId}, #{msgId}, #{referenceUserId}"
			+ ", #{referenceMsgId}, #{type}, #{state}, #{createTime}, #{updateTime}, 0)")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertMsgRelation(MsgRelation msgRelation);	
	
	//根据消息id查询评论回复
	@Select("select * from t_msg_msg_relation where state = 1 and type = #{type}"
			+ " and msg_id = #{msgId} order by create_time desc")
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "user_id", property = "userId"),
		@Result(column = "user_id", property = "user",one=@One(
				select = "com.lytips.ITags.repository.UserRepo.queryUserInfoById")),
		@Result(column = "reference_user_id", property = "referenceUser",one=@One(
				select = "com.lytips.ITags.repository.UserRepo.queryUserInfoById")),
		@Result(column = "msg_id", property = "msgId"),
		@Result(column = "reference_user_id", property = "referenceUserId"),
		@Result(column = "type", property = "type"),
		@Result(column = "reference_msg_id", property = "referenceMsgId"),
		@Result(column = "reference_msg_id", property = "extraMsg", one=@One(
				select="com.lytips.ITags.repository.MsgRepo.queryExtraMsgById")),
		@Result(column = "state", property = "state"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")
	})
	List<MsgRelation> queryMsgRelationByMsgId(@Param(value = "msgId") Integer msgId, @Param(value = "type") Integer type);
	
	//更新msg各状态 点赞 评论等
	
	@UpdateProvider(type = MsgSqlProvider.class, method = "updateMsgCondition")
	void updateMsgCondition(MsgCondition condition);
	
	//查询是否点赞 或收藏
	@Select("select id from t_msg_msg_relation where reference_user_id = #{userId} and msg_id = "
			+ " #{msgId} and state = 1 and type = #{type}")
	Integer queryIsGoodOrStore(StateQuery stateQuery);
	
	//取消赞或收藏
	@Update("update t_msg_msg_relation set state = 0 where id = #{id}")
	void setMsgUnabled(@Param(value = "id")Integer id);
	
	//查询未读消息数量
	@Select("select count(1) from t_msg_msg_relation where user_id = #{userId} and is_read = 0")
	Integer queryNoticeCount(@Param(value = "userId") Integer userId);
	
	//查询消息中心
	//根据消息id查询评论回复
		@Select("select * from t_msg_msg_relation where state = 1"
				+ " and user_id = #{userId} order by create_time desc")
		@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "user_id", property = "userId"),
			@Result(column = "user_id", property = "user",one=@One(
					select = "com.lytips.ITags.repository.UserRepo.queryUserInfoById")),
			@Result(column = "reference_user_id", property = "referenceUser",one=@One(
					select = "com.lytips.ITags.repository.UserRepo.queryUserInfoById")),
			@Result(column = "msg_id", property = "msgId"),
			@Result(column = "reference_user_id", property = "referenceUserId"),
			@Result(column = "type", property = "type"),
			@Result(column = "reference_msg_id", property = "referenceMsgId"),
			@Result(column = "reference_msg_id", property = "extraMsg", one=@One(
					select="com.lytips.ITags.repository.MsgRepo.queryExtraMsgById")),
			@Result(column = "state", property = "state"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime")
		})
	List<MsgRelation> queryNotice(@Param(value = "userId") Integer userId);
	
	//更新已读
	@Update("update t_msg_msg_relation set is_read = 1 where user_id = #{userId}")
	void updateIsRead(@Param(value = "userId") Integer userId);
	
	//查询回复消息根消息ID
	@Select("select msg_id from t_extra_msg where id = #{msgId}")
	Integer queryRootMsgId(@Param(value = "msgId") Integer msgId);
	
	@Update("update t_msg_index set state = 0 where id = #{id}")
	void hideDynamic(@Param(value = "id") Integer id);
}
