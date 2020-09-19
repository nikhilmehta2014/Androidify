Coroutines Basics
---

It is a new way of managing background threads that can simplify code by reducing the need for callbacks. 
Coroutines are a Kotlin feature that converts async callbacks for long-running tasks, such as database or network access, into sequential code.

Here is a code snippet:

```kotlin
networkRequest { result ->
   // Successful network request
   databaseSave(result) { rows ->
     // Result saved
   }
}
```

The callback-based code will be converted to sequential code using coroutines.
```kotlin
// The same code with coroutines
val result = networkRequest()
// Successful network request
databaseSave(result)
// Result saved
```

## What you'll do
* Call code written with coroutines and obtain results.
* Use suspend functions to make async code sequential.
* Use `launch` and `runBlocking` to control how code executes.
* Learn techniques to convert existing APIs to coroutines using `suspendCoroutine`.
* Use coroutines with Architecture Components.
* Learn best practices for testing coroutines.