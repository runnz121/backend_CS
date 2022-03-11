package malangcute.bellytime.bellytimeCustomer.follow.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.follow.domain.FollowUser;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowShopRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowShopRepository;
import malangcute.bellytime.bellytimeCustomer.follow.repository.FollowUserRepository;
import malangcute.bellytime.bellytimeCustomer.global.domain.DataFormatter;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;
import malangcute.bellytime.bellytimeCustomer.shop.repository.ShopRepository;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class FollowService {

    private final FollowShopRepository followShopRepository;

    private final FollowUserRepository followUserRepository;

    private final UserService userService;

    private final DataFormatter dateFormat;


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
}
