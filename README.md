Simple Database
===

### Simple Database - Thumbtack challenge
In the Simple Database problem, you'll implement an in-memory database similar to Redis. For simplicity's sake, instead of dealing with multiple clients and communicating over the network, your program will receive commands via standard input (stdin), and should write appropriate responses to standard output (stdout).

#####Guidelines
This problem should take you between 30 and 90 minutes.
We recommend that you use a high-level language, like Python, Ruby, JavaScript, or Java. We're much more interested in seeing clean code and good algorithmic performance than raw throughput.

#####Data Commands
Your database should accept the following commands:
**SET name** value – Set the variable name to the value value. Neither variable names nor values will contain spaces.
**GET name** – Print out the value of the variable name, or NULL if that variable is not set.
**UNSET name** – Unset the variable name, making it just like that variable was never set.
**NUMEQUALTO value** – Print out the number of variables that are currently set to value. If no variables equal that value, print 0.
**END** – Exit the program. Your program will always receive this as its last command.
Commands will be fed to your program one at a time, with each command on its own line. Any output that your program generates should end with a newline character. Here are some example command sequences:

```
INPUT	    OUTPUT
SET ex 10   10
GET ex
UNSET ex
GET ex      NULL
END
```

```
INPUT	        OUTPUT
SET a 10
SET b 10
NUMEQUALTO 10   2
NUMEQUALTO 20   0
SET b 30
NUMEQUALTO 10   1
END
```

#####Transaction Commands
In addition to the above data commands, your program should also support database transactions by also implementing these commands:
**BEGIN** – Open a new transaction block. Transaction blocks can be nested; a BEGIN can be issued inside of an existing block.
**ROLLBACK** – Undo all of the commands issues in the most recent transaction block, and close the block. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
**COMMIT** – Close all open transaction blocks, permanently applying the changes made in them. Print nothing if successful, or print NO TRANSACTION if no transaction is in progress.
Any data command that is run outside of a transaction block should commit immediately. Here are some example command sequences:

```
INPUT	    OUTPUT
BEGIN
SET a 10
GET a       10
BEGIN
SET a 20
GET a       20
ROLLBACK
GET a       10
ROLLBACK
GET a       NULL
END
```

```
INPUT	    OUTPUT
BEGIN
SET a 30
BEGIN
SET a 40
COMMIT
GET a       40
ROLLBACK    NO TRANSACTION
END
```

```
INPUT       OUTPUT
SET a 50
BEGIN
GET a       50
SET a 60
BEGIN
UNSET a
GET a       NULL
ROLLBACK
GET a       60
COMMIT
GET a       60
END
```

```
INPUT	        OUTPUT
SET a 10
BEGIN
NUMEQUALTO 10   1
BEGIN
UNSET a
NUMEQUALTO 10   0
ROLLBACK
NUMEQUALTO 10   1
COMMIT
END
```

#####Performance Considerations
The most common operations are GET, SET, UNSET, and NUMEQUALTO. All of these commands should have an expected worst-case runtime of O(log N) or better, where N is the total number of variables stored in the database.
The vast majority of transactions will only update a small number of variables. Accordingly, your solution should be efficient about how much memory transactions use.


### My implementation
![My image](https://docs.google.com/drawings/d/11oBJxJibTMvyJCTblzJQNf3UxrgOxjN0pd-adgO1hA8/pub?w=958&h=588)


Implementation:
A user writes a command to be executed in the database. An IReader interface is used to offer support for various inputs,
so a StdInReader was implemented that get the command the user wrote. The user input is considered to be a rawCommand that will need to
be parsed later on.

The rawCommand is sent to the Database. The database has his own parser component that returns a Command based on the
user input. After the Command is created, the database will execute it.

DataBase components:

I have created a container (DataContainer) for decoupling reasons.
DataContainer:
* Data : the current data the is in the database
* TransactionManager: maintains and handles the nested transactions

Complexity:
I wanted to achieve **O(1)** runtime for GET, SET, UNSET, and NUMEQUALTO, so I chose to keep my data in Maps. NUMEQUALTO uses it's own map
(inverted index), that is actualized at every SET, UNSET.

A **transaction** contains a Map of key-value, a Map of value-ValueCount and a list of deletedItems that marks the keys deleted in the transaction
(used when the transaction is committed). Each transaction will keep only the keys and the valueCounts that were modified inside it with set, unset.

**Nested transactions** are implemented by maintaining a list of transactions. For example if we are in a nested transaction and we need to search for a key that is in a parent transaction, we will iterate
through all of the transactions (from newest to oldest) until we found the value we were searching for. In this case the the runtime will increase, but
to minimize this, I've added a "cache" by adding the data that was searched to the newest transaction data.

**Committing transactions** are made by merging the maps from the oldest transactions to the newest, first by adding the new values,
 deleting the keys from deletedItems and then updating the valueCounts.

In a concurrent environment I would've use instead of the Maps, ConcurrentMaps. Also we would need to manage a set of locks for the map keys -
each time we would've need to update a value, locks for the affected keys would've need to be acquired before doing any changes.
