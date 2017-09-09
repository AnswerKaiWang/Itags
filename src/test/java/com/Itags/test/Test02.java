package com.Itags.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 男生人数必须大于女生 否则出现晚班人数不如情况
 * @author kiTe
 * 2017年6月29日
 * 
 */
public class Test02 {
	List<Person> man = null;
	List<Person> women = null;
	
	public void setList() throws ParseException {
		man = setPerson(15, "男");
		women = setPerson(10, "女");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now = df.format(new Date());
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = df.parse(now).getTime() + 32 * 60 * 60 * 1000; //明天08:00
		List<String> white = new ArrayList<>();
		List<String> black = new ArrayList<>();
		Integer size = man.size() + women.size();
		for(int i = 0; i < size; i++) {
			long temp = time + 12 * 60 * 60 * 1000;
			if(i % 2 == 0) {
				white.add(df2.format(new Date(time)) + " -- " + df2.format(new Date(temp)));
			} else {
				black.add(df2.format(new Date(time)) + " -- " + df2.format(new Date(temp)));
			}
			time = temp;
		}
		for(int i = 0; i < women.size(); i++) {
			setDate(women, white, i);
		}
		for(int i = 0; i < man.size(); i++) {
			if(white != null && white.size() > 0) {
				setDate(man, white, i);
				continue;
			}
			setDate(man, black, i);
		}
	}
	
	public void setDate(List<Person> person, List<String> list, Integer i) {
		int random = (int)(Math.random() * list.size());
		person.get(i).setDate(list.get(random));
		list.remove(random);
	}
	
	
	public List<Person> setPerson(Integer num, String sex) {
		List<Person> list = new ArrayList<>();
		if(num > 0) {
			for(int i = 0; i < num; i++) {
				int random = (int)(Math.random() * 27) + 97;
				String name = i + "";
				name += (char) random;
				list.add(new Person(name, sex));
			}
		}
		return list;
	}
	
	static Logger logger = LoggerFactory.getLogger(Test02.class);
	public static void main(String[] args) throws ParseException {
		Test02 test02 = new Test02();
		test02.setList();
		if(null != test02.getMan()) {
			List<Person> list = test02.getMan();
			List<Person> list2 = test02.getWomen();
			for(Person ex:list) {
				logger.info("姓名：{}  性别：{}  值班时间：{}",ex.getName(), ex.getSex(), ex.getDate());
			}
			for(Person ex:list2) {
				logger.info("姓名：{}  性别：{}  值班时间：{}",ex.getName(), ex.getSex(), ex.getDate());
			}
		}
	}

	public List<Person> getMan() {
		return man;
	}

	public void setMan(List<Person> man) {
		this.man = man;
	}

	public List<Person> getWomen() {
		return women;
	}

	public void setWomen(List<Person> women) {
		this.women = women;
	}
	
	
	
}

 class Person {
	 private String name;
	 private String sex;
	 private String date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Person(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}
 }