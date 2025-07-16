set -euo pipefail

detect_target() {
  local os
  local arch

  os="$(uname | tr '[:upper:]' '[:lower:]')"
  arch="$(uname -m)"

  case "$os" in
    linux*)
      case "$arch" in
        x86_64)        echo "linuxX64"      ;;
        aarch64|arm64) echo ""              ;; # TODO support this
        *)             echo ""              ;;
      esac
      ;;
    darwin*)
      case "$arch" in
        x86_64) echo "macosX64"   ;;
        arm64)  echo "macosArm64" ;;
        *)      echo ""           ;;
      esac
      ;;
    *)
      echo "" ;;
  esac
}

TARGET="$(detect_target)"
if [[ -z "$TARGET" ]]; then
  echo "Error: Unsupported OS or architecture: $(uname -s)/$(uname -m)" >&2
  exit 1
fi

VERSION=0.1.0
INSTALL_DIR="${HOME}/bin"

GITHUB_SOURCE="https://github.com/alpoi/kt-csv-parser/releases/download/v${VERSION}/csv-parse-${TARGET}-v${VERSION}.zip"
TMP_ZIP="$(mktemp --suffix=.zip)"

mkdir -p "$INSTALL_DIR"
curl -fsSL "$GITHUB_SOURCE" -o "$TMP_ZIP"
unzip -qo "$TMP_ZIP" -d "$INSTALL_DIR"
chmod +x "${INSTALL_DIR}/csv-parse"
rm -f "$TMP_ZIP"
