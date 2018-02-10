SoundSlice
===================================

SoundSlice is a library which reads audio files of conversations between people and identifies switches in speakers. This library aims to flag speaker switches and splits the audio file into two different speakers (one containing all the parts of each speaker). The library will also include a visualization software which will show the sound wave of the file, as well as the flags when the primary speaker swithes. 

How it works?
-------

The user is first prompted for an audio file, and as an output, an array will be outputted containing the times in which the speakers in the audio file switch. In order to do so, the library finds the points of time in which there is little amplitude in the file (signifying the speaker not talking). The array will then be used to display the points of the sounds waze where these changes occur. 

Supported Formats
-------

Currently, as the software is still in it's primary phases, the only supported audio format is the .wav format.

Other formats that will woon be added include:

- .mp3
- .midi

License
--------

This software is certified under the Apache License, Version 2.0. Refer to the [license file](https://github.com/naluconcepcion/audio-splice/blob/master/LICENSE.md) for details.

Contributing
--------

Please follow the [code of conduct](https://github.com/naluconcepcion/audio-splice/blob/master/CODE_OF_CONDUCT.md) to contribute. 
