package malangcute.bellytime.bellytimeCustomer.cooltime.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDayList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFriendRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalTodayFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeGetMyFriends;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeSettingRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeShopRecommendResponse;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.DeleteCoolTimeRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("쿨타임 컨트롤러 테스트")
class CoolTimeControllerTest extends TestSupport {

	@MockBean
	private CoolTimeService coolTimeService;

	@MockBean
	private UserService userService;

	private static final List<GetMyCoolTimeList> 나의_쿨타임_리스트 = Arrays.asList(
		new GetMyCoolTimeList(1L,"사과","23","사과.img","2022-03-21",21,
			"2022-04-13", 10L),
		new GetMyCoolTimeList(2L,"바나나","23","바나나.img","2022-03-14",21,
			"2022-04-13", 21L)
	);

	private static final CoolTimeSettingRequest 쿨타임_세팅_요청 =
		new CoolTimeSettingRequest(4L, "딸기", "2022-03-11", 24);

	private static final DeleteCoolTimeRequest 쿨타임삭제요청 =
		new DeleteCoolTimeRequest(1L);

	private static final CoolTimeCalFriendRequest 친구_쿨타임_요청 =
		new CoolTimeCalFriendRequest(2L, 2022L, 3L);

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

	private static final List<CoolTimeGetMyFriends> 친구_쿨타임_리스트 = Arrays.asList(
		new CoolTimeGetMyFriends(1L, "미선이", "24", "1.img"),
		new CoolTimeGetMyFriends(2L, "똑똑이", "25","2.img")
	);

	private static final Long 음식_아이디 = 1L;

	private static final List<CoolTimeShopRecommendResponse> 가게_리스트 = Arrays.asList(
		new CoolTimeShopRecommendResponse(1L, "르블랑","img1.jpg", 23, 4L, "서울시", true, 244, true),
		new CoolTimeShopRecommendResponse(2L, "우후죽순","img2.jpg", 23, 3L, "서울시", true, 244, true)
	);

	private static final String 필터 = "follow";

	private static final Double 위도 = 123.123123;

	private static final Double 경도 = 32.234234;

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

	private static final FieldDescriptor[] 쿨타임_리스트 = new FieldDescriptor[]{
		fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("음식 아이디(유일값)"),
		fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식 이름"),
		fieldWithPath("gauge").type(JsonFieldType.STRING).description("쿨타임 게이지"),
		fieldWithPath("foodImg").type(JsonFieldType.STRING).description("음식 이미지"),
		fieldWithPath("startDate").type(JsonFieldType.STRING).description("쿨타임 시작 날짜"),
		fieldWithPath("duration").type(JsonFieldType.NUMBER).description("쿨타임 기간"),
		fieldWithPath("predictDate").type(JsonFieldType.STRING).description("쿨타임 종료 날짜"),
		fieldWithPath("leftDays").type(JsonFieldType.NUMBER).description("쿨타임 만료까지 남은 기간")
	};

	private static final FieldDescriptor[] 친구쿨타임리스트 = new FieldDescriptor[]{
		fieldWithPath("freindId").type(JsonFieldType.NUMBER).description("친구의 아이디(유일값)"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("친구의 이름"),
		fieldWithPath("gauge").type(JsonFieldType.STRING).description("쿨타임 게이지"),
		fieldWithPath("profileImg").type(JsonFieldType.STRING).description("친구프로필이미지")
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

	@DisplayName("나의 쿨타임 리스트 반환")
	@Test
	void myList() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(coolTimeService.getMyList(any())).willReturn(나의_쿨타임_리스트);

		mockMvc.perform(
						get("/cooltime/mylist")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(나의_쿨타임_리스트)))
			.andDo(
				document("return-mycooltime-list",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("쿨타임 리스트 반환"))
						.andWithPrefix("[]", 쿨타임_리스트)
				)
			);
	}

	@DisplayName("쿨타엠 셋팅하기")
	@Test
	void setting() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		willDoNothing().given(coolTimeService).settingCoolTime(any(), any());

		mockMvc.perform(
						post("/cooltime/setting")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(쿨타임_세팅_요청))
						)
			.andExpect(status().isOk())
			.andDo(
				document("setting-cooltime",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestFields(
						fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("음식의 아이디(고유값)"),
						fieldWithPath("foodName").type(JsonFieldType.STRING).description("음식의 이름"),
						fieldWithPath("startDate").type(JsonFieldType.STRING).description("쿨타임 시작 일자"),
						fieldWithPath("duration").type(JsonFieldType.NUMBER).description("쿨타임 기간")
					)
				)
			);
	}

	@DisplayName("쿨타임 삭제하기")
	@Test
	void deleteCoolTime() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		willDoNothing().given(coolTimeService).deleteCoolTime(any(), any());

		mockMvc.perform(
						delete("/cooltime/setting")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(쿨타임삭제요청))
						)
			.andExpect(status().isOk())
			.andDo(
				document("delete-cooltime",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestFields(
						fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("음식의 아이디(고유값)")
					)
				)
			);
	}

	@DisplayName("친구 쿨타임 갖고오기")
	@Test
	void getMyFriendCoolTime() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(coolTimeService.selected(any(), anyLong(), anyLong(), anyString())).willReturn(쿨타임_전체_반환목록);

		mockMvc.perform(
						post("/cooltime/cal")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(친구_쿨타임_요청))
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(쿨타임_전체_반환목록)))
			.andDo(
				document("return-friends-cooltimeList",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestFields(
						fieldWithPath("friendId").type(JsonFieldType.NUMBER).description("친구 아이디"),
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

	@DisplayName("내가 팔로우 중인 친구 쿨타임 리스트")
	@Test
	void myFriendsCoolTime() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(coolTimeService.getMyFriendsListByFood(any(), any())).willReturn(친구_쿨타임_리스트);

		mockMvc.perform(
						get("/cooltime/followList")
							.param("foodId", String.valueOf(음식_아이디))
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(친구_쿨타임_리스트)))
			.andDo(
				document("return-myfriends-cooltimelist",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestParameters(
						parameterWithName("foodId").description("음식 아이디(유일값)")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("친구 쿨타임 리스트 반환"))
						.andWithPrefix("[]", 친구쿨타임리스트)
				)
			);
	}

	@DisplayName("필터링으로 쿨타임 갖고오기")
	@Test
	void getCoolTimeByFilter() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(coolTimeService.getShopListFilterBy(any(), anyLong(), anyString(), anyDouble(), anyDouble(), any())).willReturn(가게_리스트);

		mockMvc.perform(
						get("/cooltime/shopList")
							.param("foodId", String.valueOf(음식_아이디))
							.param("filter", 필터)
							.param("lat", String.valueOf(위도))
							.param("lon", String.valueOf(경도))
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(가게_리스트)))
			.andDo(
				document("return-feed-list-sortby-filter",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestParameters(
						parameterWithName("foodId").description("음식 아이디"),
						parameterWithName("filter").description("필터할 분류 조건 "),
						parameterWithName("lat").description("위도, null값 가능"),
						parameterWithName("lon").description("경도, null값 가능")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("검색결과 반환"))
						.andWithPrefix("[]", 샵_리스트)
				)
			);
	}
}