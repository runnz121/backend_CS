package malangcute.bellytime.bellytimeCustomer.chat.controller;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatContactIdAndImgDto;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomInviteFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.ChatRoomShopListResponse;
import malangcute.bellytime.bellytimeCustomer.chat.dto.CreateRoomRequest;
import malangcute.bellytime.bellytimeCustomer.chat.dto.MessageDto;
import malangcute.bellytime.bellytimeCustomer.chat.dto.RoomIdResponse;
import malangcute.bellytime.bellytimeCustomer.chat.service.ChatService;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

class ChatControllerTest extends TestSupport {

	private static final RoomIdResponse 채팅방_아이디_반환 =
		new RoomIdResponse("채팅방 아이디 반환");

	private static final List<Long> 초대_유저_아이디 =
		Arrays.asList(2L,3L,4L);

	private static final CreateRoomRequest 채팅방_생성_요청 =
		new CreateRoomRequest(초대_유저_아이디, null, "friend");

	private static final RoomIdResponse 채팅방_아이디_요청 =
		new RoomIdResponse("채팅방2");

	private static final List<MessageDto> 메시지_전송_리스트 = Arrays.asList(
		new MessageDto("123L",1L,"쿠쿠","안녕하세요","2022-03-21","1.img",2L),
		new MessageDto("123L",2L,"키키","모행","2022-03-21","2.img",4L)
	);

	private static final List<ChatContactIdAndImgDto> 채팅방_친구정보 = Arrays.asList(
		new ChatContactIdAndImgDto(1L, "1.img", "미미"),
		new ChatContactIdAndImgDto(2L, "2.img", "피피"),
		new ChatContactIdAndImgDto(3L, "3.img", "키키")
	);

	private static final List<ChatRoomFriendListResponse> 친구_체팅방_리스트 = Arrays.asList(
		new ChatRoomFriendListResponse("채팅방1","쿠쿠",채팅방_친구정보, "최근메시지1"),
		new ChatRoomFriendListResponse("채팅방2","미야우",채팅방_친구정보, "최근메시지2")
	);

	private static final List<ChatRoomShopListResponse> 가게_체팅방_리스트 = Arrays.asList(
		new ChatRoomShopListResponse("채팅방1","감자탕집",채팅방_친구정보, "최근메시지1"),
		new ChatRoomShopListResponse("채팅방2","돈카츠마켙",채팅방_친구정보, "최근메시지2")
	);

	private static final ChatRoomInviteFriendsRequest 채팅방_유저초대_요청 =
		new ChatRoomInviteFriendsRequest(초대_유저_아이디, "채팅방1");


	private static final FieldDescriptor[] 친구_채팅방_리스트_반환 = new FieldDescriptor[]{
		fieldWithPath("chatRoomId").type(JsonFieldType.STRING).description("채팅방 아이디(유일값)"),
		fieldWithPath("roomName").type(JsonFieldType.STRING).description("채팅방 이름"),
		fieldWithPath("recentContent").type(JsonFieldType.STRING).description("해당 채팅방 마지막 메시지"),
		fieldWithPath("contact.[].contactId").type(JsonFieldType.NUMBER).description("채팅방에 초대되어있는 유저아이디"),
		fieldWithPath("contact.[].profileImg").type(JsonFieldType.STRING).description("채팅방에 초대되어있는 유저이미지"),
		fieldWithPath("contact.[].nickName").type(JsonFieldType.STRING).description("채팅방에 초대되어있는 유저닉네임")
	};

	private static final FieldDescriptor[] 가게_채팅방_리스트_반환 = new FieldDescriptor[]{
		fieldWithPath("chatRoomId").type(JsonFieldType.STRING).description("채팅방 아이디(유일값)"),
		fieldWithPath("roomName").type(JsonFieldType.STRING).description("채팅방 이름"),
		fieldWithPath("recentContent").type(JsonFieldType.STRING).description("해당 채팅방 마지막 메시지"),
		fieldWithPath("contact.[].contactId").type(JsonFieldType.NUMBER).description("채팅방에 초대되어있는 유저아이디"),
		fieldWithPath("contact.[].profileImg").type(JsonFieldType.STRING).description("채팅방에 초대되어있는 유저이미지"),
		fieldWithPath("contact.[].nickName").type(JsonFieldType.STRING).description("채팅방에 초대되어있는 유저닉네임")
	};

	private static final FieldDescriptor[] 메시지_리스트_반환 = new FieldDescriptor[]{
		fieldWithPath("roomId").type(JsonFieldType.STRING).description("채팅방 아이디(유일값)"),
		fieldWithPath("sender").type(JsonFieldType.NUMBER).description("메시지 보내는 유저 아이디"),
		fieldWithPath("nickName").type(JsonFieldType.STRING).description("메시지 보내는 유저 닉네임"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("메시지 내용"),
		fieldWithPath("sendTime").type(JsonFieldType.STRING).description("메시지 보내는 시간"),
		fieldWithPath("contactId").type(JsonFieldType.NUMBER).description("메시지 받는 유저의 아이디"),
		fieldWithPath("profileImg").type(JsonFieldType.STRING).description("메시지 보내느 사람의 프로필이미지")
	};

	@MockBean
	private ChatService chatService;

	@MockBean
	private UserService userService;


	// @DisplayName("채팅 방 따라 보내기")
	// @Test
	// void chatController() {
	// }



	@DisplayName("채팅방 생성")
	@Test
	void createRoom() throws Exception {
		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(chatService.checkExistsRoomId(any(), any())).willReturn(채팅방_아이디_반환);

		mockMvc.perform(
						post("/chat/create")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonToString(채팅방_생성_요청))
						)
			.andExpect(status().isCreated())
			.andExpect(content().json(jsonToString(채팅방_아이디_반환)))
			.andDo(
				document("return-chatroomId",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					requestFields(
						fieldWithPath("roomName").type(JsonFieldType.NULL).description("채팅방 이름"),
						fieldWithPath("inviteId.[]").type(JsonFieldType.ARRAY).description("초대유저 아이디"),
						fieldWithPath("type").type(JsonFieldType.STRING).description("초대유저 타입")
					),
					responseFields(
						fieldWithPath("roomId").type(JsonFieldType.STRING).description("채팅방 아이디(존재시 이미 있는거 반환)")
					)
				)
			);
	}



	@DisplayName("나의 채팅방 리스트 조회(친구)")
	@Test
	void getMyFriendRoomList() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(chatService.friendChatRoomList(any())).willReturn(친구_체팅방_리스트);

		mockMvc.perform(
						get("/chat/list/friend")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(친구_체팅방_리스트)))
			.andDo(
				document("return-chattinglist-friends",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("[]").description("친구채팅방 리스트 반환"))
						.andWithPrefix("[]", 친구_채팅방_리스트_반환)
					)
				);
	}

	@DisplayName("나의 채팅방 리스트 조회(가게)")
	@Test
	void getMyShopRoomList() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(chatService.shopChatRoomList(any())).willReturn(가게_체팅방_리스트);

		mockMvc.perform(
						get("/chat/list/shop")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(가게_체팅방_리스트)))
			.andDo(
				document("return-chattinglist-shop",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("[]").description("가게채팅방 리스트 반환"))
						.andWithPrefix("[]", 가게_채팅방_리스트_반환)
				)
			);
	}

	@DisplayName("채팅방 리스트 삭제")
	@Test
	void deleteRoom() {
	}

	@DisplayName("채팅 로그 갖고오기")
	@Test
	void chatLog() throws Exception {

		given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
		given(chatService.getChatLog(any(), any())).willReturn(메시지_전송_리스트);

		mockMvc.perform(
						post("/chat/chatlog")
							.header("Authorization", "Bearer " + ACCESS_TOKEN)
							.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
							.content(jsonToString(채팅방_아이디_요청))
							.contentType(MediaType.APPLICATION_JSON)
						)
			.andExpect(status().isOk())
			.andExpect(content().json(jsonToString(메시지_전송_리스트)))
			.andDo(
				document("return-message",
					requestHeaders(
						headerWithName("Cookie").description("유저의 리프레시토큰"),
						headerWithName("Authorization").description("유저의 엑세스 토큰")
					),
					responseFields(
						fieldWithPath("[]").description("메시지 리스트"))
						.andWithPrefix("[]", 메시지_리스트_반환)
					)
				);
	}

	// @DisplayName("채팅방에친구 초대하기")
	// @Test
	// void addFriend() throws Exception {
	//
	// 	given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(사용자);
	// 	willDoNothing().given(chatService).addFriend(any(), any());
	//
	// 	mockMvc.perform(
	// 					post("/chat/add/friend")
	// 						.header("Authorization", "Bearer " + ACCESS_TOKEN)
	// 						.header("Cookie", "refreshToken=" + REFRESH_TOKEN)
	// 						.content(jsonToString(채팅방_유저초대_요청))
	// 						.contentType(MediaType.APPLICATION_JSON)
	// 					)
	// 		.andExpect(status().isOk())
	// 		.andExpect()
	// 	)
	//
	// }
}