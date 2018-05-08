import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Demonstrates how to update a page using the Confluence 5.5 REST API.
 */
public class Main
{
//    private static final String BASE_URL = "http://confluence.ca.sbrf.ru";
//    private static final String USERNAME = "SBT-xxxxx-AA";
//    private static final String PASSWORD = "xxxxxxxx123ZZZ";
//    private static final String ENCODING = "utf-8";


    private static String getContentRestUrl(final Long contentId, final String[] expansions) throws UnsupportedEncodingException {

        final String expand = URLEncoder.encode(StringUtils.join(expansions, ","), GetPass.ENCODING);

        return String.format("%s/rest/api/content/%s?expand=%s&os_authType=basic&os_username=%s&os_password=%s", GetPass.BASE_URL, contentId, expand, URLEncoder.encode(GetPass.USERNAME, GetPass.ENCODING), URLEncoder.encode(GetPass.PASSWORD, GetPass.ENCODING));
    }


    public static void main(final String[] args) throws Exception
    {
//method to read text-body from file
        GetPass.getCredentials();
        ReadFile.readFile();

        final long pageId = Integer.parseInt(GetPass.PAGE_ID);

        HttpClient client = new DefaultHttpClient();

        // Get current page version
        String pageObj = null;
        HttpEntity pageEntity = null;
        try
        {
            HttpGet getPageRequest = new HttpGet(getContentRestUrl(pageId, new String[] {"body.storage", "version", "ancestors"}));
            HttpResponse getPageResponse = client.execute(getPageRequest);
            pageEntity = getPageResponse.getEntity();

            pageObj = IOUtils.toString(pageEntity.getContent());

            System.out.println("Get Page Request returned " + getPageResponse.getStatusLine().toString());
            System.out.println("");
            System.out.println(pageObj);
        }
        finally
        {
            if (pageEntity != null)
            {
                EntityUtils.consume(pageEntity);
            }
        }

        // Parse response into JSON
        JSONObject page = new JSONObject(pageObj);

        // Update page
        // The updated value must be Confluence Storage Format (https://confluence.atlassian.com/display/DOC/Confluence+Storage+Format), NOT HTML.
        page.getJSONObject("body").getJSONObject("storage").put("value", ReadFile.textFromTXTFile);

        int currentVersion = page.getJSONObject("version").getInt("number");
        page.getJSONObject("version").put("number", currentVersion + 1);

        // Send update request
        HttpEntity putPageEntity = null;

        try
        {
            HttpPut putPageRequest = new HttpPut(getContentRestUrl(pageId, new String[]{}));

            StringEntity entity = new StringEntity(page.toString(), ContentType.APPLICATION_JSON);
            putPageRequest.setEntity(entity);

            HttpResponse putPageResponse = client.execute(putPageRequest);
            putPageEntity = putPageResponse.getEntity();

            System.out.println("Put Page Request returned " + putPageResponse.getStatusLine().toString());
            System.out.println("");
            System.out.println(IOUtils.toString(putPageEntity.getContent()));
        }
        finally
        {
            EntityUtils.consume(putPageEntity);
        }
    }
}
