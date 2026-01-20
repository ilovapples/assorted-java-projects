#!/usr/bin/env bash
if ! '../check_config_reader.sh'; then exit; fi

java -cp $(../config_reader build_path) $(../config_reader project_root | sed 's:/:.:g').Main "$@"
