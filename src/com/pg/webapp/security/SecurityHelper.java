package com.pg.webapp.security;

import javax.naming.directory.DirContext;

public class SecurityHelper{


public boolean authenticate(String userDn, String credentials) {
  DirContext ctx = null;
//  try {
//    ctx = contextSource.getContext(userDn, credentials);
//    return true;
//  } catch (Exception e) {
//    logger.error("Login failed", e);
//    return false;
//  } finally {
//    LdapUtils.closeContext(ctx);
return false;
  }
}
