# Truecaller Android Assignment App
It is an app that should grab content from truecaller [web](https://truecaller.blog/2018/01/22/life-as-an-android-engineer/) then find the 10th character, every 10th character, and count every unique word then display the results on the screen simultaneously. The app contains a single activity (ContentActivity) with a TextView for each request to display the results and a single Button to run these requests simultaneously.

## Installation:
1. Android Gradle Version: 7.0.2
2. Gradle Version 7.0.2

## Libraries Used:
1. [Retrofit](https://square.github.io/retrofit/) for network calls in order to make a request to the web
2. [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiAm7OMBhAQEiwArvGi3O2UO1RFs9b3QR1KQC6IgSbE7-n55kW_8T-34wAuSWgK_7OOSoenPxoC1e4QAvD_BwE&gclsrc=aw.ds) to make the requests run simultaneously without blocking any thread.
3. [Android Architecture](https://developer.android.com/topic/architecture) components viewmodel and livedata for using modern design practices and MVVM architecture.

## Usage:
### 1. Truecaller10thCharacterRequest:
After grabbing the content, convert the content text to char array and return the 10th character as follow: 

```
        if (content.length < 10) {
            failureLiveData.postValue(Unit)
            return ""
        }
        return content.toCharArray()[10].toString()
 ```
 ### 2. TruecallerEvery10thCharacterRequest:
Convert the content to char array then loop over it using while loop to find every 10th character and save it into array until the condition breaks then return a text containing the saved array, below is the code snippet
 ```
 if (text.isEmpty()) return ""
        var index = 0
        val array = arrayListOf<String>()
        while (index + iterations < text.toCharArray().size) {
            index += iterations
            array.add(text.toCharArray()[index] + "")
        }
        return array.toString()
```
### 3. TruecallerWordCounterRequest:
Split the content into words using regex with delimeter ** "[\\s,\r\n]+" ** then loop over the words and find the occurrence count for each unique word and return the results:
```
 var text = ""
        val array = st.split("[\\s,\r\n]+".toRegex())
        if (array.isNullOrEmpty()) return text
        val occurrences = hashMapOf<String, Int>()
        array.forEach {
            val t = it.lowercase(Locale.getDefault())
            var count = occurrences[t]
            if (count == null) count = 0
            occurrences[t] = count + 1
        }

        occurrences.forEach { (t, u) ->
            text += "$t (" + String.format(
                mContext.resources.getString(R.string.occurrence_count_text),
                u
            ) + ")\n"
        }
        return text
```
### 4. Running Above Requests using Coroutines:
```
 viewModelScope.launch(Dispatchers.Default) {
            val wordCounterRequest = async { truecallerWordCounterRequest(content) }
            val tenthCharRequest = async { truecaller10thCharacterRequest() }
            everyTenthCharLiveData.postValue(tenthCharRequest.await())
            val everyTenthCharRequest =
                async { truecallerEvery10thCharacterRequest(text = content) }
            wordCounterLiveData.postValue(everyTenthCharRequest.await())
            tenthCharLiveData.postValue(wordCounterRequest.await())

        }
```


