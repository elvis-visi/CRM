# 1. About
CRM java application based on this [Spring and Hibernate course](https://www.udemy.com/share/101WHS3@yAni4oJ1jUSRpgSPLzqg2cWRQXZQN_BCJiudaa2Gbl3u2xMuKOUhrpEVzHudS6Fl/)

- CRUD Spring MVC + Hibernate demo java application.
- Restrict URL access based on 3 roles, employee, manager and admin.
- Display content based on roles

# 2. Log in and Registration Pages 

![image](https://user-images.githubusercontent.com/92176935/222985793-2b4301bd-93f7-4880-9b1b-c008a99b4212.png)
![image](https://user-images.githubusercontent.com/92176935/222986164-1b866a0a-a465-46b7-aca3-a6851f53ddc0.png)

# 3. Admin view

Admin role: users in this role will be allowed to list, add, update and delete customers.
![image](https://user-images.githubusercontent.com/92176935/222985860-4068d931-aef1-4bd4-af3f-571a54506e59.png)

# 4. Manager view

• Manager role: users in this role will be allowed to list, add and update customers.
![image](https://user-images.githubusercontent.com/92176935/222985910-9077d4bd-e67c-4e20-8533-215e4e82b792.png)

# 5. Employee view

Employee role: users in this role will only be allowed to list customers.
![image](https://user-images.githubusercontent.com/92176935/222985951-69e936f1-1930-4236-9840-4ebd54e2a227.png)

# 6. Add Customer page

![image](https://user-images.githubusercontent.com/92176935/222986367-30bacb4f-4315-4ee1-a4d8-e83f787da583.png)

# 7. ERD

![image](https://user-images.githubusercontent.com/92176935/222986582-c546e193-688c-4acb-918d-070c020fdb6e.png)

# 8. Endpoints provided

/customer 
      /list
      /showFormForAdd
	/search
	/saveCustomer
	/showFormForUpdate
	/delete


/showMyLoginPage
/access-denied

/register
	/showRegistrationForm
	/processRegistrationForm 
