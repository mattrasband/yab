package com.mrasband.yab.slack.api;

import com.mrasband.yab.slack.api.model.SlackTeamAuthorization;
import com.mrasband.yab.slack.api.model.RTMConnection;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * Client endpoints for slack
 *
 * @author matt.rasband
 */
public interface SlackClient {
    /**
     * Query the server for the RTM info, this is aware of MPIMs (multi-person ims)
     */
    @GET("rtm.start?mpim_aware=1")
    Call<RTMConnection> connect(@Query("token") String token);

    /**
     * Send a message to slack.
     *
     * This shouldn't be consumed directly and should instead be used through
     * a service. This is because the way slack wants the payload for this is
     * the attachments are stringified JSON, and the main body is url-encoded.
     */
    @POST("chat.postMessage")
    @FormUrlEncoded
    Call<Object> postMessage(@Body Map<String, Object> message);

    @GET("files.list")
    Call<List<Object>> fileList(@Query("token") String token);

    @GET("files.info")
    Call<Object> fileInfo(@Query("token") String token,
                          @Query("file") String file);

    @GET("oauth.access")
    Call<SlackTeamAuthorization> exchangeForAccessToken(@Query("client_id") String clientId,
                                                        @Query("client_secret") String clientSecret,
                                                        @Query("redirect_uri") String redirectUri,
                                                        @Query("code") String code);
}
