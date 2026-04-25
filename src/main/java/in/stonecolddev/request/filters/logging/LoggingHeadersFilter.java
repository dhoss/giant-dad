package in.stonecolddev.request.filters.logging;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.ServerFilterPhase;
import io.micronaut.http.util.HttpHeadersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN;

@ServerFilter(MATCH_ALL_PATTERN)
public class LoggingHeadersFilter implements Ordered {

  private static final Logger log = LoggerFactory.getLogger(LoggingHeadersFilter.class);

  @RequestFilter
  void filterRequest(HttpRequest<?> request) {
    HttpHeadersUtil.trace(log, request.getHeaders());
  }

  @Override
  public int getOrder() {
    return ServerFilterPhase.FIRST.order();
  }
}