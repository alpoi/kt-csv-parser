<div align="center">

# kt-csv-parser

A simple CSV parser written in Kotlin.

</div>

## Installation
You can use [`./scripts/install.sh`](./scripts/install.sh) as shown below. This will install the latest version to 
`$HOME/bin` by default, so you should ensure this is in your `PATH`.
```shell
curl -L https://raw.githubusercontent.com/alpoi/kt-csv-parser/refs/heads/main/scripts/install.sh | bash
```

You can also download the binary for your system from the releases tab, running
`chmod +x <filename>` to make it executable.

To uninstall, you can use
```shell
rm $(which csv-parse)
````

## Usage

```
Usage: csv-parse [<options>] [<csvarg>]

Options:
  -p, --pretty     Output pretty JSON
  -n, --no-header  Parse without CSV header row
  -h, --help       Show this message and exit
```

## Example

```shell
$ echo -e "name,age,location\nAngus,26,UK" > foo.csv
$ csv-parse < foo.csv
[{"name":"Angus","age":"26","location":"UK"}]
```
