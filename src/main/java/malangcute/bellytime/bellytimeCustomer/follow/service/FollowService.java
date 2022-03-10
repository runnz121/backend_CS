package malangcute.bellytime.bellytimeCustomer.follow.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import malangcute.bellytime.bellytimeCustomer.follow.dto.*;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowShopRepository;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowUserRepository;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class FollowService {

    private final FollowShopRepository followShopRepository;

    private final FollowUserRepository followUserRepository;

    private final ShopRepository shopRepository;

    private final UserService userService;


    // public void toFollowShop(User user, List<FollowShopRequest> requests){
    //     Set<FollowShop> saveList = new HashSet<>();
    //     for(FollowShopRequest request : requests) {
    //         Shop shopResult  = shopRepository.findById(request.getShopId()).orElseThrow(() -> new NotFoundException("가게가 없습니다"));
    //         saveList.add(FollowShop.create(user, shopResult));
    //     }
    //     followShopRepository.saveAll(saveList);
    // }


    //가게 언팔로우처리하기
    public void toUnFollowShop(User user, List<FollowShopRequest> requests){
        for(FollowShopRequest request : requests) {
            followShopRepository.deleteFollowShopId(user.getId(), request.getShopId());
        }
    }

    //내가 팔로우 중인 친구 불러오기
    public List<MyFriendListResponse> getMyFriends(User user) {
        return followUserRepository.findMyFollowList(user.getId()).stream()
                .map(MyFriendListResponse::from)
                .collect(Collectors.toList());
    }

    //친구 팔로우 하기
    public void toFollowFriend(User user, List<FollowFriendsRequest> requests){
        Set<FollowUser> saveList = new HashSet<>();
        for(FollowFriendsRequest request : requests) {
            User userResult  = userService.findUserById(request.getFriendId());
            saveList.add(FollowUser.create(user, userResult));
        }
        followUserRepository.saveAll(saveList);
    }


    public void toUnFollowFriend(User user, List<FollowFriendsRequest> requests){
        for(FollowFriendsRequest request : requests) {
            followUserRepository.deleteFollowShopId(user.getId(), request.getFriendId());
        }
    }


    public int shopFollower(Shop shop) {
        System.out.println("count query 실행?");
        return followShopRepository.countFollowerShop(shop.getId());
    }
}
