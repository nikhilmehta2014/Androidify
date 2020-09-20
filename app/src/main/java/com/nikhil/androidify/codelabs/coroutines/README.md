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

## Coroutines in Kotlin

On Android, it's essential to avoid blocking the main thread. 
The main thread is a single thread that handles all updates to the UI. 
It's also the thread that calls all click handlers and other UI callbacks. 
As such, it has to run smoothly to guarantee a great user experience.

For your app to display to the user without any visible pauses, the main thread has to update the screen [every 16ms or more](https://medium.com/androiddevelopers/exceed-the-android-speed-limit-b73a0692abc1), 
which is about 60 frames per second. 
Many common tasks take longer than this, such as parsing large JSON datasets, writing data to a database, or fetching data from the network. 
Therefore, calling code like this from the main thread can cause the app to pause, stutter, or even freeze. 
And if you block the main thread for too long, the app may even crash and present an `Application Not Responding` dialog.

### The callback pattern

One pattern for performing long-running tasks without blocking the main thread is callbacks. 
By using callbacks, you can start long-running tasks on a background thread. 
When the task completes, the callback is called to inform you of the result on the main thread.

Take a look at an example of the callback pattern.
```kotlin
// Slow request with callbacks
@UiThread
fun makeNetworkRequest() {
    // The slow network request runs on another thread
    slowFetch { result ->
        // When the result is ready, this callback will get the result
        show(result)
    }
    // makeNetworkRequest() exits after calling slowFetch without waiting for the result
}
```

Because this code is annotated with `@UiThread`, it must run fast enough to execute on the main thread. 
That means, it needs to return very quickly, so that the next screen update is not delayed. 
However, since `slowFetch` will take seconds or even minutes to complete, the main thread can't wait for the result. 
The `show(result)` callback allows `slowFetch` to run on a background thread and return the result when it's ready.

### Using coroutines to remove callbacks

Callbacks are a great pattern, however they have a few drawbacks. 
Code that heavily uses callbacks can become hard to read and harder to reason about. 
In addition, callbacks don't allow the use of some language features, such as exceptions.

Kotlin coroutines let you convert callback-based code to sequential code. 
Code written sequentially is typically easier to read, and can even use language features such as exceptions.

In the end, they do the exact same thing: wait until a result is available from a long-running task and continue execution. 
However, in code they look very different.

The keyword `suspend` is Kotlin's way of marking a function, or function type, **available to coroutines**. 
When a coroutine calls a function marked `suspend`, instead of blocking until that function returns like a normal function call, 
it **suspends** execution until the result is ready then it **resumes** where it left off with the result. 
While it's suspended waiting for a result, **it unblocks the thread that it's running on** so other functions or coroutines can run.

For example in the code below, `makeNetworkRequest`() and `slowFetch`() are both `suspend` functions.
```kotlin
// Slow request with coroutines
@UiThread
suspend fun makeNetworkRequest() {
    // slowFetch is another suspend function so instead of 
    // blocking the main thread  makeNetworkRequest will `suspend` until the result is 
    // ready
    val result = slowFetch()
    // continue to execute after the result is ready
    show(result)
}

// slowFetch is main-safe using coroutines
suspend fun slowFetch(): SlowResult { ... }
```

Just like with the callback version, `makeNetworkRequest` must return from the main thread right away because it's marked `@UiThread`. 
This means that usually it could not call blocking methods like `slowFetch`. Here's where the `suspend` keyword works its magic.

> **Important**: 
>
>The `suspend` keyword doesn't specify the thread code runs on. 
>
>Suspend functions <ins>may run on a background thread or the main thread</ins>.

Compared to callback-based code, coroutine code accomplishes the same result of unblocking the current thread with less code. 
Due to its sequential style, it's easy to **chain several long running tasks** without creating multiple callbacks. 
For example, code that <ins>fetches a result from two network endpoints</ins> and saves it to the database can be written as a function in coroutines with no callbacks. 
Like so:
```kotlin
// Request data from network and save it to database with coroutines

// Because of the @WorkerThread, this function cannot be called on the
// main thread without causing an error.
@WorkerThread
suspend fun makeNetworkRequest() {
    // slowFetch and anotherFetch are `suspend` functions
    val slow = slowFetch()
    val another = anotherFetch()
    // save is a regular function and will block this thread
    database.save(slow, another)
}

// slowFetch is main-safe using coroutines
suspend fun slowFetch(): SlowResult { ... }
// anotherFetch is main-safe using coroutines
suspend fun anotherFetch(): AnotherResult { ... }
```

> **Coroutines by another name**
>
>The pattern of `async` and `await` in other languages is based on coroutines. 
>If you're familiar with this pattern, the `suspend` keyword is similar to `async`. 
>However in Kotlin, `await`() is implicit when calling a `suspend` function.
>
>Kotlin has a method `Deferred.await`() that is used to wait for the result from a coroutine started with the `async` builder.


### Moving from callbacks to coroutines

It's a good idea to understand what each part of the architecture is responsible for before we switch them to using coroutines.
1. `BasicCoroutinesDatabase` implements a database using Room that saves and loads a `Title`.
2. `BasicCoroutinesNetwork` implements a network API that fetches a new title. 
It uses `Retrofit` to fetch titles. Retrofit is configured to randomly return errors or mock data, but otherwise behaves as if it's making real network requests.
3. `TitleRepository` implements a single API for fetching or refreshing the title by combining data from the network and database.
4. `BasicCoroutinesViewModel` represents the screen's state and handles events. It will tell the repository to refresh the title when the user taps on the screen.

Since the network request is driven by UI-events and we want to start a coroutine based on them, the natural place to start using coroutines is in the `ViewModel`.

Let's step through the function `refreshTitle` in `BasicCoroutinesViewModel`:
```kotlin
viewModelScope.launch {
```
Just like the coroutine to update the tap count, begin by launching a new coroutine in `viewModelScope`. 
This will use `Dispatchers.Main` which is OK. 
Even though `refreshTitle` will make a network request and database query it can use coroutines to expose a **main-safe** interface. 
This means it'll be safe to call it from the main thread.

Because we're using `viewModelScope`, when the user moves away from this screen the work started by this coroutine will automatically be cancelled. 
That means it won't make extra network requests or database queries.

> When creating a coroutine from a non-coroutine, start with [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html).
>
>That way, if they throw an uncaught exception it'll automatically be propagated to uncaught exception handlers (which by default crash the app). 
>A coroutine started with `async` won't throw an exception to its caller until you call `await`. 
>However, you can only call `await` from inside a coroutine, since it is a `suspend` function.
>
>Once inside a coroutine, you can use `launch` or `async` to start child coroutines. 
>Use `launch` for when you don't have a result to return, and `async` when you do.

The next few lines of code actually call `refreshTitle` in the `repository`.
```kotlin
try {
    _spinner.value = true
    repository.refreshTitle()
}
```
Before this coroutine does anything it starts the loading spinner – then it calls `refreshTitle` just like a regular function. 
However, since refreshTitle is a `suspending` function, it executes differently than a normal function.

We don't have to pass a callback. 
The coroutine will suspend until it is resumed by `refreshTitle`. 
While it looks just like a regular blocking function call, it will automatically wait 
until the network and database query are complete before resuming **without blocking the main thread**.

```kotlin
} catch (error: TitleRefreshError) {
    _snackBar.value = error.message
} finally {
    _spinner.value = false
}
```
Exceptions in `suspend` functions work just like errors in regular functions. 
If you throw an error in a suspend function, it will be thrown to the caller. 
So even though they execute quite differently, you can use regular try/catch blocks to handle them. 
This is useful because it lets you rely on the built-in language support for error handling instead of building custom error handling for every callback.

And, if you throw an exception out of a coroutine – that coroutine will cancel it's parent by default. 
That means it's **easy to cancel several related tasks together**.

And then, in a finally block, we can make sure that the spinner is always turned off after the query runs.

> **What happens to uncaught exceptions**
>
> Uncaught exceptions in a coroutine are similar to uncaught exceptions in non-coroutine code. 
>By default, they'll cancel the coroutine's `Job`, and notify parent coroutines that they should cancel themselves. 
>If no coroutine handles the exception, it will eventually be passed to an uncaught exception handler on the `CoroutineScope`.
>
> By default, uncaught exceptions will be sent to the thread's uncaught exception handler on the JVM. 
> You can customize this behavior by providing a [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html).
 
## Making main-safe functions from blocking code

Existing callback code in refreshTitle inside `TitleRepository`:
```kotlin
fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
   // This request will be run on a background thread by retrofit
   BACKGROUND.submit {
       try {
           // Make network request using a blocking call
           val result = network.fetchNextTitle().execute()
           if (result.isSuccessful) {
               // Save it to database
               titleDao.insertTitle(Title(result.body()!!))
               // Inform the caller the refresh is completed
               titleRefreshCallback.onCompleted()
           } else {
               // If it's not successful, inform the callback of the error
               titleRefreshCallback.onError(
                       TitleRefreshError("Unable to refresh title", null))
           }
       } catch (cause: Throwable) {
           // If anything throws an exception, inform the caller
           titleRefreshCallback.onError(
                   TitleRefreshError("Unable to refresh title", cause))
       }
   }
}
```

In `TitleRepository.kt` the method `refreshTitleWithCallbacks` is implemented with a callback to communicate the loading and error state to the caller.

This function does quite a few things in order to implement the refresh.

1. Switch to another thread with `BACKGROUND` `ExecutorService`
2. Run the `fetchNextTitle` network request using the blocking `execute`() method. 
This will run the network request in the current thread, in this case one of the threads in `BACKGROUND`.
3. If the result is successful, save it to the database with blocking `insertTitle` and call the `onCompleted`() method.
4. If the result was not successful, or there is an exception, call the onError method to tell the caller about the failed refresh.

This callback based implementation is **main-safe** because it <ins>won't block the main thread</ins>. 
But, it has to use a callback to inform the caller when the work completes. 
It also calls the callbacks on the BACKGROUND thread that it switched too.

### Calling blocking calls from coroutines

Without introducing coroutines to the network or database, we can make this code **main-safe** using coroutines. 
This will let us get rid of the callback and allow us to pass the result back to the thread that initially called it.

You can use this pattern anytime you need to do blocking or CPU intensive work from inside a coroutine 
such as sorting and filtering a large list or reading from disk.

>This pattern should be used for integrating with blocking APIs in your code or performing CPU intensive work. 
>When possible, it's better to use regular suspend functions from libraries like Room or Retrofit.

To switch between any dispatcher, coroutines uses [`withContext`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html). 
Calling `withContext` switches to the other dispatcher just for the lambda then comes back to the dispatcher that called it with the result of that lambda.

By default, Kotlin coroutines provides three Dispatchers: `Main`, `IO`, and `Default`. 
The `IO` dispatcher is optimized for IO work like reading from the network or disk, 
while the `Default` dispatcher is optimized for CPU intensive tasks.

## Replace callback with Coroutines

This code still uses **blocking** calls.
Calling `execute`() and `insertTitle`(...) will both block the thread that this coroutine is running in. 
However, by switching to `Dispatchers.IO` using `withContext`, we're blocking one of the threads in the IO dispatcher. 
The coroutine that called this, possibly running on `Dispatchers.Main`, will be `suspended` until the `withContext` lambda is complete.

Compared to the callback version, there are two important differences:

1. `withContext` returns it's result back to the Dispatcher that called it, in this case `Dispatchers.Main`. 
The callback version called the callbacks on a thread in the `BACKGROUND` executor service.

2. The caller doesn't have to pass a callback to this function. They can rely on suspend and resume to get the result or error.

> Advanced tip
>
> This code doesn't support coroutine cancellation, but it can! 
> [**Coroutine cancellation is cooperative**](https://kotlinlang.org/docs/reference/coroutines/cancellation-and-timeouts.html). 
> That means your code needs to check for cancellation explicitly, which happens for you whenever you call the functions in kotlinx-coroutines.
>
> Because this `withContext` block only calls blocking calls it will not be cancelled until it returns from `withContext`.
>
> To fix this, you can call [yield](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/yield.html) 
> regularly to give other coroutines a chance run and check for cancellation. 
> Here you would add a call to `yield` between the network request and the database query. 
> Then, if the coroutine is cancelled during the network request, it won't save the result to the database.  
>
> You can also check cancellation explicitly, which you should do when making low-level coroutine interfaces.
  
### Coroutines in Room & Retrofit

> Both Room and Retrofit make suspending functions **main-safe**.
>
> It's **safe to call** these suspend funs from [Dispatchers.Main], even though they fetch from the network and write to the database.


> You do not need to use `withContext` to call **main-safe** suspending functions. 
> 
> By convention, you should ensure that `suspend` functions written in your application are **main-safe**. 
> That way it is safe to call them from any dispatcher, even `Dispatchers.Main`.
  


