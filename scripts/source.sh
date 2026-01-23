#!/usr/bin/env sh

ajp_config_filename=".ajp_config"

# yoinked from here: https://stackoverflow.com/questions/59895/how-do-i-get-the-directory-where-a-bash-script-is-located-from-within-the-script
config_reader_cmd=../config_reader

ajp_build() {
      if ! "../check_config_reader.sh"; then return 1; fi

      if [ ! -f "$ajp_config_filename" ]; then
            echo "ajp_build: failed to find config file '$ajp_config_filename'" >&2
            return 1
      fi

      local successes=""

      local source_path=$($config_reader_cmd $ajp_config_filename source_path)
      successes="$successes$?"
      local project_root=$($config_reader_cmd $ajp_config_filename project_root)
      successes="$successes$?"
      local entrypoint=$($config_reader_cmd $ajp_config_filename entrypoint | sed 's:\.:/:g')
      successes="$successes$?"
      local build_path=$($config_reader_cmd $ajp_config_filename build_path)
      successes="$successes$?"

      if [ ! "$successes" = "0000" ]; then
            echo -n "ajp_build: config file does not contain one or more of the necessary options: " >&2
            echo "'source_path', 'project_root', 'entrypoint', 'build_path'" >&2
            return 1
      fi

      javac --source-path $source_path $source_path/$project_root/$entrypoint.java -d $build_path
}

ajp_run() {
      if ! "../check_config_reader.sh"; then return 1; fi

      if [ ! -f "$ajp_config_filename" ]; then
            echo "ajp_run: failed to find config file '$ajp_config_filename'" >&2
            return 1
      fi

      local successes=""

      local build_path=$($config_reader_cmd $ajp_config_filename build_path)
      successes="$successes$?"
      local entrypoint=$($config_reader_cmd $ajp_config_filename entrypoint)
      successes="$successes$?"
      local project_root=$($config_reader_cmd $ajp_config_filename project_root | sed 's:/:.:g')
      successes="$successes$?"

      if [ ! "$successes" = "000" ]; then
            echo -n "ajp_run: config file does not contain one or more of the necessary options: " >&2
            echo "'build_path', 'entrypoint', 'project_root'" >&2
            return 1
      fi

      java -cp $build_path $project_root.$entrypoint "$@"
}

ajp_clean() {
      if ! "../check_config_reader.sh"; then return 1; fi

      if [ ! -f "$ajp_config_filename" ]; then
            echo "ajp_clean: failed to find config file '$ajp_config_filename'" >&2
            return 1
      fi

      local successes=""
      local build_path=$($config_reader_cmd $ajp_config_filename build_path)
      successes="$successes$?"
      local project_root=$($config_reader_cmd $ajp_config_filename project_root)
      successes="$successes$?"

      if [ ! "$successes" = "00" ]; then
            echo -n "ajp_clean: config file does not contain one or more of the necessary options: " >&2
            echo "'build_path', 'project_root'" >&2
            return 1
      fi

      rm -f $(find $build_path/$project_root -type f -name '*.class')
}
