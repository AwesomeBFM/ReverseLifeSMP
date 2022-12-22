# Reverse Life SMP

Survival Minecraft Plugin for the Reverse Life SMP

### Introduction
Please keep in mind that this plugin has been designed for the Reverse Life SMP, while
it has been made available, no support will be provided here. The plugin has been designed
for use on the Reverse Life SMP and will likely not work with other configurations. It has
not been designed to play nicely with other plugins and some values have been hard coded in.

### Features
- **Combat Tag system** - Prevents players from logging out during combat or trying to Riptide/elytra away
- **Hearts System** - On death, players will receive one extra heart. If they reach 10 they will be death banned
- **Death Ban system** - Once a player has reached 10 hearts, they will be death banned until someone either revives them or the server owner removes all deathbans
- **Custom Items** - Custom items that can be crafted to either go down a heart or revive a player

### Commands
The server has a small amount of custom commands to allow users to interact with the plugin.
- `/hearts [player]` - Default - Shows the amount of hearts you have (`rlsmp.admin` is required to see other player's hearts)
- `/deathban <player>` - `rlsmp.admin` - Death bans a player
- `/undeathban <player>` - `rlsmp.admin` - removes a player's death ban
- `/clearhearts <player>` - `rlsmp.admin` - clears a player's hearts
- `/resetdata` - Console - Removes all death bans and clears all player's hearts. For security reasons this command can only be run from the console.

### Permissions
- `rlsmp.admin` - Allows access to the admin commands

### Setup
After adding the plugin to your plugins' folder, start the server and wait until you see
read error messages. After these present themselves, stop the server and then click on the
plugins' folder. You should see a new folder called `ReverseLifeSMP`. Inside this folder should
be a file called `config.yml`. In this file please be sure to fill out your database 
details. A database is required for this plugin for function properly, if no database is
provided, the plugin will be disabled.