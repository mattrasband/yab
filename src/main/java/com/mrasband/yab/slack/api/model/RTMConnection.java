package com.mrasband.yab.slack.api.model;

import com.mrasband.yab.slack.api.model.core.Channel;
import com.mrasband.yab.slack.api.model.core.Group;
import com.mrasband.yab.slack.api.model.core.MultipartyMessage;
import com.mrasband.yab.slack.api.model.core.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Response type for creating a new RTM connection,
 * provides the current state of the team (all users,
 * groups, channels, etc)
 *
 * @author matt.rasband
 */
@Data
public class RTMConnection {
    private Boolean ok;

    private String url;

    private Me self;

    private Team team;

    private List<User> users = new ArrayList<>();

    private List<Channel> channels = new ArrayList<>();

    private List<Group> groups = new ArrayList<>();

    private List<MultipartyMessage> ims = new ArrayList<>();

    private List<Bot> bots = new ArrayList<>();
}
