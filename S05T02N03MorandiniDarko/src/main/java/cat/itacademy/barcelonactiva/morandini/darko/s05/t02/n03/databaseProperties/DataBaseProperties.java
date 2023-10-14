package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n03.databaseProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class DataBaseProperties {
	
	@Component
	public class DatabaseProperties {

	    @Value("${database.type}")
	    private String activeDatabase;

	    public String activeDatabaseProfile() {
	        return activeDatabase;
	    }
	}
}
