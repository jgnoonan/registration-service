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

    public String getUrl() {
        LOG.debug("LDAP URL accessed: " + url);
        return url;
    }

    public void setUrl(String url) {
        LOG.debug("Setting LDAP URL: " + url);
        this.url = url;
    }

    public String getBaseDn() {
        LOG.debug("LDAP BaseDn accessed: " + baseDn);
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        LOG.debug("Setting LDAP BaseDn: " + baseDn);
        this.baseDn = baseDn;
    }

    public String getUserFilter() {
        LOG.debug("LDAP UserFilter accessed: " + userFilter);
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        LOG.debug("Setting LDAP UserFilter: " + userFilter);
        this.userFilter = userFilter;
    }

    public String getBindDn() {
        LOG.debug("LDAP BindDn accessed: " + bindDn);
        return bindDn;
    }

    public void setBindDn(String bindDn) {
        LOG.debug("Setting LDAP BindDn: " + bindDn);
        this.bindDn = bindDn;
    }

    public String getBindPassword() {
        LOG.debug("LDAP BindPassword accessed.");
        return bindPassword;
    }

    public void setBindPassword(String bindPassword) {
        LOG.debug("Setting LDAP BindPassword.");
        this.bindPassword = bindPassword;
    }
}
