package copycli.com.copycli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CopycliApplication {

	public static void main(String[] args) {
		// Enable GUI mode for clipboard access
		System.setProperty("java.awt.headless", "false");
		System.setProperty("spring.shell.interactive.enabled", "true");
		System.setProperty("spring.shell.noninteractive.enabled", "false");
		
		SpringApplication app = new SpringApplication(CopycliApplication.class);
		app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
		
		// Disable the default command line runners to prevent immediate exit
		System.setProperty("spring.shell.command.quit.enabled", "false");
		
		app.run(args);
	}

}
