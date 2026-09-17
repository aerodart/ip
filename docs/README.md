# E.V. User Guide

E.V. is a desktop assistant that tracks your tasks and remembers them between
sessions. You talk to it by typing commands into a chat window.

![E.V. in action](Ui.png)

## Quick start

1. Check you have Java 25 installed by running `java -version`.
2. Download `ev.jar` from the [latest release](https://github.com/aerodart/ip/releases).
3. Put the jar in an empty folder, open a terminal in that folder, and run:

```
java -jar ev.jar
```

E.V. creates `data/ev.txt` next to the jar to remember your tasks, so launch it
from the same folder each time.

## Adding a todo

Adds a task with no date attached.

```
todo borrow book
```

```
Task logged:
  [T][ ] borrow book
Registry holds 1 task.
```

## Adding a deadline

Adds a task due at a given date and time. Dates use the format `yyyy-MM-dd HHmm`.

```
deadline return book /by 2026-09-18 1800
```

```
Task logged:
  [D][ ] return book (by: Sep 18 2026, 6:00PM)
Registry holds 2 tasks.
```

## Adding an event

Adds a task that runs between two date-times.

```
event project meeting /from 2026-09-20 1400 /to 2026-09-20 1600
```

```
Task logged:
  [E][ ] project meeting (from: Sep 20 2026, 2:00PM to: Sep 20 2026, 4:00PM)
Registry holds 3 tasks.
```

## Listing tasks

```
list
```

```
Current task registry:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sep 18 2026, 6:00PM)
3.[E][ ] project meeting (from: Sep 20 2026, 2:00PM to: Sep 20 2026, 4:00PM)
```

## Marking a task as done

```
mark 2
```

```
Task completed.
[D][X] return book (by: Sep 18 2026, 6:00PM)
```

Use `unmark 2` to reopen it, which replies with `Task reopened.` instead.

## Finding tasks

Lists every task whose description contains the keyword. Case is ignored, so
`find book` also matches `Borrow Book`. Status icons, type tags and dates are
not searched. The keyword cannot be left out.

```
find book
```

```
Matching entries in the registry:
1.[T][ ] borrow book
2.[D][X] return book (by: Sep 18 2026, 6:00PM)
```

## Sorting tasks

Lists every task ordered alphabetically by description, ignoring case. The
stored order is not changed.

```
sort
```

```
Registry sorted by description:
1.[T][ ] borrow book
2.[E][ ] project meeting (from: Sep 20 2026, 2:00PM to: Sep 20 2026, 4:00PM)
3.[D][X] return book (by: Sep 18 2026, 6:00PM)
```

## Deleting a task

```
delete 1
```

```
Task removed:
  [T][ ] borrow book
Registry holds 2 tasks.
```

## Exiting

```
bye
```

## Saving

Tasks are saved to `data/ev.txt` whenever you change the registry, and reloaded
on startup, so nothing needs to be saved by hand. Commands that only read the
registry, such as `list`, `find` and `sort`, leave the file untouched.

If a line in the data file has been damaged, E.V. skips that line, tells you how
many it left out, and keeps every task it could still read.

## Command summary

| Command | Format |
|---|---|
| Add a todo | `todo DESCRIPTION` |
| Add a deadline | `deadline DESCRIPTION /by yyyy-MM-dd HHmm` |
| Add an event | `event DESCRIPTION /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm` |
| List all tasks | `list` |
| Mark as done | `mark TASK_NUMBER` |
| Mark as not done | `unmark TASK_NUMBER` |
| Delete a task | `delete TASK_NUMBER` |
| Search | `find KEYWORD` |
| Sort by description | `sort` |
| Exit | `bye` |

Two rules apply to every description. Dates must be real, so `2026-02-30 1800`
is rejected rather than quietly moved to February 28. Descriptions cannot
contain `|`, because that character separates fields in the data file.
