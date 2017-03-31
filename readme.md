# Android Agilie Internship

TODO: add description here

### Project description

TODO: add description here

### Screenshots

<img src="/images/1page-1-sign-in.png" width="24%"> <img src="/images/2page-1-sign-up.png" width="24%"> <img src="/images/3page-1-chats-menu.png" width="24%"> <img src="/images/4page-1-chats-error.png" width="24%">
<img src="/images/5page-1-drawer.png" width="24%"> <img src="/images/6page-1-create-chat.png" width="24%"> <img src="/images/7page-1-invite-friends.png" width="24%"> <img src="/images/8page-1-friends-copy.png" width="24%">


### Project Requirements

- OS: Android (min API 19)
- Device types: mobile only
- Orientation support: portrait + landscape
- domain: \<your company name>.internship.com
- localization: en

### Used API (QuickBlox)

Backend is provided as [QuickBlox](https://quickblox.com) service. 

* QuickBlox Admin panel:
https://admin.quickblox.com

* Backend API documentation: 
http://quickblox.com/developers/Overview

* Error codes and rate limits API documentation:
http://quickblox.com/developers/Errors

* Authentication and Authorization API documentation:
http://quickblox.com/developers/Authentication_and_Authorization

* Users API documentation:
http://quickblox.com/developers/Users

* Chat API documentation:
http://quickblox.com/developers/Chat

* Content API documentation:
http://quickblox.com/developers/Content

* Push notifications API documentation:
http://quickblox.com/developers/Messages

### Functional requirements

If you’re interested on getting the requirements to the project, feel free to send an email to info@agilie.com

### Design requirements

Application’s UI should be implemented based on Material Design principles. Next points should be taken into consideration during app’s design implementation:
* use Material Theme
* use native Android SDK’s UI widgets
* ripple effect should be implemented for all clickable elements (rows, buttons, clickable texts etc) for Android 5.0+
selected/unselected/enabled/disabled states should be implemented for all clickable elements for Android with version lower than 5.0
* use animations for changing UI elements’ properties (i. e. show/hide, move). Switching between screens should be also animated.
* User should be notified in case of background processes (network, db operations). Use loading indicators. Do not block UI with loading elements, User should always have possibility to navigate the app 
* User should be notified in case of any kind of errors occur
* Use Crouton and Toast UI Widgets

### Architecture specs

All links you can find in the end of this paragraph.

In this project our team adhere to Clean Architecture principles. Its mean that we make 3 layers which performed their specific task with some adds.

Presentation:
Here we make base MVP pattern, which allow to us separate view elements, from the logic and retrieving data.
In View, we contain only View elements, lists, specific Android parts which need to be displayed to user. Also we have reference on Presenter class.
In Model we implemented retrieving of data with specific methods which needs Context for perform.
In Presenter we contain references on Model, View and UseCase classes here we implements all logic that needs for particullar View.

Domain:
In this layer we have UseCase classes, every particular class implements one event, that allows to us plan architecture and add every feature without any conflict.

Data:
In this layer we implemented retrieving data from net, database, cache, or another source. Also, in this layer we implemented choosing of source. For example if we have no any information in database, we retrieve information from net.

Clean Architecture: 
https://github.com/android10/Android-CleanArchitecture

MVP:
https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter

### Used Technologies

* Retrofit 2
https://github.com/square/retrofit
* Okhttp
https://github.com/square/okhttp
* RxAndroid
https://github.com/ReactiveX/RxAndroid
* Smack
https://github.com/igniterealtime/Smack
* Moxy
https://github.com/Arello-Mobile/Moxy
* Google-Cloud-Messaging
https://github.com/google/gcm
* EventBus
https://github.com/greenrobot/EventBus
* Swipe layout
https://github.com/daimajia/AndroidSwipeLayout
* GreenDAO
https://github.com/greenrobot/greenDAO
* SqlCipher
https://github.com/sqlcipher/android-database-sqlcipher
* Mockito
https://github.com/mockito/mockito
* Powermock
https://github.com/powermock/powermock





### License

[Apache-2.0](https://github.com/ukevgen/BizareChat/blob/master/LICENSE.txt)
