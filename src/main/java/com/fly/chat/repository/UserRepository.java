package com.fly.chat.repository;

import com.fly.chat.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fly
 * @Description
 * @Date Created in 17:05 2017/12/19
 * @Modified by
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

}
