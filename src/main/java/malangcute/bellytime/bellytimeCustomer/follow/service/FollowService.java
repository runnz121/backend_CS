package malangcute.bellytime.bellytimeCustomer.follow.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowShop;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowShopRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowShopRepository;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowUserRepository;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.domain.DataFormatter;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowShopRepository followShopRepository;

    private final FollowUserRepository followUserRepository;

    private final ShopRepository shopRepository;

    private UserService userService;

    private final DataFormatter dateFormat;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public void toFollowShop(User user, List<FollowShopRequest> requests) {
        Set<FollowShop> saveList = new HashSet<>();
        for (FollowShopRequest request : requests) {
            Shop shopResult = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new NotFoundException("가게가 없습니다"));
            saveList.add(FollowShop.create(user, shopResult));
        }
        followShopRepository.saveAll(saveList);
    }


    public void toUnFollowShop(User user, List<FollowShopRequest> requests) {
        for (FollowShopRequest request : requests) {
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
    public void toFollowFriend(User user, List<FollowFriendsRequest> requests) {
        Set<FollowUser> saveList = new HashSet<>();
        for (FollowFriendsRequest request : requests) {
            User userResult  = userService.findUserById(request.getFriendId());
            saveList.add(FollowUser.create(user, userResult));
        }
        followUserRepository.saveAll(saveList);
    }


    public void toUnFollowFriend(User user, List<FollowFriendsRequest> requests) {
        for (FollowFriendsRequest request : requests) {
            followUserRepository.deleteFollowShopId(user.getId(), request.getFriendId());
        }
    }


    public int shopFollower(Shop shop) {
        return followShopRepository.countFollowerShop(shop.getId());
    }

    // 운영중인지 확인하기
    public boolean checkStatus(Shop shop) {
        LocalDateTime currentTime = LocalDateTime.now();
        Long current = Long.parseLong(dateFormat.LocalDateTimeHour(currentTime));
        Long open = null;
        Long close = null;
        try {
            open = Long.parseLong(dateFormat.TimeStampHour(shop.getOpenTime()));
            close = Long.parseLong(dateFormat.TimeStampHour(shop.getCloseTime()));

        } catch (NullPointerException ex) {
            return false;
        }
        if (current > open && current < close) {
            return true;
        }
        return false;
    }

    public boolean followStatus (User hostId, User followId) {
        try {
            followUserRepository.findByHostIdAndFriendId(hostId, followId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean followStatusShop (User userId, Shop shopId) {
        try {
            followShopRepository.findByUserIdAndShopId(userId.getId(), shopId.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
