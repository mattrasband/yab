package com.mrasband.yab.slack.api;

import com.mrasband.yab.slack.api.model.AuthRevocation;
import com.mrasband.yab.slack.api.model.AuthTest;
import com.mrasband.yab.slack.api.model.BotInfo;
import com.mrasband.yab.slack.api.model.ChannelCreate;
import com.mrasband.yab.slack.api.model.ChatPostMessageResult;
import com.mrasband.yab.slack.api.model.FileInfo;
import com.mrasband.yab.slack.api.model.FileList;
import com.mrasband.yab.slack.api.model.RPCResult;
import com.mrasband.yab.slack.api.model.RTMStart;
import com.mrasband.yab.slack.api.model.SlackTeamAuthorization;
import com.mrasband.yab.slack.api.model.messaging.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * Client endpoints for slack, if your token doesn't have the
 * appropriate permissions the "ok" param will be false and
 * the errors field will likely be populated.
 *
 * Note, that many of these require specific oauth scopes, your
 * app needs to request only what it will use up front.
 *
 * @author matt.rasband
 */
public interface SlackClient {
    @GET("auth.revoke")
    Call<AuthRevocation> authRevoke(@Query("token") String token);

    @GET("auth.revoke")
    Call<AuthRevocation> authRevoke(@Query("token") String token,
                                    @Query("test") Boolean test);

    @GET("auth.test")
    Call<AuthTest> authTest(@Query("token") String token);

    @GET("bots.info")
    Call<BotInfo> botInfo(@Query("token") String token,
                          @Query("bot") String botId);

    @GET("channels.archive")
    Call<RPCResult> channelArchive(@Query("token") String token,
                                   @Query("channel") String channelId);

    @GET("channels.create")
    Call<ChannelCreate> channelCreate(@Query("token") String token,
                                      @Query("name") String name);

    @GET("files.list")
    Call<List<FileList>> fileList(@Query("token") String token,
                                  @Query("user") String userId,
                                  @Query("channel") String channelId,
                                  @Query("ts_from") Integer fromTimestamp,
                                  @Query("ts_to") Integer toTimestamp,
                                  @Query("types") String types,
                                  @Query("count") Integer count,
                                  @Query("page") Integer page);

    @GET("files.info")
    Call<FileInfo> fileInfo(@Query("token") String token,
                            @Query("file") String file);

    @GET("files.info")
    Call<FileInfo> fileInfo(@Query("token") String token,
                            @Query("file") String file,
                            @Query("count") Integer count,
                            @Query("page") Integer page);

    @GET("oauth.access")
    Call<SlackTeamAuthorization> oauthAccess(@Query("client_id") String clientId,
                                             @Query("client_secret") String clientSecret,
                                             @Query("redirect_uri") String redirectUri,
                                             @Query("code") String code);

    /**
     * Send a message to slack.
     *
     * This shouldn't be consumed directly and should instead be used through
     * a service. This is because the way slack wants the payload for this is
     * the attachments are stringified JSON, and the main body is url-encoded.
     */
    @POST("chat.postMessage")
    @FormUrlEncoded
    Call<ChatPostMessageResult> postMessage(@Field("token") String token,
                                            @Field("text") String text,
                                            @Field("type") String type,
                                            @Field("channel") String channel,
                                            @Field("attachments") String attachments);

    /**
     * Query the server for the RTM info, this is aware of MPIMs (multi-person ims)
     */
    @GET("rtm.start?mpim_aware=1")
    Call<RTMStart> rtmStart(@Query("token") String token);
}
