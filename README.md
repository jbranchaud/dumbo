# dumbo

Some clojure code for putting all your tweets into a Postgres database.

## Requirements

- [lein](http://leiningen.org/)
- [PostgreSQL](http://www.postgresql.org/)
- [Your twitter data as CSV](https://support.twitter.com/articles/20170160?lang=en)

## Setup

Create the database:

```
$ createdb dumbo
```

## Usage

```
$ lein run ~/tweet-data.csv
```
