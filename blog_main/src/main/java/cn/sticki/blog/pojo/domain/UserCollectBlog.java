package cn.sticki.blog.pojo.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserCollectBlog {

	Integer id;

	Integer blogId;

	Integer userId;

	Timestamp createTime;

}