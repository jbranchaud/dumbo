# dumbo

Some clojure code for putting all your tweets into a Postgres database.

## Requirements

- Lein
- PostgreSQL
- Your twitter data as CSV

## Setup

Create the database:

```
$ createdb dumbo
```

## Usage

```
$ lein run ~/tweet-data.csv
```
