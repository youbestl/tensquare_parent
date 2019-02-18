package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author LiangDong.
 */

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    public int addFriend(String userid, String friendid) {
        //先判断 userid -> friendid 是否有数据，如果有，表示重复条件，返回0
        Friend friend = null;
        friend = friendDao.findByUseridAndAndFriendid(userid, friendid);
        if (friend != null) {
            return 0;
        }
        //直接添加好友，让好友表中 userid -> friendid 方向的type改为0，返回1
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从 friendid -> userid 方向是否有数据，如果有，把双方的状态都改为1，返回1
        friend = friendDao.findByUseridAndAndFriendid(friendid, userid);
        if (friend != null) {
            friendDao.updateIsLike("1",userid,friendid);
            friendDao.updateIsLike("1", friendid, userid);
        }
        return 1;
    }
}
