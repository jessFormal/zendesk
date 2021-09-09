# Zendesk System Take Home Test

A system where a customer can search for ticket or customer with relevant fields.

## Requirements
- At least JDK 1.8

## Build

```bash
./gradlew build
```

## Run Application

```bash
./gradlew run --args='src/main/resources/users.json src/main/resources/tickets.json'
```
Note: Must pass in two files, where the first argument is user list and second argument is ticket list.

This will start up a new interactive CLI.

Output should look like:
```
Welcome to Zendesk Search
Type 'quit' to exit at any time, Press 'Enter' to continue


	 Select search options:
	  * Press 1 to search Zendesk
	  * Press 2 to view a list of searchable fields
	  * Type 'quit' to exit


  1
  2
  quit
Enter your choice:
```
The 1,2, quit near the bottom is letting the user know what the choices are in that moment.

When entering key and value it will look like this:
```
Enter your choice:
Select 1) Users or 2) Tickets

  1
  2
  quit
Enter your choice:
Enter Search Term 
_id
Enter Search Value 
[-1]:

```
The -1 in this scenario depicts the default value which will be entered if an empty string is passed.
This is due to the CLI library not allowing for empty Strings as an input therefore I have chosen to map empty strings to -1.

## Assumptions Made:
- The value "-1" will not appear so it is being used as a variable in the application to distinguish empty values.
- A ticket only has one assignee ID therefore only one assignee Name
- The ticket _id and customer _id are unique values
- To account for millions of data I chose to use parallelStream(). However I do recognise serial stream() works well for small amounts of data and is normally faster than parallelStream()
- The fields "tickets" for Users and "assigneeName" for Tickets are not searchable
