package malangcute.bellytime.bellytimeCustomer.food.controller;

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

import net.bytebuddy.NamingStrategy;

import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchFoodRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchResultResponse;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;

@DisplayName("음식 컨트롤러 테스트")
class FoodControllerTest extends TestSupport {

	private static final SearchFoodRequest 음식검색 = new SearchFoodRequest("가지");

	private static final Food 저장된_음식 =
		Food.builder()
			.name("복숭아")
			.build();

	private static final List<SearchResultResponse> 음식결과_리스트 = Arrays.asList(
		new SearchResultResponse(1L, "사과"),
		new SearchResultResponse(2L,"수박")
	);

	@MockBean
	private FoodService foodService;

	@DisplayName("음식 검색하기")
	@Test
	void search() throws Exception {

		given(foodService.findFood(any())).willReturn(음식결과_리스트);

		mockMvc.perform(
						post("/searchfood")
							.content(jsonToString(음식검색))
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(음식결과_리스트)))
			.andDo(
				document("return-food-resultlist",
					requestFields(
						fieldWithPath("search").type(JsonFieldType.STRING).description("검색할 음식 이름")
					),
					responseFields(
						fieldWithPath("[].foodId").type(JsonFieldType.NUMBER).description("음식 아이디 반환(유일값)"),
						fieldWithPath("[].foodName").type(JsonFieldType.STRING).description("음식 이름 반환")
					)
				)
			);
	}

	@DisplayName("음식 저장하기")
	@Test
	void saveFood() throws Exception {

		given(foodService.registerFood(any())).willReturn(저장된_음식);

		mockMvc.perform(
						post("/savefood")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(음식검색))
						)
			.andDo(
				document("save-food",
					requestFields(
						fieldWithPath("search").type(JsonFieldType.STRING).description("검색할 음식 이름")
					)
				)
			);
	}
}