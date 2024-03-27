package futsal.futsalMatch.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class MatchInfoRequester implements Requestable{
    protected final String baseURLString;
    private final List<String> queryParams = new ArrayList<>();

    protected MatchInfoRequester(String baseURLString) {
        this.baseURLString = baseURLString;
    }

    protected void addQueryParam(String key, String value) {
        queryParams.add(key + "=" + value);
    }

    public String getRequestUrlString() {
        StringBuilder requestUrlString = new StringBuilder(baseURLString);
        boolean queryAdded = false;
        for (String queryParam : queryParams) {
            if(!queryAdded) requestUrlString.append("?");
            else requestUrlString.append("&");
            requestUrlString.append(queryParam);
            queryAdded = true;
        }

        return requestUrlString.toString();
    }
}
