package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * When added to a slack team, these are the things we usually get.
 *
 * Items may be null or missing if the user doesn't include all the
 * available permission types.
 *
 * The enclosed types of this are a bit different than the normal API
 * models.
 *
 * @author matt.rasband
 */
@Data
@RedisHash("teams")
public class SlackTeamAuthorization {
    private Boolean ok;

    /**
     * The access token for us to talk to Slack, this
     * is unique from the bot token because it gives
     * the permissions requested with the oauth2 scopes
     * for the APP (note: we aren't authed as a user, except
     * the limited bot user below.).
     */
    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    /**
     * User that added us?
     */
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("team_name")
    private String teamName;

    @Id
    @JsonProperty("team_id")
    private String teamId;

    /**
     * The channel that we were added to for the webhook.
     *
     * Note:
     * 1. bots can post to any channel that they are a member of.
     * 2. slash commands can reply to any channel they were invoked from.
     */
    @JsonProperty("incoming_webhook")
    private Webhook incomingWebhook;

    /**
     * The app bot user, they have limited permissions to the Web API
     * but can pretty much respond to anything over the RTM API -
     * since those messages are simple (text only).
     *
     * Note that if the app requests additional scopes, the app's token
     * can be used to get more info for the bot user to utilize.
     *
     * @see <a href="https://api.slack.com/bot-users#api_usage">API Usage</a>
     */
    private Bot bot;

    @Data
    @SuppressWarnings("WeakerAccess")
    public static class Webhook {
        private String channel;

        @JsonProperty("channel_id")
        private String channelId;

        @JsonProperty("configuration_url")
        private String configurationUrl;

        private String url;
    }

    @Data
    @SuppressWarnings("WeakerAccess")
    public static class Bot {
        @JsonProperty("bot_user_id")
        private String botUserId;

        @JsonProperty("bot_access_token")
        private String botAccessToken;
    }
}
