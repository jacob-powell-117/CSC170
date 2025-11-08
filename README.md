# Frog's Feast: A Greenfoot Course Project

## Project Concept: Ecosystem Survival

This game is an attempt to model a basic **survival challenge** within an ecosystem using Greenfoot/Java. You play as the **Frog**, the scavenger/predator, trying to eat enough insects (Flies and Ladybugs) to survive 7 progressively harder levels.

The core idea is **Emergent Complexity**—simple actor rules (like "Spider always turns toward Frog") create a difficult, chaotic, and emergent survival pressure that determines the final game difficulty. It's really just a pathfinding challenge under pressure!

---

## Technical Breakdown

### Key Actors & Movement
| Actor | Focus | What It Does |
| :--- | :--- | :--- |
| **Frog** | Player | Uses WASD/Arrow Keys. **Crucially**, it is blocked by obstacles (Rocks, Plants) via an `isObstacleAt()` check, making the environment solid. |
| **Spider** | Primary Hazard | **Chases the Frog relentlessly.** I made it slow down (Crawl Speed=1) when it bumps into rocks or other actors, making the chase more realistic and less glitchy. |
| **Fly** | High Reward (+8) | **Fast and totally erratic.** It changes direction randomly a lot (15% chance per cycle) to make it a difficult target to catch. |
| **Ladybug** | Low Reward (+2) | **Slow but evasive.** It has a low base speed but is coded to actively turn away from *any* other nearby actor, meaning you have to pin it down to eat it. |

### Level Scaling
The game scales across 7 levels, using a tiered approach to prevent overcrowding. I found the pure linear scale was way too much, so I throttled it:

* **Environmentals & Food:** Density roughly **doubles every two levels**. This controls congestion without making the map instantly unplayable.
* **Spiders:** Increases by **one spider every two levels** (up to 4 spiders total on the final level).

---

## Repository Contents

* **`/Code`:** Contains the entire Greenfoot scenario folder (all Java source files, images, sounds, etc.).
* **`/Documentation`:** Contains the formal write-up (PDF) and the UML/Flowchart diagrams (PDF/PNG).

---

## Getting Started
1.  Clone the repository.
2.  Open the Greenfoot application.
3.  Load the scenario from the `/Code` folder.
4.  Hit **Run** and try to reach Level 7!
