# user-shell
User administration application.

## Overview

AWS is the main target but when decoupled with REST api could target any backend


## Development

To get an interactive development environment run:

    lein fig:build

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

	lein clean

To create a production build run:

	lein clean
	lein fig:min

### TODO:  
  * Explain how to integrate aws toolkit

## License

Copyright © 2019 FastLabs
