# Mailslot #

## Nota ##
This is a fork of http://github.com/stevej/mailslot/

This fork :

1. add scala 2.8.1 compatibility

2. add [sbt](http://code.google.com/p/simple-build-tool/)

## About ##
Mailslot is an smtp-based appserver. Under the Apache 2 license.

It is used as an example in [Programming Scala](http://programming-scala.labs.oreilly.com/index.html) book from O'REILLY

The purpose of [edla.org](http://www.edla.org) is to promote the state of the art in various domains.

## How to built it ##

1. sbt update

2. sbt clean compile

3. If you wan't to import it as an eclipse project : sbt eclipse

## Dependency ##

Mailslot depend on scala-stats.

This fork embed a compatible binary version but you can find also find source code here : https://github.com/newca12/scala-stats

That's it.