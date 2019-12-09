package com.zhangqin.redis.test.bean;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangqin.redis.test.bean.User;
import com.zhangqin.utils.RandomUtil;
import com.zhangqin.utils.StringUtil;
@RunWith(SpringJUnit4ClassRunner.class)//测试的路径
@ContextConfiguration("classpath:spring-redis.xml")//redis配置文件的路径
public class UserTest {

	@Autowired
	private RedisTemplate r;//装载redis
	//jdk测试
	@Test
	public void jdk_test() {
		//list集合
		List<User> list = new ArrayList<User>();
		for (int i = 1; i <= 50000; i++) {
			//姓名
			String name=StringUtil.randomChineseString(3);
			//电话
			String phone="13"+RandomUtil.randomNumber(9);
			//邮箱
			String c=" @qq.com";
			String email=RandomUtil.randomString(RandomUtil.random(3,20))+c;
			//生日
			int birthday=RandomUtil.random(18,70);
			//性别
			String sex="男";
			//有参构造
			User u = new User(i, name, sex, phone, email, birthday);
			//存入集合
			list.add(u);
		}
		ListOperations opsForList = r.opsForList();
		//开始时间
		long start = System.currentTimeMillis();
		//存入redis
		opsForList.leftPush("list_jdk",list);
		//结束时间
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	//json测试
	@Test
	public void json_test() {
		List<User> list = new ArrayList<User>();
		for (int i = 1; i <= 50000; i++) {
			String name=StringUtil.randomChineseString(3);
			String phone="13"+RandomUtil.randomNumber(9);
			String c=" @qq.com";
			String email=RandomUtil.randomString(RandomUtil.random(3,20))+c;
			int birthday=RandomUtil.random(18,70);
			String sex="男";
			User u = new User(i, name, sex, phone, email, birthday);
			list.add(u);
		}
		ListOperations opsForList = r.opsForList();
		long start = System.currentTimeMillis();//开始时间
		opsForList.leftPush("list_json",list);//存入redis
		long end = System.currentTimeMillis();//结束时间
		System.out.println(end-start);
	}
	//hash测试
	@Test
	public void hash_test() {
		HashMap<String, User> hashmap = new HashMap<String, User>();
		for (int i = 1; i <= 50000; i++) {
			String name=StringUtil.randomChineseString(3);
			String phone="13"+RandomUtil.randomNumber(9);
			String c=" @qq.com";
			String email=RandomUtil.randomString(RandomUtil.random(3,20))+c;
			int birthday=RandomUtil.random(18,70);
			String sex="男";
			User u = new User(i, name, sex, phone, email, birthday);
			hashmap.put(i+"",u);
		}
		ListOperations opsForList = r.opsForList();
		long start = System.currentTimeMillis();//开始时间
		opsForList.leftPush("hash",hashmap);//存入redis
		long end = System.currentTimeMillis();//结束时间
		System.out.println(end-start);
	}

}
