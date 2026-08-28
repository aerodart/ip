# E.V. User Guide

E.V. is a Spiderman-themed command-line assistant that tracks your tasks and
remembers them between sessions.

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

Lists every task whose text contains the keyword.

```
find book
```

```
Matching entries in the registry:
1.[T][ ] borrow book
2.[D][X] return book (by: Sep 18 2026, 6:00PM)
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

Tasks are saved to `data/ev.txt` after every command and reloaded on startup,
so nothing needs to be saved by hand.
