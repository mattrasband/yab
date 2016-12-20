package com.mrasband.yab.web;

import com.mrasband.yab.slack.SlackProperties;
import com.mrasband.yab.slack.api.SlackClient;
import com.mrasband.yab.slack.api.model.SlackTeamAuthorization;
import com.mrasband.yab.slack.SlackTeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import retrofit2.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Controller to handle the slack app installation, which is
 * effectively OAuth2 - but in the end we just need to save off
 * anything we care about (e.g. the various tokens).
 *
 * We could use the spring-oauth2 stuff, but it really
 * isn't necessary here since this is just the basic flow
 * for us to get the creds, after that we don't care about
 * their auth at all.
 *
 * @author matt.rasband
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class OAuth2Controller {
    private static final String SLACK_AUTH_URL = "https://slack.com/oauth/authorize";

    private final SlackProperties slackProperties;
    private final SlackClient slackClient;
    private final SlackTeamRepository slackTeamRepository;

    @Autowired
    public OAuth2Controller(SlackProperties slackProperties,
                            SlackClient slackClient,
                            SlackTeamRepository slackTeamRepository) {
        this.slackProperties = slackProperties;
        this.slackClient = slackClient;
        this.slackTeamRepository = slackTeamRepository;
    }

    @GetMapping("/add_to_slack")
    public RedirectView addToSlack(HttpServletRequest request, RedirectAttributes attrs) {
        attrs.addAttribute("scope", slackProperties.getApp().getScopes().stream()
                .collect(Collectors.joining(",")));
        attrs.addAttribute("client_id", slackProperties.getApp().getClientId());
        attrs.addAttribute("redirect_uri", host(request) + "/auth/callback");

        RedirectView view = new RedirectView(SLACK_AUTH_URL);
        view.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
        return view;
    }

    @GetMapping(value = "/callback")
    public RedirectView callback(@RequestParam(required = false) String code,
                                 @RequestParam(required = false) String state,
                                 @RequestParam(required = false) String error,
                                 HttpServletRequest request) {
        if (!StringUtils.isEmpty(error)) {
            log.error("Error from slack oauth flow: %s", error);
            return new RedirectView("/denied.html");
        }

        try {
            Response<SlackTeamAuthorization> res = slackClient.oauthAccess(
                    slackProperties.getApp().getClientId(),
                    slackProperties.getApp().getClientSecret(),
                    this.host(request) + "/auth/callback",
                    code).execute();
            assert res.isSuccessful();

            slackTeamRepository.save(res.body());
        } catch (Exception e) {
            log.error("Unable to exchange code for access token", e);
            return new RedirectView("/denied.html");
        }

        // Otherwise validate state and exchange code
        return new RedirectView("/welcome.html");
    }

    private String host(HttpServletRequest request) {
        String scheme = request.getScheme();

        if (!StringUtils.isEmpty(request.getHeader("x-forwarded-proto"))) {
            scheme = "https";
        }

        String host = String.format("%s://%s", scheme, request.getServerName());
        if (!Arrays.asList(80, 443).contains(request.getServerPort())) {
            host += ":" + Integer.toString(request.getServerPort());
        }
        return host;
    }
}
