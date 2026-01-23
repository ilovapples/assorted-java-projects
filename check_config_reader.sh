#!/usr/bin/env bash
get_script_dir_local() {
      echo $( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
}

source $(get_script_dir_local)/scripts/source.sh

if [ ! -f $(get_script_dir_local)/config_reader ]; then
      echo "\`config_reader\` must be compiled to proceed. Use the instructions in the main README.md." >&2
      exit 1
fi

exit 0
