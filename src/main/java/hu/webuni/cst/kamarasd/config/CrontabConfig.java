package hu.webuni.cst.kamarasd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "crontab")
@Component
@Data
public class CrontabConfig {

	private Central central = new Central();
	
	@Data
	public static class Central {
		private String connect;
	}
}
