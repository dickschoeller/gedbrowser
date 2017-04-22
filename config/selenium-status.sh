#!/bin/bash
errors=$(grep errors target/failsafe-reports/failsafe-summary.xml | sed -e 's/ *[<]errors[>]//' -e 's/[<][/]errors[>].*//')
failures=$(grep failures target/failsafe-reports/failsafe-summary.xml | sed -e 's/ *[<]failures[>]//' -e 's/[<][/]failures[>].*//')
if [ "$errors" != "0" ]; then echo there were $errors errors; fi
if [ "$failures" != "0" ]; then echo there were $failures failures; fi
if [ "$errors" = "0" -a "$failures" = "0" ]; then echo 'success!'; fi
