package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ego.redis.dao.JedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Repository
public class JedisDaoImpl implements JedisDao{
	@Resource
	private JedisPool redisClient;
	@Override
	public Boolean exists(String key) {
		//获取redis操作对象
		Jedis jedis = redisClient.getResource();
		Boolean exists = jedis.exists(key);
		jedis.close();
		return exists;
	}

	@Override
	public Long del(String key){
	//获取redis操作对象
	Jedis jedis = redisClient.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

	@Override
	public String set(String key, String value){
	//获取redis操作对象
	Jedis jedis = redisClient.getResource();
		String set = jedis.set(key, value);
		jedis.close();
		return set;
	}

	@Override
	public String get(String key) {
		//获取redis操作对象
		Jedis jedis = redisClient.getResource();
		String s = jedis.get(key);
		jedis.close();
		return s;
	}

	@Override
	public Long expire(String key, int  second) {
		//获取redis操作对象
		Jedis jedis = redisClient.getResource();
		Long expire = jedis.expire(key, second);
		jedis.close();
		return expire;
	}

}
