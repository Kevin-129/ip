# CowPay 🐮

> **"Moove" your productivity to the next level.**

CowPay is a simple task manager that helps students manage their tasks with a **user-friendly** and **minimalist** design.

## Quick Start:

1. **Ensure Java is installed**: You need **Java 17** or above on your computer.
2. **Ensure JavaFX is installed**: You need **JavaFX 17.0.18** installed to run the graphical interface.
3. **Download CowPay**: Get the latest `.jar` file from [here](https://github.com/Kevin-129/ip).
4. **Place the file**: Move the `cowpay.jar` into an empty folder (this folder will store your task data).
5. **Launch the App**:
    * **Option A (The Easy Way):** Simply **double-click** the `cowpay.jar` file.
    * **Option B (The Fail-Proof Way):** If double-clicking doesn't work, open your terminal (Command Prompt/Terminal), `cd` into the folder, and run:  
        `java -jar cowpay.jar`
6. **Start Typing**: Once the GUI appears, type a command (like `todo read book`) and press **Enter**!

## Features: 

### 1. Managing Tasks:
You can add three types of tasks. Each task is added to your list.

- 📝 **Todo:** `todo [description]` — Adds a basic task without a specific date.
- ⏰ **Deadline:** `deadline [description] /by [date]` — Adds a task that needs to be done before a specific time.
- 📅 **Event:** `event [description] /from [start] /to [end]` — Adds a task with a specific duration.

### 2. Organizing the List:
Once you have added tasks, use these commands to keep track:
- `list`: View all your current tasks in the system.
- `mark [index]`: Marks the task at the specified number as **Done**.
- `unmark [index]`: Reverts a task back to **Not Done**.
- `delete [index]`: Permanently removes the task from your storage.

### 3. Smart Tools:
- `find [keyword]`: Searches for tasks containing the specific keyword.
- `remind`: A special feature that displays all unfinished deadlines and events.

## Usage Examples:

Here is a sample interaction showing how a typical student might use `CowPay`:
1. `todo Read Chapter 1 of CS2103`
2. `deadline Submit Project /by 25/2/2028 2359`
3. `event Career Fair /from 25/2/2029 2359 /to 25/2/2030 2359`
4. `list`
5. `mark 1`
6. `delete 3`

## Code Example:
Here’s a simple code snippet that showcases all the commands of the app:

```java
public class CowPay {

    private static final String COMMANDS = "todo, deadline, event, list, remind, find, mark, unmark, delete";

    //Other fields and methods here...
}
```

## Next Steps:

- [ ] Download the `cowpay.jar` file from [GitHub](https://github.com/Kevin-129/ip)
- [ ] Try adding a task using the todo command
- [ ] Set a deadline using the deadline command
- [ ] Use the remind command to view all tasks

*Tip*: Use the `remind` command to quickly view all deadlines and events.