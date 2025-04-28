# This is a mess

Don't use it yet

## Ok but what is this, actually?

This is a sandbox for testing codegen systems. The short of the idea behind this is that /BaseProject contains a bunch of interfaces that can be PR'd and implemented, and then the autogen script will convert them to json files loaded by a jinja2 interpreter. SwerveCreator is a really rough example of doing this.

The base idea here is that codegen examples can be made sort of implementation-agnostic. Unlike needing several layers of abstraction, the end code is viewable and editable, theoretically it creates the project for you.

My ideal would be if vendors then PR'd the repo with their implementations. Or, even better, they could host the .java files somewhere on their servers, and we could just pull and create the .json files.

## What Works

- Autogen scraper *works*, but isn't particularly stable. It's very code-style sensitive
- Jinja2 templating works

## What needs work:

- Autogen scraper needs to be made less code-style sensitive
- CI needs to be made more efficient (removed for now)
- We need some way to cope with versioning
- Need a much better (i.e. non manual) way to create the jinja2 templates, maybe using a modified version of the autogen script?