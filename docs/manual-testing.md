# E.V. Manual Testing

Every expected output below was produced by running the current build, not written
from memory. Type the inputs in order within a section. Unless a section says
otherwise, start it from a **fresh empty folder** so `data/ev.txt` does not carry
state between sections.

```bash
cd ~/Documents/GitHub/cs2103t/ip
export JAVA_HOME=~/.sdkman/candidates/java/25.0.3.fx-zulu
./gradlew run
```

To reset between sections, close E.V. and delete `data/ev.txt`.

---

## 1. Launch and greeting

**Do:** launch E.V. from a folder with no `data/` in it.

**Expect:**

```
E.V. online.
Suit systems nominal, Spidey.
What's on the list for the friendly neighbourhood spiderman today?
```

Also check: window opens dark with the wallpaper behind the conversation, E.V.'s
J.A.R.V.I.S. avatar on the left, input bar reads `Type a command, or list`. No
`data/` folder is created yet, because nothing has changed the registry.

---

## 2. Adding all three task types

| Input |
|---|
| `todo read book` |
| `deadline submit ip /by 2026-09-18 2359` |
| `event demo /from 2026-09-18 1400 /to 2026-09-18 1500` |
| `list` |

**Expect:**

```
Task logged:
  [T][ ] read book
Registry holds 1 task.
Task logged:
  [D][ ] submit ip (by: Sep 18 2026, 11:59PM)
Registry holds 2 tasks.
Task logged:
  [E][ ] demo (from: Sep 18 2026, 2:00PM to: Sep 18 2026, 3:00PM)
Registry holds 3 tasks.
Current task registry:
1.[T][ ] read book
2.[D][ ] submit ip (by: Sep 18 2026, 11:59PM)
3.[E][ ] demo (from: Sep 18 2026, 2:00PM to: Sep 18 2026, 3:00PM)
```

Check singular "1 task" vs plural "2 tasks", and that input dates in
`yyyy-MM-dd HHmm` are displayed as `MMM d yyyy, h:mma`.

---

## 3. Marking and unmarking

| Input |
|---|
| `todo alpha` |
| `todo beta` |
| `mark 2` |
| `list` |
| `unmark 2` |
| `list` |

**Expect:**

```
Task completed.
[T][X] beta
Current task registry:
1.[T][ ] alpha
2.[T][X] beta
Task reopened.
[T][ ] beta
Current task registry:
1.[T][ ] alpha
2.[T][ ] beta
```

---

## 4. Deleting and renumbering

| Input |
|---|
| `todo alpha` |
| `todo beta` |
| `todo gamma` |
| `delete 2` |
| `list` |

**Expect:**

```
Task removed:
  [T][ ] beta
Registry holds 2 tasks.
Current task registry:
1.[T][ ] alpha
2.[T][ ] gamma
```

The point of this one is that `gamma` moves from 3 to 2. Numbers are positions,
not permanent ids.

---

## 5. Finding

| Input |
|---|
| `todo Borrow Book` |
| `todo buy milk` |
| `find book` |
| `find BOOK` |
| `find zzz` |
| `find` |

**Expect:**

```
Matching entries in the registry:
1.[T][ ] Borrow Book
Matching entries in the registry:
1.[T][ ] Borrow Book
Nothing in the registry matches that.
Tell me what to search for.
```

Three things here. Lowercase `book` matches `Borrow Book` and so does uppercase
`BOOK`, so case is ignored in both directions. A search with no match says so
rather than printing a bare header. A `find` with no keyword is refused rather
than returning the whole registry.

---

## 6. Sorting

| Input |
|---|
| `todo apple` |
| `todo Zebra` |
| `todo banana` |
| `sort` |
| `list` |

**Expect:**

```
Registry sorted by description:
1.[T][ ] apple
2.[T][ ] banana
3.[T][ ] Zebra
Current task registry:
1.[T][ ] apple
2.[T][ ] Zebra
3.[T][ ] banana
```

`Zebra` sorts last, not first, so ordering is case-insensitive rather than by
character code. And `list` afterwards still shows insertion order, so `sort`
returns a view without rewriting the registry.

**Known limitation:** `sort` ignores anything typed after it, so `sort buy`
behaves identically to `sort`. Same for `list` and `bye`.

---

## 7. Empty registry

From a fresh folder, with no tasks added.

| Input |
|---|
| `list` |
| `find anything` |
| `sort` |

**Expect:**

```
The registry is empty.
Nothing in the registry matches that.
The registry is empty.
```

---

## 8. Bad task numbers

| Input |
|---|
| `todo alpha` |
| `mark 0` |
| `mark 5` |
| `mark abc` |
| `mark` |
| `delete 99` |

**Expect:**

```
No task with that number.
No task with that number.
Task number must be a number.
Task number must be a number.
No task with that number.
```

Nothing crashes and the registry is unchanged throughout. `mark +1` and
`mark 001` are accepted as 1.

---

## 9. Missing and malformed arguments

| Input |
|---|
| `todo` |
| `deadline` |
| `deadline x` |
| `deadline x /by Sunday` |
| `event x` |
| `event x /from 2026-09-18 1400` |
| `fly` |
| `FLY` |
| *(press Enter on an empty box)* |

**Expect:**

```
A todo needs a description.
A deadline needs a description and a /by time.
A deadline needs a description and a /by time.
Dates must look like 2026-09-18 1800.
An event needs a description and a /from time.
An event needs a /to time.
I'm sorry Spidey, but this command seems to be outside my current scope. Try again.
I'm sorry Spidey, but this command seems to be outside my current scope. Try again.
No command entered.
```

`FLY` failing shows commands are case-sensitive. That is intended, not a bug.

---

## 10. Date validation

| Input | Expected |
|---|---|
| `deadline a /by 2026-02-30 1800` | `Dates must look like 2026-09-18 1800.` |
| `deadline b /by 2026-02-29 1800` | `Dates must look like 2026-09-18 1800.` |
| `deadline c /by 2024-02-29 1800` | **accepted**, `[D][ ] c (by: Feb 29 2024, 6:00PM)` |
| `deadline d /by 2026-09-18 2400` | `Dates must look like 2026-09-18 1800.` |
| `deadline e /by 2026-13-01 1800` | `Dates must look like 2026-09-18 1800.` |
| `deadline f /by 18/09/2026 1800` | `Dates must look like 2026-09-18 1800.` |

The important pair is `b` and `c`. 2026 is not a leap year so 29 February is
refused, while 2024 is, so the same date is accepted. That proves the validation
is real rather than a blanket rejection. Before this was fixed, `2026-02-30`
was silently stored as 28 February.

---

## 11. Description and time-range rules

| Input | Expected |
|---|---|
| `todo alpha \| beta` | `Descriptions cannot contain '\|'. It separates fields in my memory banks.` |
| `event conf /from 2026-09-20 1600 /to 2026-09-20 1400` | `An event cannot end before it starts.` |
| `event ok /from 2026-09-20 1400 /to 2026-09-20 1400` | **accepted**, zero-length events are allowed |

The pipe is refused because it separates fields in `data/ev.txt`. Allowing it
would mean a task saved as `alpha | beta` came back as `alpha` on the next launch.

---

## 12. Persistence across restarts

**Session 1**, from a fresh folder:

| Input |
|---|
| `todo read book` |
| `deadline submit ip /by 2026-09-18 2359` |
| `mark 1` |
| `bye` |

Then in a terminal, `cat data/ev.txt`:

```
T | 1 | read book
D | 0 | submit ip | 2026-09-18 2359
```

The `1` in the first field is the done flag, so marking persisted.

**Session 2**, relaunch from the same folder and type `list`:

```
Current task registry:
1.[T][X] read book
2.[D][ ] submit ip (by: Sep 18 2026, 11:59PM)
```

---

## 13. Damaged data file

Create `data/ev.txt` by hand containing:

```
T | 0 | keep one
X | 0 | CORRUPT
D | 0 | keep two | 2026-09-18 1800
```

Launch and type `list`.

**Expect:**

```
Warning: 1 damaged entry in my memory banks was left out.
Current task registry:
1.[T][ ] keep one
2.[D][ ] keep two (by: Sep 18 2026, 6:00PM)
```

Then `cat data/ev.txt` again. **All three lines must still be there**, including
the corrupt one. `list` does not change the registry, so it does not rewrite the
file. This is the important one: previously a single bad line discarded the whole
file, and the next command saved an empty registry over it.

Now type `todo something` and `cat data/ev.txt` once more. The corrupt line is
gone this time, because a command that changes the registry does rewrite the file.

---

## 14. Read-only data file

```bash
chmod 400 data/ev.txt
```

| Input | Expected |
|---|---|
| `list` | the registry, normally |
| `find x` | search results, normally |
| `todo new` | `I could not write to my memory banks.` |

Only the command that actually writes reports a failure. Restore with
`chmod 600 data/ev.txt`.

---

## 15. GUI-specific behaviour

These cannot be reached from the console, so test them in the window.

| Do | Expect |
|---|---|
| Type ` list` with a leading space | works, same as `list` |
| Type `list ` with a trailing space | works |
| Paste text containing a tab, e.g. `todo→x` | works |
| Press Enter on an empty box | `No command entered.` |
| Drag the window narrow | stops at about 380px wide, nothing clips |
| Drag the window short | stops at about 420px tall |
| Add ten tasks | conversation scrolls, thin cyan scrollbar, auto-scrolls to newest |
| Click into the input box | top edge turns cyan |
| Hover Send, then press it | brightens, then inverts |
| Type `bye` | farewell shows, window closes after about 1.5 seconds |

Also check the columns line up. In `list` output, the `[T][ ]` brackets on every
row should sit directly under each other. If they are ragged, the monospace font
did not resolve.

---

## 16. Exit

| Input | Expected |
|---|---|
| `bye` | `E.V. offline. Swing safe, Spidey.` then the window closes |
