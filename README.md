# Truecaller Android Assignment App
It is an app that should grab content from truecaller [web](https://truecaller.blog/2018/01/22/life-as-an-android-engineer/) then find the 10th character, every 10th character, and count every unique word then display the results on the screen simultaneously. The app contains a single activity (ContentActivity) with a TextView for each request to display the results and a single Button to run these requests simultaneously.

## Installation:
1. Android Gradle Version: 7.0.2
2. Gradle Version 7.0.2

## Libraries Used:
1. [Retrofit](https://square.github.io/retrofit/) for network calls in order to make a request to the web
2. [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiAm7OMBhAQEiwArvGi3O2UO1RFs9b3QR1KQC6IgSbE7-n55kW_8T-34wAuSWgK_7OOSoenPxoC1e4QAvD_BwE&gclsrc=aw.ds) to make the requests run simultaneously without blocking any thread.
3. [Android Architecture](https://developer.android.com/topic/architecture) components viewmodel and livedata for using modern design practices and MVVM architecture.

## Usage
### 1. Truecaller10thCharacterRequest
After grabbing the content, convert the content text to char array and return the 10th index as follow: 

```
        if (content.length < 10) {
            failureLiveData.postValue(Unit)
            return ""
        }
        return content.toCharArray()[10].toString()
 ```

