package com.lytips.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.utils.AssertUtil;

@SuppressWarnings("all")
public abstract  class BaseBusiness<T> {
	
	
	@Autowired
	private BaseRepository<T> baseDao;
	
	public Integer insert(T entity){
		return  baseDao.insert(entity);
	}
	
	public Integer insertBatch(List<T> entities){
		return baseDao.insertBatch(entities);
	}
	
	
	public T queryById(Integer id){
		AssertUtil.isTrue(null==id||id==0, "id 不能为空!");
		return baseDao.queryById(id);
	}
	
	
	public PageInfo<T> queryForPage(BaseQuery baseQuery){
		PageHelper.startPage(baseQuery.getPage(),baseQuery.getRows());
		
		List<T> list=baseDao.queryForPage(baseQuery);
		PageInfo<T> pageInfo=new PageInfo<T>(list);
 		return pageInfo;
	}
	
	
	public List<T> queryByParams(Map map){
		return baseDao.queryByParams(map);
	}
	
	public Integer update(T entity){
		return baseDao.update(entity);
	}
	
	public Integer updateBatch(Map map){
		return baseDao.updateBatch(map);
	}
	
	public Integer delete(Integer id){
		AssertUtil.isTrue(null==id||id==0, "参数非空！");
		AssertUtil.isTrue(null==queryById(id), "待删除的记录不存在!");
		return baseDao.delete(id);
	}
	
	public Integer deleteBatch(Integer[] ids){
		AssertUtil.isTrue(null==ids||ids.length==0, "待删除的记录不能为空!");
		return baseDao.deleteBatch(ids);
	}
	
	
	/**
	 * 返回当前页数据
	 * @param baseQuery
	 * @return
	 */
	public Map<String, Object> queryPageInfoByParams(BaseQuery baseQuery){
		 PageInfo<T> pageInfo= queryForPage(baseQuery);
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("total", pageInfo.getTotal());// 总记录数
		map.put("rows", pageInfo.getList());
		return map;
	}
	
	
	
	
	
	

}
