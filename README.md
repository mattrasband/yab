# yab: Yet _Another_ Bot

This is actually more than just a bot, it's a starter to build [Slack Apps](https://api.slack.com/apps) with rich content messages.

## Usage:

Required:

* Create a [new slack app](https://api.slack.com/apps?new_app=1)
* Set the environmental variables: `YAB_SLACK_CLIENT_ID`, `YAB_SLACK_CLIENT_SECRET`, `YAB_SLACK_VERIFICATION_TOKEN` (or place them in `src/main/resources/application.yml`, make sure to not check them in!)
* Add any custom scopes to `application.yml`
* Set the _OAuth Redirect URL(s)_ (The default path we handle is `/auth/callback`, just prepend your TLS protected server info)
* Have redis running (or define a different storage repository implementation)
    * If you are using the Spring `local` profile, it should be on `localhost:6379`
    * `docker run -d -p 6379:6379 redis:latest`

Optional, only enable what you need:

* Enable the [Events API](https://api.slack.com/events-api) - If you want to get Events via HTTP
    * You will need to add event handlers to capitalize on this (see below)
* Enable and define [Slash Commands](https://api.slack.com/slash-commands) - If you want to enable slash commands (ex. `/topic`)
    * You will need to implement the endpoints for this (see below)
* Enable a [Bot User](https://api.slack.com/bot-users) - If you want to be able to send messages back to slack
    * You want this, though it is technically _optional_

Fire the app up:

    ./mvnw clean spring-boot:run -Drun.profiles=local

You will need SSL protected endpoints to hit, you can use [ngrok](https://ngrok.com/) for local development.

### Install with a Team

Just navigate in your browser to the server root, the index page will have the "Add To Slack" link set up for you.

You probably want to customize the `index.html` to educate people about your app and do marketing.

## Building an App/Bot

The above just gets you my defaults, which is mostly just rubbish.

### RTM API (Real Time Messaging)

**Currently not included**

For now I chose to leave this out for a few reasons:

1. No abstraction is better than the wrong abstraction...
1. Simplicity - It's frankly simpler to cover more use-cases as a base app with the Web Events API (slack calls you)
1. Scale - This should handle a fair number of RTM connections, but at some point it's not trivial to load balance server-initiated connections across multiple instances.
1. The main use case for the RTM Api is probably a chat bot (which can still be done via the Events API), otherwise your users need to know too much about how to run your commands. Using Slash Commands gives your users helpful usage prompts.

### Events API

An alternative to RTM is the [Events API](https://api.slack.com/events-api) - which offloads the work of finding events to Slack. The benefits here are fairly obvious fairly quickly:
 
1. No need to manage a WebSocket (or N number of websockets for all the teams you service), which can fail for any number of reasons
1. Is easy to load balance and put in/out of service at a moment's notice

This is automatically mounted for you at `/slack/webevent`, which does the standard things for you:

1. Parse the event into a subtype of the `Event` class
1. Responds automatically to the `challenge` request (basically slack makes you echo a challenge back)
1. Delegates through the `SlackService` to a handler.

If you enable this in the Slack App console, be sure to include the path `/slack/webevent` and set whatever scopes you need in the `application.yml` for the types.

### Slash Commands

[Slack Documentation](https://api.slack.com/slash-commands)

Slash commands are awesome, Slack will automatically add the help for them in your user's default client - saving them from needing to remember magic trigger words. 

This takes two coordinating pieces, both out of the control of a "framework" (or in our case, base app)

1. Write some endpoints, you can use the `ExampleSlashController` as an example to get going.
1. Create the Slash Commands in the OAuth console, point them at your running app (example: `https://<hash>.ngrok.io/slash/weather`)

From here on out, they are registered with Slack - so you can go to your team and try them out in any room! 

    /weather ...

Note: at the moment, you need to manually validate the token for slash commands. **You should always validate this.** (TODO: annotation to validate slash commands)

A simple slash handler could be:

    // This is just a dumb text message, no real special formatting. 
    @PostMapping(value = "/echo",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Message echo(SlashCommand cmd) {
        return Message.builder()
            .text(cmd.getText())
            .build();
    }

Note, that we do support the richer message API, giving you the ability to add Actions and various Fields to your messages (see below)

### Rich Messages

Slack supports a number of rich message types allowing you to attach content, they provide sane formatting for your content to keep the client cohesive - but allowing you to provide some workflow based power to your users.

These messages all start from the same base: `Message`, which is a builder for convenience if they add more fields.

