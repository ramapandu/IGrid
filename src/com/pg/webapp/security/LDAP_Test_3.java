package com.pg.webapp.security;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.security.sasl.AuthenticationException;

public class LDAP_Test_3  {
	 String ldapUrl = "ldaps://10.17.220.185:/DC=global,DC=sap,DC=corp"; 
	public  LDAP_Test_3(){
		
	}
  
	public boolean Authenticate() throws Exception{
    Hashtable env = new Hashtable();

    String sp = "com.sun.jndi.ldap.LdapCtxFactory";
    env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

    //TEST-----------
    env.put(Context.PROVIDER_URL, ldapUrl);

    DirContext dctx = new InitialDirContext(env);

//    String base = "ou=People";
    String base = "DC=global,DC=sap,DC=corp";

    SearchControls sc = new SearchControls();
    String[] attributeFilter = { "cn", "mail" };
    sc.setReturningAttributes(attributeFilter);
    sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

    String filter = "(&(sn=W*)(l=Criteria*))";

    NamingEnumeration results = dctx.search(base, filter, sc);
    while (results.hasMore()) {
      SearchResult sr = (SearchResult) results.next();
      Attributes attrs = sr.getAttributes();

      Attribute attr = attrs.get("cn");
      System.out.print(attr.get() + ": ");
      attr = attrs.get("mail");
      System.out.println(attr.get());
    }
    dctx.close();
    return true;
	}
	
	public static boolean performAuthentication() {

	    // service user
	    String serviceUserDN = "cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap";
	    String serviceUserPassword = "";

	    // user to authenticate
	    String identifyingAttribute = "cn";
	    String identifier = "c5245576";
	    String password = "Rp223366!";
	    String base = "ou=ASA,ou=SRO,ou=Identities,DC=global,DC=sap,DC=corp";

	    // LDAP connection info
	    String ldap = "10.17.220.185";
	    int port = 636;
	    String ldapUrl = "ldaps://" + ldap + ":" + port;

	    // first create the service context
	    DirContext serviceCtx = null;
	    try {
	        // use the service user to authenticate
	        Properties serviceEnv = new Properties();
	        serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        serviceEnv.put(Context.PROVIDER_URL, ldapUrl);
	        serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
	        serviceEnv.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
	        serviceEnv.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
//	        serviceEnv.put(Context.SECURITY_PRINCIPAL,"sAMAccountName" );
//	        serviceEnv.put(Context.
	        serviceCtx = new InitialDirContext(serviceEnv);
	        System.out.println("Name Space: "+serviceCtx.getNameInNamespace());
	        // we don't need all attributes, just let it get the identifying one
	        String[] attributeFilter = { identifyingAttribute };
	        SearchControls sc = new SearchControls();
	        sc.setReturningAttributes(attributeFilter);
	        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
//	        sc.Filter = "(SAMAccountName= {0} )";
	       

	        // use a search filter to find only the user we want to authenticate
	        String searchFilter = "(" + identifyingAttribute + "=" + identifier + ")";
	        NamingEnumeration<SearchResult> results = serviceCtx.search(base, "sAMAccountName={0}",sc);
//	        SearchResult searchResult = serviceCtx.search(
//	                base, searchFilter,
//	                "(sAMAccountName=*)");
	        if (results.hasMore()) {
	            // get the users DN (distinguishedName) from the result
	            SearchResult result = results.next();
	            String distinguishedName = result.getNameInNamespace();

	            // attempt another authentication, now with the user
	            Properties authEnv = new Properties();
	            authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	            authEnv.put(Context.PROVIDER_URL, ldapUrl);
	            authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
	            authEnv.put(Context.SECURITY_CREDENTIALS, password);
	            new InitialDirContext(authEnv);

	            System.out.println("Authentication successful");
	            return true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (serviceCtx != null) {
	            try {
	                serviceCtx.close();
	            } catch (NamingException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    System.err.println("Authentication failed");
	    return false;
	}
	
	
	public boolean LDAP_Test() throws AuthenticationException{
		 String serviceUserPassword = "";
		String url = "ldaps://10.17.220.185:636";
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap");
		env.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
		env.put(Context.REFERRAL, "follow");
		 
		try {
		    DirContext ctx = new InitialDirContext(env);
		    System.out.println("connected");
		    System.out.println(ctx.getEnvironment());
		     
		    // do something useful with the context...
		 
		    ctx.close();
		 
		} catch (AuthenticationNotSupportedException ex) {
		    System.out.println("The authentication is not supported by the server");
		} catch (NamingException ex) {
		    System.out.println("error when trying to create the context");
		}
		
		return true;
	}
	
	
//	public boolean LDAP_Test_5() throws AuthenticationException{
//		 String serviceUserDN = "cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap";
//		 String serviceUserPassword = "";
//
//	//build a hashtable containing all the necessary configuration parameters
//	Hashtable<String, String> environment = new Hashtable<String, String>();
//
//	environment.put(LdapContext.CONTROL_FACTORIES, "com.sun.jndi.ldap.ControlFactory");
//	environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//	environment.put(Context.PROVIDER_URL, "ldaps://10.17.220.185:636");
//	environment.put(Context.SECURITY_AUTHENTICATION, "simple");
//	environment.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
//	environment.put(Context.SECURITY_CREDENTIALS,serviceUserPassword);
//	environment.put(Context.STATE_FACTORIES, "PersonStateFactory");
//	environment.put(Context.OBJECT_FACTORIES, "PersonObjectFactory");
//
//	// connect to LDAP
//	DirContext ctx = new InitialDirContext(environment);
//
//	// Specify the search filter
//	String FILTER = "(&(objectClass=Person) (sAMAccountName= {0} ))";
//
//	// limit returned attributes to those we care about
//	String[] attrIDs = { "sn", "givenName" };
//
//	SearchControls ctls = new SearchControls();
//	ctls.setReturningAttributes(attrIDs);
//	ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//	// Search for objects using filter and controls
//	NamingEnumeration answer = ctx.search(searchBase, FILTER, ctls);
//
//	
//
//	SearchResult sr = (SearchResult) answer.next();
//	Attributes attrs = sr.getAttributes();
//	surName = attrs.get("sn").toString();
//	givenName = attrs.get("givenName").toString();
//	
//	}
	
//	public static void searchUserFromLdap(String samAccountName) throws Exception{
//		String serviceUserDN = "cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap";
//	    String serviceUserPassword = "";
//
//	    // user to authenticate
//	    String identifyingAttribute = "cn";
//	    String identifier = "c5245576";
//	    String password = "Rp223366!";
//	    String base = "ou=ASA,ou=SRO,ou=Identities,DC=global,DC=sap,DC=corp";
//
//	    // LDAP connection info
//	    String ldap = "10.17.220.185";
//	    int port = 636;
//	    String ldapUrl = "ldaps://" + ldap + ":" + port;
//
//	    // first create the service context
//	    DirContext serviceCtx = null;
//	    try {
//	        // use the service user to authenticate
//	        Properties serviceEnv = new Properties();
//	        serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//	        serviceEnv.put(Context.PROVIDER_URL, ldapUrl);
//	        serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
//	        serviceEnv.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
//	        serviceEnv.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
////	        serviceEnv.put(Context.SECURITY_PRINCIPAL,"sAMAccountName" );
////	        serviceEnv.put(Context.
//	        serviceCtx = new InitialDirContext(serviceEnv);
//
//	    SearchResult searchResult = serviceCtx.search("cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap", SearchScope.SUB, "(sAMAccountName=" + samAccountName +")"); 
//
//	    if(searchResult.getSearchEntries().size()<=0){
//	        System.out.println("No such user found in LDAP");
//	        return;
//	    }
//
//	    System.out.println("Start :- LDAP attributes for given user\n");
//	    for(SearchResultEntry searchResultEntry : searchResult.getSearchEntries()){
//
//	        System.out.println(searchResultEntry.toLDIFString());
//	    }
//
//	    System.out.println("\nEnd :- LDAP attributes for given user");
//
//	}
	
	public boolean getListOfAllSamAccountName() throws Exception {
		boolean conn=false;
		  // service user
	    String serviceUserDN = "cn=ASA1_viprSP01,OU=ASA,OU=SRO,OU=Resources,DC=global,DC=corp,DC=sap";
	    String serviceUserPassword = "";

	    // user to authenticate
	    String identifyingAttribute = "cn";
	    String identifier = "c5245576";
	    String password = "Rp223366!";
	    String base = "OU=Identities";

	    // LDAP connection info
	    String ldap = "10.17.220.185";
	    int port = 636;
	    String ldapUrl = "ldaps://" + ldap + ":" + port;

	    // first create the service context
	    DirContext serviceCtx = null;
	    try {
	        // use the service user to authenticate
	        Properties serviceEnv = new Properties();
	        serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        serviceEnv.put(Context.PROVIDER_URL, ldapUrl);
	        serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
	        serviceEnv.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
	        serviceEnv.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
	        serviceEnv.put(Context.REFERRAL, "follow");
//	        serviceEnv.put(Context.SECURITY_PRINCIPAL,"sAMAccountName" );
//	        serviceEnv.put(Context.
	        serviceCtx = new InitialDirContext(serviceEnv);
		
	    String[] samAccountNameList = null;
	    
	    SearchControls sc = new SearchControls();
//	    String[] attributeFilter = { identifyingAttribute };
        sc.setReturningAttributes(samAccountNameList);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    NamingEnumeration<SearchResult> searchResult = serviceCtx.search(base, "(sAMAccountName={0})",sc);
        conn=true;
	    if (searchResult.hasMore()) {
	        System.out.println(searchResult.next());
//	        return;
	    }
//	    samAccountNameList = new ArrayList<String>();
	    System.out.println("Start :- LDAP attributes for given user\n");
//	    for (SearchResultEntry searchResultEntry : searchResult.getSearchEntries()) {
//	        Attribute attribute = searchResultEntry
//	                .getAttribute("sAMAccountName");
//	        String samAccountName = attribute.getValue();
//
//	        samAccountNameList.add(samAccountName);
//
//	    }

	    System.out
		        .println("*******************************List of Same account Name******************************");
		for (String samAccountName : samAccountNameList) {

		    System.out.println(samAccountName);
		}

	    System.out.println("\nEnd :- LDAP attributes for given user");

	}
	    finally{
	    	 System.out.println("\nEnd :- ");	
	    }
	return conn;
  }
}
