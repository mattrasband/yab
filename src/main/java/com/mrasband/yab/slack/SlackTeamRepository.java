package com.mrasband.yab.slack;

import com.mrasband.yab.slack.api.model.SlackTeamAuthorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author matt.rasband
 */
@Repository
public interface SlackTeamRepository extends CrudRepository<SlackTeamAuthorization, String> {
}
