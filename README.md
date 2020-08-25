# GDPRCheckerV2

REQUIRED :     
Simple way to use new GDPR for admob


Getting Started
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			
			maven { url 'https://www.jitpack.io' }
		}
	}
Step 2. Add the dependency
Add this tow lines to your Module dependency


dependencies {
		implementation 'com.github.fandofastest:GDPRCheckerV2:1.0.0'
		implementation 'com.google.android.ump:user-messaging-platform:1.0.0'
	}
	
	
	
How To use
        setContentView(R.layout.activity_main);
        
              new GDPRChecker()
                        .withContext(getApplicationContext())
                        .withActivity(MainActivity.this)
                        .withAppId("your admob appid")
                        .withDebug()
                        .check();
    
    
