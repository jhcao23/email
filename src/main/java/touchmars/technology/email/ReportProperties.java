package touchmars.technology.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("report")
public class ReportProperties {
	
	private String location;
	private String jasperFilename;

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJasperFilename() {
		return jasperFilename;
	}
	public void setJasperFilename(String jasperFilename) {
		this.jasperFilename = jasperFilename;
	}

}
