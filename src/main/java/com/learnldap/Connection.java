package com.learnldap;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

public class Connection {
    public DirContext connectionLdap(){
        DirContext connection = null;
        Properties env = new Properties ();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");

        // Now we will create a connection:
        try {
            connection = new InitialDirContext (env);
            System.out.println ("===Connected to LDAP server=== "+ connection);

        }catch(AuthenticationException e) {
            System.out.println (e.getMessage());
        } catch (NamingException e) {
            throw new RuntimeException (e);
        }
        return connection;
    }
}
