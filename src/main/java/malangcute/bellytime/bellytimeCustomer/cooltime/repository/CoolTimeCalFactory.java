package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.cooltime.controller.CoolTimeController;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotFoundException;

@Component
public class CoolTimeCalFactory {

	private final Map<CoolTimeStatus, CoolTimeCalStrategy> coolTimeCalStrategy;

	public CoolTimeCalFactory(CoolTimeRepository coolTimeRepository) {
		this.coolTimeCalStrategy = new EnumMap<>(CoolTimeStatus.class);
		this.coolTimeCalStrategy.put(CoolTimeStatus.ME, new CoolTimeCalMineStrategy(coolTimeRepository));
		this.coolTimeCalStrategy.put(CoolTimeStatus.FRIEND, new CoolTimeCalFriendStrategy(coolTimeRepository));
	}

	public CoolTimeCalStrategy find(CoolTimeStatus status) {
		return coolTimeCalStrategy.get(status);
	}


	public enum CoolTimeStatus {
		ME("me"), FRIEND("friend");

		public final String type;

		CoolTimeStatus(String type) {
			this.type = type;
		}

		public static CoolTimeStatus of(String type) {
			CoolTimeStatus status = Arrays.stream(values()).filter(it -> it.type.equals(type))
				.findFirst()
				.orElseThrow(() -> new NotFoundException("분류기준이 없습니다(쿨타임)"));
			System.out.println(status);
			return status;
		}
	}

}
