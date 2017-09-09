package com.lytips.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


@SuppressWarnings("all")
public interface BaseRepository<T>{
	
	public Integer insert(T entity) throws DataAccessException;
	
	public Integer insertBatch(List<T> entities) throws DataAccessException;
	
	public T queryById(Integer id) throws DataAccessException;
	
	public List<T> queryForPage(BaseQuery baseQuery) throws DataAccessException;
	
	public List<T> queryByParams(Map map) throws DataAccessException;
	
	
	public Integer update(T entity) throws DataAccessException;
	
	public Integer updateBatch(Map map) throws DataAccessException;
	
	
	public Integer delete(Integer id) throws DataAccessException;
	
	public Integer deleteBatch(Integer[] ids) throws DataAccessException;
	
	
	
	
	
	

}
