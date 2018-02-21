SoundSlice
===================================

SoundSlice is a library which reads audio files of conversations between people and identifies switches in speakers. This library aims to flag speaker switches and splits the audio file, creating new folders containing the .wav files for the parts for each of the two different speakers (one containing all the parts of each speaker with brief pauses in between each new snippet of conversation).

How it Works
-------

The user is first prompted to input the name of which audio file to process as a String, and as an output, an ArrayList called times is generated containing the times in which the speakers in the audio file switch. In order to generate this array, the software finds the points of time in which there is little amplitude in the file (signifying that the speaker is not talking) and stores them as new values in times. Splitter.java then parses times, cutting apart the first audio file into many different .wav files at each time in times.  

Supported Formats
-------

Currently, as the software is still in it's primary phases, the only supported audio format is the .wav format.

Other formats that will be added as the project progresses include:

- .mp3
- .midi

License
--------

This software is certified under the Apache License, Version 2.0. Refer to the [license file](https://github.com/naluconcepcion/audio-splice/blob/master/LICENSE.md) for details.

Contributing
--------

Please follow the [code of conduct](https://github.com/naluconcepcion/audio-splice/blob/master/CODE_OF_CONDUCT.md) to contribute.
