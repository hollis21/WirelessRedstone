# WirelessRedstone

This is a very simple mod that adds the ability to transmit redstone signals wirelessly between Redstone Transmitters and Redstone Receivers linked by a Linker.

### New Items
##### Redstone Transmitter
![Imgur](https://i.imgur.com/dKfzv38.png)

Transmits the redstone signal (including strength) it receives to the frequency it's programmed to.

##### Redstone Receiver
![Imgur](https://i.imgur.com/Jcx8XlU.png)

Outputs any redstone signal (including strength) it receives from the frequency it's programmed to.

##### Linker
![Imgur](https://i.imgur.com/PFqUicY.png)

Allows for the programming of wireless blocks' (Transmitters and Receivers) frequency.


### Multiple Transmitters/Receivers per Frequency
Redstone signals are transmitted on the frequency the Redstone Transmitter is set on and received by all Redstone Receivers also set to that frequency.

There is no restriction on the number of Transmitters or Receivers that can be set to a frequency. If multiple Transmitters are on the same frequency, the last Transmitter to have a change in redstone signal will be the active redstone signal on the frequency. The redstone signal strength on a frequency is whatever strength the last Transmitter with that frequency to have a change was.


### Setting Frequencies with Linkers
Using a blank Linker on a wireless block (a Transmitter or Receiver) that doesn't have a frequency set will create a new frequency and set both the Linker and the wireless block to that new frequency.

Using a Linker that is set to a frequency on any wireless blocks will set the wireless block to that frequency.

Using a blank Linker on a wireless block with a set frequency will set the Linker to the wireless block's frequency.

Sneak+Right Clicking with a Linker in your hand will clear the Linker's frequency.


### Additional Info
Right Clicking on a wireless block with an empty hand will tell you the frequency it is set to.

Linkers have a tooltip stating their frequency and providing usage info in game.

### Thanks to
Translations: mindy15963
