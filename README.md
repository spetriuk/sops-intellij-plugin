# sops-intellij-plugin

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) [![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)

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

### Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Sops Support"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Clone this repo,  invoke the `buildPlugin` Gradle task to create the plugin distribution. The resulting ZIP file is located in `build/distributions` and can then be installed via drag & drop (or using plugin manager):

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>



### Compatibility

The plugin was tested with: Ubuntu 22.04, IntelliJ IDEA 2022.2
