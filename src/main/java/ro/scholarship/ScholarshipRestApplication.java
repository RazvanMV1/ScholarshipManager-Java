package ro.scholarship;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ScholarshipRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScholarshipRestApplication.class, args);
    }

    @Bean
    public CommandLineRunner testDb(DataSource ds) {
        return args -> {
            try (var conn = ds.getConnection();
                 var st = conn.createStatement();
                 var rs = st.executeQuery("SELECT * FROM FACULTATI")) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") + ", DENUMIRE: " + rs.getString("DENUMIRE"));
                }
            }
        };
    }

}

