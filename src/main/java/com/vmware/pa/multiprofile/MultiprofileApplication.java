package com.vmware.pa.multiprofile;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MultiprofileApplication {

	@Bean
	public JdbcTemplate template(DataSource ds) {
		return new JdbcTemplate(ds);
	}

	public static void main(String[] args) {
		SpringApplication.run(MultiprofileApplication.class, args);
	}

	@RestController
	public class HomeController {

		@Autowired
		JdbcTemplate template;

		@GetMapping
		public ResponseEntity<String> home()
		throws DataAccessException {
			return ResponseEntity.ok("Servername: " +
				template.query(
					"select @@SERVERNAME",
					(ResultSetExtractor<String>) rs -> {
						StringBuffer result = new StringBuffer();
						while(rs.next()) {
							result.append(rs.getString(1));
						}
						return result.toString();
					}
				)
			);
		}
	}

}
