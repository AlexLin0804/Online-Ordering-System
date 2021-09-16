# Online Ordering System

The ordering system provide a customized experience for two different groups of users: restaurant customers and restaurant managers.
Restaurant customers will be able to place orders are customized specifically to them.  The system will also provide recommendations for customers to choose from based on past ordering history.
Restaurant managers will be able to adjust menu pricing and offerings.  The system will also provide recommendations of adjustments for managers based on past ordering history.


**Manager GUI**</br>

> Implementing top 2 and bottom 2 trending items</br>
> Implementing help bottons and recommendations </br>

run with: </br>
> javac ManagerGUI_product_entree.java </br>
> javac ManagerGUI_product_side.java </br>
> javac ManagerGUI_product_drink.java </br>
> javac ManagerGUI_product_dessert.java </br>
> javac ManagerGUI_product.java </br>
> javac ManagerGUI_dashboard.java </br>
> javac ManagerGUI_account.java </br>
> javac ManagerGUI.java </br>
> javac testdriver.java </br>
> java -cp ".;postgresql-42.2.8.jar" testdriver </br>
</br>

> No need to login </br> 



**Customer GUI**</br>

> Implementing topping adjustment for each item</br>
> Implementing Customer accounts for their personal user experiences
> Implementing Customer Recommendations (from past history or deals)

run with: </br>
> javac customerGUI.java </br>
> javac customerGUI_order.java </br>
> javac customerGUI_orderfood.java </br>
> javac customerGUI_recs.java </br>
> javac customerGUI_cart.java </br>
> javac customerGUI_signin.java </br>
> javac jdbcpostgreSQL.java </br>
> java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL </br>
</br>

test past orders and login with customer: </br>
> username: GREG.PETRI</br> 
> password: pswd </br>
> customerid: 160983 </br>
</br>


**Manager GUI Screeshots**</br>
<img width="50%" alt="Landing" src="https://user-images.githubusercontent.com/74691966/133677155-8aed36f2-1813-4786-9acc-e366cb29d460.png">
<img width="50%" alt="Product" src="https://user-images.githubusercontent.com/74691966/133677177-6c625461-fb6f-42fa-9635-eb0573d4da00.png">
<img width="50%" alt="Entree" src="https://user-images.githubusercontent.com/74691966/133677191-48db65e1-f976-4861-b4b1-7f2b21e16d41.png">


**Customer GUI Screeshots**</br>
<img width="50%" alt="Screen Shot 2021-04-09 at 5 40 22 PM" src="https://user-images.githubusercontent.com/74691966/133677205-dfd5714e-6429-4ea4-8809-d611dd816b0f.png">
<img width="50%" alt="Screen Shot 2021-04-09 at 5 40 31 PM" src="https://user-images.githubusercontent.com/74691966/133677211-dce23cc3-4b70-414c-a174-4d43137c591e.png">

