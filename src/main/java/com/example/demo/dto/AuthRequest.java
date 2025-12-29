authreq
package com.example.demo.dto;
public class AuthRequest {
    private String email;
    private String password;
    public AuthRequest() {}
    public AuthRequest(String email, String password) { this.email = email; this.password = password; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}


Farmreq
package com.example.demo.dto;

public class FarmRequest {
    private String name;
    private Double soilPH;
    private Double waterLevel;
    private String season;

    public FarmRequest() {}
    public FarmRequest(String name, Double soilPH, Double waterLevel, String season) {
        this.name = name; this.soilPH = soilPH; this.waterLevel = waterLevel; this.season = season;
    }

    public String getName() { return name; }
    public Double getSoilPH() { return soilPH; }
    public Double getWaterLevel() { return waterLevel; }
    public String getSeason() { return season; }

    // Setters (Required for JSON mapping)
    public void setName(String name) { this.name = name; }
    public void setSoilPH(Double soilPH) { this.soilPH = soilPH; }
    public void setWaterLevel(Double waterLevel) { this.waterLevel = waterLevel; }
    public void setSeason(String season) { this.season = season; }
}
fertilizerreq
package com.example.demo.dto;

public class FertilizerRequest {
    private String name;
    private String npkRatio;
    private String recommendedForCrops;

    public FertilizerRequest() {}

    // Constructor that might be used by tests
    public FertilizerRequest(String name, String npkRatio, String recommendedForCrops) {
        this.name = name;
        this.npkRatio = npkRatio;
        this.recommendedForCrops = recommendedForCrops;
    }

    public String getName() { return name; }
    public String getNpkRatio() { return npkRatio; }
    public String getRecommendedForCrops() { return recommendedForCrops; }
    
    public void setName(String name) { this.name = name; }
    public void setNpkRatio(String npkRatio) { this.npkRatio = npkRatio; }
    public void setRecommendedForCrops(String recommendedForCrops) { this.recommendedForCrops = recommendedForCrops; }
}
registerreq
package com.example.demo.dto;
public class RegisterRequest {
    private String name; private String email; private String password;
    public RegisterRequest() {}
    public RegisterRequest(String n, String e, String p) { this.name = n; this.email = e; this.password = p; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}