package malangcute.bellytime.bellytimeCustomer.search.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.testcontainers.shaded.com.github.dockerjava.core.exec.ExecCreateCmdExec;
import org.testcontainers.shaded.com.google.common.escape.Escaper;

import com.fasterxml.jackson.core.JsonProcessingException;

import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchDto;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchRecentListResponse;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchResultList;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchShopRequest;
import malangcute.bellytime.bellytimeCustomer.search.service.SearchService;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSaveRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListResponse;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("검색 컨트롤러 테스트")
class SearchControllerTest extends TestSupport {

	@MockBean
	private ShopService shopService;

	@MockBean
	private FoodService foodService;

	@MockBean
	private UserService userService;

	@MockBean
	private SearchService searchService;

	private static final List<String> 결과리스트 = Arrays.asList("교촌치킨", "감자탕");

	private static final Set<String> 최근검색리스트 = Set.of("교촌치킨", "감자탕");

	private static final SearchResultList 결과반환_리스트 =
		new SearchResultList(결과리스트);

	private static final String 검색단어 = "name";

	private static final SearchDto 검색음식 =
		new SearchDto("사과");

	private static final SearchShopRequest 가게검색_분류_요청 =
		new SearchShopRequest("감자", "follower");

	private static final ShopSaveRequest 가게정보_저장요청 =
		new ShopSaveRequest("감자탕","2.img","서울시");

	private static final SearchRecentListResponse 최근_검색_리스트 =
		new SearchRecentListResponse(최근검색리스트);


	private static final List<ShopSearchResultListResponse> 가게_정보반환_리스트   =  Arrays.asList(
		new ShopSearchResultListResponse(1L, "금강산 감자탕", "1.img",24,4L,
			"서울시 강동구",true,44, false),
		new ShopSearchResultListResponse(2L, "중국집", "2.img",244,3L,
			"서울시 둔촌동",false,424, true)
	);

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



	@DisplayName("검색단어 결과 반환")
	@Test
	void searchAny() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(searchService.searching(any(), anyString())).willReturn(결과반환_리스트);

		mockMvc.perform(
						get("/searchby/name/{name}", 검색단어)
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(결과반환_리스트)))
			.andDo(
				document("return-search-result-string",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					pathParameters(
						parameterWithName("name").description("찾을 검색단어")
					),
					responseFields(
						fieldWithPath("searchResult.[]").type(JsonFieldType.ARRAY).description("검색결과 반환")
					)
				)
			);
	}

	@DisplayName("가게 이름으로 리스트 반환")
	@Test
	void searchShop() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(searchService.specificSearch(any(), any())).willReturn(가게_정보반환_리스트);

		mockMvc.perform(
						post("/searchby/resultlist")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(가게검색_분류_요청))
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(가게_정보반환_리스트)))
			.andDo(
				document("return-shop-result-sort-by-filter",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("분류 기준으로 가게 리스트 결과반환"))
						.andWithPrefix("[]", 샵_리스트)
				)
			);
	}

	@DisplayName("가게 이름으로 찾기")
	@Test
	void searchShop1() throws Exception {


		given(shopService.searchByName(any())).willReturn(결과리스트);

		mockMvc.perform(
						get("/searchby/shop/{name}", 검색단어)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(결과리스트)))
			.andDo(
				document("return-shop-result-detail-list-findby-shop",
					pathParameters(
						parameterWithName("name").description("가게이름으로 찾기")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("가게 리스트 반환")
					)
				)
			);
	}

	@DisplayName("음식이름으로 음식 찾기")
	@Test
	void searchfood() throws Exception {

		given(foodService.searchByName(any())).willReturn(결과리스트);

		mockMvc.perform(
						get("/searchby/food")
							.content(jsonToString(검색음식))
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(결과리스트)))
			.andDo(
				document("return-shop-result-detail-list-findby-food",
					requestFields(
						fieldWithPath("search").type(JsonFieldType.STRING).description("음식 검색")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("가게 리스트 반환")
					)
				)
			);
	}

	@DisplayName("새로운 가게 저장")
	@Test
	void saveShop() throws Exception {

		willDoNothing().given(shopService).saveShop(any());

		mockMvc.perform(
						post("/searchby/shop")
							.content(jsonToString(가게정보_저장요청))
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isCreated())
			.andDo(
				document("save-new-shop",
					requestFields(
						fieldWithPath("name").type(JsonFieldType.STRING).description("저장할 가게 이름"),
						fieldWithPath("img").type(JsonFieldType.STRING).description("저장할 가게 이미지"),
						fieldWithPath("address").type(JsonFieldType.STRING).description("저장할 가게 주소")
					)
				)
			);
	}

	@DisplayName("최근 검색리스트 반환")
	@Test
	void recentSearchList() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(searchService.recentSearch(any())).willReturn(최근_검색_리스트);

		mockMvc.perform(
						get("/searchby/recent")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(최근_검색_리스트)))
			.andDo(
				document("return-recent-search-list",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("recent.[]").type(JsonFieldType.ARRAY).description("최근검색 가게 리스트 반환")
					)
				)
			);
	}

	@DisplayName("최근 검색어 리스트 삭제")
	@Test
	void deleteRecentSearch() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		willDoNothing().given(searchService).deleteRecentSearch(any(), any());

		mockMvc.perform(
						delete("/searchby/recent")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andDo(
				document("delete-recent-search",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					)
				)
			);
	}
}