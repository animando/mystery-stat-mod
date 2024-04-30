# Mystery Stat Mod

## Description

This mod adds some commands to provide a fun mystery statistic challenge competition in shared servers.

## Features

It will display the score for a mystery statistic objective in the player list
Allow players to list the top 3 as a leaderboard
Publishes the results to a discord server

## Discord

https://discord.gg/Tuywdy59KX

## Dependencies

- [fabric-api](https://modrinth.com/mod/fabric-api)
- [fabric-permissions-api](https://modrinth.com/mod/fabric-permissions-api)

## Installation

- Add the mod jar and the dependencies
- Start the server
- Edit config/mysterystat.toml with your discord bot's authentication token and channel id
- Restart the server

## Commands

### Add
Adds a new mystery challenge objective. These are registered as vanilla objectives using a prefix `mysterystat_`.

`mysterystat add <name> <criterion>`

Requires permission 'mysterystat.add'

### Activate

Activates a mystery challenge. This sets it as the current objective, visible in the player list display slot.

`mysterystat activate <name>`

Requires permission 'mysterystat.activate'

### List current objective

Lists the top 3 players for the currently active objective

`mysterystat list`

Requires permission 'mysterystat.list.current'

### List any objective

Lists the top 3 players for the provided objective

`mysterystat list <name>`

Requires permission 'mysterystat.list.any'

### Clear

Removes the provided objective

`mysterystat clear <name>`

Requires permission 'mysterystat.clear'

### Publish

Publishes the top 3 players the provided objective to a configured discord server

`mysterystat publish <name>`

Requires permission 'mysterystat.publish'

### Populate

Populates a mystery challenge objective with the scores from a pre-existing vanilla objective

`mysterystat populate <name> <objective>`

Requires permission 'mysterystat.publish'

