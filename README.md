=== Simple Database ===

![My image](https://docs.google.com/drawings/d/11oBJxJibTMvyJCTblzJQNf3UxrgOxjN0pd-adgO1hA8/pub?w=958&h=588)


Implementation:
A user writes a command to be executed in the database. An IReader interface is used to offer support for various inputs,
so a StdInReader was implemented that get the command the user wrote. This is consider to be a rawCommand that will need to
be parsed later on.

The rawCommand is sent to the Database. The database has his own parser component that returns a Command based on the
user input. After the Command is created, the database will execute it.

DataBase components:

DataContainer - Is an IoC that contains:
* Data : the actual data of the database, if there are no current transactions open OR the newest transaction, if there are transactions opened
* TransactionManager: maintains a list of the parent transactions and methods to query data from them


The data is stored using Maps, this way we have O(1) runtime for GET, SET, UNSET, and NUMEQUALTO. If we are in a nested transaction
and the data we are looking for is somewhere in a parent transaction, then we will need to iterate trough all of them util we find or not
what we were searching (in case we found it, we will stop when we'll get to the first occurrence). In this case the the runtime will increase, but
to minimize this, I've added a "cache" by adding the data that was searched to the current transaction data.

