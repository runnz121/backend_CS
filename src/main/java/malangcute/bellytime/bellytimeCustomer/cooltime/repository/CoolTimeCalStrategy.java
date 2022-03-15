package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import java.util.List;
import java.util.Objects;

import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDateList;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeList;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;

public interface CoolTimeCalStrategy {

	CoolTimeCalListResponse1 coolTimeCalByFilter(User user, Long month, Long year);
}
