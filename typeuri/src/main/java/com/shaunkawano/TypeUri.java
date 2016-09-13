/*
 *
 *  * Copyright (C) 2016 Shohei Kawano.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.shaunkawano;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class TypeUri {

  private final URI uri;
  private final List<String> pathSegments;
  private final Map<String, List<String>> queryMap;

  public static TypeUri parse(URI uri) {
    return new TypeUri(uri);
  }

  public static TypeUri parse(String url) {
    return new TypeUri(URI.create(url));
  }

  private TypeUri(URI uri) {
    this.uri = uri;
    this.pathSegments = parsePathSegments(uri.getPath());

    try {
      this.queryMap = createQueryMap(uri.getQuery());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public URI URI() {
    return uri;
  }

  public Map<String, List<String>> queryMap() {
    return queryMap;
  }

  public List<String> getPathSegments() {
    return pathSegments;
  }

  public boolean hasEmptyPath() {
    return pathSegments.isEmpty();
  }

  public boolean isNetwork() {
    String scheme = uri.getScheme();
    return "http".equals(scheme) || "https".equals(scheme);
  }

  public boolean isHttp() {
    return "http".equals(uri.getScheme());
  }

  public boolean isHttps() {
    return "https".equals(uri.getScheme());
  }

  public boolean isHost(String host) {
    return host.equals(uri.getHost());
  }

  public boolean isScheme(String scheme) {
    return scheme.equals(uri.getScheme());
  }

  public boolean hasQuery() {
    return uri.getQuery() != null;
  }

  public boolean hasQuery(String query) {
    if (queryMap == null || queryMap.isEmpty()) {
      return false;
    }

    return queryMap.containsKey(query);
  }

  public boolean containsQueryMap(Map<String, String> queryMap) {
    if (queryMap == null || queryMap.isEmpty()) {
      return false;
    }

    for (Map.Entry<String, String> query : queryMap.entrySet()) {
      if (containsQueryMap(query.getKey(), query.getValue())) {
        return true;
      }
    }

    return false;
  }

  public boolean containsQueryMap(String queryKey, String queryValue) {
    List<String> queryValues = queryMap.get(queryKey);
    if (queryValues == null || queryValues.isEmpty()) {
      return false;
    }

    for (int i = 0, size = queryValues.size(); i < size; i++) {
      if (queryValue.equals(queryValues.get(i))) {
        return true;
      }
    }

    return false;
  }

  private Map<String, List<String>> createQueryMap(String query)
      throws UnsupportedEncodingException {
    if (query == null || query.isEmpty()) {
      return Collections.emptyMap();
    }

    String[] queryParameters = query.split("&");
    int length = queryParameters.length;
    Map<String, List<String>> queryMap = new LinkedHashMap<>(length);

    for (int i = 0; i < length; i++) {
      String parameter = queryParameters[i];
      int equalIndex = parameter.indexOf("=");

      String key;
      if (equalIndex > 0) {
        key = URLDecoder.decode(parameter.substring(0, equalIndex), "UTF-8");
      } else {
        key = parameter;
      }

      if (!queryMap.containsKey(key)) {
        queryMap.put(key, new LinkedList<String>());
      }

      String value;
      if (equalIndex > 0 && parameter.length() > equalIndex + 1) {
        value = URLDecoder.decode(parameter.substring(equalIndex + 1), "UTF-8");
      } else {
        value = "";
      }

      queryMap.get(key).add(value);
    }

    return queryMap;
  }

  private List<String> parsePathSegments(String pathString) {
    if (pathString == null || pathString.isEmpty()) {
      return Collections.emptyList();
    }

    String[] splitter = pathString.split("/");
    List<String> pathSegments = new ArrayList<>(splitter.length);
    for (int i = 0, length = splitter.length; i < length; i++) {
      String pathSegment = splitter[i];
      if (!pathSegment.isEmpty()) {
        pathSegments.add(pathSegment);
      }
    }

    if (pathSegments.isEmpty()) {
      return Collections.emptyList();
    } else {
      return pathSegments;
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypeUri typeUri = (TypeUri) o;
    return uri != null ? uri.equals(typeUri.uri)
        : typeUri.uri == null && (queryMap != null ? queryMap.equals(typeUri.queryMap)
            : typeUri.queryMap == null && (pathSegments != null ? pathSegments.equals(
                typeUri.pathSegments) : typeUri.pathSegments == null));
  }

  @Override public int hashCode() {
    int result = uri != null ? uri.hashCode() : 0;
    result = 31 * result + (queryMap != null ? queryMap.hashCode() : 0);
    result = 31 * result + (pathSegments != null ? pathSegments.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return String.format("TypeUri{uri=%s, queryMap=%s, pathSegments=%s}", uri, queryMap,
        pathSegments);
  }
}
