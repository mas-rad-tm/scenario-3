package lab.application.api.web.common;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RequestMatcher permettant au filtre jwt de skipper les path définis comme public
 */
public class JwtSkipPathRequestMatcher implements RequestMatcher{

    private OrRequestMatcher matchers;
    private RequestMatcher processingMatcher;

    public JwtSkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        Objects.nonNull(pathsToSkip);
        List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        matchers = new OrRequestMatcher(m);
        processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (matchers.matches(request)) {
            return false;
        }
        return processingMatcher.matches(request) ? true : false;
    }
}
