
# Clojure Database Connection Pool Example

A barebones Clojure app, which can easily be deployed to Heroku.  

This application supports the [Getting Started with Clojure](https://devcenter.heroku.com/articles/getting-started-with-clojure) article - check it out.

## Running Locally

Make sure you have Clojure installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

Then [install Postgres](http://www.postgresql.org/docs/9.1/interactive/tutorial-install.html), make sure it's running, and create a `ticks` database like so:

```sh-session
$ createdb ticks
```

Then prepare the app:

```sh-session
$ git clone https://github.com/kissaten/clojure-db-pool.git
$ cd clojure-db-pool
$ lein repl
user=> (require 'ticks.web)
user=>(def server (ticks.web/-main))
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Deploying to Heroku

```sh
$ heroku create
$ heroku addons:add heroku-postgresql:hobby-dev
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Clojure on Heroku, see these Dev Center articles:

- [Clojure on Heroku](https://devcenter.heroku.com/categories/clojure)
