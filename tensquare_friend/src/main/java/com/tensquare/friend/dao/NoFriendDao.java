package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author LiangDong.
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    NoFriend findByUseridAndAndFriendid(String userid, String friendid);

}
