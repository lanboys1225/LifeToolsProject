package com.bing.lan.project.userProvider;

import com.bing.lan.domain.CommRequestParams;
import com.bing.lan.domain.QueryDomain;
import com.bing.lan.project.userApi.domain.GroupApproval;
import com.bing.lan.project.userApi.domain.GroupUser;
import com.bing.lan.project.userApi.domain.Groups;
import com.bing.lan.project.userApi.service.DubboGroupUserService;
import com.bing.lan.redis.RedisClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:user-provider-context-test.xml")
public class DubboGroupUserServiceTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private DubboGroupUserService dubboGroupUserService;

    private CommRequestParams commRequestParams;

    {
        commRequestParams = CommRequestParams.builder()
                .platform("android")
                .ip("127.9.0.121")
                .version("v1.1.2")
                .channel("tengxun")
                .build();
    }

    /**
     * 测试 创建组
     */
    @Test
    public void testCreateGroup() {
        Groups group = dubboGroupUserService.createGroup(1, "groupName");
        System.out.println("testCreateGroup(): " + group);
    }

    ///**
    // * 测试 加入组
    // */
    //@Test
    //public void testJoinGroup() {
    //    GroupUser groupUser = dubboGroupUserService.joinGroup("16", "2","member");
    //    System.out.println("testJoinGroup(): " + groupUser);
    //}

    /**
     * 测试 申请加入组
     */
    @Test
    public void testApplyJoinGroup() {
        GroupApproval groupApproval = dubboGroupUserService.applyJoinGroup(16, 2);
        System.out.println("testApplyJoinGroup(): " + groupApproval);
    }

    /**
     * 测试 查询群审核
     */
    @Test
    public void testGroupApprovalList() {
        QueryDomain<GroupApproval> groupApprovalQueryDomain = dubboGroupUserService.groupApprovalList(1);
        List<GroupApproval> list = groupApprovalQueryDomain.getList();

        System.out.println("testGroupApprovalList()--------------------------------");
        for (GroupApproval groupApproval : list) {
            System.out.println("testGroupApprovalList(): " + groupApproval.getId());
        }
        System.out.println("testGroupApprovalList()--------------------------------");
    }

    /**
     * 测试 查询所有群
     */
    @Test
    public void testDubboUserServiceGroupList() {
        QueryDomain<Groups> queryDomain = new QueryDomain<>();
        queryDomain.setCurrentPage(1);
        queryDomain.setPageSize(5);

        QueryDomain<Groups> groupsQueryDomain = dubboGroupUserService.findAllGroupList(queryDomain);
        List<Groups> list = groupsQueryDomain.getList();

        System.out.println("testDubboUserServiceGroupList()--------------------------------");
        for (Groups groups : list) {
            System.out.println("testDubboUserServiceGroupList(): " + groups.getId());
        }
        System.out.println("testDubboUserServiceGroupList()--------------------------------");
    }

    /**
     * 测试 查询用户加入的所有群
     */
    @Test
    public void testDubboUserServiceGroupUserList() {
        QueryDomain<GroupUser> queryDomain = new QueryDomain<>();
        queryDomain.setCurrentPage(1);
        queryDomain.setPageSize(15);

        QueryDomain<GroupUser> groupsQueryDomain = dubboGroupUserService.findGroupOfUserJoinList(1, queryDomain);
        List<GroupUser> list = groupsQueryDomain.getList();

        System.out.println("testDubboUserServiceGroupUserList()--------------------------------");
        for (GroupUser groupUser : list) {
            System.out.println("testDubboUserServiceGroupUserList(): " + groupUser.getId());
        }
        System.out.println("testDubboUserServiceGroupUserList()--------------------------------");
    }

    /**
     * 群成员列表
     */
    @Test
    public void testDubboUserServiceUserGroupList() {
        QueryDomain<GroupUser> queryDomain = new QueryDomain<>();
        queryDomain.setCurrentPage(1);
        queryDomain.setPageSize(15);

        QueryDomain<GroupUser> groupsQueryDomain = dubboGroupUserService.findMemberOfGroupList(16, queryDomain);
        List<GroupUser> list = groupsQueryDomain.getList();

        System.out.println("testDubboUserServiceGroupUserList()--------------------------------");
        for (GroupUser groupUser : list) {
            System.out.println("testDubboUserServiceGroupUserList(): " + groupUser.getId());
        }
        System.out.println("testDubboUserServiceGroupUserList()--------------------------------");
    }
}
