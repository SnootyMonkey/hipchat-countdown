# -= hipchat-countdown =-

[![Unlicense](http://img.shields.io/badge/license-unlicense-green.svg?style=flat)](http://unlicense.org/)

Schedulable script to update a HipChat room's topic with a countdown to a specific date.

## Dependencies

You need a Java 7+ JVM to run Clojure. Run the following to ensure you have the right Java VM:

```console
java -version
```

You must have Leiningen 2.5+ which can be installed by following the very simple directions [here](http://leiningen.org/).

## Configuration

There is a config file, `config.edn` in the root project directory, which is in [EDN format](https://github.com/edn-format/edn). Use it to configure the HipChat room's topic updates called . The properties in the config file should be self-explanatory:

```clojure
{
  :countdown-date "2014-04-15T12:00:00" ; an ISO 8601 timestamp http://www.w3.org/TR/NOTE-datetime
  :room "<Name or ID of your HipChat room>"
  :v2-api-token "<your HipChat v2 API auth-token>"
  :msg-prefix "100 Day Project Countdown: "
  :msg-suffix " days remaining until launch on April 15th, 2014"
  :final-msg "100 day project is complete. Ship it!"
}
```

The above configuration would result in a HipChat room topic such as:

```
100 Day Project Countdown: 63 days remaining until launch on April 15th, 2014.
```

And then on April 15th, the room topic would update to:

```
100 day project is complete. Ship it!
```

## Usage

To run `hipchat-countdown`, execute it from within the project directory:

```console
lein run
```

It will compare the current time to the countdown with what is in ```.last-update``` to see if it needs to update the HipChat room topic. If it does it will make the API call to update the topic. Then it completes and exits.

Since it exits after a single update, running needs to be scheduled with something like [cron](http://en.wikipedia.org/wiki/Cron).

Ideally you'll schedule it to run once an hour so the updates won't be very delayed. But you can also chose to run it once a day at a specific time if you want your topic updates to happen at that time.

Here's what a sample crontab entry to run once an hour might look like:

```
# Run hipchat-countdown every hour on the hour
0 * * * * cd ~/hipchat-countdown; lein run >> countdown.log 2>&1
```

Here's what a sample crontab entry to run once a day at 9AM might look like:

```
# Run hipchat-countdown once a day at 9AM
0 9 * * * cd ~/hipchat-countdown; lein run >> countdown.log 2>&1
```

## Logging

The output to stdout is suitable for logging. If you would like a logfile (recommended) then ensure stdout is captured to a logfile in your scheduled execution (as in the above crontab example).

## License

hipchat-countown is is free and unencumbered software released into the public domain.

For more information, please refer to [unlicense.org](http://unlicense.org).