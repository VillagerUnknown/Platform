# Changelog

All notable changes to this project will be documented in this file.

## [1.2.4+1.21.1]

This update backports functionality and features from version 1.2.3+1.21.5. 
It also corrects the location of the language files. It purposefully skips version numbers.

_This update changes the feature loader and breaks older versions. 
Users are encouraged to wait to update Platform until updates are available for all of their Platform dependent mods._

### Added

- Added persistent player profile caching system.
- Added `ItemCountMapTypeAdapter` and `ProfileResultMapTypeAdapter` for `GsonUtil`.
- Added `villagerunknown-platform-flushcaches` command to empty caches.
- Added `enablePlayerCaching` option.
- Added `flushCacheOnServerRestart` option.
- Added `bedClearsWeatherFeature`.
- Added `bedInteractionsAlwaysClearWeather` option.
- Added `bedInteractionsClearWeatherAtNight` option.
- Added `hideNametagsFeature` and corresponding `NametagVisibilityPayload`.
- Added `hideNametags` option.
- Added `hidePlayerNametags` option.

### Changed

- Changed location of translation files from `resources/assets/platform` to `resources/assets/villagerunknown-platform`.
- Changed `featureManager` to use a queuing system. This allows controlled loading of features in mods registered with Platform.

## [1.2.0.1]

_This update resolves conflicts with another Platform mod._

### Fixed

- Fixed conflicting mixin name with another Platform mod.
- Fixed help command providing wrong URL.

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