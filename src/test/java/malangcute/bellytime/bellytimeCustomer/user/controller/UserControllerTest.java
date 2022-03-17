package malangcute.bellytime.bellytimeCustomer.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDayList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalTodayFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCheckRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowShopRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFollowShopResponse;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.reservation.service.ReservationService;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("유저 컨트롤러 테스트")
class UserControllerTest extends TestSupport {

    @MockBean
    private UserService userService;

    @MockBean
    private FollowService followService;

    @MockBean
    private ShopService shopService;

    @MockBean
    private CoolTimeService coolTimeService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private CommentService commentService;


    private static final MockMultipartFile 요청_프로필_이미지 =
        new MockMultipartFile("profileImg", "cats.JPEG", MediaType.IMAGE_JPEG_VALUE, "cats".getBytes());

    private static final UserUpdateRequest 업데이트요청_유저 =
        new UserUpdateRequest("test@test.com", "미미", 요청_프로필_이미지);

    private static final UserProfileResponse 유저_프로필_반환 =
        new UserProfileResponse(사용자.getProfileImg(), 사용자.getNickname().getNickName());

    private static final List<MyFollowShopResponse> 팔로우샵_리스트 = Arrays.asList(
        new MyFollowShopResponse(1L, "르블랑","img1.jpg", 23, 4L, "서울시", true, 244, true),
        new MyFollowShopResponse(2L, "우후죽순","img2.jpg", 23, 3L, "서울시", true, 244, true)
    );

    private static final List<FollowShopRequest> 팔로우_요청_샵_리스트 = Arrays.asList(
        new FollowShopRequest(1L),
        new FollowShopRequest(2L)
    );

    private static final List<MyFriendListResponse> 나의_친구_목록 = Arrays.asList(
        new MyFriendListResponse(1L,"쿠쿠","img1.jpg"),
        new MyFriendListResponse(2L,"미미","img2.jpg")
    );

    private static final FindMyFriendSearchRequest 찾을_친구_이메일 =
        new FindMyFriendSearchRequest("test@test.com");

    private static final MyFriendSearchResponse 찾은_친구_정보 =
        new MyFriendSearchResponse(1L, "쿠쿠", "img1.jpg", true);

    private static final List<FollowFriendsRequest> 팔로우_리스트_친구_아이디 = Arrays.asList(
        new FollowFriendsRequest(1L), new FollowFriendsRequest(2L)
    );

    private static final CoolTimeCalRequest 조회할_쿨타임_시점 =
        new CoolTimeCalRequest(2022L, 3L);


    private static final List<CoolTimeCalFoodList3> 쿨타임_해당_일_목록_1 = Arrays.asList(
        new CoolTimeCalFoodList3("바나나", 1L,"banana.jpg"),
        new CoolTimeCalFoodList3("사과", 2L,"apple.jpg")
    );

    private static final List<CoolTimeCalFoodList3> 쿨타임_해당_일_목록_2 = Arrays.asList(
        new CoolTimeCalFoodList3("딸기", 3L,"strawberry.jpg"),
        new CoolTimeCalFoodList3("수박", 4L,"watermelone.jpg")
    );

    private static final List<CoolTimeCalDayList2> 쿨타임_해당_달_목록 = Arrays.asList(
        new CoolTimeCalDayList2(1, 쿨타임_해당_일_목록_1),
        new CoolTimeCalDayList2(2, 쿨타임_해당_일_목록_2)
    );

    private static final List<CoolTimeCalTodayFoodList3> 오늘날짜_음식목록 = Arrays.asList(
        new CoolTimeCalTodayFoodList3("바나나", 1L,"banana.jpg", true),
        new CoolTimeCalTodayFoodList3("사과", 2L,"apple.jpg", false)
    );

    private static final CoolTimeCalListResponse1 쿨타임_전체_반환목록 =
        new CoolTimeCalListResponse1(쿨타임_해당_달_목록, 오늘날짜_음식목록);

    private static final List<CoolTimeCheckRequest> 쿨타임_체크_리스트 = Arrays.asList(
        new CoolTimeCheckRequest(1L, true),
        new CoolTimeCheckRequest(2L, false)
    );


    private static final FieldDescriptor[] 전체요일 = new FieldDescriptor[] {
        fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식의 이름"),
        fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("음식의 아이디(유일값)"),
        fieldWithPath("foodImg").type(JsonFieldType.STRING).description("음식의 이미지(S3)")
    };

    private static final FieldDescriptor[] 오늘날짜 = new FieldDescriptor[] {
        fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식의 이름"),
        fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("음식의 아이디(유일값)"),
        fieldWithPath("foodImg").type(JsonFieldType.STRING).description("음식의 이미지(S3)"),
        fieldWithPath("eat").type(JsonFieldType.BOOLEAN).description("음식을 먹었는지의 여부")
    };

    private static final FieldDescriptor[] 샵_리스트 = new FieldDescriptor[]{
        fieldWithPath("shopId").type(JsonFieldType.NUMBER).description("가게 고유 id"),
        fieldWithPath("shopName").type(JsonFieldType.STRING).description("가게 이름"),
        fieldWithPath("profileImg").type(JsonFieldType.STRING).description("가게 프로필이미지(s3)"),
        fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("가게 리뷰 갯수"),
        fieldWithPath("score").type(JsonFieldType.NUMBER).description("가게 밸스코어 점수"),
        fieldWithPath("address").type(JsonFieldType.STRING).description("가게 주소"),
        fieldWithPath("status").type(JsonFieldType.BOOLEAN).description("현재시간 기준 오픈 여부"),
        fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("이 가게를 팔로우 하고 있는 유저 수"),
        fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("내가 이 가게를 팔로우 하고있는지의 여부")
    };


    @DisplayName("유저 정보 업데이트")
    @Test
    void update() throws Exception {

        willDoNothing().given(userService).userUpdate(업데이트요청_유저);

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/user/update")
                            .file(요청_프로필_이미지)
                            .param("email","runnz121@gmail.com")
                            .param("nickname","쿠쿠")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)

                        )
                .andExpect(status().isOk())
                .andDo(
                        document("update-user",
                            requestParameters(
                                parameterWithName("email").description("변경할 요청을 하는 유저 이메일"),
                                parameterWithName("nickname").description("유저가 변경을 요청하는 닉네임")
                            ),
                            requestParts(
                                partWithName("profileImg").description("유저가 변경을 요청하는 프로필이미지")
                            )
                        )
                );
    }

    @DisplayName("유저 프로필이미지 반환하기")
    @Test
    void getMyProfile() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        given(userService.userProfile(any())).willReturn(유저_프로필_반환);

        mockMvc.perform(
                        get("/user/myprofile")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(유저_프로필_반환)))
            .andDo(
                    document("return-user-profile",
                        requestHeaders(
                            headerWithName("Cookie").description("유저의 리프레시토큰"),
                            headerWithName("Authorization").description("유저의 엑세스 토큰")
                        ),
                        responseFields(
                            fieldWithPath("profileImg").type(JsonFieldType.STRING).description("유저의 프로필 이미지"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("유저의 닉네임")
                        )
                    )
            );
    }

    @DisplayName("유저가 팔로우한 가게 리스트 갖고오기")
    @Test
    void myFollowShopList() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        given(shopService.myFollowShop(any(), any())).willReturn(팔로우샵_리스트);

        mockMvc.perform(
                        get("/user/follow/shop")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(팔로우샵_리스트)))
            .andDo(
                    document("return-follow-shop-list",
                        requestHeaders(
                            headerWithName("Cookie").description("유저의 리프레시토큰"),
                            headerWithName("Authorization").description("유저의 엑세스 토큰")
                        ),
                        responseFields(
                            fieldWithPath("[]").type(JsonFieldType.ARRAY).description("유저가 팔로우한 가게 리스트"))
                            .andWithPrefix("[]", 샵_리스트)
                        )
            );
    }

    @DisplayName("유저가 팔로우 할 가게 리스트 저장하기")
    @Test
    void toFollowShop() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        willDoNothing().given(followService).toFollowShop(any(), any());

        mockMvc.perform(
                        post("/user/follow/shop")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(팔로우_요청_샵_리스트))
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                       )
            .andExpect(status().is2xxSuccessful())
            .andDo(
                document("follow-shop-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("[].shopId").type(JsonFieldType.NUMBER).description("가게 아이디(유일값)"))
                )
            );
    }

    @DisplayName("유저가 팔로우 취소할 가게 리스트 받기")
    @Test
    void toUnFollowShop() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        willDoNothing().given(followService).toUnFollowShop(any(), any());

        mockMvc.perform(
                        delete("/user/follow/shop")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(팔로우_요청_샵_리스트))
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andDo(
                document("unfollow-shop-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("[].shopId").type(JsonFieldType.NUMBER).description("가게 아이디(유일값)"))
                )
            );
    }

    @DisplayName("유저가 팔로우 하는 유저 목록 리스트 반환하기")
    @Test
    void myFriendList() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        given(followService.getMyFriends(any())).willReturn(나의_친구_목록);

        mockMvc.perform(
                        get("/user/friends/list")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(나의_친구_목록)))
            .andDo(
                document("my-follow-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    responseFields(
                        fieldWithPath("[].contactId").type(JsonFieldType.NUMBER).description("유저의 아이디"),
                        fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("유저의 닉네임"),
                        fieldWithPath("[].profileImg").type(JsonFieldType.STRING).description("유저의 프로필이미지"))
                )
            );
    }

    @DisplayName("유저이메일로 친구 유저 찾기(단일건)")
    @Test
    void findMyFriend() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        given(userService.findUserByNickname(any(), any(FindMyFriendSearchRequest.class))).willReturn(찾은_친구_정보);

        mockMvc.perform(
                        post("/user/friends/search")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(찾을_친구_이메일))
                        )
            .andExpect(status().isOk())
            .andDo(
                document( "find-user-with-email",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("찾을 친구의 이메일")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("찾은 친구의 아이디(유일값)"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("찾은 친구의 닉네임"),
                        fieldWithPath("profileImg").type(JsonFieldType.STRING).description("찾은 친구의 프로필 사진(S3)"),
                        fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("찾은 친구를 팔로우하고있는지 여부")
                    )
                )
            );
    }

    @DisplayName("유저가 팔로우할 친구 리스트로 받기")
    @Test
    void followMyFriends() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        willDoNothing().given(followService).toFollowFriend(any(), any());

        mockMvc.perform(
                        post("/user/follow/friends")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(팔로우_리스트_친구_아이디))
                        )
            .andExpect(status().isOk())
            .andDo(
                document("to-follow-friends-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("[].friendId").type(JsonFieldType.NUMBER).description("팔로우할 친구 ID(유일값)")
                    )
                )
            );
    }

    @DisplayName("유저가 언팔로우할 친구 리스트 받기")
    @Test
    void unFollowMyFriend() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        willDoNothing().given(followService).toUnFollowFriend(any(), any());

        mockMvc.perform(
                delete("/user/follow/friends")
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonToString(팔로우_리스트_친구_아이디))
            )
            .andExpect(status().isOk())
            .andDo(
                document("to-unfollow-friends-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("[].friendId").type(JsonFieldType.NUMBER).description("언팔로우할 친구 ID(유일값)")
                    )
                )
            );
    }

    @DisplayName("유저의 쿨타임 반환하기")
    @Test
    void myCoolTimeCalender() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        given(coolTimeService.selected(any(), any(Long.class), any(Long.class), any(String.class)))
            .willReturn(쿨타임_전체_반환목록);

        mockMvc.perform(
                        post("/user/cal")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(조회할_쿨타임_시점))
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(쿨타임_전체_반환목록)))
            .andDo(
                document("return-my-cooltime-list",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("year").type(JsonFieldType.NUMBER).description("쿨타임 조회 연도"),
                        fieldWithPath("month").type(JsonFieldType.NUMBER).description("쿨타임 조회 월")
                    ),
                    responseFields(
                        fieldWithPath("dateList.[].day").type(JsonFieldType.NUMBER).description("해당하는 일의 쿨타임"))
                        .andWithPrefix("dateList.[].data.[]", 전체요일)
                        .andWithPrefix("today.[]", 오늘날짜)
                    )
            );
    }

    @DisplayName("유저가 선택한 쿨타임 여부 확인")
    @Test
    void checkMyCoolTime() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
        willDoNothing().given(coolTimeService).updateCoolTimeEatCheck(any(), any());

        mockMvc.perform(
                        post("/user/cal/check")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(쿨타임_체크_리스트))
                        )
            .andExpect(status().isOk())
            .andDo(
                document("check-my-cooltime",
                    requestHeaders(
                        headerWithName("Cookie").description("유저의 리프레시토큰"),
                        headerWithName("Authorization").description("유저의 엑세스 토큰")
                    ),
                    requestFields(
                        fieldWithPath("[].foodId").type(JsonFieldType.NUMBER).description("체크할 음식 아이디(유일값)"),
                        fieldWithPath("[].eat").type(JsonFieldType.BOOLEAN).description("먹었는지 여부 체크")
                    )
                )
            );
    }

    //
    // @DisplayName("유저 사진 업데이트")
    // @Test
    // void myReservationList() {
    // }
    //
    // @DisplayName("유저가 작성한 후기 리스트 반환하기")
    // @Test
    // void getMyCommentList() {
    // }
    //
    // @DisplayName("유저가 작성한 후기")
    // @Test
    // void writeComment() {
    // }
}