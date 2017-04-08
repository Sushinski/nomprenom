# nomprenom
This projects helps parents to find name for newborn child (or anyone can use it to find some interesting names).
It was created to learn and understand basic Android development conceptions: Manifest, Activities, Fragments, Adapters,
Resources, Database Connection, Localization, etc. Also it uses some third-party libraries spreaded widely in modern
development: ActiveAndroid, Retrofit, RxJava.
Application uses SQLite database to store & filter data. As addition, it has update feature uses rest service to get 
new and edited names from remote database server (based on django rest framework, server project you can get here:
https://github.com/Sushinski/npnbase). Update starts when application launches and uses working thread for database interaction.

As main idea, application has 3 features: 
1) search for name with desired gender, zodiacal sign and region and check compatibility with patronymic; 
2) add new name as addition to built-in; 
3) share preferred names with others usingmessages or social networks.

For now, application has only russian-based names.

Google Play application page: https://play.google.com/store/apps/details?id=com.nomprenom2 
