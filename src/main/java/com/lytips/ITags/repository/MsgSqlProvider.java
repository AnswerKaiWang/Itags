package com.lytips.ITags.repository;

import com.lytips.ITags.entity.MsgCondition;

public class MsgSqlProvider{
	
	public String updateMsgCondition(MsgCondition condition) {
		String sql = "update t_msg set ";
		if(null != condition.getUpCommentCount()) {
			if(condition.getUpCommentCount()) {
				sql += "comment_count = comment_count + 1";
			} else {
				sql += "comment_count = comment_count - 1";
			}
		}
		if(null != condition.getUpStoreCount()) {
			if(condition.getUpStoreCount()) {
				sql += "store_count = store_count + 1";
			} else {
				sql += "store_count = store_count - 1";
			}
		}
		if(null != condition.getUpTransferCount()) {
			if(condition.getUpTransferCount()) {
				sql += "transfer_count = transfer_count + 1";
			} else {
				sql += "transfer_count = transfer_count - 1";
			}
		}
		if(null != condition.getUpGoodCount()) {
			if(condition.getUpGoodCount()) {
				sql += "good_count = good_count + 1";
			} else {
				sql += "good_count = good_count - 1";
			}
		}
		sql += " where id = " + condition.getMsgId();
		return sql;
	}

	
}
