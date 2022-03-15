package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDateList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDayList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeListIF;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

@RequiredArgsConstructor
public class CoolTimeCalFriendStrategy implements CoolTimeCalStrategy{

	private final CoolTimeRepository coolTimeRepository;

	@Override
	public CoolTimeCalListResponse1 coolTimeCalByFilter(User user, Long month, Long year) {
		System.out.println("프렌드 셀렉티드!");

		List<GetMyCoolTimeListIF> listFromRepo = coolTimeRepository.findMyCoolTime(user.getId());

		List<CoolTimeCalDayList2> totalList = new ArrayList<>();

		//달이 31일까지
		for (int i = 1; i <= 31; i++) {
			int finalI = i;
			List<CoolTimeCalFoodList3> list3 = listFromRepo.stream()
				.filter(it -> it.getEndDate().getDayOfMonth() == finalI && it.getEndDate().getMonthValue() == month)
				.map(CoolTimeCalFoodList3::from)
				.collect(Collectors.toList());

			if (!list3.isEmpty()) {
				totalList.add(CoolTimeCalDayList2.of(finalI, list3));
			}
		}
		return CoolTimeCalListResponse1.of(totalList, null);
	}
}
