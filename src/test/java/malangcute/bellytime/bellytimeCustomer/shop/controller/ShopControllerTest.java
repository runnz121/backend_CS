package malangcute.bellytime.bellytimeCustomer.shop.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("가게 컨트롤러 테스트")
//@WebMvcTest(controllers = ShopControllerTest.class)
class ShopControllerTest extends TestSupport {

	@MockBean
	private ShopService shopService;

	@MockBean
	private UserService userService;


	private static final ShopSearchResponse 인기가게_1 =
		 new ShopSearchResponse(1L,"르블랑", "img", 24, 4L,
			 "서울시", true, 255, false);

	private static final ShopSearchResponse 인기가게_2 =
		new ShopSearchResponse(1L,"감자탕", "img", 24, 4L,
			"대전", true, 255, false);

	private static final List<ShopSearchResponse> 인기가게_리스트 = Arrays.asList(인기가게_1, 인기가게_2);

	private static final FieldDescriptor[] 샵리스트 = new FieldDescriptor[]{
		fieldWithPath("shopId").type(JsonFieldType.NUMBER).description("가게의 고유번호(유일값)"),
		fieldWithPath("shopName").type(JsonFieldType.STRING).description("가게의 이름"),
		fieldWithPath("profileImg").type(JsonFieldType.STRING).description("갸게의 프로필 이미지"),
		fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("가게의 리뷰 갯수"),
		fieldWithPath("score").type(JsonFieldType.NUMBER).description("가게의 벨스코어 점수"),
		fieldWithPath("address").type(JsonFieldType.STRING).description("가게의 주소"),
		fieldWithPath("status").type(JsonFieldType.BOOLEAN).description("가게의 운영여부"),
		fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("가게를 팔로운한 유저수"),
		fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("내가 가게를 팔로우한 여부")
	};

	@DisplayName("최고 인기 가게 3개 반환하기")
	@Test
	void getTop3PopularShopList() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(shopService.getTop3ShopList(any())).willReturn(인기가게_리스트);

		mockMvc.perform(
						get("/shop/popular")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(인기가게_리스트)))
			.andDo(
					document("return-3-top-rated-shop-info",
						requestHeaders(
							headerWithName("Cookie").description("유저의 리프레시토큰"),
							headerWithName("Authorization").description("유저의 엑세스 토큰")
						),
						responseFields(
							fieldWithPath("[]").type(JsonFieldType.ARRAY).description("인기가게 리스트"))
							.andWithPrefix("[]", 샵리스트)
						)
					);
	}
}