# Restaurant List Application

## Application Proposal

This application allows the users to keep track of restaurants they wish to visit in the future and restaurants they 
have visited. Users will be able to:
- *manage* a list of restaurants
- *record* details of restaurants they will be visiting in the future
- *mark off* restaurants they have visited, and record a rating

This application will be used by  **anyone** who loves exploring for a new food and finding new restaurants.

This project is an interest to me because I often have a hard time deciding where to eat with friends. There are 
several lists of restaurants that I kept to use in the future, but these lists proved to be impractical as they lacked 
detailed information about the restaurants such as the location, price range and types of food. This restaurant list
application will solve this problem as it helps record the necessary information of the restaurants in detail. Not 
only that, but it will also keep a record of restaurants visited with a rating to refer back to in the future. 

## User Stories
- As a user, I want to be able to add a restaurant to my collection
- As a user, I want to be able to remove a restaurant from the collection
- As a user, I want to be able to mark off a restaurant as visited and rate it 
- As a user, I want to be able to view all the restaurant names in the collection
- As a user, I want to be able to select a restaurant with the given name, type or location and view its details 
- As a user, I want to save my restaurant list to file.
- As a user, I want to be able to load my restaurant list from file. 

## Phase 4: Task 2
For this task, I have included a type hierarchy in my code.
- AddListener, RemoveListener, SaveListener and RemoveListener classes all implement ActionListener interface.
- Each of these classes override actionPerformed method and implement it in different ways. 

## Phase 4: Task 3
To improve the design on the project...
- I would first reduce repetitive code found in the RestaurantGUI class. Since each Restaurant has a name, a type and 
a location, there were several times when a code had to be implemented three times for each of the restaurant 
information. I would reduce these repetitive codes by making helper methods. 
- Also, I would increase cohesion of the RestaurantGUI class. Inside the class, there are private classes called
RemoveListener, AddListener, LoadListener and SaveListener classes. Since more of these private classes would 
be required whenever a new function gets added, I think it better to sort them into a separate class. As these
Listener classes utilize and alter much of the fields in RestaurantGUI class, there needs to be a bi-directional 
association between the Listener class, and the RestaurantGUI class.
- Lastly, I would increase the cohesion of the methods inside the RestaurantGUI class. Some methods are 
performing multiple roles at once so in adherence to the single responsibility principles, I would construct more 
methods to make sure each method performs one single function.