package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.requester.Requestable;

import java.util.ArrayList;
import java.util.List;

public abstract class MatchInfoRequester implements Requestable {
    protected final String baseURLString;
    protected MatchInfoRequester(String baseURLString) {
        this.baseURLString = baseURLString;
    }
    protected void addQueryParam(List<String> paramList, String key, String value) {
        paramList.add(key + "=" + value);
    }

    public String getRequestUrlString(List<String> paramList) {
        StringBuilder requestUrlString = new StringBuilder(baseURLString);

        boolean isFirstParam = true;
        for (String queryParam : paramList) {
            if(isFirstParam) {
                requestUrlString.append("?");
                isFirstParam = false;
            }
            else requestUrlString.append("&");

            requestUrlString.append(queryParam);
        }

        return requestUrlString.toString();
    }
}
