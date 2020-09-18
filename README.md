# libreoffice-command-server
## Executing LibreOffice commands via HTTP server

To compile:

#$> gradle build

or

#$> mvn assembly:single

## Example:

commands.json
```
[
	{
		"command": "CreateTempFileFromUpload",
		"name": "file",
		"return": "temp0"
	},
	{
		"command": "Open",
		"path": "temp0",
		"hidden": true,
		"return": "component0"
	},
	{
		"command": "Replace",
		"component": "component0",
		"searchString": "Lorem",
		"replaceString": "LOREM"
	},
	{
		"command": "CreateTempFile",
		"extension": ".pdf",
		"return": "temp1"
	},
	{
		"command": "StoreToURL",
		"component": "component0",
		"path": "temp1",
		"filterName": "writer_pdf_Export"
	},
	{
		"command": "DownloadFile",
		"path": "temp1"
	},
	{
		"command": "Close",
		"component": "component0"
	}
]
```

To Run:

```
#> java -jar libreoffice-command-server.jar
```

Windows

```
> curl -X PUT -F file=@file-sample_100kB.doc -F commands=@commands.json http://localhost:4567/ --output %TEMP%\out.pdf
```

Linux

```
# curl -X PUT -F file=@file-sample_100kB.doc -F commands=@commands.json http://localhost:4567/ --output /tmp/out.pdf
```
