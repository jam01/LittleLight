# LittleLight
The idea for Little Light came about in 2015 when I was throwing hours at Bungie's Destiny while I waited for some personal matters to be resolved. At that moment there was no easy way to transfer items from character to character. One had to change characters, travel to the tower, and return to the character where you wanted the item. I figured there had to be a better way.
I had done Android dev work before and was still learning some things, so I decided to dig around and see if there was anything to be done about this missing piece. I found Bungie's API and the rest... well it's in this repo.

## Architecture and Design
Little Light's current state is influenced by a few architectural and design practices. Namely Domain Driven Design, Clean Architecture, Ports and Adapters, and Model-View-Presenter. I found all these to be compatible, and they became great tools to create a coherent and flexible application.

### Ports and Adapters
This approach allowed me to separate all the code that would be only specific to the Android application while leaving the core functionality to be reused if there ever was the possibility/opportunity to create another adapter ie. iOS or Desktop app. Actually iOS adpater was my intent when I learned about Intel's [Multi-OS Engine], though I never realized that idea.

### MVP
If you talk to any Android developer you have to mention MVP. In my effort to understand Model-View-Presenter I dug through a few different resources:
* Potel, Mike. "MVP: Model-View-Presenter the Taligent programming model for C++ and Java." Taligent Inc (1996): 20.
* Bower, Andy, and Blair McGlashan. "Twisting the triad." Tutorial Paper for European Smalltalk User Group (ESUP) (2000).
* Greer, Derek. "Interactive application architecture patterns." Aspiringcraftsman. com (2007).

To me, the moral of the story is that no one has the clear cut notion of MVP, because there isn't one. As long as you're separating your UI code from your Domain logic I think you'll be alright.

## Popular libraries
In this repo you'll find some popular libraries like: Retrofit 2, Picasso, Dagger, rxJava 2, and gson.

### rxJava
On an earlier iteration of LittleLight I tried to implement something similar to Fernando Cejas' [clean arch example] in which you see reactive/observable pattern from the Presenter all the way to the Repository. After spending a considerable amount of time going through the Rx dcoumentation for map, flatMap, zip, backpressure, etc (which might be the best docs I've ever used), and even finding and reporting a bug about replay, I realized that I was fighting the language and I was using rx just because it was popular. I now only use rx for propagating domain events to the UI, which I think is a perfect fit. Nonetheless, rx is a great library and was a great learning experience. The reactive pattern is being adopted in many places and I'd recommend anyone to take a look at it.

### Dagger
At the time I started looking to introduce Dagger I was also learning about the Spring framework for other projects. Perhaps looking at both is what allowed me to wrap my head around Dependency Injection and how to put it to use. You'll see that my use of Dagger is a little more explicit than most examples, I feel this keeps the [magic at bay].

## Google Play Store report and listing
As these images suggest, the app had rapid adoption and subsequent rapid decline. The folks over at [r/DestinyTheGame] were the reason the app became popular in a short amount of time. Eventually I had less and less time to keep the app up to date or even play the game at all. The app became stale as Bungie added new content to the game and other apps became more popular and useful (Ishtar Commander is probably my favorite), alas here is a graphic depiction of Little Light's up and down journey.

![Report][report]
![Listing][listing]

[Multi-OS Engine]: https://software.intel.com/en-us/multi-os-engine
[r/DestinyTheGame]: https://www.reddit.com/r/DestinyTheGame/
[clean arch example]: https://github.com/android10/Android-CleanArchitecture
[magic at bay]: https://8thlight.com/blog/uncle-bob/2015/08/06/let-the-magic-die.html
[report]: readmeFiles/report.png  "Report"
[listing]: readmeFiles/listing.png  "Listing"

