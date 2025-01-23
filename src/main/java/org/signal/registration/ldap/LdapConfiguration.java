/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package org.signal.registration.ldap;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@ConfigurationProperties("micronaut.registration.ldap")
public class LdapConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(LdapConfiguration.class);

    private String url;
    private String baseDn;
    private String userFilter;
    private String bindDn;
    private String bindPassword;
    private int port = 389;
    private boolean useSsl = false;
    private int connectionTimeout = 5000;
    private int readTimeout = 30000;
    private int maxPoolSize = 10;
    private int minPoolSize = 3;
    private long poolTimeout = 300000;
    private int maxRetries = 3;
    private String phoneNumberAttribute = "telephoneNumber";

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

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
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