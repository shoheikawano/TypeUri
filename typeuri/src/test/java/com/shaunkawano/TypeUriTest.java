package com.shaunkawano;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TypeUriTest {

  @Test public void testParse() throws Exception {
    URI originalURI = URI.create("https://github.com");
    URI URIFromTypeUri = TypeUri.parse(originalURI).URI();

    assertTrue(originalURI.equals(URIFromTypeUri));
  }

  @Test public void testURI() throws Exception {
    URI originalURI = URI.create("https://github.com");
    URI URIFromTypeUri = TypeUri.parse(originalURI).URI();

    assertTrue(URIFromTypeUri.equals(originalURI));
  }

  @Test public void testIsNetwork() throws Exception {
    TypeUri httpUri = TypeUri.parse("http://shaunkawano.com");
    TypeUri httpsUri = TypeUri.parse("https://github.com");

    assertTrue(httpUri.isNetwork());
    assertTrue(httpsUri.isNetwork());
  }

  @Test public void testIsHttp() throws Exception {
    TypeUri httpUri = TypeUri.parse("http://shaunkawano.com");

    assertTrue(httpUri.isHttp());
  }

  @Test public void testIsHttps() throws Exception {
    TypeUri httpsUri = TypeUri.parse("https://github.com");

    assertTrue(httpsUri.isHttps());
  }

  @Test public void testIsHost() throws Exception {
    TypeUri githubUri = TypeUri.parse("https://github.com");
    TypeUri blogUri = TypeUri.parse("http://shaunkawano.com");

    assertTrue(githubUri.isHost("github.com"));
    assertTrue(blogUri.isHost("shaunkawano.com"));
  }

  @Test public void testIsScheme() throws Exception {
    TypeUri httpsUri = TypeUri.parse("https://github.com");
    TypeUri httpUri = TypeUri.parse("http://shaunkawano.com");

    assertTrue(httpsUri.isScheme("https"));
    assertTrue(httpUri.isScheme("http"));
  }

  @Test public void testHasQuery() throws Exception {
    TypeUri hasQueryUri = TypeUri.parse("https://github.com/trending?l=java");
    TypeUri hasNoQueryUri = TypeUri.parse("https://github.com/trending");

    assertTrue(hasQueryUri.hasQuery());
    assertTrue(hasQueryUri.hasQuery("l"));
    assertFalse(hasNoQueryUri.hasQuery());
  }

  @Test public void testPathSize() throws Exception {
    TypeUri zeroPathUri = TypeUri.parse("https://github.com");
    TypeUri zeroPathWithSlashUri = TypeUri.parse("https://github.com/");
    TypeUri onePathUri = TypeUri.parse("https://github.com/shaunkawano");
    TypeUri twoPathUri = TypeUri.parse("https://github.com/shaunkawano/typeuri");

    assertTrue(zeroPathUri.getPathSegments().size() == 0);
    assertTrue(zeroPathWithSlashUri.getPathSegments().size() == 0);
    assertTrue(onePathUri.getPathSegments().size() == 1);
    assertTrue(twoPathUri.getPathSegments().size() == 2);
  }

  @Test public void testQueryMap() throws Exception {
    TypeUri hasQueryType = TypeUri.parse("https://github.com/trending?l=java");
    Map<String, List<String>> queryMap = hasQueryType.queryMap();
    List<String> trendingQueryValues = queryMap.get("l");

    assertTrue(trendingQueryValues != null);
    assertTrue(trendingQueryValues.size() == 1);
  }

  @Test public void testGetPathSegments() throws Exception {
    TypeUri pathContainedUri = TypeUri.parse("https://github.com/shaunkawano");
    assertTrue(pathContainedUri.getPathSegments().size() == 1);
  }

  @Test public void testHasEmptyPath() throws Exception {
    TypeUri emptyPathUri = TypeUri.parse("https://github.com/");
    TypeUri pathContainedUri = TypeUri.parse("https://github.com/shaunkawano");

    assertFalse(pathContainedUri.hasEmptyPath());
    assertTrue(emptyPathUri.hasEmptyPath());
  }

  @Test public void testContainsQueryMap() throws Exception {
    TypeUri typeUri = TypeUri.parse("https://github.com/trending?l=java");
    String queryKey = "l";
    String queryValue = "java";
    Map<String, String> queryMap = new HashMap<>();
    queryMap.put(queryKey, queryValue);

    assertTrue(typeUri.containsQueryMap(queryMap));
    assertTrue(typeUri.containsQueryMap(queryKey, queryValue));
  }
}
