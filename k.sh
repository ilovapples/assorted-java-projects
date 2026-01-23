# Source - https://stackoverflow.com/a
# Posted by matt b, modified by community. See post 'Timeline' for change history
# Retrieved 2026-01-23, License - CC BY-SA 4.0

#!/usr/bin/env bash

k() {
      echo "The script you are running has:"
      echo "basename : [$(basename "$0")]"
      echo "dirname  : [$(dirname "$0")]"
      echo "pwd      : [$(pwd)]"
      echo "realpath : [$(realpath $0)]"
      echo "type     : [$(type $0)]"
}
