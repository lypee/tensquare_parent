package com.tensquare.friend.service;
import com.netflix.discovery.converters.Auto;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class FriendService {
    @Autowired
    private FriendDao friendDao ;
    @Autowired
    private NoFriendDao noFriendDao ;

    public int addFriend(String userid , String friendid)
    {
            //先判断userid 和 friendid 的表中是否有数据 有就是重复添加 返回 0
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if(friend != null)
        {
            return 0 ;
        }
        friend = new Friend() ;
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从friendid到 userid是否有数据 如果有 改成 1
        if(friendDao.findByUseridAndFriendid(friendid , userid) != null)
        {
            friendDao.updateIslike("1", userid, friendid);
            friendDao.updateIslike("1", friendid, userid);
        }
        return 1 ;
    }

    public int addNoFriend(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if(noFriend != null)
        {
            //表示存在关系
            return 0 ;
        }
        noFriend = new NoFriend() ;
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return  1;
    }

    public void deleteFriend(String userid, String friendid) {
        friendDao.deletefriend(userid, friendid) ;
        friendDao.updateIslike("0", friendid, userid);

        //向非好友表中添加数据
        NoFriend noFriend = new NoFriend() ;
        noFriend.setFriendid(friendid);
        noFriend.setUserid(userid);
        noFriendDao.save(noFriend);
    }
}
