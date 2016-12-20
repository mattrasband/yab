package com.mrasband.yab.config;

import org.apache.catalina.session.StandardManager;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author matt.rasband
 */
@Configuration
@ConditionalOnClass(Tomcat.class)
public class SessionConfig {
    /**
     * Disable the tomcat sessions on disk, pretty much just
     * disables a cache error on some boots.
     */
    @Bean
    EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addContextCustomizers((TomcatContextCustomizer) context -> {
            if (context.getManager() instanceof StandardManager) {
                ((StandardManager) context.getManager()).setPathname("");
            }
        });
        return tomcat;
    }
}
