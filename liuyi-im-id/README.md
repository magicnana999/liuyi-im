
## SnowflakeID
##### 详见 Twitter-Server
##### copy from https://github.com/beyondfengyu/SnowFlake/blob/master/SnowFlake.java

## MessageID

##### MessageID 的要求
* 不重复
* 单调增长
* 可以不连续
* 单个用户内唯一
* 满足亿级用户量需求

##### MessageID 的实现概述
* 把多个 用户放到一个 Section 中,用户的数量,称之为 SectionCapacity
* 同一个 Section 内的所有用户的 MessageID,不重复,单调增长.
* section 内含有 currentId 和 MaxId 属性, 不超过 MaxID 的,在redis 中保存,超过 maxId 的,持久化到数据库. redis 中没有的,取maxId 作为下一个阶段的 currentId
* 每次 MaxId 的增长数量=step


##### 数据库表(Mysql)


````
/**
* 初始状态下，两张表都是空的。
*/
CREATE TABLE `message_id_section` (
  `section_id` bigint(20) NOT NULL,
  `current_id` bigint(20) DEFAULT NULL,
  `max_id` bigint(20) DEFAULT NULL,
  `step` bigint(20) DEFAULT '10000',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `message_id_section_user` (
  `user_id` bigint(20) NOT NULL,
  `section_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `section_id` (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  ````
  
````
/**
* 有几个缓存
* liuyi-im:message_id:section_id       String       存储当前的sectionId
*/
````


##### 测试
本次测试基于以下基础设置 和代码
```
    private static final int SECTION_CAPACITY = 3;                  //每个 section 中的用户数量
    private static final Long SECTION_ID_OFFSET = 1000000000L;
    private static final int SECTION_STEP = 10;                         //maxId 每次增长
    private static final Long MESSAGE_ID_OFFSET = 0L;         //新 section 的初始 MessageId(实际上是MESSAGE_ID_OFFSET+1+SECTION_STEP)
    
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)101)
    messageIdService.nextId((long)102)
    messageIdService.nextId((long)102)
    messageIdService.nextId((long)102)
    messageIdService.nextId((long)102)
    messageIdService.nextId((long)102)
    messageIdService.nextId((long)103)
    messageIdService.nextId((long)103)
    messageIdService.nextId((long)103)
    messageIdService.nextId((long)103)
    messageIdService.nextId((long)103)
    messageIdService.nextId((long)103)
    
    messageIdService.nextId((long)104)
```


| 序号 |结果| 用户 ID  | 所属 section |缓存currentId|缓存 maxId|DB currentId|DB maxId|写缓存|写 DB|
| ------| ------ | ------ | ------ |------ |------ |------ |------ |------ |------ |
| 1 |11| 用户101 | 1000000000 |11|20|10|20|✔|✔|
| 2 |12| 用户101 | 1000000000 |12|20|10|20|✔|✗|
| 3 |13| 用户101 | 1000000000 |13|20|10|20|✔|✗|
| 4 |14| 用户101 | 1000000000 |14|20|10|20|✔|✗|
| 5 |15| 用户101 | 1000000000 |15|20|10|20|✔|✗|
| 6 |16| 用户101 | 1000000000 |16|20|10|20|✔|✗|
| 7 |17| 用户101 | 1000000000 |17|20|10|20|✔|✗|
| 8 |18| 用户101 | 1000000000 |18|20|10|20|✔|✗|
| 9 |19| 用户101 | 1000000000 |19|20|10|20|✔|✗|
| 10 |20| 用户101 | 1000000000 |20|20|10|20|✔|✗|
| 11 |21| 用户101 | 1000000000 |21|30|20|30|✔|✔|
| 12 |22| 用户102 | 1000000000 |22|30|20|30|✔|✗|
| 13 |23| 用户102 | 1000000000 |23|30|20|30|✔|✗|
| 14 |24| 用户102 | 1000000000 |24|30|20|30|✔|✗|
| 15 |25| 用户102 | 1000000000 |25|30|20|30|✔|✗|
| 16 |26| 用户102 | 1000000000 |26|30|20|30|✔|✗|
| 17 |27| 用户103 | 1000000000 |27|30|20|30|✔|✗|
| 18 |28| 用户103 | 1000000000 |28|30|20|30|✔|✗|
| 19 |29| 用户103 | 1000000000 |29|30|20|30|✔|✗|
| 20 |30| 用户103 | 1000000000 |30|30|20|30|✔|✗|
| 21 |31| 用户103 | 1000000000 |31|40|30|40|✔|✔|
| 22 |32| 用户103 | 1000000000 |32|40|30|40|✔|✗|
| 23 |11| 用户104 | 1000000001 |11|20|10|20|✔|✔|
 