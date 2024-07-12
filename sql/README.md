# Introduction
The purpose of this project is to further hone my skills with SQL by using a problem set to refresh my knowledge on SQL statements. The goal of this problem set is to help newcomers understand how to form SQL statements by using various conditions, aggregations, and subqueries. Technologies such as Docker and PostgreSQL are being used to host and store data. As well as Git and GitHub being used for version management.
# SQL Queries

###### Table Setup (DDL)
Members table creation:
```sql
create table members (
memid INTEGER not null,
surname VARCHAR(200) not null,
firstname VARCHAR(200) not null,
address VARCHAR(300) not null,
zipcode INTEGER not null,
telephone VARCHAR(20) not null,
recommendedby INTEGER,
joindate TIMESTAMP not null,
constraint members_pk primary key (memid),
constraint fk_members_recommendedby foreign key (recommendedby) references members(memid) on delete set null
);
```

Facilities table creation:
```sql
create table facilities (
facid INTEGER not null,
name VARCHAR(100) not null,
membercost numeric not null,
guestcost numeric not null,
initialoutlay numeric not null,
monthlymaintenance numeric not null,
constraint facilities_pk primary key (facid)
);
```

Bookings table creation:
```sql
create table bookings (
bookid INTEGER not null,
facid INTEGER not null,
memid INTEGER not null,
starttime TIMESTAMP not null,
slots INTEGER not null,
constraint bookings_pk primary key (bookid),
constraint fk_bookings_facid foreign key (facid) references facilities(facid) on delete set null,
constraint fk_bookings_memid foreign key (memid) references members(memid) on delete set null
);
```

###### Question 1: The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:
###### facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800. 

```sql
INSERT INTO cd.facilities (
facid, name, membercost, guestcost,
initialoutlay, monthlymaintenance
)
VALUES
(9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else:
###### Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.

```sql
INSERT INTO cd.facilities (
facid, name, membercost, guestcost,
initialoutlay, monthlymaintenance
)
VALUES
(
(
SELECT
max(facid)
from
cd.facilities
)+ 1,
'Spa',
20,
30,
100000,
800
);
```
###### Question 3: We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.

```sql
UPDATE
cd.facilities
SET
initialoutlay = 10000
WHERE
name = 'Tennis Court 2';
```

###### Question 4: We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.

```sql
UPDATE
cd.facilities
SET
membercost = membercost + (membercost * 0.1),
guestcost = guestcost + (guestcost * 0.1)
WHERE
name = 'Tennis Court 2';
```

###### Question 5: As part of a clearout of our database, we want to delete all bookings from the cd.bookings table. How can we accomplish this?

```sql
DELETE FROM
cd.bookings;
```

###### Question 6: We want to remove member 37, who has never made a booking, from our database. How can we achieve that?

```sql
DELETE FROM
cd.members
where
memid = 37;
```

###### Question 7: How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.

```sql
SELECT
facid,
name,
membercost,
monthlymaintenance
FROM
cd.facilities
WHERE
membercost > 0
AND membercost < (monthlymaintenance / 50);
```

###### Question 8: How can you produce a list of all facilities with the word 'Tennis' in their name?

```sql
SELECT
*
FROM
cd.facilities
WHERE
name LIKE '%Tennis%';
```

###### Question 9: How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.

```sql
SELECT
*
FROM
cd.facilities
WHERE
facid IN (1, 5);
```

###### Question 10: How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.

```sql
SELECT
memid,
surname,
firstname,
joindate
FROM
cd.members
WHERE
joindate >= '2012-09-01';
```

###### Question 11: You, for some reason, want a combined list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!

```sql
SELECT
surname
FROM
cd.members
UNION
SELECT
name
FROM
cd.facilities;
```

###### Question 12: How can you produce a list of the start times for bookings by members named 'David Farrell'?

```sql
SELECT
bookings.starttime
from
cd.bookings bookings
INNER JOIN cd.members members ON members.memid = bookings.memid
WHERE
members.firstname = 'David'
AND members.surname = 'Farrell';
```

###### Question 13: How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.

```sql
SELECT
bookings.starttime as start,
facilities.name
FROM
cd.bookings bookings
INNER JOIN cd.facilities facilities ON bookings.facid = facilities.facid
WHERE
facilities.name LIKE 'Tennis Court%'
AND bookings.starttime >= '2012-09-21'
AND bookings.starttime < '2012-09-22'
ORDER BY
bookings.starttime;
```

###### Question 14: How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).

```sql
SELECT
mem.firstname as "memfname",
mem.surname as "memsname",
rec.firstname as "recfname",
rec.surname as "recsname"
FROM
cd.members mem
LEFT OUTER JOIN cd.members rec ON mem.recommendedby = rec.memid
ORDER BY
memsname,
memfname;
```

###### Question 15: How can you output a list of all members who have recommended another member? Ensure that there are no duplicates in the list, and that results are ordered by (surname, firstname).

```sql
SELECT
DISTINCT recs.firstname AS firstname,
recs.surname as surname
FROM
cd.members members
INNER JOIN cd.members recs on recs.memid = members.recommendedby
ORDER BY
surname,
firstname;
```

###### Question 16: How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.

```sql
SELECT
DISTINCT members.firstname || ' ' || members.surname AS member,
(
SELECT
recommender.firstname || ' ' || recommender.surname
FROM
cd.members recommender
WHERE
recommender.memid = members.recommendedby
) AS recommender
FROM
cd.members members
ORDER BY
member;
```

###### Question 17: Produce a count of the number of recommendations each member has made. Order by member ID.

```sql
SELECT
recommendedby,
COUNT(recommendedby)
FROM
cd.members
WHERE
recommendedby IS NOT NULL
GROUP BY
recommendedby
ORDER BY
recommendedby;
```

###### Question 18: Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.

```sql
SELECT
DISTINCT facid,
SUM(slots) as "Total Slots"
from
cd.bookings
GROUP BY
facid
ORDER BY
facid;
```

###### Question 19: Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.

```sql
SELECT
facid,
SUM(slots) as "Total Slots"
FROM
cd.bookings
WHERE
starttime >= '2012-09-01'
AND starttime < '2012-10-01'
GROUP BY
facid
ORDER BY
SUM(slots);
```

###### Question 20: Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.

```sql
SELECT
facid,
EXTRACT(
MONTH
FROM
starttime
) as "month",
SUM(slots) as "Total Slots"
FROM
cd.bookings
WHERE
EXTRACT(
year
from
starttime
) = '2012'
GROUP BY
facid,
month
ORDER BY
facid,
month;
```

###### Question 21: Find the total number of members (including guests) who have made at least one booking.

```sql
SELECT
COUNT(DISTINCT memid)
FROM
cd.bookings;
```

###### Question 22: Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.

```sql
SELECT
mem.surname,
mem.firstname,
book.memid,
MIN(book.starttime) as "starttime"
FROM
cd.bookings book
INNER JOIN cd.members mem ON book.memid = mem.memid
WHERE
starttime > '2012-09-01'
GROUP BY
mem.surname,
mem.firstname,
book.memid
ORDER BY
book.memid;
```

###### Question 23: Produce a list of member names, with each row containing the total member count. Order by join date, and include guest members.

```sql
SELECT
COUNT(memid) over(),
firstname,
surname
FROM
cd.members
GROUP BY
memid
ORDER BY
joindate;
```

###### Question 24: Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.

```sql
SELECT
row_number() over(
ORDER BY
joindate
),
firstname,
surname
FROM
cd.members
GROUP BY
firstname,
surname,
joindate
ORDER BY
joindate;
```

###### Question 25: Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.

```sql
SELECT
facid,
total
FROM
(
SELECT
facid,
SUM(slots) total,
RANK() OVER (
ORDER BY
SUM(slots) DESC
) rank
FROM
cd.bookings
GROUP BY
facid
) AS ranked
WHERE
rank = 1;
```

###### Question 26: Output the names of all members, formatted as 'Surname, Firstname'

```sql
SELECT
surname || ', ' || firstname AS name
FROM
cd.members;
```

###### Question 27: You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.

```sql
SELECT
memid,
telephone
FROM
cd.members
WHERE
telephone LIKE '(%)%';
```

###### Question 28: You'd like to produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.

```sql
SELECT
SUBSTRING(surname, 1, 1) as letter,
COUNT(*)
from
cd.members
GROUP BY
letter
ORDER BY
letter;
```