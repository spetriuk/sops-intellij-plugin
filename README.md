# sops-intellij-plugin

### About
The simple plugin for Intellij Idea IDE for files encryption using [sops](https://github.com/mozilla/sops).

The plugin requires sops to be installed in system so it can use the following commands:

to encrypt file
```bash
sops -e -i ./file.yaml
```
to decrypt file
```bash
sops -d -i ./file.yaml
```

### Compatibility

The plugin was tested with: Ubuntu 22.04, IntelliJ IDEA 2022.2
