/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.signal.registration.ldap;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@ConfigurationProperties("micronaut.config.registration.ldap")
public class LdapConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(LdapConfiguration.class);

    @Value("${micronaut.config.registration.ldap.url}")
    private String url;

    @Value("${micronaut.config.registration.ldap.baseDn}")
    private String baseDn;

    @Value("${micronaut.config.registration.ldap.userFilter:(uid={0})}")
    private String userFilter;

    @Value("${micronaut.config.registration.ldap.bindDn:}")
    private String bindDn;

    @Value("${micronaut.config.registration.ldap.bindPassword:}")
    private String bindPassword;

    @Value("${micronaut.config.registration.ldap.phoneNumberAttribute:mobile}")
    private String phoneNumberAttribute;

    @Value("${micronaut.config.registration.ldap.port:389}")
    private int port;

    @Value("${micronaut.config.registration.ldap.useSsl:false}")
    private boolean useSsl;

    @Value("${micronaut.config.registration.ldap.connectionTimeout:5000}")
    private int connectionTimeout;

    @Value("${micronaut.config.registration.ldap.readTimeout:30000}")
    private int readTimeout;

    @Value("${micronaut.config.registration.ldap.minPoolSize:3}")
    private int minPoolSize;

    @Value("${micronaut.config.registration.ldap.maxPoolSize:10}")
    private int maxPoolSize;

    @Value("${micronaut.config.registration.ldap.poolTimeout:300000}")
    private long poolTimeout;

    @Value("${micronaut.config.registration.ldap.maxRetries:3}")
    private int maxRetries;

    // Existing getters/setters
    public String getUrl() {
        LOG.debug("LDAP URL accessed: {}", url);
        return url;
    }

    public void setUrl(String url) {
        LOG.debug("Setting LDAP URL: {}", url);
        this.url = url;
    }

    public String getBaseDn() {
        LOG.debug("LDAP BaseDn accessed: {}", baseDn);
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        LOG.debug("Setting LDAP BaseDn: {}", baseDn);
        this.baseDn = baseDn;
    }

    public String getUserFilter() {
        LOG.debug("LDAP UserFilter accessed: {}", userFilter);
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        LOG.debug("Setting LDAP UserFilter: {}", userFilter);
        this.userFilter = userFilter;
    }

    public String getBindDn() {
        LOG.debug("LDAP BindDn accessed: {}", bindDn);
        return bindDn;
    }

    public void setBindDn(String bindDn) {
        LOG.debug("Setting LDAP BindDn: {}", bindDn);
        this.bindDn = bindDn;
    }

    public String getBindPassword() {
        LOG.debug("LDAP BindPassword accessed");
        return bindPassword;
    }

    public void setBindPassword(String bindPassword) {
        LOG.debug("Setting LDAP BindPassword");
        this.bindPassword = bindPassword;
    }

    // New getters/setters
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUseSsl() {
        return useSsl;
    }

    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getPoolTimeout() {
        return poolTimeout;
    }

    public void setPoolTimeout(long poolTimeout) {
        this.poolTimeout = poolTimeout;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getPhoneNumberAttribute() {
        LOG.debug("LDAP phone number attribute accessed: {}", phoneNumberAttribute);
        return phoneNumberAttribute;
    }

    public void setPhoneNumberAttribute(String phoneNumberAttribute) {
        LOG.debug("Setting LDAP phone number attribute: {}", phoneNumberAttribute);
        this.phoneNumberAttribute = phoneNumberAttribute;
    }
}