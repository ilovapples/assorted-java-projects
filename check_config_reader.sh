#!/usr/bin/env bash
# yoinked from here: https://stackoverflow.com/questions/59895/how-do-i-get-the-directory-where-a-bash-script-is-located-from-within-the-script
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [ ! -f $SCRIPT_DIR/config_reader ]; then
      echo "\`config_reader\` must be compiled to proceed. Use the instructions in the main README.md." >&2
      exit 1
fi

exit 0
