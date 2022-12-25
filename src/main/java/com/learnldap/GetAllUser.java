package com.learnldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import javax.naming.directory.*;
import java.util.Properties;

public class GetAllUser {
    // ===========Getting All User====================
    public void allUser() throws NamingException {
        String searchFilter = "(objectClass=inetOrgPerson)";
        String[] reqAtt = {"cn", "sn", "uid"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);

        // Call the connection:
        Connection con = new Connection ();
        DirContext connection = con.connectionLdap();

        // We need to get the all user:
        NamingEnumeration users = connection.search("ou=users, ou=system", searchFilter, controls);

        // Print the user:
        SearchResult result = null;
        while (users.hasMore()) {
            result = (SearchResult) users.next();
            Attributes attr = result.getAttributes ();
            System.out.println ("uid: " + attr.get("uid").get());
            System.out.println ("cn: " + attr.get("cn").get());
            System.out.println ("sn: " + attr.get("sn").get());
        }
    }


    // ==============Adding the User======================
    public void addUser(){
        //Basic configuration before adding the user:
        Attributes attributes = new BasicAttributes ();
        Attribute attr = new BasicAttribute ("objectClass");
        attr.add ("inetOrgPerson");

        attributes.put(attr);

        // Details about user:
        attributes.put("sn", "Kumar");
        attributes.put("cn", "Tom");
        attributes.put("userPassword", "5");




        // Call the connection:
        Connection con = new Connection ();
        DirContext connection = con.connectionLdap();

        try {
            connection.createSubcontext ("uid=5, ou=users, ou=system", attributes);
            System.out.println ("===User added successfully=== ");
        } catch (NamingException e) {
            throw new RuntimeException (e);
        }
    }

    public static boolean authUser(String userName, String password) throws NamingException {
        try{
            Properties env = new Properties ();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
            env.put(Context.SECURITY_PRINCIPAL, "cn="+userName+",ou=users,ou=system");
            env.put(Context.SECURITY_CREDENTIALS, password);
            DirContext ctx = new InitialDirContext(env);
            ctx.close();
            return true;
        }catch(Exception e) {
            System.out.println (e.getMessage ());
            return false;
        }


    }

    public static void main(String[] args) {
        GetAllUser getAllUser = new GetAllUser ();
//        try {
////            getAllUser.allUser ();
//              getAllUser.addUser ();
////            System.out.println (getAllUser.authUser("mike", "2"));;
//
//        } catch (NamingException e) {
//            e.printStackTrace ();
//        }


    }
}
