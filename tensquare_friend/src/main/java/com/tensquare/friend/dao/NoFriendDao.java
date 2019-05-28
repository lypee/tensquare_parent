package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend , String> {
    public NoFriend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(nativeQuery = true, value = "update tb_friend set islike = ? where userid = ? and friendid = ?")
    public void updateIslike(String islike, String userid, String friendid);

    @Modifying
    @Query(nativeQuery = true, value = " delete from " +
            " tb_friend where userid = ? and friendid = ? ")
    public void deletefriend(String userid, String friendid);
}
