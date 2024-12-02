package io.camunda.demo.process_payments.dto.response;

import java.util.Map;

public class LoginResponse {

    String odataMetadata;
    String sessionId;
    String version;
    String timeout;

    public String getVersion() {
        return version;
    }

    public LoginResponse(Map response) {
        this.odataMetadata = (String) response.get("odata.metadata");
        this.sessionId = (String) response.get("SessionId");
        this.version = (String) response.get("Version");
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


}
