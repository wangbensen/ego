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
		return jedis.exists(key);
	}

	@Override
	public Long del(String key){
	//获取redis操作对象
	Jedis jedis = redisClient.getResource();
		return jedis.del(key);
	}

	@Override
	public String set(String key, String value){
	//获取redis操作对象
	Jedis jedis = redisClient.getResource();
		return jedis.set(key, value);
	}

	@Override
	public String get(String key) {
		//获取redis操作对象
		Jedis jedis = redisClient.getResource();
		return jedis.get(key);
	}

	@Override
	public Long expire(String key, int  second) {
		//获取redis操作对象
		Jedis jedis = redisClient.getResource();
		return jedis.expire(key,second);
	}
}
