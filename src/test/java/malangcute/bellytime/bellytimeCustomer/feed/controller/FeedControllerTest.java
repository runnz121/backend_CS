package malangcute.bellytime.bellytimeCustomer.feed.controller;

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
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedListResponse;
import malangcute.bellytime.bellytimeCustomer.feed.dto.FeedResultResponse;
import malangcute.bellytime.bellytimeCustomer.feed.service.FeedService;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("피드 컨트롤러 테스트")
class FeedControllerTest extends TestSupport {

	@MockBean
	private UserService userService;

	@MockBean
	private FeedService feedService;

	private static final String 필터 = "follow";

	private static final Double 위도 = 123.123123;

	private static final Double 경도 = 32.234234;

	private static final Long 포스트_아이디 = 1L;

	private static final List<String> 가게이미지 = Arrays.asList("1.img", "2.img", "3.img");

	private static final List<FeedListResponse> 피드_리스트 = Arrays.asList(
		new FeedListResponse(1L, 1L, "감자탕", "1.img", "안녕하세요!" ),
		new FeedListResponse(2L, 3L, "르블랑", "2.img", "테스트!!" )
	);

	private static final FeedResultResponse 상세피드정보 =
		new FeedResultResponse(1L, "감자탕", "1.img", "안녕하세요","내용1", 가게이미지);

	private static final FieldDescriptor[] 결과_리스트 = new FieldDescriptor[]{
		fieldWithPath("shopId").type(JsonFieldType.NUMBER).description("가게 번호(유일값)"),
		fieldWithPath("postId").type(JsonFieldType.NUMBER).description("포스트 번호"),
		fieldWithPath("shopName").type(JsonFieldType.STRING).description("가게 이름"),
		fieldWithPath("representImg").type(JsonFieldType.STRING).description("피드 대표이미지"),
		fieldWithPath("title").type(JsonFieldType.STRING).description("피드 타이틀")
	};

	@DisplayName("피드 리스트를 필터기준으로 분류해서 반환")
	@Test
	void getMyFeedListByFilter() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(feedService.getListBy(any(), anyString(), anyDouble(), anyDouble(), any())).willReturn(피드_리스트);

		mockMvc.perform(
						get("/feed/list")
							.param("filter", 필터)
							.param("lat", String.valueOf(위도))
							.param("lon", String.valueOf(경도))
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(피드_리스트)))
			.andDo(
				document("return-feed-list-sortby-filter",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestParameters(
						parameterWithName("filter").description("필터할 분류 조건 "),
						parameterWithName("lat").description("위도, null값 가능"),
						parameterWithName("lon").description("경도, null값 가능")
					),
					responseFields(
						fieldWithPath("[]").type(JsonFieldType.ARRAY).description("검색결과 반환"))
						.andWithPrefix("[]", 결과_리스트)
				)
			);
	}

	@DisplayName("피드 상세정보를 반환")
	@Test
	void getFeedFromRequest() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(feedService.getFeedList(anyLong())).willReturn(상세피드정보);

		mockMvc.perform(
						get("/feed/post")
							.param("postId", String.valueOf(포스트_아이디))
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
					)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(상세피드정보)))
			.andDo(
				document("return-detail-feed-info",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestParameters(
						parameterWithName("postId").description("상세조회할 피드 포스트 아이디(유일값)")
					),
					responseFields(
						fieldWithPath("shopId").type(JsonFieldType.NUMBER).description("가게 번호(유일값)"),
						fieldWithPath("shopName").type(JsonFieldType.STRING).description("가게 이름"),
						fieldWithPath("profileImg").type(JsonFieldType.STRING).description("가게 프로필 이미지"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("피드 타이틀"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("피드 내용"),
						fieldWithPath("images.[]").type(JsonFieldType.ARRAY).description("피드 이미지 여러개")
				)
			)
		);
	}
}