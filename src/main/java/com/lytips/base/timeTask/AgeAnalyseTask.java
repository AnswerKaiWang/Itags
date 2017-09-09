package com.lytips.base.timeTask;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.lytips.ITags.query.FollowQuery;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.ITags.utils.ITagsUtils;
import com.lytips.ITags.vo.PersonAgeVo;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class AgeAnalyseTask {
	@Autowired
	private UserRepo userRepo;
	
	/**
	 * 首次运行调用 处理所有数据库年龄分段数据
	 */
	@Test
	public void processAgeData() {
		List<Integer> ids = userRepo.queryAllUserId();
		FollowQuery followQuery = new FollowQuery();
		List<PersonAgeVo> list = new ArrayList<>();
		if(ITagsUtils.isNotEmptyList(ids)) {
			for(Integer id:ids) {
				followQuery.setUserId(id);
				followQuery.setType("follow");
				PersonAgeVo followData = userRepo.queryAgeData(followQuery);
				followData.setUserId(id);
				followQuery.setType("followed");
				PersonAgeVo followedData = userRepo.queryAgeData(followQuery);
				if(followData.getUnWrite() != null)
					list.add(followedData);
				if(followData.getUnWrite() != null)
				 list.add(followData);
			}
		}
		if(ITagsUtils.isNotEmptyList(list)) {
			userRepo.insertAgeCache(list);
		}
	}
	
	/**
	 * 通过定时任务每周日晚上 后台自动处理更新的年龄分段数据
	 */
	@Scheduled(cron="0 0 2 ? * 1")
	public void processAgeDataTask() {
		List<Integer> ids = userRepo.queryAllUserId();
		FollowQuery followQuery;
		List<PersonAgeVo> insertAgeData;
		if(ITagsUtils.isNotEmptyList(ids)) {
			insertAgeData = new ArrayList<>();
			for(Integer id:ids) {
				followQuery = new FollowQuery(id, "follow");
				saveOrUpdateAgeCache(followQuery, insertAgeData);
				followQuery.setType("followed");
				saveOrUpdateAgeCache(followQuery, insertAgeData);
			}
			if(ITagsUtils.isNotEmptyList(insertAgeData)) {
				userRepo.insertAgeCache(insertAgeData);
			}
		}
	}

	public void saveOrUpdateAgeCache(FollowQuery followQuery, List<PersonAgeVo> list) {
		PersonAgeVo follow = userRepo.queryAgeData(followQuery);
		if(follow.getUnWrite() != null) {
			Integer cacheId = userRepo.queryAgeCacheId(followQuery.getUserId(), followQuery.getType());
			if(null != cacheId) {
				userRepo.updateAgeCache(follow);
			} else {
				list.add(follow);
			}
		}
	}
	
	
}
