# 484TeamBobsBullies
Kenzo Banaag
Tim Borisenko
Dane Erosa
Rebecca Rothchild
Isaac Schultz

# Team Meetings and Records
* [Shared Google Drive Folder](https://drive.google.com/drive/folders/1EVjfKT3Mp5qEZ30nHGDYmVxo4dSZ5dGO?usp=sharing)


# How to use: 
Add TextToSpeechClass.java to your project
Add the class to manifest 

import statement
`object = new TextToSpeechClass(getApplicationContext(), "Opening message");`

Speak function
`object.speak("message")`

Still working on bugs with speechtotext, main issue is that both classes run 
    at the same time so the speech to text picks up text to speech voice commands
    when not wearing headphones. 
    
If you import the whole project the speechtotext.java is not working yet.
Also you should wear headphones

Valid commands: 
Go to
Take me
Room
Yes
No
Set up
Commands
Stop