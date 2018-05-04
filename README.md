# Authdemo2 OAuth2 Authorization Demonstration 

This demonstration consists of two applications 
* [Authdemo1](https://github.com/tarnaldo/Authdemo1) 
* Authdemo2

## Summary 

This application is configured as a microservice API using Spring Boot.  It uses Spring Security to protect the end points using a Bearer Access Token in the Authorizatiion Header.

Scope is used to secure endpoints. When configuring API Services in Auth0, specify the scope used.  This demonstration is set up with two scopes coming from Authdemon1.  If another API were to call this, it could specify just read scope instead of read and update.

This  demonstrates a lighter weight implementation of the Spring Security.  The latest versions reduce the effort for Token Verification.
  
## Special Notes When Reusing
**Be Sure to remove log statements containing secrets.**
The log statements are there for demonstration purposes and should be revised