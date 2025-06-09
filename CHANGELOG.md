# Changelog

All notable changes to this project will be documented in this file.

## [1.2.4+1.21.5]

_This update fixes an issue reported for the Head Hunters mod._

### Fixed

- Fixed issues with HeadUtil providing Steve heads for some mobs with variants (Wolves, Frogs, Rabbits).

## [1.2.3+1.21.5]

_This update resolves conflicts with another Platform mod._

### Fixed

- Fixed conflicting mixin name with another Platform mod.

## [1.2.2+1.21.5]

### Added

- Added persistent player profile caching system.
- Added `villagerunknown-platform-flushcaches` command to empty caches.
- Added RegistryKey property to `VillagerUtil.CustomVillager` to adapt to changes from MC1.21.5.

### Changed

- Changed `featureManager` to use a queuing system. This allows controlled loading of features in mods registered with Platform.

## [1.2.1+1.21.5]

### Added

- Added variations for Chickens to `HeadUtil`.
- Added variations for Cows to `HeadUtil`.
- Added variations for Pigs to `HeadUtil`.

## [1.2.0+1.21.5]

### Changed

- Changed supported Minecraft version to 1.21.5.

## [1.2.0+1.21.4]

### Added

- Added Creaking head properties to `HeadUtil`.

### Changed

- Changed supported Minecraft version to 1.21.4.

## [1.2.0+1.21.3]

### Changed

- Changed supported Minecraft version to 1.21.3.

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