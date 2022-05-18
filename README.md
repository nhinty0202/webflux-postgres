## **Friend management**

### **1. Introduction**
The API server that does simple "Friend Management"

### **2. Requirements**

Install `Java 11`

Install `Postgres 14.2` (if running locally)

Install a Spring supported IDE like `IntelliJ IDEA 2021.2`

### **3. Run project**
Guideline to run project from IntelliJ IDEA 2022.1:

#### **3.1. Create database schema and tables (if running locally)**
Form MySQL, create a database with name `productdb`

Change login info (username, password) in this project's `application.properties`

Run SQL commands in `init.sql` to create schema & create table in schema.

#### **3.2. Config Project SDK in IntelliJ (for building project)**
From IntelliJ IDEA, open `Project Structure` (`File` -> `Project Structure`)

Set both `Project SDK` and `Project language level` to Java SDK 8

### **3.3. Modify Run configuration and config Java SDK in Run configuration (for running project)**
Open file `FriendManagementApplication.java` in Project

Right click on class name -> `More Run/Debug` -> `Modify Run Configuration...

In `Create Run Configuration` window, set `JRE` to Java 8 and `Active profiles` to `local`

#### **3.4. Run project**
Click `Run FriendManagementApplication` button in IntelliJ or press `Shift+F10`


### **4. Document**
#### **4.1. API 1**
**METHOD**:  POST

**API_NAME**: /add-friend

**PURPOSE**:  Create a friend connection between two email addresses

**REQUEST**: 

    {
    
       "friends":
       
	    [
    
           "user1@gmail.com",
           
           "user2@gmail.com"
           
	    ]   
       
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true    
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }

#### **4.2. API 2**
**METHOD**:  GET

**API_NAME**: /friends

**PURPOSE**: Retrieve the friends list for an email address

**REQUEST**: 

    {
       "email": "user1@gmail.com"   
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true    
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }
#### **4.3. API 3**
**METHOD**:  GET

**API_NAME**: /friends-between

**PURPOSE**: Retrieve the common friends list between two email addresses

**REQUEST**: 

    {
       "friends":
       
	    [
    
           "user1@gmail.com",
           
           "user2@gmail.com"
           
	    ]    
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true ,
       "friends":[
	       "user3@gmail.com
	    ],
	    "count": 1  
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }

#### **4.4. API 4**
**METHOD**:  POST

**API_NAME**: /subscribe

**PURPOSE**: Subscribe to update from an email address

**REQUEST**: 

    {
       "requester":  "user1@gmail.com",
       "target":  "user2@gmail.com"   
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true   
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }
   
   #### **4.5. API 5**
**METHOD**:  POST

**API_NAME**: /block

**PURPOSE**: Block updates form an email address

**REQUEST**: 

    {
       "requester":  "user1@gmail.com",
       "target":  "user2@gmail.com"   
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true   
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }

   #### **4.6. API 6**
**METHOD**:  POST

**API_NAME**: /retrieve-update

**PURPOSE**:  Retrieve all email addresses that can receive updates from an email address

**REQUEST**: 

    {
       "sender":  "user1@gmail.com",
       "text":  " Hello Word! user2@gmail.com"   
    }
**RESPONSE**: 

**success:**
    
    {
       "success": true,
       "recipients":[
		  "user3@gmail.com",
		  "user2@gmail.com
       ]  
    }
**fail":**
    
    {
       "success": false,
       "message": error_message    
    }
