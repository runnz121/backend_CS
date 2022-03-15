package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CoolTimeCalDateList {

	private int day;

	private List<CoolTimeCalFoodList3> data = new ArrayList<>();

	public static CoolTimeCalDateList create(int day, List<CoolTimeCalFoodList3> list3) {
		return new CoolTimeCalDateList(day, list3);
	}
}
