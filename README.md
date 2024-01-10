# Somestuff you might need to know :
1- the car list loaded from the rest_api is saved on launch inside the Car.carList static ArrayList of Car objects

2- the database name is stored inside the User.dmName static String belonging tot he UserClass

3- a singelton tagged with "loggedInEmail" is saved in shared preferences, and is used to hold who is currently logged in.

## Database structure

### Tables : 
1- USER, stores email account and all other relevant infromation required by the project description(No Users are loaded at start, you can find some code you can uncomment that loads two rows, 1 Admin and 1 Normal User), 
    each row contains a fields that says if the email in this row is the "remembered" user or not.
    
2- CAR, stores car details loaded from the rest API (currently the car information is not Loaded into this table)

3- COUNTRY, static table that holds a list of all countries and their relevant phone code(filled at the onCreate() of the LoginActivity)

4- CITY, static table that holds a list of cities and the countries they belong to

5- FAVORITE, holds list of cars and users that have favorited them(empty at runtime, should be filled with app use)

6- RESERVED, holds list of cars and useres that have reserved them(empty at runtime, should be filled with app use)
### Relations:
1- COUNTRY and CITY: one to many relationship (signle country has multiple cities)

2- CITY and USER: one to many(single city has multiple users)

3- USER and CAR: many to many, favorites (multiple users have multiple favorited cars)

4- USER and CAR: many to many, resereves (multiple useres have multiple reserved cars)

![image](https://github.com/NoorAdinTirhi/carDealership/assets/115925101/ee37f6b1-82de-4ac2-b4a7-9087dffda839)

        
## Created Fragments 
### NavigationMenuFragment:
contains buttons to fulfil all the options required by the spec, e.g. Home, reserved, special offers .....(currently the buttons will send you to empty activities that we need to implement),
also contains textViews that give a description to where each button leads
### InputFieldsFragment:
Contains all the input editTexts(Fields) required at sign up, e.g. email, first name, last name, as well as textViews telling what each fields needs to hold
Also contains spinners for other stuff like gender, country and cities.

cities spinner and phone code text view are automatically filled with appropriate values depending on the last chosen value of the country spinner

# What we need to do :

1- each of the activities that can be reached by the navigation menu are currently empty and need implementation

2- there's a chance that the current database structure needs to be changed, but I don't know

