#!/usr/bin/env bash
if ! '../check_config_reader.sh'; then exit; fi

source_path=$(../config_reader source_path)
javac --source-path $source_path $source_path/$(../config_reader project_root)/Main.java -d $(../config_reader build_path)
