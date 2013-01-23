package proposal;

public class Proposal {
	private String fullname;
	private String email;
	private String phone;
	private String company;
	private String[] mobileDevelopment;
	private String[] webDevelopment;
	private String[] cloudSolution;
	private String observaciones;
	private String ipHost;
	private String rangoMin;
	private String rangoMax;
	
	
	public String getIpHost() {
		return ipHost;
	}
	public void setIpHost(String ipHost) {
		this.ipHost = ipHost;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String[] getMobileDevelopment() {
		return mobileDevelopment;
	}
	public String getMobileDevelopmentToString()
	{
		return this.ArrayToString(this.getMobileDevelopment());
	}
	private String ArrayToString(String[] arreglo)
	{
		String mensaje="";
		int cont=0;
		for(String a:arreglo)
		{
			cont++;
			mensaje+=a;
			if(cont!=arreglo.length)
				mensaje+=",";
		}
		return mensaje;
	}
	public void setMobileDevelopment(String[] mobileDevelopment) {
		this.mobileDevelopment = mobileDevelopment;
	}
	public String[] getWebDevelopment() {
		return webDevelopment;
	}
	public String getWebDevelopmentToString() {
		return this.ArrayToString(this.getWebDevelopment());
	}
	public void setWebDevelopment(String[] webDevelopment) {
		this.webDevelopment = webDevelopment;
	}
	public String[] getCloudSolution() {
		return cloudSolution;
	}
	public String getCloudSolutionToString() {
		return this.ArrayToString(this.getCloudSolution());
	}
	public void setCloudSolution(String[] cloudSolution) {
		this.cloudSolution = cloudSolution;
	}
	public String getRangoMin() {
		return rangoMin;
	}
	public void setRangoMin(String rangoMin) {
		this.rangoMin = rangoMin;
	}
	public String getRangoMax() {
		return rangoMax;
	}
	public void setRangoMax(String rangoMax) {
		this.rangoMax = rangoMax;
	}

	
}
