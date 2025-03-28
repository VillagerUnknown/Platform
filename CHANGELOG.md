# Changelog

All notable changes to this project will be documented in this file.

## [1.2.0+1.21.2]

### Changed

- Changed supported Minecraft version to 1.21.2.

## [1.2.0]

### Added

- Added `HeadUtil` class.
- Added `PotionsUtil` class.
- Added `ProfileUtil` class.
- Added `VillagerUtil` class.
- Added `simulateDeath` method to `EntityUtil` class.
- Added `createPotionStack` to `ItemStackUtil` class.
- Added `createWaterBottleStack` to `ItemStackUtil` class.
- Added `registerVillager` to `RegistryUtil` class.
- Added `registerPointOfInterest` to `RegistryUtil` class.
- Added `registerVillagerProfession` to `RegistryUtil` class.

### Changed

- Changed Minecraft version requirement to allow the mod to run on versions past 1.21.1.

## [1.1.1]

### Added

- Added `define` method to `Platform` class to allow mod developers to change the "villagerunknown-" prefix.
- Added `register` method to `Platform` class to allow mod developers to insert a `PlatformMod` object directly into the registered mods list. 
- Added `registered` method to `Platform` class to allow mod developers to check if a mod is registered.
- Added `registration` method to `Platform` class to allow mod developers to retrieve a mod's `PlatformMod` object.
- Added `reportConversionToLog` method to `EntityUtil` class.
- Added `reportConversionToChat` method to `EntityUtil` class.
- Added `registerEntity` method to `RegistryUtil` class.
- Added `getItemGroup` method to `RegistryUtil` class.
- Added `registerItemGroup` method to `RegistryUtil` class.
- Added `addItemToGroup` method to `RegistryUtil` class.
- Added `capitalizeAll` method to `RegistryUtil` class.
- Added `LocatorUtil` class to provide methods for locating structures and biomes.

### Fixed

- Set pretty printing in Gson to correct formatting of files created by list.StringsList class. 
- Resolved an issue causing an incompatibility with Custom Villager Names and Healthy Habits.

## [1.1.0]

### Added

- Added bonus feature: Welcome Notice
- Added bonus feature: Sleep Notice
- Added bonus feature: Death Coordinate Notice
- Added bonus command: `/villagerunknown-platform-worldspawn`

### Changed

- Optimized workflow between Platform and Mods.
- Improved Utilities.
- Changed license from All Rights Reserved to Creative Commons International 4.0.

## [1.0.1]

### Added

- Added EntityUtil method to get nearby blocks.

## [1.0.0]

_Initial release for Minecraft 1.21.1_