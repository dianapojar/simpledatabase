Simple Database
===

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
* Data : the actual data of the database, if there are no current transactions open OR the newest transaction, if there are transactions opened
* TransactionManager: maintains a list of the parent transactions and methods to query data from them

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
