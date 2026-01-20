#!/usr/bin/env bash
if ! '../check_config_reader.sh'; then exit; fi

rm -f $(find $(../config_reader build_path)/$(../config_reader project_root) -type f -name '*.class')
