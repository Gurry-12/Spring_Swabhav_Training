Here are 22 practical CRUD and query problem statements based exactly on your entity fields. You can use these to practice writing JPQL, HQL, or Native SQL methods.
## Basic CRUD Operations (1–5)

   1. Create: 
   Write a method to insert a new Employee record into the database.
   
   2. Read Single: 
   Retrieve a specific Employee details using their unique empId.
   
   3. Update Entire Entity: 
   Update all details of an existing employee by passing the modified entity object.
   
   4. Delete Single: 
   Remove an employee record from the database based on their empId.
   
   5. Read All: 
   Fetch a complete list of all employees currently stored in the database.

## Single Field Updates (6–10)

   1. Update City: 
   Modify the empCity and corresponding cityCode for a specific employee id.
   
   2. Update Salary: 
   Modify the empSalary of a single employee using their empId.
   
   3. Update Age: 
   Change the empAge of an employee when they celebrate a birthday.
   
   4. Update Email: 
   Change the empEmail of an employee using their unique empId.
   
   5. Bulk Salary Increment: 
   Increase the empSalary by 10% for all employees living in a specific empCity.
   

## Targeted Select Queries (11–15)

   1. Filter by City: 
   Find all employees who live in a specific city (e.g., "New York").
   
   2. Filter by Age Range: 
   Retrieve all employees whose empAge falls between 25 and 35 years.
   
   3. High Earners: 
   Fetch a list of all employees earning an empSalary greater than a specific threshold value.
   
   4. Email Search:
   Search for a specific employee using their exact empEmail address.
   
   5. City Code Grouping: 
   Retrieve all unique cityCode values present in the table to see where employees are located.

## Projections & Partial Selection (16–18)

   1. Name List: 
   Fetch only the empName strings for all employees to build a simple dropdown menu.
   
   2. Contact Directory: 
   Retrieve only the empName and empEmail columns for a company contact sheet.
   
   3. Payroll Summary: 
   Fetch a combined list containing only the empId, empName, and empSalary fields.

## Aggregations & Pagination (19–22)

   1. Total Payroll: 
   Calculate the total sum of empSalary paid to all employees combined.
   
   2. Average Age: 
   Calculate the average empAge of the entire workforce.
   
   3. Headcount by City: 
   Count the total number of employees residing in a specific cityCode.
   
   4. Paginated Employee List: 
   Fetch a list of employees limited to 10 records per page, sorted alphabetically by empName.

Here are 25 more advanced and complex query scenarios based on your fields. These include pattern matching, conditional logic, bulk data modification, and complex filtering to test your Hibernate skills.
## Pattern Matching & Text Searches (23–27)

   1. Name Search (Prefix): Find all employees whose empName starts with a specific letter or prefix (e.g., "John").
   2. Domain Filter: Retrieve all employees using a specific email domain (e.g., finding all records ending with @company.com).
   3. Case-Insensitive Search: Search for an employee by empCity regardless of whether the input is uppercase or lowercase.
   4. Short Names: Find all employees whose names are less than or equal to 4 characters long.
   5. Invalid Emails: Identify any employee records where the empEmail field does not contain an @ symbol.

## Multi-Conditional Filtering (28–32)

   1. Young High Earners: Find all employees under the empAge of 30 who earn an empSalary above 80,000.
   2. Location-Based Payroll: Fetch employees living in a specific cityCode (e.g., "NY") and earning less than 50,000.
   3. Exclusion Filter: Retrieve all employees except those living in a specified list of cities.
   4. Senior Staff Search: Find employees who are either over the empAge of 55 or earn above 120,000.
   5. Strict Matching: Find an employee matching an exact combination of empName, empAge, and empEmail.

## Advanced Bulk Updates & Deletes (33–37)

   1. Reset City Data: Set both empCity and cityCode to null or "UNKNOWN" for employees matching a specific criteria.
   2. Standardise Codes: Bulk update the cityCode to uppercase for all records in the database.
   3. Conditional Bonus: Give a flat 5,000 empSalary bonus only to employees whose current salary is below the company average.
   4. Age-Based Deletion: Delete all employee records for individuals over a specific retirement empAge.
   5. Purge Orphaned Records: Delete records where the empEmail field is completely empty (null or blank).

## Sorting, Ordering & Subqueries (38–42)

   1. Salary Rank: Fetch all employees sorted by empSalary in descending order.
   2. Multi-Level Sort: List all employees sorted primarily by empCity alphabetically, and secondarily by empAge from youngest to oldest.
   3. Highest Paid Employee: Retrieve the single employee record with the maximum empSalary value.
   4. Second Highest Earner: Find the employee who has the second highest salary using pagination offsets.
   5. Above Average Filter: Find all individual employees who earn more than the overall average empSalary.

## Complex Aggregations & Analytics (43–47)

   1. Salary Bracket Counts: Count how many employees earn between 30,000–60,000, and how many earn 60,000+.
   2. City Salary Cap: Find the maximum empSalary currently being paid within a specific cityCode.
   3. Youngest Workforce: Identify the empCity that has the lowest average empAge among its residents.
   4. Email Duplicate Check: Write a query to find if any empEmail addresses are duplicated across different empId values.
   5. Total Workforce Value: Calculate the total monthly financial liability (sum of all empSalary) for a specific cityCode.

Would you like the complete repository code for a specific subset of these questions (e.g., 23 to 32)?


