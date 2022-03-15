package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CoolTimeStrategyConfig {

	private final CoolTimeRepository coolTimeRepository;

	@Bean
	public CoolTimeCalMineStrategy selectMine() {
		return new CoolTimeCalMineStrategy(coolTimeRepository);
	}

	@Bean
	public CoolTimeCalFriendStrategy selectFriend() {
		return new CoolTimeCalFriendStrategy(coolTimeRepository);
	}
}
